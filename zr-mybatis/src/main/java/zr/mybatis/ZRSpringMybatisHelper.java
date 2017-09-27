package zr.mybatis;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import zr.common.util.ZRSpringUtil;
import zr.mybatis.annotation.MapperConfig;
import zr.mybatis.info.BeanInfo;
import zr.mybatis.info.BeanInfoMgr;
import zr.mybatis.info.MapperConfigInfo;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ZRSpringMybatisHelper implements ApplicationContextAware, Initializable, Clearable {
	protected final Map<MapperConfigInfo, SimpleMapper> mapperMap;
	protected final BeanInfoMgr infoMgr;
	ZRMybatisFilter[] filters;

	ApplicationContext appContext;
	SqlSessionTemplate defTemplate;
	TableNameHandler nameHandler;

	boolean ignoreEmpty = false;
	boolean insertAsMap = false;

	public ZRSpringMybatisHelper() {
		this.mapperMap = new HashMap<>();
		this.infoMgr = new BeanInfoMgr();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.appContext = applicationContext;
	}

	public final void setIgnoreEmpty(boolean ignoreEmpty) {
		this.ignoreEmpty = ignoreEmpty;
	}

	public final void setInsertAsMap(boolean insertAsMap) {
		this.insertAsMap = insertAsMap;
	}

	public final void setNameHandler(TableNameHandler nameHandler) {
		this.nameHandler = nameHandler;
	}

	@PostConstruct
	@Override
	public final void init() {
		if (nameHandler == null)
			nameHandler = new DefTableNameHandler();
		defTemplate = getDefTemplate(appContext);
		initFilters();
		initMappers();
		initDaos();
	}

	protected SqlSessionTemplate getDefTemplate(final ApplicationContext appContext) {
		Map<String, SqlSessionTemplate> beans = appContext.getBeansOfType(SqlSessionTemplate.class);
		SqlSessionTemplate hr = beans.get("org.mybatis.spring.SqlSessionTemplate#0");
		if (hr != null)
			return hr;
		hr = beans.get("sqlTemplate");
		if (hr != null)
			return hr;
		hr = beans.get("sqlTemplate0");
		if (hr != null)
			return hr;
		hr = beans.get("sqlTemplate-0");
		if (hr != null)
			return hr;
		hr = beans.get("sqlTemplate_0");
		if (hr != null)
			return hr;
		hr = beans.get("sqlSessionTemplate");
		if (hr != null)
			return hr;
		hr = beans.get("sqlSessionTemplate0");
		if (hr != null)
			return hr;
		hr = beans.get("sqlSessionTemplate-0");
		if (hr != null)
			return hr;
		hr = beans.get("sqlSessionTemplate_0");
		if (hr != null)
			return hr;
		return beans.values().iterator().next();
	}

	private void initFilters() {
		Map<String, ZRMybatisFilter> beans = appContext.getBeansOfType(ZRMybatisFilter.class);
		List<ZRMybatisFilter> list = new ArrayList<>(beans.values());
		Collections.sort(list, new Comparator<ZRMybatisFilter>() {
			@Override
			public int compare(ZRMybatisFilter o1, ZRMybatisFilter o2) {
				return o1.getPriority() - o2.getPriority();
			}
		});
		filters = list.toArray(new ZRMybatisFilter[list.size()]);
	}

	private void initMappers() {
		Map<String, Object> beans = appContext.getBeansWithAnnotation(Repository.class);
		for (Object e : beans.values()) {
			e = ZRSpringUtil.getRawObj(e);
			List<MapperField> fields = Util.getMapperFields(e.getClass());
			for (MapperField f : fields) {
				MapperConfigInfo info = buildConfig(f);
				SimpleMapper mapper = getMapper(info);
				try {
					f.field.set(e, mapper);
				} catch (Exception e1) {
				}
			}
		}
	}

	private void initDaos() {
		Map<String, SimpleDao> daos = appContext.getBeansOfType(SimpleDao.class);
		for (SimpleDao e : daos.values()) {
			e = (SimpleDao) ZRSpringUtil.getRawObj(e);
			MapperConfigInfo info = buildConfig(e.getClass());
			e.mapper = getMapper(info);
		}
	}

	private final MapperConfigInfo buildConfig(MapperField f) {
		return buildConfig0(Util.getFieldGenericType(f.field), f.config);
	}

	private final MapperConfigInfo buildConfig(Class<?> clazz) {
		Class<?> type = Util.getDaoGenericType(clazz);
		MapperConfig config = Util.getDaoConfig(clazz);
		return buildConfig0(type, config);
	}

	private final MapperConfigInfo buildConfig0(Class<?> type, MapperConfig config) {
		SqlSessionTemplate template = getTemplate(config == null ? null : config.template());
		BeanInfo bean = infoMgr.getFields(type);
		String table = config == null ? null : config.table();
		if (StrUtil.isEmpty(table))
			table = nameHandler.getTableName(type);
		Ternary ignoreEmpty = config == null ? Ternary.UNKNOWN : config.ignoreEmpty();
		Ternary insertAsMap = config == null ? Ternary.UNKNOWN : config.insertAsMap();
		String[] fields = config == null ? MapperConfig.DEF_FIELDS : config.fields();
		return new MapperConfigInfo(template, bean, table.intern(), ignoreEmpty, insertAsMap, fields);
	}

	private final SimpleMapper getMapper(MapperConfigInfo info) {
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
		if (filters == null || filters.length == 0)
			mapper = new SimpleMapper(namespace, info.getTemplate(), info, this);
		else
			mapper = new FilterSimpleMapper(namespace, info.getTemplate(), info, this);
		mapperMap.put(info, mapper);
		return mapper;
	}

	protected SqlSessionTemplate getTemplate(final String name) {
		if (StrUtil.isEmpty(name))
			return defTemplate;
		return appContext.getBean(name, SqlSessionTemplate.class);
	}

	@PreDestroy
	@Override
	public final void clear() {
		mapperMap.clear();
		infoMgr.clear();
	}
}
