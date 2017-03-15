package com.eeduspace.challenge.service.impl;



import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeduspace.challenge.enumeration.RewardEnum;
import com.eeduspace.challenge.enumeration.SystemDictionaryEnum;
import com.eeduspace.challenge.model.IntegralChangeModel;
import com.eeduspace.challenge.model.UserFeedBackModel;
import com.eeduspace.challenge.persist.dao.UserFeedBackMapper;
import com.eeduspace.challenge.persist.po.SystemDictionary;
import com.eeduspace.challenge.persist.po.User;
import com.eeduspace.challenge.persist.po.UserFeedBack;
import com.eeduspace.challenge.service.SystemDictionaryService;
import com.eeduspace.challenge.service.UserFeedBackService;
import com.eeduspace.challenge.service.UserService;
/**
 * 反馈
 */
@Service("UserFeedBackServiceImpl")
public class UserFeedBackServiceImpl extends BaseServiceImpl<UserFeedBack> implements UserFeedBackService{
	@Resource
	private UserFeedBackMapper userFeedBackMapperImpl;
	@Autowired
	public UserFeedBackServiceImpl(UserFeedBackMapper questionMapper) {
		this.baseDao = questionMapper;
	}
	@Autowired
	private UserService userService;
	@Autowired
    private SystemDictionaryService systemDictionaryService;
	
	/**保存用户反馈
	 * @return **/
	@Override
	public void saveUserFeedBack(User user,UserFeedBackModel userFeedBackModel) {
        List<UserFeedBack> feedBackList = this.findByUsercode(userFeedBackModel.getUserCode());
		UserFeedBack userFeedBack = new UserFeedBack();
		String replace = UUID.randomUUID().toString().replace("-", "");
		userFeedBack.setMessage(userFeedBackModel.getMessage());
		userFeedBack.setUserCode(userFeedBackModel.getUserCode());
		userFeedBack.setUuid(replace);
		userFeedBack.setContactInformation(userFeedBackModel.getContactInformation());
		userFeedBack.setCreateDate(new Date());
		userFeedBack.setUpdateDate(new Date());
		this.save(userFeedBack);
		/** 用户反馈变更积分添加积分记录 **/
        if (feedBackList.size()==0) {
      	    IntegralChangeModel integralChangeModel = new IntegralChangeModel();
        	integralChangeModel.setRewardType(RewardEnum.RewardType.FEEDBACK);
        	integralChangeModel.setUser(user);
        	integralChangeModel.setRewardSourceUuid(replace);
        	integralChangeModel.setChangeType(RewardEnum.ChangeType.REWARD);
        	SystemDictionary systemDictionary = systemDictionaryService.findByName(SystemDictionaryEnum.USER_FEEDBACK.getCode());
        	if (StringUtils.isNotBlank(systemDictionary.getValue())) {
        		integralChangeModel.setRewardIntegral(Long.valueOf(systemDictionary.getValue()));
        		userService.updateUserIntegralChange(integralChangeModel);
			}
        }
	}
	/**根据usercode查询反馈信息
	 * @return **/
	@Override
	public List<UserFeedBack> findByUsercode(String userCode) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("userCode", userCode);
		return this.findByCondition(queryMap);
	}
	
}
