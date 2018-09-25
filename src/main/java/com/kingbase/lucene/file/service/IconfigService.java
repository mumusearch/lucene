package com.kingbase.lucene.file.service;

import java.util.Map;

public interface IconfigService {

	/**
	 * 更新配置信息
	 * @param map
	 * @return
	 */
	public boolean updateConfig(Map<String, String> map);

}
