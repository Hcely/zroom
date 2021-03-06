package zr.mybatis;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.mybatis.spring.SqlSessionTemplate;

import v.common.unit.Ternary;
import zr.mybatis.info.BeanInfo;
import zr.mybatis.info.MapperConfigInfo;
import zr.mybatis.sql.ObjCriteria;
import zr.mybatis.sql.SqlCriteria;
import zr.mybatis.sql.SqlWhere;

public class SimpleMapper<T> {

	protected final SqlSessionTemplate template;
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
		if (insertAsMap) {
			Map<String, Object> map = ZRMybatisUtil.toMap(beanInfo, e, true, ignoreEmpty);
			int hr = insertMap(map);
			ZRMybatisUtil.setIncColumn(beanInfo, e, map);
			return hr;
		}
		return template.insert(insertObj, e);
	}

	public int insertMap(Map<String, Object> map) {
		return template.insert(insertMap, map);
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

	public int delete(SqlCriteria criteria) {
		return template.delete(delete, criteria);
	}

	public final void insertBatch(Collection<T> list) {
		if (list == null || list.isEmpty())
			return;
		for (T e : list)
			insert(e);
	}

	public final T selectOne(ObjCriteria<?> condition) {
		return selectOne(condition.criteria());
	}

	public final List<T> selectList(ObjCriteria<?> condition) {
		return selectList(condition.criteria());
	}

	public final Map<String, Object> selectOneMap(ObjCriteria<?> condition) {
		return selectOneMap(condition.criteria());
	}

	public final List<Map<String, Object>> selectListMap(ObjCriteria<?> condition) {
		return selectListMap(condition.criteria());
	}

	public final T selectOneByMap(Map<String, Object> condition) {
		SqlCriteria c = SqlCriteria.create();
		SqlWhere w = c.where();
		for (Entry<String, Object> e : condition.entrySet())
			w.eq(e.getKey(), e.getValue());
		return selectOne(c);
	}

	public final List<T> selectListByMap(Map<String, Object> condition) {
		SqlCriteria c = SqlCriteria.create();
		SqlWhere w = c.where();
		for (Entry<String, Object> e : condition.entrySet())
			w.eq(e.getKey(), e.getValue());
		return selectList(c);
	}

	public final T selectById(String key, Object value) {
		return selectOne(SqlCriteria.create().where().eq(key, value).end());
	}

	public final List<T> selectByIds(String key, Collection<?> values) {
		return selectList(SqlCriteria.create().where().in(key, values).end());
	}

	public final int selectCount(SqlCriteria criteria) {
		if (criteria == null)
			criteria = SqlCriteria.create().addRawField("COUNT(*) as num");
		else
			criteria.resetFields().addRawField("COUNT(*) as num");
		Map<String, Object> map = selectOneMap(criteria);
		return ((Number) map.get("num")).intValue();
	}

	public final int updateByKey(T obj) {
		return updateByKey(obj, ignoreEmpty);
	}

	public final int updateByKey(T obj, boolean ignoreEmpty) {
		if (obj == null)
			return 0;
		SqlCriteria criteria = SqlCriteria.create();
		for (Field f : beanInfo.getNormals()) {
			Object value = ZRMybatisUtil.get(f, obj);
			if (value == null)
				continue;
			if (ignoreEmpty && (value instanceof CharSequence) && ((CharSequence) value).length() == 0)
				continue;
			criteria.update(f.getName(), value);
		}
		SqlWhere where = criteria.where();
		for (Field f : beanInfo.getKeys()) {
			Object value = ZRMybatisUtil.get(f, obj);
			where.eq(f.getName(), value);
		}
		return update(criteria);
	}

	public final int update(ObjCriteria<?> objCriteria) {
		return update(objCriteria.criteria());
	}

	public final int update(T update, ObjCriteria<?> condition) {
		return update(update, condition, ignoreEmpty);
	}

	public final int update(T update, ObjCriteria<?> condition, boolean ignoreEmpty) {
		SqlCriteria criteria = condition.criteria();
		criteria.resetUpdates();
		for (Field f : beanInfo.getFields()) {
			Object value = ZRMybatisUtil.get(f, update);
			if (value == null)
				continue;
			if (ignoreEmpty && (value instanceof CharSequence) && ((CharSequence) value).length() == 0)
				continue;
			criteria.update(f.getName(), value);
		}
		return update(criteria);
	}

	public final int update(T update, T condition) {
		return update(update, condition, ignoreEmpty);
	}

	public final int update(T update, T condition, boolean ignoreEmpty) {
		if (update == null)
			return 0;
		SqlCriteria criteria = SqlCriteria.create();
		for (Field f : beanInfo.getFields()) {
			Object value = ZRMybatisUtil.get(f, update);
			if (value == null)
				continue;
			if (ignoreEmpty && (value instanceof CharSequence) && ((CharSequence) value).length() == 0)
				continue;
			criteria.update(f.getName(), value);
		}
		if (condition != null) {
			SqlWhere where = criteria.where();
			for (Field f : beanInfo.getFields()) {
				Object value = ZRMybatisUtil.get(f, condition);
				where.eq(f.getName(), value, true);
			}
		}
		return update(criteria);
	}

	public final int delete(ObjCriteria<?> objCriteria) {
		return delete(objCriteria.criteria());
	}

	public final BeanInfo getBeanInfo() {
		return beanInfo;
	}

}
