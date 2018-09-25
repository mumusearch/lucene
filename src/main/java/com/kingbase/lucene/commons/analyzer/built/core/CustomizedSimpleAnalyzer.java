package com.kingbase.lucene.commons.analyzer.built.core;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LetterTokenizer;
import org.apache.lucene.analysis.core.LowerCaseTokenizer;
import org.apache.lucene.analysis.core.UpperCaseFilter;

/**
 * SimpleAnalyzer 以非字符来分割语汇单元  在将语汇单元转化为小写
 * 自定义SimpleAnalyzer 
 * @author ganliang
 */
public class CustomizedSimpleAnalyzer extends Analyzer{

	private boolean lowerCase=true;
	
	public CustomizedSimpleAnalyzer() {
		this.lowerCase=true;
	}
	
	public CustomizedSimpleAnalyzer(boolean lowerCase) {
		this.lowerCase=lowerCase;
	}

	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		if(lowerCase){
		    return new TokenStreamComponents(new LowerCaseTokenizer());
		}
		Tokenizer source =new LetterTokenizer();
		return new TokenStreamComponents(source, new UpperCaseFilter(source));
	}
}
