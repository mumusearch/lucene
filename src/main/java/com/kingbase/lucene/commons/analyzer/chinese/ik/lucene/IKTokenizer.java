/*     */
package com.kingbase.lucene.commons.analyzer.chinese.ik.lucene;
/*     */
/*     */

import com.kingbase.lucene.commons.analyzer.chinese.ik.core.IKSegmenter;
import com.kingbase.lucene.commons.analyzer.chinese.ik.core.Lexeme;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

import java.io.IOException;
import java.io.Reader;

/*     */
/*     */
/*     */
/*     */
/*     */


/*     */ public final class IKTokenizer extends Tokenizer
        /*     */ {
    /*     */   private IKSegmenter _IKImplement;
    /*     */   private final CharTermAttribute termAtt;
    /*     */   private final OffsetAttribute offsetAtt;
    /*     */   private final TypeAttribute typeAtt;
    /*     */   private int endPosition;

    /*     */
    /*     */
    public IKTokenizer(Reader in, boolean useSmart)
    /*     */ {
        /*  64 */
//        super(in);
        /*  65 */
        this.offsetAtt = ((OffsetAttribute) addAttribute(OffsetAttribute.class));
        /*  66 */
        this.termAtt = ((CharTermAttribute) addAttribute(CharTermAttribute.class));
        /*  67 */
        this.typeAtt = ((TypeAttribute) addAttribute(TypeAttribute.class));
        /*  68 */
        this._IKImplement = new IKSegmenter(this.input, useSmart);
        /*     */
    }

    /*     */
    /*     */
    public boolean incrementToken()
    /*     */     throws IOException
    /*     */ {
        /*  77 */
        clearAttributes();
        /*  78 */
        Lexeme nextLexeme = this._IKImplement.next();
        /*  79 */
        if (nextLexeme != null)
            /*     */ {
            /*  82 */
            this.termAtt.append(nextLexeme.getLexemeText());
            /*     */
            /*  84 */
            this.termAtt.setLength(nextLexeme.getLength());
            /*     */
            /*  86 */
            this.offsetAtt.setOffset(nextLexeme.getBeginPosition(), nextLexeme.getEndPosition());
            /*     */
            /*  88 */
            this.endPosition = nextLexeme.getEndPosition();
            /*     */
            /*  90 */
            this.typeAtt.setType(nextLexeme.getLexemeTypeString());
            /*     */
            /*  92 */
            return true;
            /*     */
        }
        /*     */
        /*  95 */
        return false;
        /*     */
    }

    /*     */
    /*     */
    public void reset()
    /*     */     throws IOException
    /*     */ {
        /* 104 */
        super.reset();
        /* 105 */
        this._IKImplement.reset(this.input);
        /*     */
    }

    /*     */
    /*     */
    public final void end()
    /*     */ {
        /* 111 */
        int finalOffset = correctOffset(this.endPosition);
        /* 112 */
        this.offsetAtt.setOffset(finalOffset, finalOffset);
        /*     */
    }
    /*     */
}

/* Location:           C:\Users\Administrator\.m2\repository\org\wltea\ik-analyzer\IKAnalyzer\5x\IKAnalyzer-5x.jar
 * Qualified Name:     org.wltea.analyzer.lucene.IKTokenizer
 * JD-Core Version:    0.6.2
 */