package com.kingbase.lucene.file.web.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;

import com.google.gson.Gson;
import com.kingbase.lucene.commons.analyzer.AnalyzerConfig;
import com.kingbase.lucene.commons.analyzer.built.core.TokenAttributes;

/**
 * 将短语分解生成 语汇单元
 * @author ganliang
 */
@WebServlet(urlPatterns={"/file/TokenStreamServlet.action"})
public class TokenStreamServlet extends HttpServlet{
	
	private static final long serialVersionUID = -4899578263222282340L;
	
	private static final Logger log=Logger.getLogger(TokenStreamServlet.class);
	private static final String ANALYZERSTXT="/analyzers.txt";
	private static String analyzers="[]";
	
	@Override
	public void init() throws ServletException {
		super.init();
		//获取分析器配置文件信息
		InputStream stream = TokenStreamServlet.class.getResourceAsStream(ANALYZERSTXT);
		if(stream==null){
		   log.error("分解器配置文件【"+ANALYZERSTXT+"】不存在");
		}
		try {
			BufferedReader reader=new BufferedReader(new InputStreamReader(stream));
			StringBuilder builder=new StringBuilder("[");
			String readline=null;
			while((readline=reader.readLine())!=null){
				String[] strs = readline.split("=");
				if(strs.length>=2){
					builder.append("{'analyzerName':'"+strs[0]+"','analyzerDesc':'"+strs[1]+"'},");
				}
			}
			builder.append("]");
			int lastIndexOf = builder.lastIndexOf(",");
			if(lastIndexOf!=-1){
				builder.deleteCharAt(lastIndexOf);
			}
			analyzers=builder.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		if (type == null) {
			throw new IllegalArgumentException();
		}
		switch (type) {
	    //获取分解器
		case "getAnalyzers":
			response.getWriter().print(analyzers);
			break;
		//分解短语
		case "tokenStream":
			tokenStream(request,response);
			break;
		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * 分解短语
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings("rawtypes")
	private void tokenStream(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String value=request.getParameter("value");
		Gson gson=new Gson();
		Map map = gson.fromJson(value, Map.class);
		String inputValue=(String) map.get("inputValue");
		String analyzerName=(String) map.get("analyzerName");
		//获取解析器
		AnalyzerConfig analyzerConfig=new AnalyzerConfig();
		Analyzer analyzer=analyzerConfig.analyzer(analyzerName);
		
		//获取语汇单元的项
		TokenAttributes attributes=new TokenAttributes();
		String token = attributes.tokenTerm(analyzer, "", inputValue);
		response.getWriter().print(analyzerName+"  "+token);
		log.debug("语汇单元项  "+token);
	}

}
