package com.kingbase.lucene.commons.analyzer.chinese.ik;

import com.kingbase.lucene.commons.analyzer.chinese.ik.lucene.IKTokenizer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

import java.io.Reader;

/**
 * IKAnalyzer中文分词器 支持lucene5版本
 *
 * @author ganliang
 */
public class IKAnalyzer5x extends Analyzer {

    private boolean useSmart;

    public boolean useSmart() {
        return useSmart;
    }

    public void setUseSmart(boolean useSmart) {
        this.useSmart = useSmart;
    }

    /**
     * IK分词器Lucene  Analyzer接口实现类
     * <p>
     * 默认细粒度切分算法
     */
    public IKAnalyzer5x() {
        this(false);
    }

    /**
     * IK分词器Lucene Analyzer接口实现类
     *
     * @param useSmart 当为true时，分词器进行智能切分
     */
    public IKAnalyzer5x(boolean useSmart) {
        super();
        this.useSmart = useSmart;
    }


    /**
     * lucene早起版本
     *
     * @param fieldName
     * @param in
     * @return
     */
    protected TokenStreamComponents createComponents(String fieldName, final Reader in) {
        Tokenizer _IKTokenizer = new IKTokenizer(in, this.useSmart());
        return new TokenStreamComponents(_IKTokenizer);
    }


    /**
     * Lucene 版本5
     * 重写最新版本的createComponents
     * 重载Analyzer接口，构造分词组件
     */
    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer _IKTokenizer = new IKTokenizer5x(this.useSmart());
        return new TokenStreamComponents(_IKTokenizer);
    }
}