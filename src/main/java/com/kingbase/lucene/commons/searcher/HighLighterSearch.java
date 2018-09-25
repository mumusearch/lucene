package com.kingbase.lucene.commons.searcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;

import com.kingbase.lucene.commons.configuration.ReadConfig;
import com.kingbase.lucene.commons.indexer.index.DocumentBuilding;
import com.kingbase.lucene.commons.indexer.indexSearcher.IndexSearcherFactory;

/**
 * 高亮查询
 * 
 * @author ganliang
 */
public class HighLighterSearch extends BaseSearcher {

	// 查询结果是否高亮显示
	private boolean highlighter;
	// 高亮显示的格式 系统默认的为 <B></B>
	private String formatter;
	// 高亮格式的前缀
	private String preTag;
	// 高亮格式的后缀
	private String postTag;

	public HighLighterSearch() {
		this.highlighter = true;
		this.formatter = null;
	}

	/**
	 * lucene highlighter默认的高亮格式为 <B></B>
	 * @param highlighter 是否高亮
	 */
	public HighLighterSearch(boolean highlighter) {
		this.highlighter = highlighter;
		this.formatter = null;
	}
	
	/**
	 * @param highlighter 是否高亮
	 * @param formatter 高亮自定义的格式  例子 <a><font color='red'></font></a>
	 */
	public HighLighterSearch(boolean highlighter,String formatter){
		this.highlighter=highlighter;
		this.formatter=formatter;
		if(formatter!=null){
			int lastIndexOf = formatter.indexOf("/");
			if(lastIndexOf!=-1){
				preTag=formatter.substring(0, lastIndexOf-1);
				postTag=formatter.substring(lastIndexOf-1);
			}
		}
	}

	/**
	 * 高亮显示 匹配的字符
	 * @param query
	 * @param count
	 * @throws IOException
	 * @throws InvalidTokenOffsetsException 
	 */
	public List<Map<String, Object>> search(Query query, String configName, int count, String fieldName)
			throws IOException {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		// 获取indexSearch
		IndexSearcher indexSearcher = IndexSearcherFactory.create(configName);

		// 查询
		TopDocs topDocs = indexSearcher.search(query, count);

		if (highlighter && fieldName != null) {
			data = highLighter(query, topDocs, indexSearcher, fieldName);
		} else {
			DocumentBuilding documentBuilding = new DocumentBuilding();
			// 获取文档集合
			List<Document> docs = documentBuilding.handleTopDocs(indexSearcher, topDocs);
			data = documentBuilding.parseDocuments(docs);
		}
		return data;
	}

	/**
	 * 高亮显示 匹配的字符
	 * @param query
	 * @param count
	 * @throws IOException 
	 * @throws InvalidTokenOffsetsException 
	 */
	private List<Map<String, Object>> highLighter(Query query, TopDocs topDocs, IndexSearcher indexSearcher,
			String fieldName) throws IOException {
		Highlighter highlighter = null;
		QueryScorer scorer = new QueryScorer(query);
		Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
		// 高亮的格式 默认情况下为 <B></B>
		if (formatter != null) {
			Formatter formatter = new SimpleHTMLFormatter(preTag, postTag);
			// 创建自定义高亮格式的Highlighter
			highlighter = new Highlighter(formatter, scorer);
		} else {
			// 创建默认高亮格式的Highlighter
			highlighter = new Highlighter(scorer);
		}
		highlighter.setTextFragmenter(fragmenter);
		// 获取analyzer
		ReadConfig config=new ReadConfig();
		Analyzer analyzer = config.getAnalyzer();
		
		DocumentBuilding documentBuilding = new DocumentBuilding();
		List<Document> documents = documentBuilding.handleTopDocs(indexSearcher, topDocs);
		return documentBuilding.parseHighLighterDocuments(documents,highlighter,analyzer,fieldName);
	}
}
