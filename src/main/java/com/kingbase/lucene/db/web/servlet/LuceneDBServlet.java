package com.kingbase.lucene.db.web.servlet;

import com.google.gson.Gson;
import com.kingbase.lucene.commons.searcher.BaseSearcher;
import com.kingbase.lucene.commons.searcher.HighLighterSearch;
import com.kingbase.lucene.db.domain.DBField;
import com.kingbase.lucene.db.domain.LuceneDBCache;
import com.kingbase.lucene.db.domain.LuceneDBDao;
import com.kingbase.lucene.db.service.IDBService;
import com.kingbase.lucene.db.service.impl.DBServiceImpl;
import org.apache.log4j.Logger;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns={"/db/LuceneDBServlet.action"})
public class LuceneDBServlet extends HttpServlet{
	private static final Logger log=Logger.getLogger(LuceneDBServlet.class);
	
	private static final String LUCENE_DB_BASE="lucene_db_base";
	
	private static final long serialVersionUID = 3023612254600703897L;
	Gson gson=new Gson();
	LuceneDBDao luceneDBDao=new LuceneDBDao();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String type=request.getParameter("type");
		String tableName=request.getParameter("tableName");
		String json="";
		switch (type) {
		//获取所有的表
		case "getAlltable":
			json=LuceneDBCache.getAllTable();
			break;
		//获取一个表的所有的字段
		case "getAllField":
			json=gson.toJson(LuceneDBCache.getAllField(tableName));
			break;
		//将表的数据加载到lucene索引中
		case "load":
			IDBService dbService=new DBServiceImpl();
			dbService.buildIndexes(tableName);
			//建立索引
			//new BuildIndex().buildDBIndexes(tableName);
			//获取自定义的gridpanel
			json=getGridPanel(tableName);
			log.info("加载表格"+json);
			break;
		//搜索
		case "search":
			String fieldName=request.getParameter("fieldName");
			String fieldValue=request.getParameter("fieldValue");
			//搜索
			json=search(fieldName.toUpperCase(),fieldValue,tableName);
			log.info("搜索结果"+json);
			break;
		default:
			break;
		}
		response.getWriter().println(json);
	}

	/**
	 * 搜素
	 * @param fieldName 域名
	 * @param fieldValue 查询的值
	 * @param tableName 表名
	 * @throws IOException 
	 */
	private String search(String fieldName, String fieldValue,String tableName) throws IOException {
		List<DBField> fields=LuceneDBCache.getAllField(tableName);
		List<Map<String, Object>> data=new ArrayList<Map<String,Object>>();
		BaseSearcher searcher=new HighLighterSearch(true,"<font color='red'></font>");
		TermQuery tableQuery=new TermQuery(new Term("TABLENAME",tableName));
		//如果字段值没有 则将该表的所有数据都搜索出来
		if("".equals(fieldValue)){
			data=searcher.termQuery(LUCENE_DB_BASE, "TABLENAME", tableName, Integer.MAX_VALUE);
		}else{
			//精准查询
			TermQuery termQuery=new TermQuery(new Term(fieldName,fieldValue));
			data=searcher.booleanQuery(new Query[]{tableQuery,termQuery},new Occur[]{Occur.MUST, Occur.MUST},LUCENE_DB_BASE, fieldName, Integer.MAX_VALUE);
			//前缀
			if(data.size()==0){
				PrefixQuery prefixQuery=new PrefixQuery(new Term(fieldName,fieldValue));
				data=searcher.booleanQuery(new Query[]{tableQuery,prefixQuery},new Occur[]{Occur.MUST, Occur.MUST},LUCENE_DB_BASE, fieldName, Integer.MAX_VALUE);
			}
			//模糊查询
			if(data.size()==0){
				FuzzyQuery fuzzyQuery=new FuzzyQuery(new Term(fieldName,fieldValue));
				data=searcher.booleanQuery(new Query[]{tableQuery,fuzzyQuery},new Occur[]{Occur.MUST, Occur.MUST},LUCENE_DB_BASE, fieldName, Integer.MAX_VALUE);
			}
		}
		String json=gson.toJson(data);
		
		//封装数据 返回浏览器
		StringBuilder fieldsBuilder=new StringBuilder();
		//遍历表的字段 获取fields
		Iterator<DBField> iterator = fields.iterator();
		while(iterator.hasNext()){
			DBField dbField= iterator.next();
			fieldsBuilder.append("{\"name\":\""+dbField.getFieldName()+"\"}");
		    if(iterator.hasNext()){
		    	fieldsBuilder.append(",");
		    }
		}
		json="{\"metaData\":{\"root\":\"data\",\"fields\":["+fieldsBuilder.toString()+"]},\"data\":"+json+"}";
		return json;
	}

	/**
	 * @param tableName 表的名称
	 * @return 自定义的gridPanel
	 */
	private String getGridPanel(String tableName) {
		List<DBField> dbFields=LuceneDBCache.getAllField(tableName);
		StringBuilder builder=new StringBuilder();
		StringBuilder grid=new StringBuilder();
		builder.append("[");
		Iterator<DBField> iterator = dbFields.iterator();
		//拼接columns属性
		while(iterator.hasNext()){
			DBField dbField = iterator.next();
			builder.append("{ text: '"+dbField.getFieldName()+"',  dataIndex: '"+dbField.getFieldName()+"' ,flex:1}");
		    if(iterator.hasNext()){
		    	builder.append(",");
		    }
		}
		builder.append("]");
		//拼接gridPanel
		grid.append("{xtype:'gridpanel',autoScroll:true,frame:true,id:'searchResultGridPanel',rowLines:true,columnLines:true,store:Ext.data.StoreManager.lookup('searchResultStore'),columns:"+builder.toString()+"}");
		return grid.toString();
	}
}
