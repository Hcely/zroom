package zr.mybatis.sql;

import java.util.LinkedList;
import java.util.List;

import v.common.helper.StrUtil;

public final class SqlCriteria {
	protected List<String> fields;
	protected List<SqlUpdate> updates;
	protected LinkedList<SqlWhere> wheres;
	protected String tailSql;

	void addWhere(SqlWhere where) {
		if (wheres == null)
			wheres = new LinkedList<>();
		wheres.add(where);
	}

	void popWhere() {
		if (wheres != null)
			wheres.pollLast();
	}

	public SqlCriteria addField(final String key) {
		StringBuilder sb = new StringBuilder(key.length() + 2);
		sb.append('`').append(key).append('`');
		fields.add(StrUtil.sbToString(sb));
		return this;
	}

	public SqlCriteria addRawField(final String key) {
		fields.add(key);
		return this;
	}

	public SqlCriteria update(String key, Object value, boolean ignoreNull) {
		if (ignoreNull && value == null)
			return this;
		updates.add(new SqlUpdate(false, key, value));
		return this;
	}

	public SqlCriteria update(String key, Object value) {
		return update(key, value, false);
	}

	public SqlCriteria updateInc(String key, Number num) {
		if (num == null)
			return this;
		StringBuilder sb = new StringBuilder(24 + (key.length() << 1));
		sb.append('`').append(key).append("`=`").append(key).append("`+").append(num);
		updates.add(new SqlUpdate(true, sb.toString(), null));
		return this;
	}

	public SqlCriteria updateMax(String key, Number num) {
		if (num == null)
			return this;
		StringBuilder sb = new StringBuilder(key.length() * 3 + 48);
		sb.append('`').append(key).append("`=if(`").append(key).append("`>").append(num).append(",`").append(key)
				.append("`,").append(num).append(')');
		updates.add(new SqlUpdate(true, sb.toString(), null));
		return this;
	}

	public SqlCriteria updateMin(String key, Number num) {
		if (num == null)
			return this;
		StringBuilder sb = new StringBuilder(key.length() * 3 + 48);
		sb.append('`').append(key).append("`=if(`").append(key).append("`<").append(num).append(",`").append(key)
				.append("`,").append(num).append(')');
		updates.add(new SqlUpdate(true, sb.toString(), null));
		return this;
	}

	public SqlCriteria updateRaw(String update) {
		updates.add(new SqlUpdate(true, update, null));
		return this;
	}

	public SqlWhere where() {
		return new SqlWhere(this);
	}

	public SqlCriteria setTailSql(String tailSql) {
		this.tailSql = tailSql;
		return this;
	}

	public SqlCriteria clearFields() {
		if (fields != null)
			fields.clear();
		return this;
	}

	public SqlCriteria clearUpdates() {
		if (updates != null)
			updates.clear();
		return this;
	}

	public SqlCriteria clearWheres() {
		if (wheres != null)
			wheres.clear();
		return this;
	}

	public SqlCriteria reset() {
		clearFields();
		clearUpdates();
		clearWheres();
		tailSql = null;
		return this;
	}

	public boolean isFieldValid() {
		return fields != null && fields.size() > 0;
	}

	public boolean isUpdateValid() {
		return updates != null && updates.size() > 0;
	}

	public boolean isWhereValid() {
		return wheres != null && wheres.size() > 0;
	}

	public String getTailSql() {
		return tailSql;
	}

	public List<String> getFields() {
		return fields;
	}

	public List<SqlUpdate> getUpdates() {
		return updates;
	}

	public LinkedList<SqlWhere> getWheres() {
		return wheres;
	}

}
