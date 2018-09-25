/*     */ package com.kingbase.lucene.commons.analyzer.chinese.ik.core;
/*     */ 
/*     */ public class Lexeme
/*     */   implements Comparable<Lexeme>
/*     */ {
/*     */   public static final int TYPE_UNKNOWN = 0;
/*     */   public static final int TYPE_ENGLISH = 1;
/*     */   public static final int TYPE_ARABIC = 2;
/*     */   public static final int TYPE_LETTER = 3;
/*     */   public static final int TYPE_CNWORD = 4;
/*     */   public static final int TYPE_CNCHAR = 64;
/*     */   public static final int TYPE_OTHER_CJK = 8;
/*     */   public static final int TYPE_CNUM = 16;
/*     */   public static final int TYPE_COUNT = 32;
/*     */   public static final int TYPE_CQUAN = 48;
/*     */   private int offset;
/*     */   private int begin;
/*     */   private int length;
/*     */   private String lexemeText;
/*     */   private int lexemeType;
/*     */ 
/*     */   public Lexeme(int offset, int begin, int length, int lexemeType)
/*     */   {
/*  66 */     this.offset = offset;
/*  67 */     this.begin = begin;
/*  68 */     if (length < 0) {
/*  69 */       throw new IllegalArgumentException("length < 0");
/*     */     }
/*  71 */     this.length = length;
/*  72 */     this.lexemeType = lexemeType;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object o)
/*     */   {
/*  81 */     if (o == null) {
/*  82 */       return false;
/*     */     }
/*     */ 
/*  85 */     if (this == o) {
/*  86 */       return true;
/*     */     }
/*     */ 
/*  89 */     if ((o instanceof Lexeme)) {
/*  90 */       Lexeme other = (Lexeme)o;
/*  91 */       if ((this.offset == other.getOffset()) && 
/*  92 */         (this.begin == other.getBegin()) && 
/*  93 */         (this.length == other.getLength())) {
/*  94 */         return true;
/*     */       }
/*  96 */       return false;
/*     */     }
/*     */ 
/*  99 */     return false;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 108 */     int absBegin = getBeginPosition();
/* 109 */     int absEnd = getEndPosition();
/* 110 */     return absBegin * 37 + absEnd * 31 + absBegin * absEnd % getLength() * 11;
/*     */   }
/*     */ 
/*     */   public int compareTo(Lexeme other)
/*     */   {
/* 119 */     if (this.begin < other.getBegin())
/* 120 */       return -1;
/* 121 */     if (this.begin == other.getBegin())
/*     */     {
/* 123 */       if (this.length > other.getLength())
/* 124 */         return -1;
/* 125 */       if (this.length == other.getLength()) {
/* 126 */         return 0;
/*     */       }
/* 128 */       return 1;
/*     */     }
/*     */ 
/* 132 */     return 1;
/*     */   }
/*     */ 
/*     */   public int getOffset()
/*     */   {
/* 137 */     return this.offset;
/*     */   }
/*     */ 
/*     */   public void setOffset(int offset) {
/* 141 */     this.offset = offset;
/*     */   }
/*     */ 
/*     */   public int getBegin() {
/* 145 */     return this.begin;
/*     */   }
/*     */ 
/*     */   public int getBeginPosition()
/*     */   {
/* 152 */     return this.offset + this.begin;
/*     */   }
/*     */ 
/*     */   public void setBegin(int begin) {
/* 156 */     this.begin = begin;
/*     */   }
/*     */ 
/*     */   public int getEndPosition()
/*     */   {
/* 164 */     return this.offset + this.begin + this.length;
/*     */   }
/*     */ 
/*     */   public int getLength()
/*     */   {
/* 172 */     return this.length;
/*     */   }
/*     */ 
/*     */   public void setLength(int length) {
/* 176 */     if (this.length < 0) {
/* 177 */       throw new IllegalArgumentException("length < 0");
/*     */     }
/* 179 */     this.length = length;
/*     */   }
/*     */ 
/*     */   public String getLexemeText()
/*     */   {
/* 187 */     if (this.lexemeText == null) {
/* 188 */       return "";
/*     */     }
/* 190 */     return this.lexemeText;
/*     */   }
/*     */ 
/*     */   public void setLexemeText(String lexemeText) {
/* 194 */     if (lexemeText == null) {
/* 195 */       this.lexemeText = "";
/* 196 */       this.length = 0;
/*     */     } else {
/* 198 */       this.lexemeText = lexemeText;
/* 199 */       this.length = lexemeText.length();
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getLexemeType()
/*     */   {
/* 208 */     return this.lexemeType;
/*     */   }
/*     */ 
/*     */   public String getLexemeTypeString()
/*     */   {
/* 216 */     switch (this.lexemeType)
/*     */     {
/*     */     case 1:
/* 219 */       return "ENGLISH";
/*     */     case 2:
/* 222 */       return "ARABIC";
/*     */     case 3:
/* 225 */       return "LETTER";
/*     */     case 4:
/* 228 */       return "CN_WORD";
/*     */     case 64:
/* 231 */       return "CN_CHAR";
/*     */     case 8:
/* 234 */       return "OTHER_CJK";
/*     */     case 32:
/* 237 */       return "COUNT";
/*     */     case 16:
/* 240 */       return "TYPE_CNUM";
/*     */     case 48:
/* 243 */       return "TYPE_CQUAN";
/*     */     }
/*     */ 
/* 246 */     return "UNKONW";
/*     */   }
/*     */ 
/*     */   public void setLexemeType(int lexemeType)
/*     */   {
/* 252 */     this.lexemeType = lexemeType;
/*     */   }
/*     */ 
/*     */   public boolean append(Lexeme l, int lexemeType)
/*     */   {
/* 262 */     if ((l != null) && (getEndPosition() == l.getBeginPosition())) {
/* 263 */       this.length += l.getLength();
/* 264 */       this.lexemeType = lexemeType;
/* 265 */       return true;
/*     */     }
/* 267 */     return false;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 276 */     StringBuffer strbuf = new StringBuffer();
/* 277 */     strbuf.append(getBeginPosition()).append("-").append(getEndPosition());
/* 278 */     strbuf.append(" : ").append(this.lexemeText).append(" : \t");
/* 279 */     strbuf.append(getLexemeTypeString());
/* 280 */     return strbuf.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Administrator\.m2\repository\org\wltea\ik-analyzer\IKAnalyzer\5x\IKAnalyzer-5x.jar
 * Qualified Name:     org.wltea.analyzer.core.Lexeme
 * JD-Core Version:    0.6.2
 */