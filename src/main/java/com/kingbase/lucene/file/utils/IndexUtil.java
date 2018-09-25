package com.kingbase.lucene.file.utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.kingbase.lucene.commons.indexer.index.IndexBuilding;

/**
 * 创建索引
 * @author ganliang
 */
public class IndexUtil {

	/**
	 * 根据一个目录 来创建索引
	 * @param configName
	 * @param directory
	 * @throws IOException 
	 */
	public void createIndex(String configName,String directory) throws IOException{
		//抽取文件数据
		FetchFileDataUtil dataUtil=new FetchFileDataUtil();
		List<Map<String,Object>> datas = dataUtil.fetchFileDatas(configName, directory);
		
		//创建索引
		IndexBuilding indexBuilding=new IndexBuilding();
		indexBuilding.build(datas, configName);
	}
	
	/**
	 * 自定义map集合创建索引
	 * @param configName
	 * @param list
	 * @throws IOException 
	 */
	public void createIndex(String configName,List<Map<String,Object>> list) throws IOException{
		//创建索引
		IndexBuilding indexBuilding=new IndexBuilding();
		indexBuilding.build(list, configName);
	}
	
	/**
	 * 自定义map创建索引
	 * @param configName
	 * @param list
	 */
	public void createIndex(String configName,Map<String,Object> data){
		//创建索引
		IndexBuilding indexBuilding=new IndexBuilding();
		indexBuilding.build(data, configName);
	}
	
	/**
	 * 清空索引
	 * @param configName
	 * @throws IOException
	 */
	public void clear(String configName) throws IOException{
		IndexBuilding indexBuilding=new IndexBuilding();
		indexBuilding.clear(configName);
	}
	
	/**
	 * 删除指定的文档
	 * @param configName
	 * @throws IOException
	 */
	public void delete(String configName,String fieldName,String fieldValue) throws IOException{
		IndexBuilding indexBuilding=new IndexBuilding();
		indexBuilding.delete(configName, fieldName, fieldValue);
	}
	
	/**
	 * 删除指定的文档
	 * @param configName
	 * @throws IOException
	 */
	public void delete(String configName,String fieldName,List<String> fieldValues) throws IOException{
		IndexBuilding indexBuilding=new IndexBuilding();
		indexBuilding.delete(configName, fieldName, fieldValues);
	}
}
