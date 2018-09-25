package com.kingbase.lucene.commons.analyzer.built.synonym;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.synonym.SynonymFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;

/**
 * 同义词分析器
 * @author ganliang
 *
 */
public class SynonymAnalyzer extends Analyzer {

	private static SynonymMap synonymMap = SynonymMapFactory.instance();

	public SynonymAnalyzer() {
		super();
	}

	@Override
	protected TokenStreamComponents createComponents(String fieldName) {

		TokenFilter filter = null;

		//标准分词
		Tokenizer tokenizer=new StandardTokenizer();
		filter = new StandardFilter(tokenizer);
		
		//转化为小写
		filter = new LowerCaseFilter(filter);
		// 去除停分词
		filter = new StopFilter(filter, StopAnalyzer.ENGLISH_STOP_WORDS_SET);

		//添加同义词语汇单元
		filter = new SynonymFilter(filter, synonymMap, false);
		
		return new TokenStreamComponents(tokenizer, filter);
	}

}
