package zr.mybatis.operate;

import java.util.Collection;
import java.util.Map;

public interface InsertOperate<T> {
	public int insert(T e);

	public void insertBatch(Collection<T> e);

	public int insert(Map<String, Object> e);

}
