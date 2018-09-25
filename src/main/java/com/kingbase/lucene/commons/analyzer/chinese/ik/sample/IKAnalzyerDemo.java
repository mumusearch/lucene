package com.kingbase.lucene.commons.analyzer.chinese.ik.sample;

import com.kingbase.lucene.commons.analyzer.chinese.ik.lucene.IKAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

import java.io.IOException;
import java.io.StringReader;

public class IKAnalzyerDemo {
    public static void main(String[] args) {
        Analyzer analyzer = new IKAnalyzer(true);
        TokenStream ts = null;
        try {
            ts = analyzer.tokenStream("myfield", new StringReader("这是一个中文分词的例子，你可以直接运行它！IKAnalyer can analysis english text too"));
            OffsetAttribute offset = (OffsetAttribute) ts.addAttribute(OffsetAttribute.class);
            CharTermAttribute term = (CharTermAttribute) ts.addAttribute(CharTermAttribute.class);
            TypeAttribute type = (TypeAttribute) ts.addAttribute(TypeAttribute.class);
            ts.reset();
            while (ts.incrementToken()) {
                System.out.println(offset.startOffset() + " - " + offset.endOffset() + " : " + term.toString() + " | " + type.type());
            }
            ts.end();
        } catch (IOException e) {
            e.printStackTrace();
            if (ts != null)
                try {
                    ts.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
        } finally {
            if (ts != null)
                try {
                    ts.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}