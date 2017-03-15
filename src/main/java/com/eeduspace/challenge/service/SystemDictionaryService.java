package com.eeduspace.challenge.service;


import com.eeduspace.challenge.model.SystemDictionaryModel;
import com.eeduspace.challenge.persist.po.SystemDictionary;

import java.util.List;

/**
 *字典项
 */
public interface SystemDictionaryService extends BaseService<SystemDictionary>{

	public	SystemDictionary  saveSystemDictionary(SystemDictionaryModel systemDictionaryModel);

	
	/**
	 * 字典项的查询
	 * **/
	public List<SystemDictionary> findAll();


    /**
     * 根据名称查询
     * @param name
     * @return
     */
    SystemDictionary findByName(String name);
	
	
	
}
