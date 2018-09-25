package com.kingbase.lucene.file.web.listeners;

import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

import com.kingbase.lucene.commons.configuration.ConfigInitialization;

@WebListener
public class ContextLoadListeners implements ServletContextListener{

	private static final Logger log=Logger.getLogger(ContextLoadListeners.class);
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		log.debug("读取Lucene配置文件...............");
		ConfigInitialization.load();
	}

}
