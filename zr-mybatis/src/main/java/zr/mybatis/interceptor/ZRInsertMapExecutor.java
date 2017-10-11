package zr.mybatis.interceptor;

import java.util.Map;

import zr.mybatis.info.BeanInfo;

public interface ZRInsertMapExecutor {
	public int insertMap(BeanInfo info, Map<String, Object> map);
}
