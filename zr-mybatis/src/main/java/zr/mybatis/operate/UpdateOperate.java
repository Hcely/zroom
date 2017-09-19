package zr.mybatis.operate;

public interface UpdateOperate<T> {
	public int update(T update);

	public int update(T update, T condition);
}
