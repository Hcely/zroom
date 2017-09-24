package zr.mybatis;

import java.lang.reflect.Field;

import zr.mybatis.info.BeanInfo;
import zr.mybatis.info.MapperConfigInfo;
import zr.mybatis.sql.SqlCondition;
import zr.mybatis.sql.SqlCriteriaImpl;

final class MybatisXmlBuilder {
	static final String INSERT_OBJ = "insertObj";
	static final String INSERT_MAP = "insertMap";
	static final String SELECT_OBJ = "selectObj";
	static final String SELECT_MAP = "selectMap";
	static final String UPDATE = "update";
	static final String DELETE = "delete";

	private static final String SQL_CRITERIA = SqlCriteriaImpl.class.getName();

	public static final String build(String namespace, MapperConfigInfo info) {
		StringBuilder sb = new StringBuilder(1024 * 12);

		writeXmlHeader(sb, namespace);
		writeTableSql(sb, info);

		writeSelectSql(sb, info);
		writeUpdateSql(sb);
		writeWhereSql(sb);
		writeGroupBySql(sb);
		writeHavingSql(sb);
		writeSortSql(sb);
		writeLimitSql(sb);

		writeInsertObj(sb, info);
		writeInsertMap(sb, info);
		writeSelectObj(sb, info);
		writeSelectMap(sb);
		writeUpdate(sb);
		writeDelete(sb);

		writeXmlTail(sb);
		return sb.toString();
	}

	private static final void writeXmlHeader(StringBuilder sb, String namespace) {
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append(
				"<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
		sb.append("<mapper namespace=\"").append(namespace).append("\">\n");
	}

	private static final void writeTableSql(StringBuilder sb, MapperConfigInfo info) {
		sb.append("<sql id=\"_TABLE\">").append(info.getTable()).append("</sql>\n");
	}

	private static final void writeSelectSql(StringBuilder sb, MapperConfigInfo info) {
		sb.append("<sql id=\"_SELECT\">\n");
		sb.append("<choose>\n");
		sb.append("<when test=\"fieldValid\">${fields}</when>\n");
		sb.append("<otherwise>\n");
		writeSelectFields(sb, info);
		sb.append("</otherwise>\n");
		sb.append("</choose>\n");
		sb.append("</sql>\n");
	}

	private static final void writeSelectFields(StringBuilder sb, MapperConfigInfo info) {
		String[] fields = info.getFields();
		if (info.getFields().length > 0) {
			for (int i = 0, len = fields.length; i < len; ++i) {
				if (i > 0)
					sb.append(',');
				sb.append(fields[i]);
			}
			return;
		}
		BeanInfo binfo = info.getBean();
		Field[] fs = binfo.getFields();
		if (fs.length > 0) {
			for (int i = 0, len = fs.length; i < len; ++i) {
				if (i > 0)
					sb.append(',');
				sb.append(fs[i].getName());
			}
			return;
		}
		sb.append('*');
	}

	private static final void writeUpdateSql(StringBuilder sb) {
		sb.append("<sql id=\"_UPDATE\">\n");
		sb.append("SET\n");
		sb.append("<foreach collection=\"updates\" item=\"i\" separator=\",\">\n");
		sb.append("<choose>\n");
		sb.append("<when test=\"i.raw\">${i.key}</when>\n");
		sb.append("<otherwise>${i.key}=#{i.value}</otherwise>\n");
		sb.append("</choose>\n");
		sb.append("</foreach>\n");

		sb.append("</sql>\n");
	}

	private static final void writeWhereSql(StringBuilder sb) {
		// select sql
		sb.append("<sql id=\"_WHERE\">\n");

		sb.append("<if test=\"whereValid\">\n");
		sb.append("WHERE\n");
		sb.append("<foreach collection=\"wheres\" item=\"e\" open=\"(\" separator=\")OR(\" close=\")\">\n");
		sb.append("<foreach collection=\"e.conditions\" item=\"c\" separator=\"AND\">\n");

		sb.append("<choose>\n");
		sb.append("<when test=\"c.type==" + SqlCondition.KEY_NORMAL + "\">\n");
		sb.append("${c.key} #{c.value0}\n");
		sb.append("</when>\n");
		sb.append("<when test=\"c.type==" + SqlCondition.KEY_COLLECTION + "\">\n");
		sb.append("${c.key} #{c.value0} and #{c.value1}\n");
		sb.append("</when>\n");
		sb.append("<when test=\"c.type==" + SqlCondition.KEY_COLLECTION + "\">${c.key}\n");
		sb.append(
				"<foreach collection=\"c.value0\" item=\"i\" open=\"(\" separator=\",\" close=\")\">#{i}</foreach>\n");
		sb.append("</when>\n");
		sb.append("<when test=\"c.type==" + SqlCondition.KEY_RAW + "\">\n");
		sb.append("${c.key}\n");
		sb.append("</when>\n");
		sb.append("</choose>\n");

		sb.append("</foreach>\n");
		sb.append("</foreach>\n");
		sb.append("</if>\n");

		sb.append("</sql>\n");
	}

	private static final void writeGroupBySql(StringBuilder sb) {
		sb.append("<sql id=\"_GROUP_BY\">\n");
		sb.append("<if test=\"groupByValid\">${groupBy}</if>\n");
		sb.append("</sql>\n");
	}

	private static final void writeHavingSql(StringBuilder sb) {
		sb.append("<sql id=\"_HAVING\">\n");
		sb.append("<if test=\"havingValid\">\n");
		sb.append("HAVING\n");
		sb.append("<choose>\n");
		sb.append("<when test=\"having.type==").append(SqlCondition.KEY_NORMAL).append("\">\n");
		sb.append("${having.key} #{having.value0}\n");
		sb.append("</when>\n");
		sb.append("<when test=\"having.type==").append(SqlCondition.KEY_DOUBLE).append("\">\n");
		sb.append("${having.key} #{having.value0} and #{c.value1}\n");
		sb.append("</when>\n");
		sb.append("<when test=\"having.type==").append(SqlCondition.KEY_COLLECTION).append("\">\n");
		sb.append("${having.key}\n");
		sb.append("<foreach collection=\"having.value0\" item=\"i\" open=\"(\" separator=\",\" close=\")\">\n");
		sb.append("#{i}\n");
		sb.append("</foreach>\n");
		sb.append("</when>\n");
		sb.append("<when test=\"having.type==").append(SqlCondition.KEY_RAW).append("\">\n");
		sb.append("${having.key}\n");
		sb.append("</when>\n");
		sb.append("</choose>\n");
		sb.append("</if>\n");
		sb.append("</sql>\n");
	}

	private static final void writeSortSql(StringBuilder sb) {
		sb.append("<sql id=\"_SORT\">\n");
		sb.append("<if test=\"sortValid\">ORDER BY ${sorts}</if>\n");
		sb.append("</sql>\n");
	}

	private static final void writeLimitSql(StringBuilder sb) {
		sb.append("<sql id=\"_LIMIT\">\n");
		sb.append("<if test=\"limitValid\">LIMIT ${offset},${count}</if>\n");
		sb.append("</sql>\n");
	}

	private static final void writeInsertObj(StringBuilder sb, MapperConfigInfo info) {
		BeanInfo binfo = info.getBean();
		// start
		sb.append("<insert id=\"" + INSERT_OBJ + "\" parameterType=\"").append(binfo.getTypeName()).append('"');
		Field incCol = binfo.getIncColumn();
		if (incCol != null)
			sb.append(" useGeneratedKeys=\"true\" keyProperty=\"").append(incCol.getName()).append('"');
		sb.append(">\n");
		if (!binfo.isBaseBean())
			writeInsertObjSql(sb, binfo);
		else if (binfo.isMap())
			writeInsertMapSql(sb);
		else
			writeInsertSingleSql(sb);
		sb.append("</insert>\n");
	}

	private static final void writeInsertMap(StringBuilder sb, MapperConfigInfo info) {
		sb.append("<insert id=\"" + INSERT_MAP + "\" parameterType=\"map\"");
		Field incCol = info.getBean().getIncColumn();
		if (incCol != null)
			sb.append(" useGeneratedKeys=\"true\" keyProperty=\"").append(incCol.getName()).append('"');
		sb.append(">\n");
		writeInsertMapSql(sb);
		sb.append("</insert>\n");
	}

	private static final void writeInsertObjSql(StringBuilder sb, BeanInfo binfo) {
		Field[] fields = binfo.getFields();
		sb.append("INSERT INTO\n");
		sb.append("<include refid=\"_TABLE\" />\n");
		sb.append('(');
		for (int i = 0, len = fields.length; i < len; ++i) {
			if (i > 0)
				sb.append(',');
			sb.append('`').append(fields[i].getName()).append('`');
		}
		sb.append(")\n");
		sb.append("VALUES\n");
		sb.append('(');
		for (int i = 0, len = fields.length; i < len; ++i) {
			if (i > 0)
				sb.append(',');
			sb.append("#{").append(fields[i].getName()).append('}');
		}
		sb.append(")\n");
	}

	private static final void writeInsertSingleSql(StringBuilder sb) {
		sb.append("INSERT INTO <include refid=\"_TABLE\" /> VALUES (#{0})");
	}

	private static final void writeInsertMapSql(StringBuilder sb) {
		sb.append("INSERT INTO\n");
		sb.append("<include refid=\"_TABLE\" />\n");
		sb.append(
				"<foreach collection=\"_parameter\" index=\"key\"  open=\"(\" separator=\",\" close=\")\">${key}</foreach>");
		sb.append("VALUES\n");
		sb.append(
				"<foreach collection=\"_parameter\" item=\"value\" open=\"(\" separator=\",\" close=\")\">#{value}</foreach>");
	}

	private static final void writeSelectObj(StringBuilder sb, MapperConfigInfo info) {
		BeanInfo binfo = info.getBean();
		sb.append("<select id=\"" + SELECT_OBJ + "\" parameterType=\"").append(SQL_CRITERIA).append("\" resultType=\"")
				.append(binfo.getTypeName()).append("\">\n");
		sb.append("SELECT\n");
		sb.append("<include refid=\"_SELECT\" />\n");
		sb.append("FROM\n");
		sb.append("<include refid=\"_TABLE\" />\n");
		sb.append("<include refid=\"_WHERE\" />\n");
		sb.append("<include refid=\"_GROUP_BY\" />\n");
		sb.append("<include refid=\"_HAVING\" />\n");
		sb.append("<include refid=\"_SORT\" />\n");
		sb.append("<include refid=\"_LIMIT\" />\n");
		sb.append("<if test=\"tailValid\">\n");
		sb.append("${tailSql}\n");
		sb.append("</if>\n");
		sb.append("</select>\n");
	}

	private static final void writeSelectMap(StringBuilder sb) {
		sb.append("<select id=\"" + SELECT_MAP + "\" parameterType=\"").append(SQL_CRITERIA)
				.append("\" resultType=\"map\">\n");
		sb.append("SELECT\n");
		sb.append("<include refid=\"_SELECT\" />\n");
		sb.append("FROM\n");
		sb.append("<include refid=\"_TABLE\" />\n");
		sb.append("<include refid=\"_WHERE\" />\n");
		sb.append("<if test=\"tailSql != null\">\n");
		sb.append("${tailSql}\n");
		sb.append("</if>\n");
		sb.append("</select>\n");
	}

	private static final void writeUpdate(StringBuilder sb) {
		sb.append("<update id=\"" + UPDATE + "\" parameterType=\"").append(SQL_CRITERIA).append("\">\n");
		sb.append("UPDATE\n");
		sb.append("<include refid=\"_TABLE\" />\n");
		sb.append("<include refid=\"_UPDATE\" />\n");
		sb.append("<include refid=\"_WHERE\" />\n");
		sb.append("<if test=\"tailSql != null\">\n");
		sb.append("${tailSql}\n");
		sb.append("</if>\n");
		sb.append("</update>\n");
	}

	private static final void writeDelete(StringBuilder sb) {
		sb.append("<delete id=\"" + DELETE + "\" parameterType=\"").append(SQL_CRITERIA).append("\">\n");
		sb.append("DELETE FROM\n");
		sb.append("<include refid=\"_TABLE\" />\n");
		sb.append("<include refid=\"_WHERE\" />\n");
		sb.append("<if test=\"tailSql != null\">\n");
		sb.append("${tailSql}\n");
		sb.append("</if>\n");
		sb.append("</delete>\n");
	}

	private static final void writeXmlTail(StringBuilder sb) {
		sb.append("</mapper>");
	}
}
