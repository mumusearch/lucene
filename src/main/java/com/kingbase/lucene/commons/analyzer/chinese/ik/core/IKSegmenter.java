/*     */ package com.kingbase.lucene.commons.analyzer.chinese.ik.core;
/*     */ 
/*     */

import com.kingbase.lucene.commons.analyzer.chinese.ik.cfg.Configuration;
import com.kingbase.lucene.commons.analyzer.chinese.ik.cfg.DefaultConfig;
import com.kingbase.lucene.commons.analyzer.chinese.ik.dic.Dictionary;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ 
/*     */ public final class IKSegmenter
/*     */ {
/*     */   private Reader input;
/*     */   private Configuration cfg;
/*     */   private AnalyzeContext context;
/*     */   private List<ISegmenter> segmenters;
/*     */   private IKArbitrator arbitrator;
/*     */ 
/*     */   public IKSegmenter(Reader input, boolean useSmart)
/*     */   {
/*  62 */     this.input = input;
/*  63 */     this.cfg = DefaultConfig.getInstance();
/*  64 */     this.cfg.setUseSmart(useSmart);
/*  65 */     init();
/*     */   }
/*     */ 
/*     */   public IKSegmenter(Reader input, Configuration cfg)
/*     */   {
/*  75 */     this.input = input;
/*  76 */     this.cfg = cfg;
/*  77 */     init();
/*     */   }
/*     */ 
/*     */   private void init()
/*     */   {
/*  85 */     Dictionary.initial(this.cfg);
/*     */ 
/*  87 */     this.context = new AnalyzeContext(this.cfg);
/*     */ 
/*  89 */     this.segmenters = loadSegmenters();
/*     */ 
/*  91 */     this.arbitrator = new IKArbitrator();
/*     */   }
/*     */ 
/*     */   private List<ISegmenter> loadSegmenters()
/*     */   {
/*  99 */     List segmenters = new ArrayList(4);
/*     */ 
/* 101 */     segmenters.add(new LetterSegmenter());
/*     */ 
/* 103 */     segmenters.add(new CN_QuantifierSegmenter());
/*     */ 
/* 105 */     segmenters.add(new CJKSegmenter());
/* 106 */     return segmenters;
/*     */   }
/*     */ 
/*     */   public synchronized Lexeme next()
/*     */     throws IOException
/*     */   {
/* 115 */     Lexeme l = null;
/* 116 */     while ((l = this.context.getNextLexeme()) == null)
/*     */     {
/* 122 */       int available = this.context.fillBuffer(this.input);
/* 123 */       if (available <= 0)
/*     */       {
/* 125 */         this.context.reset();
/* 126 */         return null;
/*     */       }
/*     */ 
/* 130 */       this.context.initCursor();
/*     */       do
/*     */       {
/* 133 */         for (ISegmenter segmenter : this.segmenters) {
/* 134 */           segmenter.analyze(this.context);
/*     */         }
/*     */       }
/* 137 */       while ((!this.context.needRefillBuffer()) && (
/* 141 */         this.context.moveCursor()));
/*     */ 
/* 143 */       for (ISegmenter segmenter : this.segmenters) {
/* 144 */         segmenter.reset();
/*     */       }
/*     */ 
/* 148 */       this.arbitrator.process(this.context, this.cfg.useSmart());
/*     */ 
/* 150 */       this.context.outputToResult();
/*     */ 
/* 152 */       this.context.markBufferOffset();
/*     */     }
/* 154 */     return l;
/*     */   }
/*     */ 
/*     */   public synchronized void reset(Reader input)
/*     */   {
/* 162 */     this.input = input;
/* 163 */     this.context.reset();
/* 164 */     for (ISegmenter segmenter : this.segmenters)
/* 165 */       segmenter.reset();
/*     */   }
/*     */ }

/* Location:           C:\Users\Administrator\.m2\repository\org\wltea\ik-analyzer\IKAnalyzer\5x\IKAnalyzer-5x.jar
 * Qualified Name:     org.wltea.analyzer.core.IKSegmenter
 * JD-Core Version:    0.6.2
 */