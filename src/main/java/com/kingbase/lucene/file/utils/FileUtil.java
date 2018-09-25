package com.kingbase.lucene.file.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 文件工具类
 * @author ganliang
 */
public class FileUtil {

	/**
	 * 列出目录下的所有子目录
	 * @param directory
	 * @param childrens
	 * @return
	 * @throws FileNotFoundException
	 */
	public List<File> listFiles(String directory,boolean childrens) throws FileNotFoundException{
		
		List<File> files=new ArrayList<File>();
		File file=new File(directory);
		//当目录是一个文件的时候 直接返回该文件
		if(file.isFile()){
			files.add(file);
			return files;
		}
		
		checkDirectory(directory);
		
		if(childrens){
			iteratorDirectory(file,files);
		}else{
			files=listFiles(file);
		}
		return files;
	}
	
	/**
	 * 列出目录的直接文件(不包括子目录的文件)
	 * @param file
	 * @return
	 */
	private List<File> listFiles(File file) {
		List<File> list=new ArrayList<File>();
		File[] listFiles = file.listFiles();
		for (File f : listFiles) {
			if(f.isFile()){
				list.add(f);
			}
		}
		return list;
	}
	
	/**
	 * 迭代目录
	 * @param file
	 * @param files
	 */
	private void iteratorDirectory(File file, List<File> files) {
		if(file.isFile()){
			files.add(file);
		}else if(file.isDirectory()){
			File[] fs = file.listFiles();
			if(fs!=null){
				for (File f : fs) {
					iteratorDirectory(f, files);
				}
			}
		}
	}

	/**
	 * 列出目录下的所有子目录
	 * @param directory
	 * @param childrens
	 * @param includers 
	 * @return
	 * @throws FileNotFoundException
	 */
	public List<File> listIncludesFiles(String directory,boolean childrens,List<String> includes) throws FileNotFoundException{
		if(includes==null){
			return null;
		}
		List<File> files=new ArrayList<File>();
		
		File file=new File(directory);
		//当目录是一个文件的时候 直接返回该文件
		if(file.isFile()){
			files.add(file);
			return files;
		}
		checkDirectory(directory);
		
		if(childrens){
			iteratorIncludesDirectory(file,files,includes);
		}else{
			files=listIncludesFiles(file,includes);
		}
		return files;
	}
	
	private List<File> listIncludesFiles(File file, final List<String> includes) {
		File[] listFiles = file.listFiles(new FileFilter() {
			@Override
			public boolean accept(File f) {
				if(f.isFile()){
					String fileType = getFileType(f);
					if(includes.contains(fileType)){
						return true;
					}
				}
				return false;
			}
		});
		
		return Arrays.asList(listFiles);
	}

	private void iteratorIncludesDirectory(File file, List<File> files, List<String> includes) {
		if(file.isFile()){
			String fileType = getFileType(file);
			if(includes.contains(fileType)){
				files.add(file);
			}
		}else if(file.isDirectory()){
			File[] fs = file.listFiles();
			if(fs!=null){
				for (File f : fs) {
					iteratorIncludesDirectory(f, files,includes);
				}
			}
		}
	}

	/**
	 * 检测目录
	 * @param directory 目录的绝对地址
	 * @return
	 */
	private void checkDirectory(String directory){
		if(directory==null||"".equals(directory)){
			throw new IllegalArgumentException();
		}
		File file=new File(directory);
		if(!file.exists()){
			throw new IllegalArgumentException("文件找不到");
		}
		if(!file.isDirectory()){
			throw new IllegalArgumentException("不是目录");
		}
	}
	
	/**
	 * 获取文件的类型
	 * @param file
	 * @return
	 */
	private String getFileType(File file){
		String fileType = "文件";
		String fileName = file.getName();
		int lastIndexOf = fileName.lastIndexOf(".");
		if(lastIndexOf!=-1){
			fileType=fileName.substring(lastIndexOf+1);
		}
		return fileType;
	}

	/**
	 * 获取文件的权限
	 * @param file
	 * @return rwx
	 */
	public String getFileAttributes(File file) {
		StringBuilder attributes=new StringBuilder();
		if(file.canRead()){
			attributes.append("r");
		}
		if(file.canWrite()){
			attributes.append("w");
		}
		if(file.canExecute()){
			attributes.append("x");
		}
		return attributes.toString();
	}

	/**
	 * 获取文件的类型
	 * @param file
	 * @return
	 */
	public Object getType(File file) {
		String type="文件";
		String fileName = file.getName();
		int lastIndexOf = fileName.lastIndexOf(".");
		if(lastIndexOf!=-1){
			type=fileName.substring(lastIndexOf+1);
		}
		return type;
	}
	
	/**
	 * 删除集合文件
	 * @param files
	 */
	public void deleteFiles(List<File> files){
		if(files==null){
			return;
		}
		for (File file : files) {
			file.deleteOnExit();
		}
	}
}
