package zr.mybatis;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

import v.Clearable;
import v.Initializable;
import v.common.helper.StrUtil;
import v.common.unit.Ternary;
import zr.mybatis.annotation.MapperConfig;
import zr.mybatis.info.BeanInfo;
import zr.mybatis.info.BeanInfoMgr;
import zr.mybatis.info.MapperConfigInfo;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ZRSpringMybatisHelper implements ApplicationContextAware, Initializable, Clearable {
	protected final Map<MapperConfigInfo, SimpleMapper> mapperMap;
	protected final BeanInfoMgr infoMgr;
	protected ApplicationContext appContext;
	protected TableNameHandler nameHandler;
	protected boolean ignoreEmpty = false;
	protected boolean insertAsMap = false;

	public ZRSpringMybatisHelper() {
		this.mapperMap = new HashMap<>();
		this.infoMgr = new BeanInfoMgr();
		this.nameHandler = DefTableNameHandler.INSTANCE;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.appContext = applicationContext;
	}

	public boolean isIgnoreEmpty() {
		return ignoreEmpty;
	}

	public void setIgnoreEmpty(boolean ignoreEmpty) {
		this.ignoreEmpty = ignoreEmpty;
	}

	public boolean isInsertAsMap() {
		return insertAsMap;
	}

	public void setInsertAsMap(boolean insertAsMap) {
		this.insertAsMap = insertAsMap;
	}

	public TableNameHandler getNameHandler() {
		return nameHandler;
	}

	public void setNameHandler(TableNameHandler nameHandler) {
		this.nameHandler = nameHandler;
	}

	@PostConstruct
	@Override
	public void init() {
		SqlSessionTemplate defTemplate = appContext.getBean(SqlSessionTemplate.class);
		Map<String, Object> beans = appContext.getBeansWithAnnotation(Repository.class);
		for (Object e : beans.values()) {
			e = Util.getRawObj(e);
			List<MapperField> fields = Util.getMapperFields(e.getClass());
			for (MapperField f : fields) {
				MapperConfigInfo info = buildConfig(f, defTemplate);
				SimpleMapper mapper = getMapper(info);
				try {
					f.field.set(e, mapper);
				} catch (Exception e1) {
				}
			}
		}
		Map<String, SimpleDao> daos = appContext.getBeansOfType(SimpleDao.class);
		for (SimpleDao e : daos.values()) {
			e = (SimpleDao) Util.getRawObj(e);
			MapperConfigInfo info = buildConfig(e.getClass(), defTemplate);
			e.mapper = getMapper(info);
		}
	}

	private MapperConfigInfo buildConfig(MapperField f, SqlSessionTemplate defTemplate) {
		return buildConfig0(Util.getFieldGenericType(f.field), f.config, defTemplate);
	}

	private MapperConfigInfo buildConfig(Class<?> clazz, SqlSessionTemplate defTemplate) {
		Class<?> type = Util.getDaoGenericType(clazz);
		MapperConfig config = Util.getDaoConfig(clazz);
		return buildConfig0(type, config, defTemplate);
	}

	private MapperConfigInfo buildConfig0(Class<?> type, MapperConfig config, SqlSessionTemplate defTemplate) {
		String templateName = config == null ? null : config.template();
		SqlSessionTemplate template = StrUtil.isEmpty(templateName) ? defTemplate
				: appContext.getBean(templateName, SqlSessionTemplate.class);
		BeanInfo bean = infoMgr.getFields(type);
		String table = config == null ? null : config.table();
		if (StrUtil.isEmpty(table))
			table = nameHandler.getTableName(type);
		Ternary ignoreEmpty = config == null ? Ternary.UNKNOWN : config.ignoreEmpty();
		Ternary insertAsMap = config == null ? Ternary.UNKNOWN : config.insertAsMap();
		String[] fields = config == null ? MapperConfig.DEF_FIELDS : config.fields();
		return new MapperConfigInfo(template, bean, table, ignoreEmpty, insertAsMap, fields);
	}

	private SimpleMapper getMapper(MapperConfigInfo info) {
		SimpleMapper mapper = mapperMap.get(info);
		if (mapper != null)
			return mapper;
		String namespace = Util.getNextNamespace();
		SqlSessionTemplate template = info.getTemplate();
		Configuration configuration = template.getConfiguration();
		String xml = MybatisXmlBuilder.build(namespace, info);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(xml.getBytes());
		XMLMapperBuilder builder = new XMLMapperBuilder(inputStream, configuration, namespace + ".xml",
				configuration.getSqlFragments(), namespace);
		builder.parse();
		mapper = new SimpleMapper(namespace, info.getTemplate(), info, this);
		mapperMap.put(info, mapper);
		return mapper;
	}

	@PreDestroy
	@Override
	public void clear() {
		mapperMap.clear();
		infoMgr.clear();
	}
}
