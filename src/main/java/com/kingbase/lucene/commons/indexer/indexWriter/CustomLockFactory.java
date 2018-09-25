package com.kingbase.lucene.commons.indexer.indexWriter;

import java.io.IOException;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.Lock;
import org.apache.lucene.store.LockFactory;

public class CustomLockFactory extends LockFactory{

	@Override
	public Lock obtainLock(Directory dir, String lockName) throws IOException {
		return null;
	}

}
