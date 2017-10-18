package zr.mybatis.sql;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import v.common.helper.StrUtil;

public final class SqlCriteriaImpl implements SqlCriteria, SqlSorts {
	protected Map<String, Void> fieldMap;
	protected Map<String, SqlUpdate> updateMap;
	protected LinkedList<SqlWhereImpl> wheres;
	protected String groupBy;
	protected StringBuilder sorts;
	protected SqlHavingImpl havingOps;
	protected SqlCondition having;

	protected int offset;
	protected int count;

	protected String tailSql;

	SqlCriteriaImpl() {
	}

	void addWhere(SqlWhereImpl where) {
		if (wheres == null)
			wheres = new LinkedList<>();
		wheres.add(where);
	}

	void popWhere() {
		if (wheres != null)
			wheres.pollLast();
	}

	@Override
	public SqlCriteria addField(final String key) {
		if (fieldMap == null)
			fieldMap = new LinkedHashMap<>();
		StringBuilder sb = new StringBuilder(key.length() + 2).append('`').append(key).append('`');
		fieldMap.put(StrUtil.sbToString(sb), null);
		return this;
	}

	@Override
	public SqlCriteria addRawField(final String key) {
		if (fieldMap == null)
			fieldMap = new LinkedHashMap<>();
		fieldMap.put(key, null);
		return this;
	}

	@Override
	public SqlCriteria update(String key, Object value, boolean ignoreNull) {
		if (ignoreNull && value == null)
			return this;
		return addUpdate(key, new SqlUpdate(false, key, value));
	}

	@Override
	public SqlCriteria update(String key, Object value) {
		return update(key, value, false);
	}

	@Override
	public SqlCriteria updateInc(String key, Number num) {
		if (num == null)
			return this;
		StringBuilder sb = new StringBuilder(24 + (key.length() << 1));
		sb.append('`').append(key).append("`=`").append(key).append("`+").append(num);
		return addUpdate(key, new SqlUpdate(true, sb.toString(), null));
	}

	@Override
	public SqlCriteria updateMax(String key, Number num) {
		if (num == null)
			return this;
		StringBuilder sb = new StringBuilder(key.length() * 3 + 48);
		sb.append('`').append(key).append("`=if(`").append(key).append("`>").append(num).append(",`").append(key)
				.append("`,").append(num).append(')');
		return addUpdate(key, new SqlUpdate(true, sb.toString(), null));
	}

	@Override
	public SqlCriteria updateMin(String key, Number num) {
		if (num == null)
			return this;
		StringBuilder sb = new StringBuilder(key.length() * 3 + 48);
		sb.append('`').append(key).append("`=if(`").append(key).append("`<").append(num).append(",`").append(key)
				.append("`,").append(num).append(')');
		return addUpdate(key, new SqlUpdate(true, sb.toString(), null));
	}

	@Override
	public boolean containUpdate(String key) {
		if (updateMap == null)
			return false;
		return updateMap.containsKey(key);
	}

	private SqlCriteria addUpdate(String key, SqlUpdate update) {
		if (updateMap == null)
			updateMap = new LinkedHashMap<>();
		updateMap.put(key, update);
		return this;
	}

	@Override
	public SqlWhere where() {
		if (wheres == null || wheres.isEmpty())
			return new SqlWhereImpl(this);
		else
			return wheres.peekLast();
	}

	@Override
	public SqlCriteria groupBy(String... cols) {
		if (cols == null || cols.length == 0) {
			groupBy = null;
			return this;
		}
		StringBuilder sb = new StringBuilder(128);
		sb.append("GROUP BY ");
		for (int i = 0, len = cols.length; i < len; ++i) {
			if (i > 0)
				sb.append(',');
			sb.append('`').append(cols[i]).append('`');
		}
		groupBy = sb.toString();
		return this;
	}

	@Override
	public SqlHaving having() {
		if (havingOps == null)
			havingOps = new SqlHavingImpl(this);
		return havingOps;
	}

	@Override
	public SqlSorts sorts() {
		return this;
	}

	@Override
	public SqlSorts clear() {
		if (sorts != null)
			sorts.setLength(0);
		return this;
	}

	@Override
	public SqlCriteria end() {
		return this;
	}

	@Override
	public SqlSorts sort(String name, boolean asc) {
		if (sorts == null)
			sorts = new StringBuilder(128);
		if (sorts.length() > 0)
			sorts.append(',');
		sorts.append('`').append(name);
		if (asc)
			sorts.append("` ASC");
		else
			sorts.append("` DESC");
		return this;
	}

	@Override
	public SqlSorts asc(String name) {
		return sort(name, true);
	}

	@Override
	public SqlSorts desc(String name) {
		return sort(name, false);
	}

	@Override
	public SqlCriteria limit(int count) {
		return limit(0, count);
	}

	@Override
	public SqlCriteria limit(int offset, int count) {
		this.offset = offset;
		this.count = count;
		return this;
	}

	@Override
	public SqlCriteria setTailSql(String tailSql) {
		this.tailSql = tailSql;
		return this;
	}

	@Override
	public SqlCriteria reset() {
		if (fieldMap != null)
			fieldMap.clear();
		if (updateMap != null)
			updateMap.clear();
		if (wheres != null)
			wheres.clear();
		groupBy = null;
		having = null;
		if (sorts != null)
			sorts.setLength(0);
		count = 0;
		tailSql = null;
		return this;
	}

	@Override
	public SqlCriteria resetFields() {
		if (fieldMap != null)
			fieldMap.clear();
		return this;
	}

	@Override
	public SqlCriteria resetUpdates() {
		if (updateMap != null)
			updateMap.clear();
		return this;
	}

	@Override
	public SqlCriteria resetWheres() {
		if (wheres != null)
			wheres.clear();
		return this;
	}

	@Override
	public SqlCriteria resetGroupBy() {
		groupBy = null;
		return this;
	}

	@Override
	public SqlCriteria resetHaving() {
		having = null;
		return this;
	}

	@Override
	public SqlCriteria resetSorts() {
		if (sorts != null)
			sorts.setLength(0);
		return this;
	}

	@Override
	public SqlCriteria resetLimit() {
		count = 0;
		return this;
	}

	@Override
	public SqlCriteria resetTailSql() {
		tailSql = null;
		return this;
	}

	@Override
	public boolean isFieldValid() {
		return fieldMap != null && fieldMap.size() > 0;
	}

	@Override
	public boolean isUpdateValid() {
		return updateMap != null && updateMap.size() > 0;
	}

	@Override
	public boolean isWhereValid() {
		return wheres != null && wheres.size() > 0;
	}

	@Override
	public boolean isGroupByValid() {
		return groupBy != null;
	}

	@Override
	public boolean isHavingValid() {
		return having != null;
	}

	@Override
	public boolean isSortValid() {
		return sorts != null && sorts.length() > 0;
	}

	@Override
	public boolean isLimitValid() {
		return count > 0;
	}

	@Override
	public boolean isTailValid() {
		return tailSql != null;
	}

	public Map<String, Void> getFields() {
		return fieldMap;
	}

	public Map<String, SqlUpdate> getUpdates() {
		return updateMap;
	}

	public LinkedList<SqlWhereImpl> getWheres() {
		return wheres;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public SqlCondition getHaving() {
		return having;
	}

	public String getSorts() {
		return sorts.toString();
	}

	public int getOffset() {
		return offset;
	}

	public int getCount() {
		return count;
	}

	public String getTailSql() {
		return tailSql;
	}

}
