package com.kingbase.lucene.file.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import com.kingbase.lucene.commons.configuration.ReadConfig;
import com.kingbase.lucene.file.config.FieldConfig;

/**
 * 抽取文件内容 属性
 * @author ganliang
 */
public class FetchFileDataUtil {

	/**
	 * 抽取 目录下的文件属性
	 * @param configName 配置文件名称
	 * @param directory 目录名称
	 * @throws FileNotFoundException 
	 */
	public List<Map<String,Object>> fetchFileDatas(String configName,String directory) throws FileNotFoundException{
		//获取目录下的所有文件
		FileUtil fileUtil=new FileUtil();
		List<File> files = fileUtil.listFiles(directory, true);
		
		//获取配置下的所有字段名称
		ReadConfig config=new ReadConfig(configName);
		Map<String, Map<String, String>> fields = config.getFields();
		
		List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();
		for (File file : files) {
			Map<String,Object> map=fetchFileData(file,fields);
			data.add(map);
		}
		return data;
	}
	
	/**
	 * 从文件集合中抽取数据
	 * @param configName
	 * @param files
	 * @return
	 * @throws FileNotFoundException
	 */
	public List<Map<String,Object>> fetchFileDatas(String configName, List<File> files) throws FileNotFoundException {
		ReadConfig config=new ReadConfig(configName);
		Map<String, Map<String, String>> fields = config.getFields();
		
		List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();
		for (File file : files) {
			Map<String,Object> map=fetchFileData(file,fields);
			data.add(map);
		}
		return data;
	}
	
	
	/**
	 * 从一个文件中抽取数据
	 * @param configName
	 * @param files
	 * @return
	 * @throws FileNotFoundException
	 */
	public List<Map<String,Object>> fetchFileDatas(String configName, File file) throws FileNotFoundException {
		ReadConfig config=new ReadConfig(configName);
		Map<String, Map<String, String>> fields = config.getFields();
		
		List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();
		Map<String,Object> map=fetchFileData(file,fields);
		data.add(map);
		return data;
	}
	
	

	/**
	 * 抽取文件的属性
	 * @param file
	 * @param fields
	 * @return
	 * @throws FileNotFoundException
	 */
	private Map<String, Object> fetchFileData(File file, Map<String, Map<String, String>> fields) throws FileNotFoundException {
		Map<String,Object> map=new HashMap<String,Object>();
		FileUtil fileUtil=new FileUtil();
		
		//遍历配置的字段集合
		for (Entry<String, Map<String, String>> entry : fields.entrySet()) {
			String key = entry.getKey().toUpperCase();
			switch (key) {
			case FieldConfig.ID:
				map.put(key, UUID.randomUUID().toString().replace("-", ""));
				break;
			case FieldConfig.FILENAME:
				String fileName = file.getName();
				int lastIndexOf = fileName.lastIndexOf(".");
				if(lastIndexOf!=-1){
					fileName=fileName.substring(0,lastIndexOf);
				}
				map.put(key, fileName);
				break;
			case FieldConfig.SIZE:
				map.put(key, file.length());
				break;
			case FieldConfig.CONTENT:
				map.put(key, new FileReader(file));
				break;
			case FieldConfig.ATTRIBUTE:
				map.put(key,fileUtil.getFileAttributes(file));
				break;
			case FieldConfig.TYPE:
				map.put(key,fileUtil.getType(file));
				break;
			case FieldConfig.PATH:
				map.put(key,file.getAbsolutePath());
				break;
			case FieldConfig.CREATEDATE:
				map.put(key,file.lastModified());
				break;
			case FieldConfig.LASRMODIFYDATE:
				map.put(key,file.lastModified());
				break;
			default:
				break;
			}
		}
		return map;
	}

	
}
