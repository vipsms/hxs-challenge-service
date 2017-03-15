package com.eeduspace.challenge.persist.dao;

import com.eeduspace.challenge.persist.po.SystemDictionary;

/**
 * 字典项
 */
public interface SystemDictionaryMapper extends BaseDao<SystemDictionary>{

	 public SystemDictionary saveSystemDictionary(SystemDictionary systemDictionary);
   
}