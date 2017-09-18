package zr.mybatis.util;

import org.mybatis.spring.SqlSessionTemplate;

import v.common.unit.Ternary;

public class MapperConfigInfo {
	protected final SqlSessionTemplate template;
	protected final Class<?> clazz;
	protected final String table;
	protected final Ternary ignoreEmpty;

	public MapperConfigInfo(SqlSessionTemplate template, Class<?> clazz, String table, Ternary ignoreEmpty) {
		this.template = template;
		this.clazz = clazz;
		this.table = table;
		this.ignoreEmpty = ignoreEmpty;
	}

	public SqlSessionTemplate getTemplate() {
		return template;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public String getTable() {
		return table;
	}

	public Ternary getIgnoreEmpty() {
		return ignoreEmpty;
	}

}
