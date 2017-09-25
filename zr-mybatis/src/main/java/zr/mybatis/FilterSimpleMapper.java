package zr.mybatis;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import zr.mybatis.info.MapperConfigInfo;
import zr.mybatis.sql.SqlCriteria;

class FilterSimpleMapper<T> extends SimpleMapper<T> {
	protected final ZRMybatisFilter[] filters;
	protected final String table;

	FilterSimpleMapper(String namespace, SqlSessionTemplate template, MapperConfigInfo configInfo,
			ZRSpringMybatisHelper helper) {
		super(namespace, template, configInfo, helper);
		this.filters = helper.filters;
		this.table = configInfo.getTable();
	}

	@Override
	public int insert(T e) {
		for (ZRMybatisFilter filter : filters)
			filter.onInsertObj(table, e);
		return super.insert(e);
	}

	@Override
	public int insertMap(Map<String, Object> map) {
		for (ZRMybatisFilter filter : filters)
			filter.onInsertMap(table, map);
		return super.insertMap(map);
	}

	@Override
	public T selectOne(SqlCriteria criteria) {
		for (ZRMybatisFilter filter : filters)
			filter.onSelect(table, criteria, true);
		return super.selectOne(criteria);
	}

	@Override
	public List<T> selectList(SqlCriteria criteria) {
		for (ZRMybatisFilter filter : filters)
			filter.onSelect(table, criteria, false);
		return super.selectList(criteria);
	}

	@Override
	public Map<String, Object> selectOneMap(SqlCriteria criteria) {
		for (ZRMybatisFilter filter : filters)
			filter.onSelect(table, criteria, true);
		return super.selectOneMap(criteria);
	}

	@Override
	public List<Map<String, Object>> selectListMap(SqlCriteria criteria) {
		for (ZRMybatisFilter filter : filters)
			filter.onSelect(table, criteria, false);
		return super.selectListMap(criteria);
	}

	@Override
	public int update(SqlCriteria criteria) {
		for (ZRMybatisFilter filter : filters)
			filter.onUpdate(table, criteria);
		return super.update(criteria);
	}

	@Override
	public int delete(SqlCriteria criteria) {
		for (ZRMybatisFilter filter : filters)
			filter.onDelete(table, criteria);
		return super.delete(criteria);
	}

}
