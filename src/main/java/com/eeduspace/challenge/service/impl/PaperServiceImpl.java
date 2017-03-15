package com.eeduspace.challenge.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeduspace.challenge.persist.dao.PaperMapper;
import com.eeduspace.challenge.persist.po.Paper;
import com.eeduspace.challenge.service.PaperService;

@Service("paperServiceImpl")
public class PaperServiceImpl extends BaseServiceImpl<Paper>
		implements PaperService {
	@Resource
	private PaperMapper paperMapperImpl;
	@Autowired
	public PaperServiceImpl(PaperMapper paperMapper) {
		this.baseDao = paperMapper;
	}
	@Override
	public Paper findByUUID(String uuid) {
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put("uuid", uuid);
		return this.findByCondition(queryMap).get(0);
	}
}
