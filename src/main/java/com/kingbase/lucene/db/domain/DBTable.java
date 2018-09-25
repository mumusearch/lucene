package com.kingbase.lucene.db.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * 数据库表的属性
 * @author lgan
 * @Param tableName
 * @Param tableType
 * @Param remarks
 */
public class DBTable {

	private String tableName;// 表名
	private String tableType;// 表的类型 view ,table,system table
	private String remarks;// 表的描述

	public DBTable() {
		super();
	}
	public DBTable(ResultSet rs) {
		try {
			this.tableName = rs.getString("TABLE_NAME");
			this.tableType =rs.getString("TABLE_TYPE");
			this.remarks = rs.getString("REMARKS");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public DBTable(String tableName, String tableType, String remarks) {
		super();
		this.tableName = tableName;
		this.tableType = tableType;
		this.remarks = remarks;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Override
	public String toString() {
		return "DBTable [tableName=" + tableName + ", tableType=" + tableType + ", remarks=" + remarks + "]";
	}

}
