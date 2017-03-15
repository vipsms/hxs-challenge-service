package com.eeduspace.challenge.service.impl;

import com.eeduspace.challenge.persist.dao.IntegralChangeMapper;
import com.eeduspace.challenge.persist.po.IntegralChange;
import com.eeduspace.challenge.service.IntegralChangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: dingran
 * Date: 2016/7/14
 * Description:
 */
@Service
public class IntegralChangeServiceImpl  extends BaseServiceImpl<IntegralChange> implements IntegralChangeService {
    private final Logger logger = LoggerFactory.getLogger(IntegralChangeServiceImpl.class);

    @Autowired
    public IntegralChangeServiceImpl(IntegralChangeMapper integralChangeMapper) {
        this.baseDao =integralChangeMapper;
    }

}
