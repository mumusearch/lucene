package com.kingbase.lucene.commons.indexer.index;

import com.kingbase.lucene.commons.indexer.indexWriter.IndexWriterFactory;
import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 创建索引
 * @author ganliang
 */
public class IndexBuilding {
	private static final Logger log=Logger.getLogger(IndexBuilding.class);
	
	/**
	 * 创建索引
	 * @param data
	 * @param configName
	 */
	public void build(Map<String,Object> data,String configName){
		try {
			//获取IndexWriter
			IndexWriter indexWriter = IndexWriterFactory.create(configName);
			//获取Document
			DocumentBuilding documentBuilding=new DocumentBuilding();
			
			log.debug("开始创建索引...............");
			Document document = documentBuilding.createDocument(data, configName);
			//建立索引
			indexWriter.addDocument(document);
			
			//关闭IndexWriter
			IndexWriterFactory.close(configName);
			log.debug("创建索引结束...............");
		} catch (IOException e) {
			IndexWriterFactory.rollback(configName);//回滚
			log.error("建立索引失败",e);
		}
		
	}

	/**
	 * 创建索引
	 * @param list 数据map
	 * @param configName
	 * @throws IOException 
	 */
	public void build(List<Map<String,Object>> list,String configName) throws IOException{
		try {
			log.debug("开始创建索引...............");
			//获取IndexWriter
			IndexWriter indexWriter = IndexWriterFactory.create(configName);
			//获取Document
			DocumentBuilding documentBuilding=new DocumentBuilding();
			List<Document> documents = documentBuilding.createDocuments(list, configName);
			
			//建立索引
			indexWriter.addDocuments(documents);
			
			//关闭IndexWriter
			IndexWriterFactory.close(configName);
			log.debug("创建索引结束...............");
		} catch (IOException e) {
			IndexWriterFactory.rollback(configName);//回滚
			log.error("建立索引失败",e);
			throw new IOException();
		}
	}
	
	/**
	 * 删除指定的索引
	 * @param configName
	 * @throws IOException 
	 */
	public void delete(String configName,String fieldName,String fieldValue) throws IOException{
		//获取IndexWriter
		IndexWriter indexWriter = IndexWriterFactory.create(configName);
		
		//删除指定的文档
		indexWriter.deleteDocuments(new Term(fieldName,fieldValue));
		log.debug("清空文档成功......................");
		//关闭IndexWriter
		IndexWriterFactory.close(configName);
	}
	
	/**
	 * 删除多个文档
	 * @param configName
	 * @throws IOException 
	 */
	public void delete(String configName,String fieldName,List<String> fieldValues) throws IOException{
		//获取IndexWriter
		IndexWriter indexWriter = IndexWriterFactory.create(configName);
		Term[] terms=new Term[fieldValues.size()];
		for(int i=0;i<fieldValues.size();i++){
			terms[i]=new Term(fieldName,fieldValues.get(i));
		}
		//删除文档集合
		indexWriter.deleteDocuments(terms);
		log.debug("删除文档成功......................");
		//关闭IndexWriter
		IndexWriterFactory.close(configName);
	}
	
	/**
	 * 清空索引
	 * @param configName
	 * @throws IOException 
	 */
	public void clear(String configName) throws IOException{
		//获取IndexWriter
		IndexWriter indexWriter = IndexWriterFactory.create(configName);
		
		//删除所有的索引
		indexWriter.deleteAll();
		log.debug("清空文档成功......................");
		indexWriter.deleteUnusedFiles();
		//关闭IndexWriter
		IndexWriterFactory.close(configName);
	}
}
