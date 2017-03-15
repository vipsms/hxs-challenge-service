package com.eeduspace.challenge.persist.dao.impl;

import org.springframework.stereotype.Repository;

import com.eeduspace.challenge.persist.dao.PaperMapper;
import com.eeduspace.challenge.persist.po.Paper;
@Repository("paperMapperImpl")
public class PaperMapperImpl extends BaseDaoImpl<Paper> implements PaperMapper{
}
