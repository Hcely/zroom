package zr.mybatis;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

public class SimpleDao<T> {
	private SimpleMapper<T> mapper;

	public int insert(T e) {
	}

	public T findById(T e) {
	}

	public T find(T condition) {
	}

	public T find(Map<String, Object> condition) {
	}

	public List<T> queryAll() {
	}

	public List<T> query(T conditions) {
	}

	public List<T> query(Map<String, Object> condition) {
	}

	public int updateById(T e) {
	}

	public int update(T updates, T conditions) {
	}

	public int update(T updates, Map<String, Object> conditions) {
	}

	public int deleteById(T e) {
	}

	public int delete(T condition) {
	}

	public int delete(Map<String, Object> conditions) {
	}

	protected final SimpleMapper<T> getMapper() {
		return mapper;
	}

	protected final SqlSessionTemplate getSqlTemplate() {
	}

}
