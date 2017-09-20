package zr.mybatis.operate;

public interface DeleteOperate<T> {
	public int delete(T condition);
}
