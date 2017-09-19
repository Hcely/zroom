package zr.mybatis.info;

import java.util.Arrays;

import org.mybatis.spring.SqlSessionTemplate;

import v.common.unit.Ternary;

public final class MapperConfigInfo {
	protected final SqlSessionTemplate template;
	protected final BeanInfo bean;
	protected final Class<?> clazz;
	protected final String table;
	protected final Ternary ignoreEmpty;
	protected final Ternary insertAsMap;
	protected final String[] fields;

	protected int hashcode = 0;

	public MapperConfigInfo(SqlSessionTemplate template, BeanInfo bean, Class<?> clazz, String table,
			Ternary ignoreEmpty, Ternary insertAsMap, String[] fields) {
		this.template = template;
		this.bean = bean;
		this.clazz = clazz;
		this.table = table;
		this.ignoreEmpty = ignoreEmpty;
		this.insertAsMap = insertAsMap;
		this.fields = fields;
	}

	public SqlSessionTemplate getTemplate() {
		return template;
	}

	public BeanInfo getBean() {
		return bean;
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

	public Ternary getInsertAsMap() {
		return insertAsMap;
	}

	public String[] getFields() {
		return fields;
	}

	@Override
	public int hashCode() {
		if (hashcode == 0) {
			int result = 1;
			result = 31 * result + ((bean == null) ? 0 : bean.hashCode());
			result = 31 * result + Arrays.hashCode(fields);
			result = 31 * result + ((ignoreEmpty == null) ? 0 : ignoreEmpty.hashCode());
			result = 31 * result + ((insertAsMap == null) ? 0 : insertAsMap.hashCode());
			result = 31 * result + ((table == null) ? 0 : table.hashCode());
			result = 31 * result + ((template == null) ? 0 : template.hashCode());
			hashcode = result;
		}
		return hashcode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MapperConfigInfo other = (MapperConfigInfo) obj;
		if (bean != other.bean)
			return false;
		if (!Arrays.equals(fields, other.fields))
			return false;
		if (ignoreEmpty != other.ignoreEmpty)
			return false;
		if (insertAsMap != other.insertAsMap)
			return false;
		if (!table.equals(other.table))
			return false;
		if (template != other.template)
			return false;
		return true;
	}

}
