package zr.mybatis;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import zr.mybatis.info.BeanInfo;
import zr.mybatis.operate.DeleteOperate;
import zr.mybatis.operate.InsertOperate;
import zr.mybatis.operate.SelectOperate;
import zr.mybatis.operate.UpdateOperate;
import zr.mybatis.sql.SqlCriteria;
import zr.mybatis.sql.SqlWhere;

public abstract class SimpleDao<T> implements InsertOperate<T>, SelectOperate<T>, UpdateOperate<T>, DeleteOperate<T> {
	SimpleMapper<T> mapper;

	protected final SimpleMapper<T> mapper() {
		return mapper;
	}

	protected final SqlSessionTemplate template() {
		return mapper.template;
	}

	@Override
	public int delete(T condition) {
		SqlCriteria criteria = SqlCriteria.create();
		if (condition != null) {
			SqlWhere where = criteria.where();
			BeanInfo bean = mapper.getBeanInfo();
			for (Field f : bean.getFields()) {
				Object value = Util.get(f, condition);
				if (value != null)
					where.eq(f.getName(), value);
			}
		}
		return mapper.delete(criteria);
	}

	@Override
	public int update(T update) {
		return mapper.updateByKey(update);
	}

	@Override
	public int update(T update, T condition) {
		return mapper.update(update, condition);
	}

	@Override
	public T find(T condition) {
		SqlCriteria criteria = SqlCriteria.create();
		if (condition != null) {
			SqlWhere where = criteria.where();
			BeanInfo bean = mapper.getBeanInfo();
			for (Field f : bean.getFields())
				where.eq(f.getName(), Util.get(f, condition), true);
		}
		return mapper.selectOne(criteria);
	}

	@Override
	public List<T> queryAll() {
		return mapper.selectList(SqlCriteria.create());
	}

	@Override
	public List<T> queryAll(SqlCriteria sorts) {
		return mapper.selectList(sorts.resetFields().resetWheres());
	}

	@Override
	public List<T> query(T condition) {
		SqlCriteria criteria = SqlCriteria.create();
		if (condition != null) {
			SqlWhere where = criteria.where();
			BeanInfo bean = mapper.getBeanInfo();
			for (Field f : bean.getFields())
				where.eq(f.getName(), Util.get(f, condition), true);
		}
		return mapper.selectList(criteria);
	}

	@Override
	public List<T> query(T condition, SqlCriteria sorts) {
		sorts.resetFields();
		sorts.resetWheres();
		if (condition != null) {
			SqlWhere where = sorts.where();
			BeanInfo bean = mapper.getBeanInfo();
			for (Field f : bean.getFields())
				where.eq(f.getName(), Util.get(f, condition), true);
		}
		return mapper.selectList(sorts);
	}

	@Override
	public int insert(T e) {
		return mapper.insert(e);
	}

	@Override
	public void insertBatch(Collection<T> list) {
		mapper.insertBatch(list);
	}

	@Override
	public int insert(Map<String, Object> e) {
		return mapper.insertMap(e);
	}

}
