package com.eeduspace.challenge.persist.dao;

import com.eeduspace.challenge.persist.po.SignRule;

public interface SignRuleMapper  extends BaseDao<SignRule>{

    /**
     * 更加次数获取规则
     * @param signTimes
     * @return
     */
    SignRule findBySignTimes(double signTimes);

}