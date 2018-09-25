package com.kingbase.lucene.commons.analyzer.chinese.ik.core;

abstract interface ISegmenter
{
  public abstract void analyze(AnalyzeContext paramAnalyzeContext);

  public abstract void reset();
}

/* Location:           C:\Users\Administrator\.m2\repository\org\wltea\ik-analyzer\IKAnalyzer\5x\IKAnalyzer-5x.jar
 * Qualified Name:     org.wltea.analyzer.core.ISegmenter
 * JD-Core Version:    0.6.2
 */