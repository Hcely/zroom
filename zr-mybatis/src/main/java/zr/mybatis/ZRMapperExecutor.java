package zr.mybatis;

import java.util.List;
import java.util.Map;

import zr.mybatis.info.BeanInfo;
import zr.mybatis.interceptor.ZRDeleteExecutor;
import zr.mybatis.interceptor.ZRInsertMapExecutor;
import zr.mybatis.interceptor.ZRInsertObjExecutor;
import zr.mybatis.interceptor.ZRMapperInterceptor;
import zr.mybatis.interceptor.ZRSelectListExecutor;
import zr.mybatis.interceptor.ZRSelectOneExecutor;
import zr.mybatis.interceptor.ZRUpdateExecutor;
import zr.mybatis.sql.SqlCriteria;

@SuppressWarnings({ "rawtypes", "unchecked" })
final class ZRMapperExecutor implements ZRInsertObjExecutor, ZRInsertMapExecutor, ZRSelectOneExecutor,
		ZRSelectListExecutor, ZRUpdateExecutor, ZRDeleteExecutor {
	protected final ZRMapperInterceptor[] interceptors;
	protected final int length;
	protected FilterSimpleMapper mapper;
	protected int idx;

	public ZRMapperExecutor(ZRMapperInterceptor[] interceptors) {
		this.interceptors = interceptors;
		this.length = interceptors.length;
	}

	final ZRMapperExecutor set(FilterSimpleMapper mapper) {
		this.mapper = mapper;
		this.idx = 0;
		return this;
	}

	@Override
	public int insertObj(BeanInfo info, Object obj) {
		if (idx < length)
			return interceptors[idx++].onInsertObj(this, info, obj);
		else
			return mapper._insertObj(obj);
	}

	@Override
	public int insertMap(BeanInfo info, Map<String, Object> map) {
		if (idx < length)
			return interceptors[idx++].onInsertMap(this, info, map);
		else
			return mapper._insertMap(map);
	}

	@Override
	public Object selectOne(BeanInfo info, SqlCriteria criteria, boolean map) {
		if (idx < length)
			return interceptors[idx++].onSelectOne(this, info, criteria, map);
		else
			return mapper._selectOne(criteria, map);
	}

	@Override
	public List selectList(BeanInfo info, SqlCriteria criteria, boolean map) {
		if (idx < length)
			return interceptors[idx++].onSelectList(this, info, criteria, map);
		else
			return mapper._selectList(criteria, map);
	}

	@Override
	public int update(BeanInfo info, SqlCriteria criteria) {
		if (idx < length)
			return interceptors[idx++].onUpdate(this, info, criteria);
		else
			return mapper._update(criteria);
	}

	@Override
	public int delete(BeanInfo info, SqlCriteria criteria) {
		if (idx < length)
			return interceptors[idx++].onDelete(this, info, criteria);
		else
			return mapper._delete(criteria);
	}

}
