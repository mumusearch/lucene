package com.kingbase.lucene.commons.analyzer.built.porter;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseTokenizer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;

/**
 * 词干分词器
 * 将形 breathe breathes breathing breathed 还原成 breath
 * @author ganliang
 */
public class PorterStemAnalyzer extends Analyzer{

	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		
		Tokenizer tokenizer=new LowerCaseTokenizer();
		
		StopFilter stopFilter = new StopFilter(tokenizer, StopAnalyzer.ENGLISH_STOP_WORDS_SET);
		
		return new TokenStreamComponents(tokenizer, new PorterStemFilter(stopFilter));
	}

}
