package com.eeduspace.challenge.service.impl;

import com.eeduspace.challenge.persist.dao.IntegralGetMapper;
import com.eeduspace.challenge.persist.po.IntegralGet;
import com.eeduspace.challenge.service.IntegralGetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: dingran
 * Date: 2016/7/13
 * Description:
 */
@Service
public class IntegralGetServiceImpl extends BaseServiceImpl<IntegralGet> implements IntegralGetService {
    private final Logger logger = LoggerFactory.getLogger(UserFriendServiceImpl.class);

    @Autowired
    public IntegralGetServiceImpl(IntegralGetMapper integralGetMapper) {
        this.baseDao =integralGetMapper;
    }

}
