package com.kingbase.lucene.commons.analyzer.built.metaphone;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

public class MetaphoneFilter extends TokenFilter {

	private static final String METAPHONE = "metaphone";

	private Metaphone metaphone = new Metaphone();

	// 语汇单元项
	private CharTermAttribute termAttr = null;
	// 语汇单元类型
	private TypeAttribute typeAttr = null;

	protected MetaphoneFilter(TokenStream input) {
		super(input);
		termAttr = addAttribute(CharTermAttribute.class);
		typeAttr = addAttribute(TypeAttribute.class);
	}

	@Override
	public boolean incrementToken() throws IOException {

		boolean incrementToken = input.incrementToken();
		if (!incrementToken) {
			return false;
		}

		// 近音词 处理
		String term = termAttr.toString();
		String encoded = metaphone.encode(term);
		termAttr.setEmpty();
		termAttr.append(encoded);
		typeAttr.setType(METAPHONE);// 设置语汇单元的类型
		return true;
	}

}
