package com.kingbase.lucene.commons.analyzer.chinese.ik.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

import java.io.Reader;

public final class IKAnalyzer extends Analyzer {
    private boolean useSmart;

    public boolean useSmart() {
        return this.useSmart;
    }

    public void setUseSmart(boolean useSmart) {
        this.useSmart = useSmart;
    }

    public IKAnalyzer() {
        this(false);
    }

    @Override
    protected TokenStreamComponents createComponents(String s) {
        return createComponents(s, null);
    }

    public IKAnalyzer(boolean useSmart) {
        this.useSmart = useSmart;
    }

    protected TokenStreamComponents createComponents(String fieldName, Reader in) {
        Tokenizer _IKTokenizer = new IKTokenizer(in, useSmart());
        return new TokenStreamComponents(_IKTokenizer);
    }
}