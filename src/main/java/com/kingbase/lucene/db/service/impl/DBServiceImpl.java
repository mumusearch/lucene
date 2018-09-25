package com.kingbase.lucene.db.service.impl;

import java.io.StringReader;
import java.util.List;

import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import com.kingbase.lucene.db.domain.DBField;
import com.kingbase.lucene.db.service.IDBService;

public class DBServiceImpl implements IDBService{

	@Override
	public void buildIndexes(String tableName) {
		
	}
	
	/**
	 * 根据数据库字段的设计而生成field
	 * @param fieldName 字段名称
	 * @param fieldValue 字段值
	 * @param dbfields 该表所有字段的属性
	 * @return
	 */
	private Field createDBField(String fieldName, String fieldValue, List<DBField> dbfields) {
		Field field=null;
		for (DBField dbField : dbfields) {
			//找到该字段的属性
			if(fieldName.equalsIgnoreCase(dbField.getFieldName())){
				String fieldType = dbField.getFieldType();
				switch (fieldType) {
				case "VARCHAR"://字符串类型
					if(fieldValue.length()>100){//当字符串长度大于100时 不存储
						field=new StringField(fieldName, fieldValue, Store.NO);
					}else{
						field=new StringField(fieldName, fieldValue, Store.YES);
					}
					break;
				case "INT":
				case "LONG"://数字内省 统一以long型为准
					field=new LongField(fieldName, Long.parseLong(fieldValue), Store.YES);
					break;
				case "FLOAT":
				case "DOUBLE"://浮点类型统一以double为准
					field=new DoubleField(fieldName, Double.parseDouble(fieldValue), Store.YES);
					break;
				default://流  日期
					field=new TextField(fieldName, new StringReader(fieldValue));
					break;
				}
				break;
			}
		}
		return field;
	}
}
