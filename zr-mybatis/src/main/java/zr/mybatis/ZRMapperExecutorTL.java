package zr.mybatis;

import zr.mybatis.interceptor.ZRMapperInterceptor;

class ZRMapperExecutorTL extends ThreadLocal<ZRMapperExecutor> {
	protected final ZRMapperInterceptor[] interceptors;

	ZRMapperExecutorTL(ZRMapperInterceptor[] interceptors) {
		this.interceptors = interceptors;
	}

	@Override
	protected ZRMapperExecutor initialValue() {
		return new ZRMapperExecutor(interceptors);
	}

}
