package com.kingbase.lucene.commons.analyzer.chinese.mmseg4j.analysis;

import java.io.File;

import com.kingbase.lucene.commons.analyzer.chinese.mmseg4j.core.ComplexSeg;
import com.kingbase.lucene.commons.analyzer.chinese.mmseg4j.core.Dictionary;
import com.kingbase.lucene.commons.analyzer.chinese.mmseg4j.core.Seg;

/**
 * mmseg 的 complex analyzer
 * 
 * @author chenlb 2009-3-16 下午10:08:16
 */
public class ComplexAnalyzer extends MMSegAnalyzer {

	public ComplexAnalyzer() {
		super();
	}

	public ComplexAnalyzer(String path) {
		super(path);
	}
	
	public ComplexAnalyzer(Dictionary dic) {
		super(dic);
	}

	public ComplexAnalyzer(File path) {
		super(path);
	}

	protected Seg newSeg() {
		return new ComplexSeg(dic);
	}
}
