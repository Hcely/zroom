package zr.mybatis;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import zr.mybatis.info.MapperConfigInfo;
import zr.mybatis.sql.SqlCriteria;

@SuppressWarnings({ "unchecked", "rawtypes" })
class FilterSimpleMapper<T> extends SimpleMapper<T> {
	protected final ZRMapperExecutorTL executorTL;

	FilterSimpleMapper(String namespace, SqlSessionTemplate template, MapperConfigInfo configInfo,
			ZRSpringMybatisHelper helper) {
		super(namespace, template, configInfo, helper);
		this.executorTL = helper.executorTL;
	}

	@Override
	public int insert(T e) {
		return executorTL.get().set(this).insertObj(beanInfo, e);
	}

	@Override
	public int insertMap(Map<String, Object> map) {
		return executorTL.get().set(this).insertMap(beanInfo, map);
	}

	@Override
	public T selectOne(SqlCriteria criteria) {
		return (T) executorTL.get().set(this).selectOne(beanInfo, criteria, false);
	}

	@Override
	public Map<String, Object> selectOneMap(SqlCriteria criteria) {
		return (Map<String, Object>) executorTL.get().set(this).selectOne(beanInfo, criteria, true);
	}

	@Override
	public List<T> selectList(SqlCriteria criteria) {
		return executorTL.get().set(this).selectList(beanInfo, criteria, false);
	}

	@Override
	public List<Map<String, Object>> selectListMap(SqlCriteria criteria) {
		return executorTL.get().set(this).selectList(beanInfo, criteria, true);
	}

	@Override
	public int update(SqlCriteria criteria) {
		return executorTL.get().set(this).update(beanInfo, criteria);
	}

	@Override
	public int delete(SqlCriteria criteria) {
		return executorTL.get().set(this).delete(beanInfo, criteria);
	}

	final int _insertObj(Object obj) {
		return super.insert((T) obj);
	}

	final int _insertMap(Map<String, Object> map) {
		return super.insertMap(map);
	}

	final Object _selectOne(SqlCriteria criteria, boolean map) {
		if (map)
			return super.selectOneMap(criteria);
		else
			return super.selectOne(criteria);
	}

	final List _selectList(SqlCriteria criteria, boolean map) {
		if (map)
			return super.selectListMap(criteria);
		else
			return super.selectList(criteria);
	}

	final int _update(SqlCriteria criteria) {
		return super.update(criteria);
	}

	final int _delete(SqlCriteria criteria) {
		return super.delete(criteria);
	}

}
