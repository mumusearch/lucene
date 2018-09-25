/*     */ package com.kingbase.lucene.commons.analyzer.chinese.ik.core;
/*     */ 
/*     */


import com.kingbase.lucene.commons.analyzer.chinese.ik.cfg.Configuration;
import com.kingbase.lucene.commons.analyzer.chinese.ik.dic.Dictionary;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ 
/*     */ class AnalyzeContext
/*     */ {
/*     */   private static final int BUFF_SIZE = 4096;
/*     */   private static final int BUFF_EXHAUST_CRITICAL = 100;
/*     */   private char[] segmentBuff;
/*     */   private int[] charTypes;
/*     */   private int buffOffset;
/*     */   private int cursor;
/*     */   private int available;
/*     */   private Set<String> buffLocker;
/*     */   private QuickSortSet orgLexemes;
/*     */   private Map<Integer, LexemePath> pathMap;
/*     */   private LinkedList<Lexeme> results;
/*     */   private Configuration cfg;
/*     */ 
/*     */   public AnalyzeContext(Configuration cfg)
/*     */   {
/*  81 */     this.cfg = cfg;
/*  82 */     this.segmentBuff = new char[4096];
/*  83 */     this.charTypes = new int[4096];
/*  84 */     this.buffLocker = new HashSet();
/*  85 */     this.orgLexemes = new QuickSortSet();
/*  86 */     this.pathMap = new HashMap();
/*  87 */     this.results = new LinkedList();
/*     */   }
/*     */ 
/*     */   int getCursor() {
/*  91 */     return this.cursor;
/*     */   }
/*     */ 
/*     */   char[] getSegmentBuff()
/*     */   {
/*  99 */     return this.segmentBuff;
/*     */   }
/*     */ 
/*     */   char getCurrentChar() {
/* 103 */     return this.segmentBuff[this.cursor];
/*     */   }
/*     */ 
/*     */   int getCurrentCharType() {
/* 107 */     return this.charTypes[this.cursor];
/*     */   }
/*     */ 
/*     */   int getBufferOffset() {
/* 111 */     return this.buffOffset;
/*     */   }
/*     */ 
/*     */   int fillBuffer(Reader reader)
/*     */     throws IOException
/*     */   {
/* 121 */     int readCount = 0;
/* 122 */     if (this.buffOffset == 0)
/*     */     {
/* 124 */       readCount = reader.read(this.segmentBuff);
/*     */     } else {
/* 126 */       int offset = this.available - this.cursor;
/* 127 */       if (offset > 0)
/*     */       {
/* 129 */         System.arraycopy(this.segmentBuff, this.cursor, this.segmentBuff, 0, offset);
/* 130 */         readCount = offset;
/*     */       }
/*     */ 
/* 133 */       readCount += reader.read(this.segmentBuff, offset, 4096 - offset);
/*     */     }
/*     */ 
/* 136 */     this.available = readCount;
/*     */ 
/* 138 */     this.cursor = 0;
/* 139 */     return readCount;
/*     */   }
/*     */ 
/*     */   void initCursor()
/*     */   {
/* 146 */     this.cursor = 0;
/* 147 */     this.segmentBuff[this.cursor] = CharacterUtil.regularize(this.segmentBuff[this.cursor]);
/* 148 */     this.charTypes[this.cursor] = CharacterUtil.identifyCharType(this.segmentBuff[this.cursor]);
/*     */   }
/*     */ 
/*     */   boolean moveCursor()
/*     */   {
/* 157 */     if (this.cursor < this.available - 1) {
/* 158 */       this.cursor += 1;
/* 159 */       this.segmentBuff[this.cursor] = CharacterUtil.regularize(this.segmentBuff[this.cursor]);
/* 160 */       this.charTypes[this.cursor] = CharacterUtil.identifyCharType(this.segmentBuff[this.cursor]);
/* 161 */       return true;
/*     */     }
/* 163 */     return false;
/*     */   }
/*     */ 
/*     */   void lockBuffer(String segmenterName)
/*     */   {
/* 173 */     this.buffLocker.add(segmenterName);
/*     */   }
/*     */ 
/*     */   void unlockBuffer(String segmenterName)
/*     */   {
/* 181 */     this.buffLocker.remove(segmenterName);
/*     */   }
/*     */ 
/*     */   boolean isBufferLocked()
/*     */   {
/* 190 */     return this.buffLocker.size() > 0;
/*     */   }
/*     */ 
/*     */   boolean isBufferConsumed()
/*     */   {
/* 199 */     return this.cursor == this.available - 1;
/*     */   }
/*     */ 
/*     */   boolean needRefillBuffer()
/*     */   {
/* 216 */     return (this.available == 4096) && 
/* 214 */       (this.cursor < this.available - 1) && 
/* 215 */       (this.cursor > this.available - 100) && 
/* 216 */       (!isBufferLocked());
/*     */   }
/*     */ 
/*     */   void markBufferOffset()
/*     */   {
/* 223 */     this.buffOffset += this.cursor;
/*     */   }
/*     */ 
/*     */   void addLexeme(Lexeme lexeme)
/*     */   {
/* 231 */     this.orgLexemes.addLexeme(lexeme);
/*     */   }
/*     */ 
/*     */   void addLexemePath(LexemePath path)
/*     */   {
/* 240 */     if (path != null)
/* 241 */       this.pathMap.put(Integer.valueOf(path.getPathBegin()), path);
/*     */   }
/*     */ 
/*     */   QuickSortSet getOrgLexemes()
/*     */   {
/* 251 */     return this.orgLexemes;
/*     */   }
/*     */ 
/*     */   void outputToResult()
/*     */   {
/* 261 */     int index = 0;
/* 262 */     while (index <= this.cursor)
/*     */     {
/* 264 */       if (this.charTypes[index] == 0) {
/* 265 */         index++;
/*     */       }
/*     */       else
/*     */       {
/* 269 */         LexemePath path = (LexemePath)this.pathMap.get(Integer.valueOf(index));
/* 270 */         if (path != null)
/*     */         {
/* 272 */           Lexeme l = path.pollFirst();
/* 273 */           while (l != null) {
/* 274 */             this.results.add(l);
/*     */ 
/* 276 */             index = l.getBegin() + l.getLength();
/* 277 */             l = path.pollFirst();
/* 278 */             if (l != null)
/*     */             {
/* 280 */               for (; index < l.getBegin(); index++)
/* 281 */                 outputSingleCJK(index);
/*     */             }
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 287 */           outputSingleCJK(index);
/* 288 */           index++;
/*     */         }
/*     */       }
/*     */     }
/* 292 */     this.pathMap.clear();
/*     */   }
/*     */ 
/*     */   private void outputSingleCJK(int index)
/*     */   {
/* 300 */     if (4 == this.charTypes[index]) {
/* 301 */       Lexeme singleCharLexeme = new Lexeme(this.buffOffset, index, 1, 64);
/* 302 */       this.results.add(singleCharLexeme);
/* 303 */     } else if (8 == this.charTypes[index]) {
/* 304 */       Lexeme singleCharLexeme = new Lexeme(this.buffOffset, index, 1, 8);
/* 305 */       this.results.add(singleCharLexeme);
/*     */     }
/*     */   }
/*     */ 
/*     */   Lexeme getNextLexeme()
/*     */   {
/* 317 */     Lexeme result = (Lexeme)this.results.pollFirst();
/* 318 */     while (result != null)
/*     */     {
/* 320 */       compound(result);
/* 321 */       if (Dictionary.getSingleton().isStopWord(this.segmentBuff, result.getBegin(), result.getLength()))
/*     */       {
/* 323 */         result = (Lexeme)this.results.pollFirst();
/*     */       }
/*     */       else {
/* 326 */         result.setLexemeText(String.valueOf(this.segmentBuff, result.getBegin(), result.getLength()));
/* 327 */         break;
/*     */       }
/*     */     }
/* 330 */     return result;
/*     */   }
/*     */ 
/*     */   void reset()
/*     */   {
/* 337 */     this.buffLocker.clear();
/* 338 */     this.orgLexemes = new QuickSortSet();
/* 339 */     this.available = 0;
/* 340 */     this.buffOffset = 0;
/* 341 */     this.charTypes = new int[4096];
/* 342 */     this.cursor = 0;
/* 343 */     this.results.clear();
/* 344 */     this.segmentBuff = new char[4096];
/* 345 */     this.pathMap.clear();
/*     */   }
/*     */ 
/*     */   private void compound(Lexeme result)
/*     */   {
/* 352 */     if (!this.cfg.useSmart()) {
/* 353 */       return;
/*     */     }
/*     */ 
/* 356 */     if (!this.results.isEmpty())
/*     */     {
/* 358 */       if (2 == result.getLexemeType()) {
/* 359 */         Lexeme nextLexeme = (Lexeme)this.results.peekFirst();
/* 360 */         boolean appendOk = false;
/* 361 */         if (16 == nextLexeme.getLexemeType())
/*     */         {
/* 363 */           appendOk = result.append(nextLexeme, 16);
/* 364 */         } else if (32 == nextLexeme.getLexemeType())
/*     */         {
/* 366 */           appendOk = result.append(nextLexeme, 48);
/*     */         }
/* 368 */         if (appendOk)
/*     */         {
/* 370 */           this.results.pollFirst();
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 375 */       if ((16 == result.getLexemeType()) && (!this.results.isEmpty())) {
/* 376 */         Lexeme nextLexeme = (Lexeme)this.results.peekFirst();
/* 377 */         boolean appendOk = false;
/* 378 */         if (32 == nextLexeme.getLexemeType())
/*     */         {
/* 380 */           appendOk = result.append(nextLexeme, 48);
/*     */         }
/* 382 */         if (appendOk)
/*     */         {
/* 384 */           this.results.pollFirst();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Administrator\.m2\repository\org\wltea\ik-analyzer\IKAnalyzer\5x\IKAnalyzer-5x.jar
 * Qualified Name:     org.wltea.analyzer.core.AnalyzeContext
 * JD-Core Version:    0.6.2
 */