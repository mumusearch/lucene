package com.kingbase.lucene.file.web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.kingbase.lucene.commons.configuration.ConfigInitialization;
import com.kingbase.lucene.file.service.IconfigService;
import com.kingbase.lucene.file.service.impl.ConfigServiceImpl;

/**
 * 配置集合
 * @author ganliang
 */
@WebServlet(urlPatterns={"/file/ConfigServlet.action"})
public class ConfigServlet extends HttpServlet{

	private static final long serialVersionUID = -4654276658945109880L;

	private static final Logger log=Logger.getLogger(ConfigServlet.class);
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		if (type == null) {
			throw new IllegalArgumentException();
		}
		switch (type) {
	    //获取配置信息
		case "load":
			Map<String, Map<String, Object>> mapData = ConfigInitialization.getMapData();
			Gson gson=new Gson();
			String json = gson.toJson(mapData);
			response.getWriter().print(json);
			log.debug("读取配置信息  "+json);
			break;
		//更新字段集合
		case "update":
			IconfigService configService=new ConfigServiceImpl();
			String configName=request.getParameter("configName");
			String fieldName=request.getParameter("fieldName");
			String store=request.getParameter("store");
			String tokenized=request.getParameter("tokenized");
			String indexed=request.getParameter("indexed");
			
			Map<String,String> map=new HashMap<String,String>();
			map.put("configName", configName);
			map.put("fieldName", fieldName);
			map.put("store", store);
			map.put("tokenized", tokenized);
			map.put("indexed", indexed);
			
			boolean success=configService.updateConfig(map);
			response.getWriter().print("{success:"+success+"}");
			break;
		default:
			throw new IllegalArgumentException();
		}
	}

}
