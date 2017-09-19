package zr.mybatis.operate;

import java.util.List;

public interface SelectOperate<T> {
	public T find(T condition);

	public List<T> queryAll();

	public List<T> query(T condition);
}
