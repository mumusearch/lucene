package com.kingbase.lucene.file.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IBaseFileService {

	/**
	 * 从上传的文件中 添加索引
	 * @param configName 配置文件名称
	 * @param files 文件集合
	 * @return 
	 */
	public boolean addIndexFromUpload(String configName, List<File> files);

	/**
	 * 从本地文件目录中添加索引
	 * @param luceneFileBase
	 * @param directory
	 * @return
	 */
	public boolean addIndexFromDirectory(String luceneFileBase, String directory);

	/**
	 * 搜索
	 * @param luceneFileBase
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public String search(String luceneFileBase, String fieldName, String fieldValue);

	/**
	 * 删除文档集合
	 * @param luceneFileBase
	 * @param id
	 * @param fieldValues
	 * @return
	 * @throws IOException 
	 */
	public String delete(String luceneFileBase, String fieldName, List<String> fieldValues) throws IOException;

	/**
	 * 删除所有的文档
	 * @param luceneFileBase
	 * @return
	 */
	public String deleteAll(String luceneFileBase);

}
