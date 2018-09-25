package com.kingbase.lucene.commons.analyzer.chinese.mmseg4j.analysis;

import java.io.IOException;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

import com.kingbase.lucene.commons.analyzer.chinese.mmseg4j.core.MMSeg;
import com.kingbase.lucene.commons.analyzer.chinese.mmseg4j.core.Seg;
import com.kingbase.lucene.commons.analyzer.chinese.mmseg4j.core.Word;

public class MMSegTokenizer extends Tokenizer {

	private MMSeg mmSeg;

	private CharTermAttribute termAtt;
	private OffsetAttribute offsetAtt;
	private TypeAttribute typeAtt;

	public MMSegTokenizer(Seg seg) {
		super();
		
		termAtt = addAttribute(CharTermAttribute.class);
		offsetAtt = addAttribute(OffsetAttribute.class);
		typeAtt = addAttribute(TypeAttribute.class);
		
		mmSeg = new MMSeg(input, seg);
	}

	public void reset() throws IOException {
		//lucene 4.0
		//org.apache.lucene.analysis.Tokenizer.setReader(Reader)
		//setReader 自动被调用, input 自动被设置。
		super.reset();
		mmSeg.reset(input);
	}

	//lucene 2.9/3.0
	@Override
	public final boolean incrementToken() throws IOException {
		clearAttributes();
		Word word = mmSeg.next();
		if(word != null) {
			termAtt.copyBuffer(word.getSen(), word.getWordOffset(), word.getLength());
			offsetAtt.setOffset(word.getStartOffset(), word.getEndOffset());
			typeAtt.setType(word.getType());
			return true;
		} else {
			end();
			return false;
		}
	}
}
