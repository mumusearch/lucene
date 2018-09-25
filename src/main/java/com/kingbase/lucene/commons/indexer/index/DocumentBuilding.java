package com.kingbase.lucene.commons.indexer.index;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.util.BytesRef;

import com.kingbase.lucene.commons.configuration.ReadConfig;

/**
 * 创建索引
 * @author ganliang
 */
public class DocumentBuilding {

	private static final Logger log=Logger.getLogger(DocumentBuilding.class);
	
	/**
	 * 生成文档
	 * @param data
	 * @param configName 
	 * @return 文档
	 */
	public Document createDocument(Map<String,Object> data,String configName){
		if(data==null){
			throw new IllegalArgumentException();
		}
		
		ReadConfig config=new ReadConfig(configName);
		FieldBuilding fieldBuilding=new FieldBuilding();
		
		Document documnet=new Document();
		for (Entry<String, Object> entry : data.entrySet()) {
			String fieldName = entry.getKey();//字段名称
			Object fieldValue = entry.getValue();//字段值
			
			Map<String, String> attributes = config.getField(fieldName);//获取字段的属性值
			//过滤
			if(attributes==null||fieldValue==null||"".equals(fieldValue.toString())){
				continue;
			}
			Field field = fieldBuilding.createField(attributes,fieldName,fieldValue);
			documnet.add(field);
			log.debug("创建Field【"+fieldName+"="+fieldValue+"】 "+field.toString());
		}
		return documnet;
	}
	
	/**
	 * 生成多个文档
	 * @param data
	 * @param configName
	 * @return
	 */
	public List<Document> createDocuments(List<Map<String,Object>> data,String configName){
		if(data==null||configName==null){
			throw new IllegalArgumentException();
		}
		List<Document> documents=new ArrayList<Document>();
		
		ReadConfig config=new ReadConfig(configName);
		FieldBuilding fieldBuilding=new FieldBuilding();
		//遍历集合
		for (Map<String, Object> map : data) {
			Document documnet=new Document();
			//遍历map
			for (Entry<String, Object> entry : map.entrySet()) {
				String fieldName = entry.getKey();//字段名称
				Object fieldValue = entry.getValue();//字段值
				
				Map<String, String> attributes = config.getField(fieldName);//获取字段的属性值
				Field field = fieldBuilding.createField(attributes,fieldName,fieldValue);
				documnet.add(field);
			}
			log.debug("建立索引"+documnet);
			documents.add(documnet);
		}
		return documents;
	}

	/**
	 * 解析documents 
	 * @param docs 文档集合
	 * @return
	 */
	public List<Map<String, Object>> parseDocuments(List<Document> docs) {
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		//遍历文档集合
		for (Document document : docs) {
			list.add(parseDocument(document));
		}
		return list;
	}

	/**
	 * 解析单个document
	 * @param document 文档
	 * @return
	 */
	private Map<String, Object> parseDocument(Document document) {
		Map<String,Object> map=new HashMap<String,Object>();
		List<IndexableField> fields = document.getFields();
		//遍历文档的多个域
		for (IndexableField indexableField : fields) {
			String name = indexableField.name();
			//如果是字符串 int float double long
			Object fieldValue = document.get(name);
			//如果是字节流
			if(fieldValue==null){
				fieldValue=document.getBinaryValue(name);
			}
			map.put(name, fieldValue);
		}
		return map;
	}
	
	/**
	 * 获取搜索出来的文档
	 * @param indexSearcher 
	 * @param topDocs 
	 * @return
	 * @throws IOException
	 */
	public List<Document> handleTopDocs(IndexSearcher indexSearcher, TopDocs topDocs) throws IOException {
		List<Document> documents=new ArrayList<Document>();
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : scoreDocs) {
			Document document = indexSearcher.doc(scoreDoc.doc);
			documents.add(document);
		}
		return documents;
	}

	/**
	 * 处理高亮的文档集合
	 * @param documents
	 * @param highlighter
	 * @param analyzer
	 * @param fieldName
	 * @return
	 * @throws IOException
	 * @throws InvalidTokenOffsetsException
	 */
	public List<Map<String, Object>> parseHighLighterDocuments(List<Document> documents,Highlighter highlighter,Analyzer analyzer,String fieldName) throws IOException {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		for (Document document : documents) {
			Map<String,Object> map=parseHighLighterDocument(document,highlighter,analyzer, fieldName);
			data.add(map);
		}
		return data;
	}

	/**
	 * 解析附带高亮的文档
	 * @param document
	 * @param highlighter
	 * @param analyzer
	 * @param fieldName
	 * @return
	 * @throws IOException
	 */
	private Map<String, Object> parseHighLighterDocument(Document document, Highlighter highlighter, Analyzer analyzer,
			String fieldName) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		Iterator<IndexableField> iterator = document.iterator();
		//迭代遍历字段
		try {
			while (iterator.hasNext()) {
				IndexableField field = iterator.next();
				String name = field.name();
				Object value = null;
				// 如果该字段是 搜索的字段 则高亮显示匹配的字段
				if (name.equals(fieldName)) {
					String stringValue = document.get(fieldName);
					BytesRef binaryValue = document.getBinaryValue(fieldName);
					if (binaryValue != null) {
						stringValue = new String(binaryValue.bytes, "UTF-8");
					}
					// 如果是数字 高亮 会返回null
					value = highlighter.getBestFragment(analyzer, fieldName, stringValue);
					if (value == null || "".equals(value)) {
						value = stringValue;
					}
				} else {
					// 如果是数字类型 返回数字 否则返回null
					Number numericValue = field.numericValue();
					// 如果是String类型 返回数字 否则返回null
					String stringValue = field.stringValue();
					// 如果是Reader类型 返回数字 否则返回null
					Reader readerValue = field.readerValue();
					// 如果是binary类型 返回数字 否则返回null
					BytesRef binaryValue = field.binaryValue();
					value = numericValue != null ? numericValue : value;
					value = stringValue != null ? stringValue : value;
					value = readerValue != null ? readerValue : value;
					value = binaryValue != null ? binaryValue : value;
				}
				map.put(name, value);
			}
		} catch (InvalidTokenOffsetsException e) {
			e.printStackTrace();
		}
		return map;
	}
}
