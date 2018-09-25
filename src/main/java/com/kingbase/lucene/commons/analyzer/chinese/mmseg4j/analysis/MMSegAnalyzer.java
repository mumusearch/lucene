package com.kingbase.lucene.commons.analyzer.chinese.mmseg4j.analysis;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;

import com.kingbase.lucene.commons.analyzer.chinese.mmseg4j.core.Dictionary;
import com.kingbase.lucene.commons.analyzer.chinese.mmseg4j.core.MaxWordSeg;
import com.kingbase.lucene.commons.analyzer.chinese.mmseg4j.core.Seg;

/**
 * 默认使用 max-word
 *
 * @see {@link SimpleAnalyzer}, {@link ComplexAnalyzer}, {@link MaxWordAnalyzer}
 *
 * @author chenlb
 */
public class MMSegAnalyzer extends Analyzer {

	protected Dictionary dic;

	/**
	 * @see Dictionary#getInstance()
	 */
	public MMSegAnalyzer() {
		dic = Dictionary.getInstance();
	}

	/**
	 * @param path 词库路径
	 * @see Dictionary#getInstance(String)
	 */
	public MMSegAnalyzer(String path) {
		dic = Dictionary.getInstance(path);
	}

	/**
	 * @param path 词库目录
	 * @see Dictionary#getInstance(File)
	 */
	public MMSegAnalyzer(File path) {
		dic = Dictionary.getInstance(path);
	}

	public MMSegAnalyzer(Dictionary dic) {
		super();
		this.dic = dic;
	}

	protected Seg newSeg() {
		return new MaxWordSeg(dic);
	}

	public Dictionary getDict() {
		return dic;
	}

	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		System.out.println(fieldName);
		return new TokenStreamComponents(new MMSegTokenizer(newSeg()));
	}
}
