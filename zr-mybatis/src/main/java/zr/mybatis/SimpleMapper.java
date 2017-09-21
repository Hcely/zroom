package zr.mybatis;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import v.common.unit.Ternary;
import zr.mybatis.info.BeanInfo;
import zr.mybatis.info.MapperConfigInfo;
import zr.mybatis.sql.SqlCriteria;
import zr.mybatis.sql.SqlWhere;

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
			ZRSpringMybatisHelper helper) {
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

	public int insert(T e) {
		if (insertAsMap)
			return insertMap(Util.toMap(beanInfo, e, true, ignoreEmpty));
		return template.insert(insertObj, e);
	}

	public void insertBatch(Collection<T> list) {
		if (list == null || list.isEmpty())
			return;
		for (T e : list)
			insert(e);
	}

	public int insertMap(Map<String, Object> e) {
		return template.insert(insertMap, e);
	}

	public T selectOne(SqlCriteria criteria) {
		return template.selectOne(selectObj, criteria.limit(1));
	}

	public List<T> selectList(SqlCriteria criteria) {
		return template.selectList(selectObj, criteria);
	}

	public Map<String, Object> selectOneMap(SqlCriteria criteria) {
		return template.selectOne(selectMap, criteria.limit(1));
	}

	public List<Map<String, Object>> selectListMap(SqlCriteria criteria) {
		return template.selectList(selectMap, criteria);
	}

	public int update(SqlCriteria criteria) {
		if (criteria.isUpdateValid())
			return template.update(update, criteria);
		return 0;
	}

	public int updateByKey(T obj) {
		return updateByKey(obj, ignoreEmpty);
	}

	public int updateByKey(T obj, boolean ignoreEmpty) {
		if (obj == null)
			return 0;
		SqlCriteria criteria = SqlCriteria.create();
		for (Field f : beanInfo.getNormals()) {
			Object value = Util.get(f, obj);
			if (value == null)
				continue;
			if (ignoreEmpty && (value instanceof CharSequence) && ((CharSequence) value).length() == 0)
				continue;
			criteria.update(f.getName(), value);
		}
		SqlWhere where = criteria.where();
		for (Field f : beanInfo.getKeys()) {
			Object value = Util.get(f, obj);
			where.eq(f.getName(), value);
		}
		return update(criteria);
	}

	public int update(T update, T condition) {
		return update(update, condition, ignoreEmpty);
	}

	public int update(T update, T condition, boolean ignoreEmpty) {
		if (update == null)
			return 0;
		SqlCriteria criteria = SqlCriteria.create();
		for (Field f : beanInfo.getFields()) {
			Object value = Util.get(f, update);
			if (value == null)
				continue;
			if (ignoreEmpty && (value instanceof CharSequence) && ((CharSequence) value).length() == 0)
				continue;
			criteria.update(f.getName(), value);
		}
		if (condition != null) {
			SqlWhere where = criteria.where();
			for (Field f : beanInfo.getFields()) {
				Object value = Util.get(f, condition);
				where.eq(f.getName(), value, true);
			}
		}
		return update(criteria);
	}

	public int delete(SqlCriteria criteria) {
		return template.delete(delete, criteria);
	}

	public BeanInfo getBeanInfo() {
		return beanInfo;
	}

}
