package zr.mybatis;

import org.mybatis.spring.SqlSessionTemplate;

public class SimpleDao<T> {
	private SimpleMapper<T> mapper;

	protected final SimpleMapper<T> getMapper() {
		return mapper;
	}

	protected final SqlSessionTemplate getSqlTemplate() {
		return mapper.template;
	}
	
}
