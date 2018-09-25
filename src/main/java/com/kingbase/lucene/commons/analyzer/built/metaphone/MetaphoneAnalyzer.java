package com.kingbase.lucene.commons.analyzer.built.metaphone;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LetterTokenizer;

/**
 * 近音词分析器
 * @author ganliang
 */
public class MetaphoneAnalyzer extends Analyzer{

	public MetaphoneAnalyzer() {
		super();
	}

	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		Tokenizer tokenizer=new LetterTokenizer();
		return new TokenStreamComponents(tokenizer, new MetaphoneFilter(tokenizer));
	}

}
