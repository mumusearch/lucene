package com.kingbase.lucene.commons.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

import com.kingbase.lucene.commons.analyzer.built.metaphone.MetaphoneAnalyzer;
import com.kingbase.lucene.commons.analyzer.built.porter.PorterStemAnalyzer;
import com.kingbase.lucene.commons.analyzer.built.synonym.SynonymAnalyzer;
import com.kingbase.lucene.commons.analyzer.chinese.ik.IKAnalyzer5x;
import com.kingbase.lucene.commons.analyzer.chinese.mmseg4j.Mmseg4jAnalyzerFactory;
import com.kingbase.lucene.commons.analyzer.chinese.paoding.PaodingAnalyzerFactory;

/**
 * 获取项目配置的分词器
 * 
 * @author ganliang
 *
 */
public class AnalyzerConfig {

	/**
	 * @param analyzerName
	 *            分词器的名称
	 * @return
	 */
	public Analyzer analyzer(String analyzerName) {
		// 默认选择 StandardAnalyzer
		if (analyzerName == null || "".equals(analyzerName)) {
			return new StandardAnalyzer();
		}
		Analyzer analyzer = null;
		switch (analyzerName.toUpperCase()) {
		// 空格分词器 (以空格分割语汇单元)
		case "WHITESPACEANALYZER":
			analyzer = new WhitespaceAnalyzer();
			break;
		// 简单分词器 (以非字符分割语汇单元 并将语汇单元转化为小写)
		case "SIMPLEANALYZER":
			analyzer = new SimpleAnalyzer();
			break;
		// 停分词分词器 (以非字符分割语汇单元 并将语汇单元转化为小写 并且去除停分词的语汇单元)
		case "STOPANALYZER":
			analyzer = new StopAnalyzer();
			break;
		// 标准分词器
		case "STANDARDANALYZER":
			analyzer = new StandardAnalyzer();
			break;
		// 关键字分词器 将输入的字符流 生成一个语汇单元
		case "KEYWORDANALYZER":
			analyzer = new KeywordAnalyzer();
			break;
			
		// Metaphone近音词分析器
		case "METAPHONEANALYZER":
			analyzer = new MetaphoneAnalyzer();
			break;
		// PorterStemAnalyzer词干分词器
		case "PORTERSTEMANALYZER":
			analyzer = new PorterStemAnalyzer();
			break;
		//SynonymAnalyzer同义词分词器
		case "SYNONYMANALYZER":
			analyzer = new SynonymAnalyzer();
			break;
			
		//CJKAnalyzer=cjk中文分词器
		case "CJKANALYZER":
			analyzer = new CJKAnalyzer();
			break;
		//SmartChineseAnalyzer智能中文分词器
		case "SMARTCHINESEANALYZER":
			analyzer = new SmartChineseAnalyzer();
			break;
		//IKAnalyzer5x=IK中文分词器	
		case "IKANALYZER5X":
			analyzer = new IKAnalyzer5x();
			break;
		//MMSEG简单中文分词器
		case "MMSEGSIMPLEANALYZER":
			analyzer = Mmseg4jAnalyzerFactory.simpleAnalyzer();
			break;
		//MMSEG复杂中文分词器
		case "MMSEGCOMPLEXANALYZER":
			analyzer = Mmseg4jAnalyzerFactory.complexAnalyzer();
			break;
		//Paoding复杂中文分词器
		case "PAODINGANALYZER":
			analyzer = PaodingAnalyzerFactory.instance();
			break;
		default:
			analyzer = new StandardAnalyzer();
			break;
		}
		return analyzer;
	}
}
