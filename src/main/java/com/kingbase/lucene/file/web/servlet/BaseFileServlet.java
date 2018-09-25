package com.kingbase.lucene.file.web.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.kingbase.lucene.commons.configuration.ReadConfig;
import com.kingbase.lucene.file.config.FieldConfig;
import com.kingbase.lucene.file.service.IBaseFileService;
import com.kingbase.lucene.file.service.impl.BaseFileServiceImpl;
import com.kingbase.lucene.file.utils.FileUtil;

/**
 * 基本检索
 * @author lgan
 */
@WebServlet(urlPatterns = { "/file/BaseFileServlet.action" })
public class BaseFileServlet extends HttpServlet {

	private static final Properties properties=new Properties();
	
	private static final long serialVersionUID = 7930573704078335991L;
	private static final Logger log=Logger.getLogger(BaseFileServlet.class);
	private static final String LUCENE_FILE_BASE = "lucene_file_base";// 索引配置文件

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		InputStream stream =BaseFileServlet.class.getResourceAsStream("/fileupload.properties");
		if(stream==null){
			log.warn("fileupload.properties配置文件不存在");
		}else{
			try {
				properties.load(stream);
			} catch (IOException e) {
			}
		}
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		if (type == null) {
			throw new IllegalArgumentException();
		}

		switch (type) {
		// 进入到索引目录
		case "index":
			log.debug("跳转到pages/file/base/index.jsp页面");
			response.sendRedirect(request.getContextPath() + "/pages/file/base/index.jsp");
			break;
		// 添加索引
		case "addIndex":
			String isMultipartContent = request.getParameter("isMultipartContent");
			//是文件的上传
			if(isMultipartContent!=null&&"true".equalsIgnoreCase(isMultipartContent)){
				addIndexFromUpload(request, response);
			}
			//从本地文件目录
			else{
				addIndexFromDirectory(request, response);
			}
			break;
		//获取配置的域名
		case "getFieldNames":
			getFieldNames(request, response);
			break;
		//查询
		case "search":
			search(request, response);
			break;
		//删除选择的文档
		case "delete":
			delete(request, response);
			break;
		//删除所有的文档
		case "deleteAll":
			deleteAll(request, response);
			break;
		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * 从上传的文件中 添加索引
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings("deprecation")
	private void addIndexFromUpload(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String tempPath=properties.getProperty("tempPath");//临时目录的名称
		StringBuilder result=new StringBuilder();
		result.append("索引............................/n/n");
		result.append("开始创建索引  "+new Date().toLocaleString()+"/n/n");
		boolean success=true;
		
		if(tempPath==null){
			tempPath="/temp";
		}
		String filePath=request.getSession().getServletContext().getRealPath(tempPath);//临时目录的绝对路径
		File tempFile = new File(filePath);
		if(!tempFile.exists()){
			tempFile.mkdirs();//如果临时文件不存在 则级联创建
	    }
		
		String maxSize=properties.getProperty("maxSize");//上传文件的最大字节
		int maxSizeMB=2;//默认最大上传文件大小
		if(maxSize==null){
			maxSizeMB=Integer.parseInt(maxSize);
		}
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 最大缓存
		factory.setSizeThreshold(maxSizeMB * 1024);
		// 设置临时文件目录
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 文件最大上限
		upload.setSizeMax(maxSizeMB * 1024 * 1024);
		try {
			List<File> files=new ArrayList<File>();
			// 获取所有文件列表
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {
				if (!item.isFormField()) {
					// 文件名
					String fileName = item.getName();
					File file = new File(filePath,fileName);
					item.write(file);
					files.add(file);
				}
			}
			//添加索引
			IBaseFileService baseFileService=new BaseFileServiceImpl();
			baseFileService.addIndexFromUpload(LUCENE_FILE_BASE,files);
			
			//删除临时文件
			FileUtil fileUtil=new FileUtil();
			fileUtil.deleteFiles(files);
			result.append("创建索引成功................./n/n");
		} catch (Exception e) {
			success=false;
			result.append("创建索引失败/n"+e.getLocalizedMessage()+"/n/n");
		}
		result.append("创建索引结束  "+new Date().toLocaleString()+"/n/n");
		out.print("{success:"+success+",result:'"+result.toString()+"'}");
		log.debug("创建索引  "+result);
	}

    /**
     * @param request
     * @param response
     * @throws IOException 
     */
	@SuppressWarnings("deprecation")
	private void addIndexFromDirectory(HttpServletRequest request, HttpServletResponse response) throws IOException {
		StringBuilder result=new StringBuilder();
		result.append("索引............................/n/n");
		result.append("开始创建索引  "+new Date().toLocaleString()+"/n/n");
		
		String directory=request.getParameter("directory");
		IBaseFileService baseFileService=new BaseFileServiceImpl();
		baseFileService.addIndexFromDirectory(LUCENE_FILE_BASE,directory);
		result.append("创建索引成功................./n/n");
		result.append("创建索引结束  "+new Date().toLocaleString()+"/n/n");
		response.getWriter().print("{success:true,result:'"+result.toString()+"'}");
	}
	
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void search(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fieldName=request.getParameter("fieldName");//域名
		String fieldValue=request.getParameter("fieldValue");//输入值
		
		IBaseFileService baseFileService=new BaseFileServiceImpl();
		//检测索引目录是否存在
		ReadConfig config=new ReadConfig(LUCENE_FILE_BASE);
		String dir = config.getDir();
		File dirFile=new File(dir);
		String json="";
		if(!dirFile.exists()||!dirFile.isDirectory()||dirFile.listFiles()==null){
			json="{success:false,msg:'请先创建索引,再来搜索'}";
		}else{
			json=baseFileService.search(LUCENE_FILE_BASE,fieldName,fieldValue);
		}
		log.debug("搜索结果  "+json);
		response.getWriter().print(json);
	}
	
	/**
	 * 获取域集合
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void getFieldNames(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ReadConfig config=new ReadConfig(LUCENE_FILE_BASE);
		Map<String, Map<String, String>> fields = config.getFields();
		StringBuilder builder=new StringBuilder("[");
		Iterator<String> iterator = fields.keySet().iterator();
		while(iterator.hasNext()){
			String fieldName = iterator.next();
			Map<String, String> map = fields.get(fieldName);
			builder.append("{'fieldName':'"+fieldName+"','fieldValue':'"+map.get(FieldConfig.NAME.toLowerCase())+"'}");
			if(iterator.hasNext()){
				builder.append(",");
			}
		}
		builder.append("]");
		
		String json="{'metaData':{'fields':[{'name':'fieldName'},{'name':'fieldValue'}],'root':'data'},'data':"+builder.toString()+"}";
		response.getWriter().print(json);
		log.debug("域下拉框...."+json);
	}

	/**
	 * 删除选择的文档
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings({"unchecked", "rawtypes" })
	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data=request.getParameter("value");
		Gson gson=new Gson();
		List<Map> list = gson.fromJson(data, List.class);
		
		IBaseFileService baseFileService=new BaseFileServiceImpl();
		List<String> fieldValues=new ArrayList<String>();
		for (Map map : list) {
			fieldValues.add(map.get(FieldConfig.ID).toString());
		}
		String json=baseFileService.delete(LUCENE_FILE_BASE,FieldConfig.ID,fieldValues);
		response.getWriter().print(json);
	}
	
    /**
     * 删除所有的文档
     * @param request
     * @param response
     * @throws IOException 
     */
	private void deleteAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
		IBaseFileService baseFileService=new BaseFileServiceImpl();
		String json=baseFileService.deleteAll(LUCENE_FILE_BASE);
		response.getWriter().print(json);
	}
}
