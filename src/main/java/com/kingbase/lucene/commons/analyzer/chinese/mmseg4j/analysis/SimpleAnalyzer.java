package com.kingbase.lucene.commons.analyzer.chinese.mmseg4j.analysis;

import java.io.File;

import com.kingbase.lucene.commons.analyzer.chinese.mmseg4j.core.Dictionary;
import com.kingbase.lucene.commons.analyzer.chinese.mmseg4j.core.Seg;
import com.kingbase.lucene.commons.analyzer.chinese.mmseg4j.core.SimpleSeg;

/**
 * mmseg 的 simple anlayzer.
 * 
 * @author chenlb 2009-3-16 下午10:08:13
 */
public class SimpleAnalyzer extends MMSegAnalyzer {
	
	public SimpleAnalyzer() {
		super();
	}
	
	public SimpleAnalyzer(String path) {
		super(path);
	}
	
	public SimpleAnalyzer(Dictionary dic) {
		super(dic);
	}

	public SimpleAnalyzer(File path) {
		super(path);
	}

	protected Seg newSeg() {
		return new SimpleSeg(dic);
	}
}
