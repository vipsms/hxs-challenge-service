package com.eeduspace.challenge.service.impl;



import com.eeduspace.challenge.enumeration.SystemDictionaryEnum;
import com.eeduspace.challenge.model.SystemDictionaryModel;
import com.eeduspace.challenge.persist.dao.SystemDictionaryMapper;
import com.eeduspace.challenge.persist.po.SystemDictionary;
import com.eeduspace.challenge.service.SystemDictionaryService;
import com.eeduspace.challenge.util.Page;
import com.eeduspace.challenge.util.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典项
 */
@Service("SystemDictionaryServiceImpl")
public class SystemDictionaryServiceImpl extends BaseServiceImpl<SystemDictionary> implements SystemDictionaryService{
	@Resource
	private SystemDictionaryMapper systemDictionaryMapperImpl;
	@Autowired
	public SystemDictionaryServiceImpl(SystemDictionaryMapper questionMapper) {
		this.baseDao = questionMapper;
	}
	/**
	 * 字典项的保存
	 * **/
	@Override
	public SystemDictionary saveSystemDictionary(SystemDictionaryModel systemDictionaryModel) {
				
		SystemDictionary systemDictionary = new SystemDictionary();
		systemDictionary.setName(SystemDictionaryEnum.toEnumValue(systemDictionaryModel.getName()));
		systemDictionary.setValue(systemDictionaryModel.getValue());
		systemDictionary.setDescription(systemDictionaryModel.getDescription());
		
		systemDictionary = systemDictionaryMapperImpl.saveSystemDictionary(systemDictionary);
		
		
		return systemDictionary;
	}
	/**
	 * 字典项的查询
	 * **/
	@Override
	public List<SystemDictionary> findAll() {
			Pageable pageable = new Pageable();
			pageable.setPageNumber(0);
			pageable.setPageSize(9999);
			Page<SystemDictionary> findByPager = this.findByPager(pageable);
			List<SystemDictionary> content = findByPager.getContent();
			return content;
		}

    @Override
    public SystemDictionary findByName(String name) {
        Map<String, Object> queryMap=new HashMap<String, Object>();
        queryMap.put("name", name);
        List<SystemDictionary> list=this.findByCondition(queryMap);
        if(list==null || list.isEmpty()){
            return null;
        }
        return list.get(0);
    }


}
	

	
