package zr.mybatis.interceptor;

import zr.mybatis.info.BeanInfo;

public interface ZRInsertObjExecutor {
	public int insertObj(BeanInfo info, Object obj);
}
