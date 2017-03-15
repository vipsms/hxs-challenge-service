package com.eeduspace.challenge.persist.dao.impl;

import com.eeduspace.challenge.persist.dao.SignRuleMapper;
import com.eeduspace.challenge.persist.po.SignRule;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: dingran
 * Date: 2016/7/21
 * Description:签到规则
 */
@Repository("SignRuleMapperImpl")
public class SignRuleMapperImpl  extends BaseDaoImpl<SignRule> implements SignRuleMapper {


	@Override
    public SignRule findBySignTimes(double signTimes) {
        Map<String, Object> queryMap=new HashMap<String, Object>();
        queryMap.put("signTimes", signTimes);
        List<SignRule> signRules= this.findByCondition(queryMap,null);
        if(signRules==null && signRules.isEmpty()){
            return null;
        }
        return signRules.get(0);
    }
}
