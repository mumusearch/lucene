package com.kingbase.lucene.db.domain;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

/**
 * 缓存lucene的 全部表名 标的全部属性
 * @author Administrator
 *
 */
public class LuceneDBCache {
	public static volatile String ALL_TABLE=null;//保存所有的表名
	public static Map<String,List<DBField>> TABLE_FIELD=new ConcurrentHashMap<String,List<DBField>>();//key 为表名 值为表的字段
	private static LuceneDBDao luceneDBDao=new LuceneDBDao();
	private static Gson gson=new Gson();
	private static final Logger log=Logger.getLogger(LuceneDBCache.class);
	
	/**
	 * @return 从内存中或者数据库中获取所有的表
	 */
	public static String getAllTable() {
		if(ALL_TABLE==null){
			ReentrantLock lock=new ReentrantLock();
			try{
				lock.lock();//上锁
			    List<DBTable> allTable = luceneDBDao.getAllTable();
			    ALL_TABLE=gson.toJson(allTable);
			    log.info("从数据库查询所有表的数据"+ALL_TABLE);
			}finally{
				lock.unlock();//解锁
			}
		}else{
		    log.info("从内存中获取所有表的数据"+ALL_TABLE);
		}
		return ALL_TABLE;
	}
	
	/**
	 * @return 获取一个表的字段属性
	 */
    public static List<DBField> getAllField(String tableName){
    	List<DBField> dbfields=TABLE_FIELD.get(tableName);
    	if(dbfields==null){//如果表的字段属性还没有保存到内存 则查询数据库
    		ReentrantLock lock=new ReentrantLock();
			try{
				lock.lock();//上锁
				dbfields=luceneDBDao.getAllField(tableName);
				TABLE_FIELD.put(tableName, dbfields);
			    log.info("从数据库查询表【"+tableName+"】所有的属性"+TABLE_FIELD);
			}finally{
				lock.unlock();//解锁
			}
    	}else{
		    log.info("从内存中获取表【"+tableName+"】所有的属性"+ALL_TABLE);
    	}
    	return dbfields;
    }
    
}
