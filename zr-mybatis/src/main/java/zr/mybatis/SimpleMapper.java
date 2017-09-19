package zr.mybatis;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import v.common.unit.Ternary;
import zr.mybatis.info.BeanInfo;
import zr.mybatis.info.MapperConfigInfo;

public class SimpleMapper<T> {
	protected final SqlSessionTemplate template;
	protected final MapperConfigInfo configInfo;
	protected final BeanInfo beanInfo;
	protected final boolean ignoreEmpty;
	protected final boolean insertAsMap;
	
	protected final String insertObj;
	protected final String insertMap;
	protected final String selectObj;
	protected final String selectMap;
	protected final String update;
	protected final String delete;

	SimpleMapper(String namespace, SqlSessionTemplate template, MapperConfigInfo configInfo,
			SpringMybatisHelper helper) {
		this.template = template;
		this.configInfo = configInfo;
		this.beanInfo = configInfo.getBean();
		Ternary e = configInfo.getIgnoreEmpty();
		this.ignoreEmpty = e == Ternary.UNKNOWN ? helper.ignoreEmpty : (e == Ternary.TRUE);
		e = configInfo.getInsertAsMap();
		this.insertAsMap = e == Ternary.UNKNOWN ? helper.insertAsMap : (e == Ternary.TRUE);

		this.insertObj = namespace + '.' + MybatisXmlBuilder.INSERT_OBJ;
		this.insertMap = namespace + '.' + MybatisXmlBuilder.INSERT_MAP;
		this.selectObj = namespace + '.' + MybatisXmlBuilder.SELECT_OBJ;
		this.selectMap = namespace + '.' + MybatisXmlBuilder.SELECT_MAP;
		this.update = namespace + '.' + MybatisXmlBuilder.UPDATE;
		this.delete = namespace + '.' + MybatisXmlBuilder.DELETE;
	}

	
	protected Map<String, Object> toMap(Object obj, boolean ignoreEmpty) {
		Map<String, Object> hr = new LinkedHashMap<>();
		for (Field f : beanInfo.getFields())
			try {
				Object value = f.get(obj);
				if (value == null)
					continue;
				if (ignoreEmpty && value instanceof CharSequence)
					if (((CharSequence) value).length() == 0)
						continue;
				hr.put(f.getName(), value);
			} catch (Exception e) {
			}
		return hr;
	}

}
