package com.kingbase.lucene.commons.analyzer.built.core;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TermToBytesRefAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.BytesRef;

/**
 * 解析语汇单元 
 * @author ganliang
 */
public class TokenAttributes {
	
	/**
	 * 获取语汇单元的 项
	 * @param analyzer
	 * @param fieldName
	 * @param token
	 * @throws IOException
	 */
	public String tokenTerm(Analyzer analyzer,String fieldName,String token) throws IOException{
		TokenStream tokenStream = analyzer.tokenStream(fieldName, new StringReader(token));
		tokenStream.reset();
		
		TermToBytesRefAttribute byteterm = tokenStream.addAttribute(TermToBytesRefAttribute.class);
		//CharTermAttribute charTerm = tokenStream.addAttribute(CharTermAttribute.class);
		StringBuilder builder=new StringBuilder();
		while(tokenStream.incrementToken()){
			BytesRef bytesRef = byteterm.getBytesRef();
			builder.append("["+bytesRef.utf8ToString()+"] ");
			
			//String chars = charTerm.toString();
			//log.debug("charTerm项 "+chars);
		}
		//analyzer.close();
		tokenStream.close();
		return builder.toString();
	}
	
	/**
	 * 获取语汇单元的 项
	 * @param analyzer
	 * @param fieldName
	 * @param token
	 * @throws IOException
	 */
	public String token(Analyzer analyzer,String fieldName,String token) throws IOException{
		TokenStream tokenStream = analyzer.tokenStream(fieldName, new StringReader(token));
		tokenStream.reset();
		//项
		TermToBytesRefAttribute term = tokenStream.addAttribute(TermToBytesRefAttribute.class);
		//位置增量
		PositionIncrementAttribute positionIncrement = tokenStream.addAttribute(PositionIncrementAttribute.class);
		//偏移量
		OffsetAttribute offset = tokenStream.addAttribute(OffsetAttribute.class);
		//类型
		TypeAttribute type = tokenStream.addAttribute(TypeAttribute.class);
		
		int position=0;
		StringBuilder builder=new StringBuilder();
		while(tokenStream.incrementToken()){
			//获取项
			BytesRef bytesRef = term.getBytesRef();
			String termStr = bytesRef.utf8ToString();
			
			//获取语汇单元的偏移量
			int increment = positionIncrement.getPositionIncrement();
			if(increment>0){
				position=position+increment;
			}
			
			//偏移量
			int startOffset = offset.startOffset();
			int endOffset = offset.endOffset();
			
			//类型
			String typeStr = type.type();
			
			builder.append("positionIncrement:"+position+" term:"+termStr+" offset:"+startOffset+"-"+endOffset+" type:"+typeStr+"\n");
		}
		analyzer.close();
		return builder.toString();
	}
}
