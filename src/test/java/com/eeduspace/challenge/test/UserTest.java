package com.eeduspace.challenge.test;

import com.eeduspace.challenge.convert.UserConvert;
import com.eeduspace.challenge.enumeration.RewardEnum;
import com.eeduspace.challenge.model.IntegralChangeModel;
import com.eeduspace.challenge.model.UserModel;
import com.eeduspace.challenge.model.request.UserRequestModel;
import com.eeduspace.challenge.persist.dao.UserInfoMapper;
import com.eeduspace.challenge.persist.po.User;
import com.eeduspace.challenge.persist.po.UserInfo;
import com.eeduspace.challenge.persist.po.UserSign;
import com.eeduspace.challenge.responsecode.BaseResponse;
import com.eeduspace.challenge.service.IntegralChangeService;
import com.eeduspace.challenge.service.UserFriendService;
import com.eeduspace.challenge.service.UserService;
import com.eeduspace.challenge.service.UserSignService;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;

import java.util.List;
import java.util.UUID;

/**
 * Author: dingran
 * Date: 2016/7/15
 * Description:
 */

public class UserTest extends BaseTest {

    @Inject
    private UserService userService;

    @Inject
    private UserFriendService userFriendService;
    @Inject
    private UserSignService userSignService;
    @Autowired
    private UserConvert userConvert;
    @Inject
    private IntegralChangeService integralChangeService;
    @Inject
    private UserInfoMapper userInfoMapper;
    @Test
    public void testRegister(){
        UserRequestModel userModel=new UserRequestModel();
        userModel.setPhone("13390897871");
        userModel.setPassword("13390897871");
        try {
            userService.register(userModel);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  // @org.junit.TestMessage
    public void login(){
        UserRequestModel userModel=new UserRequestModel();
        userModel.setMobile("13390897878");
        userModel.setPassword("13390897878");
        userModel.setUserCode("1854aec395e74ca28815267bf5577209");
        try {
//              User userInfoPo =userService.findByUserCode(userModel.getUserCode());
//            User userInfoPo =userService.findByMobile(userModel.getMobile());
//            logger.debug("userInfoPo:{}", gson.toJson(userInfoPo));
//            logger.debug("userInfoModel:{}",gson.toJson(userConvert.fromUserPo(userInfoPo, true)));
//            UserModel userM= userService.login(userModel,false);
//            logger.debug("userM:{}",gson.toJson(userM));
//            UserModel userModel1= userService.findDetailAllByUserCode("1854aec395e74ca28815267bf5577209");
//            logger.debug("userModel1:{}",gson.toJson(userModel1));
//            UserInfo userInfo=   userService.getDetailByUserCode("1854aec395e74ca28815267bf5577209");
            UserModel userModel1=userService.findDetailAllByUserCode("da95e1572e134177875c18a3289de842");
            logger.debug("userInfo:{}",gson.toJson(userModel1));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  //  @org.junit.TestMessage
         public void editPwd(){
        UserRequestModel userRequestModel=new UserRequestModel();
        userRequestModel.setPassword("13390897878000");
        userRequestModel.setUserCode("1854aec395e74ca28815267bf5577209");
        userRequestModel.setOldPassword("13390897878");
        userRequestModel.setToken("TK7D272252F1EA882D");
        try {
//            userService.editPwd(userRequestModel);
            BaseResponse user= userService.resetPwd(userRequestModel);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   // @org.junit.TestMessage
    public void update(){
        UserRequestModel userRequestModel=new UserRequestModel();
        userRequestModel.setPassword("13390897878000");
        userRequestModel.setUserCode("1854aec395e74ca28815267bf5577209");
        userRequestModel.setOldPassword("13390897878");
        userRequestModel.setToken("TK7D272252F1EA882D");

        userRequestModel.setHeadImgUrl("test");
        try {
//            userService.editPwd(userRequestModel);
              userService.update(userRequestModel);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   // @org.junit.TestMessage
    public void findFriendByUserCode(){
        UserRequestModel userRequestModel=new UserRequestModel();
        userRequestModel.setPassword("13390897878000");
        userRequestModel.setUserCode("1854aec395e74ca28815267bf5577209");
        userRequestModel.setOldPassword("13390897878");
        userRequestModel.setToken("TK7D272252F1EA882D");

        userRequestModel.setHeadImgUrl("test");
        try {
//            userService.editPwd(userRequestModel);
            List<UserModel> list=  userService.findFriendByUserCode("da95e1572e134177875c18a3289de842");
            List<UserModel> list2=userFriendService.findFriendByUserCode("da95e1572e134177875c18a3289de842");
            logger.debug("list:{}",gson.toJson(list));
            logger.debug("list2:{}",gson.toJson(list2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //@org.junit.TestMessage
    public void updateUserIntegralChange(){
        try {
//            userService.editPwd(userRequestModel);
            userService.updateUserIntegralChange("da95e1572e134177875c18a3289de842",200l, RewardEnum.ChangeType.EXCHANGE, RewardEnum.RewardType.SIGN,null);

            User user=userService.findByUserCode("da95e1572e134177875c18a3289de842");
            IntegralChangeModel integralChangeModel=new IntegralChangeModel();
            integralChangeModel.setChangeType(RewardEnum.ChangeType.EXCHANGE);
            integralChangeModel.setRewardType(RewardEnum.RewardType.SIGN);
            integralChangeModel.setRewardIntegral(200l);
            integralChangeModel.setRewardSourceUuid("test123");
            integralChangeModel.setUser(user);
//            userService.updateUserIntegralChange(integralChangeModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   // @Test
    public void testSign(){
        try {
            User user=userService.findByUserCode("da95e1572e134177875c18a3289de842");
            UserSign userSign = userSignService.findLastByUserCode("da95e1572e134177875c18a3289de842");
            UserSign userSign1 = userSignService.sign(user);
            logger.debug("userSign:{}",gson.toJson(userSign));
            logger.debug("userSign1:{}",gson.toJson(userSign1));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  //  @Test
    public void save(){
        for (int i = 45; i <60; i++) {
            User user=new User();
            user.setAccessKey(UUID.randomUUID().toString().replace("-", ""));
            user.setUserCode("us"+i);
            user.setChannelSource("000");
            user.setFightValue(Long.valueOf(i+""));
            user.setEquipmentType("TestMessage");
            UserInfo userInfo=new UserInfo();
            userInfo.setUserCode(user.getUserCode());
            userInfo.setNickName("用户1234"+i);
            userService.save(user);
            userInfoMapper.save(userInfo);
        }
    }
}
