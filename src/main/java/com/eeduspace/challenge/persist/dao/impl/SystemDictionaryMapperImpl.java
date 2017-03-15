
package com.eeduspace.challenge.persist.dao.impl;


import org.springframework.stereotype.Repository;

import com.eeduspace.challenge.persist.dao.SystemDictionaryMapper;
import com.eeduspace.challenge.persist.po.SystemDictionary;
@Repository("SystemDictionaryMapperImpl")
public class SystemDictionaryMapperImpl extends BaseDaoImpl<SystemDictionary> implements SystemDictionaryMapper{

	@Override
	public SystemDictionary saveSystemDictionary(
			SystemDictionary systemDictionary) {
		this.save(systemDictionary);
	     return this.get(systemDictionary.getId());
	}

	

}
