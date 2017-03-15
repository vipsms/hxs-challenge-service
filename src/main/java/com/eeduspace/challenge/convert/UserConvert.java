package com.eeduspace.challenge.convert;

import com.eeduspace.challenge.enumeration.SystemDictionaryEnum;
import com.eeduspace.challenge.enumeration.TaskEnum;
import com.eeduspace.challenge.enumeration.UserEnum;
import com.eeduspace.challenge.enumeration.VipEnum;
import com.eeduspace.challenge.model.*;
import com.eeduspace.challenge.persist.dao.UserInfoMapper;
import com.eeduspace.challenge.persist.dao.UserMapper;
import com.eeduspace.challenge.persist.po.*;
import com.eeduspace.challenge.service.SystemDictionaryService;
import com.eeduspace.challenge.util.redis.RedisClientTemplate;
import com.eeduspace.uuims.comm.util.base.DateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Author: dingran
 * Date: 2016/7/12
 * Description:
 */
@Component
public class UserConvert {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserMapper userMapper;
    @Resource
    private SystemDictionaryService systemDictionaryService;

    @Autowired
    private RedisClientTemplate redisClientTemplate;
    /**
     * 转换用户实体
     * @param user
     * @return
     */
    public UserModel fromUserPo(User user, Boolean isDetail) {
        UserModel userModel = new UserModel();
        if(user!=null){
            userModel.setUserCode(user.getUserCode());
            userModel.setUserName(user.getUserName());
            userModel.setMobile(user.getMobile());
            userModel.setEmail(user.getEmail());
            userModel.setIsBlack(user.getIsBlack());
            userModel.setUserOnlineState(user.getUserOnlineState());
            userModel.setIsVip(user.getIsVip());
            userModel.setVipStartDate(user.getVipStartDate());
            userModel.setVipEndDate(user.getVipEndDate());
            userModel.setFightValue(user.getFightValue());
            userModel.setUserPoints(user.getUserPoints());
            userModel.setFirst(user.getFirst());
            userModel.setSecond(user.getSecond());
            userModel.setThird(user.getThird());
            userModel.setUserInfoDegree(user.getUserInfoDegree());
            userModel.setCreateDate(user.getCreateDate());
            userModel.setUpdateDate(user.getUpdateDate());
            userModel.setOverdue(true);
            userModel.setVipEndDate(null);
            if(!StringUtils.isEmpty(user.getVipEndDate())){
                userModel.setVipEndDate(user.getVipEndDate());
                Date dateNow=new Date();
                if(user.getVipEndDate().after(dateNow)){
                    userModel.setOverdue(false);
                    userModel.setVIPExpireDays(DateUtils.daysBetween(new Timestamp(dateNow.getTime()), new Timestamp(user.getVipEndDate().getTime()))+1);
                }
            }
            //获取详情
            if(isDetail){
                UserInfo userInfo= userInfoMapper.findByUserCode(user.getUserCode());
                if(userInfo!=null){
                    userModel.setSchool(userInfo.getSchool());
                    userModel.setHeadImgUrl(userInfo.getHeadImgUrl());
                    userModel.setSex(userInfo.getSex());
                    userModel.setNickName(userInfo.getNickName());
                    userModel.setBirthday(userInfo.getBirthday());
                    userModel.setGrade(userInfo.getGrade());
                    userModel.setProvince(userInfo.getProvince());
                    userModel.setCity(userInfo.getCity());
                    userModel.setCountry(userInfo.getCountry());
                    userModel.setArea(userInfo.getArea());
                }
            }
            //TODO 设置体力值
            String stamina=  redisClientTemplate.get("hxs_challenge_stamina_"+userModel.getUserCode());
            if(org.apache.commons.lang.StringUtils.isBlank(stamina)){
                SystemDictionary systemDictionary=new SystemDictionary();
                systemDictionary.setValue("3,1");
                if(userModel.getIsVip()==0){
                    systemDictionary= systemDictionaryService.findByName(SystemDictionaryEnum.USER_STAMINA.getCode());
                }else{
                	systemDictionary= systemDictionaryService.findByName(SystemDictionaryEnum.USER_STAMINA_VIP.getCode());
                }
                    

                stamina=systemDictionary.getValue();
                redisClientTemplate.setex("hxs_challenge_stamina_"+userModel.getUserCode(), com.eeduspace.challenge.util.DateUtils.getTadaySurplusTime(), systemDictionary.getValue());
            }
            String[] staminaStrings=stamina.split(",");
            userModel.setStamina(Integer.parseInt(staminaStrings[0]));

            //TODO 获取好友排名、战斗力排名
            userModel.setFightRank(userMapper.getFightBankByUserCode(userModel.getUserCode()).getFightRank());
            userModel.setFriendRank(userMapper.getFriendBankByUserCode(userModel.getUserCode()).getFriendRank());
        }
        return userModel;
    }

    public List<UserModel> fromUserPos(List<User> userPos, Boolean isAll) {
        List<UserModel> list = new ArrayList<>();
        if (userPos != null && !userPos.isEmpty()) {
            for (User po : userPos) {
                list.add(this.fromUserPo(po,false));
            }
        }
        return list;
    }

    /**
     * 扫码登录实体转换
     * @param userPo
     * @param scanStatus
     * @return
     */

    public static UserModel fromUserPo(User userPo,UserEnum.ScanStatus scanStatus) {
        UserModel userModel = new UserModel();
        if(userPo!=null){
            userModel.setUserCode(userPo.getUserCode());
            userModel.setUserName(userPo.getUserName());
            userModel.setEmail(userPo.getEmail());
            userModel.setMobile(userPo.getMobile());
            userModel.setVIPExpireDays(0l);
            userModel.setOverdue(true);
            userModel.setVipEndDate(null);
            if(!StringUtils.isEmpty(userPo.getVipEndDate())){
                userModel.setVipEndDate(userPo.getVipEndDate());
                Date dateNow=new Date();
                if(userPo.getVipEndDate().after(dateNow)){
                    userModel.setOverdue(false);
                    userModel.setVIPExpireDays(DateUtils.daysBetween(new Timestamp(dateNow.getTime()), new Timestamp(userPo.getVipEndDate().getTime()))+1);
                }
            }
            userModel.setPassword(userPo.getPassword());
            userModel.setIsVip(userPo.getIsVip());
            userModel.setScanStatus(scanStatus==null?"":scanStatus.toString());
            userModel.setCreateDate(userPo.getCreateDate());
        }

        return userModel;
    }
    /**
     * vip包转化实体类
     * @param vipPack
     * @return  
     */
    public static VipPackModel fromVipPackPo(VipPack vipPack){
		VipPackModel vipPackModel=new VipPackModel();
		vipPackModel.setId(vipPack.getId()==null?null:vipPack.getId());
		vipPackModel.setVipDesc(vipPack.getVipType()==null?null:VipEnum.VipPackTypeEnum.getDesc(vipPack.getVipType()));
		vipPackModel.setVipDay(vipPack.getVipDay());
		vipPackModel.setVipPrice(String.valueOf(vipPack.getVipPrice()));
		vipPackModel.setVipType(vipPack.getVipType());
		vipPackModel.setUuid(vipPack.getUuid());
		vipPackModel.setOperationManager(vipPack.getOperationManager());
		if (vipPack.getDiscountStartTime()!=null&&vipPack!=null) {
			if(DateUtils.isBetween(new Date(), vipPack.getDiscountStartTime(), vipPack.getDiscountEndTime(), 1)){
				vipPackModel.setDiscountPrice(vipPack.getDiscountPrice());
				vipPackModel.setDiscountStartDate(DateUtils.toString(vipPack.getDiscountStartTime(), DateUtils.DATE_FORMAT_DATETIME));
				vipPackModel.setDiscountEndDate(DateUtils.toString(vipPack.getDiscountEndTime(), DateUtils.DATE_FORMAT_DATETIME));
				vipPackModel.setIsDiscount(true);
			}
		}
		return vipPackModel;
	}
    /**
     * vip包转化实体类
     */
    public static VipPack fromVipPackModel( VipPackModel vipPackModel) throws ParseException{
    	VipPack vipPack=new VipPack();
    	vipPack.setVipDesc(vipPackModel.getVipDesc()==null?null:vipPackModel.getVipDesc());
    	vipPack.setVipPrice(vipPackModel.getVipPrice()==null?null:Double.valueOf(vipPackModel.getVipPrice()));
    	vipPack.setVipType(vipPackModel.getVipType()==null?null:vipPackModel.getVipType());
    	vipPack.setUuid(vipPackModel.getUuid()==null?UUID.randomUUID().toString().replace("-", ""):vipPackModel.getUuid());
    	vipPack.setOperationManager(vipPackModel.getOperationManager()==null?null:vipPackModel.getOperationManager());
    	vipPack.setIsDiscount((byte) (vipPackModel.getIsDiscount()==false?0:1));
    	vipPack.setDiscountPrice(vipPackModel.getDiscountPrice()==null?null:Double.valueOf(vipPackModel.getDiscountPrice()));
    	vipPack.setOperationManager(vipPackModel.getOperationManager()==null?null:vipPackModel.getOperationManager());
    	vipPack.setDiscountEndTime(vipPackModel.getDiscountEndDate()==null?null:DateUtils.parseDate(vipPackModel.getDiscountEndDate()));
    	vipPack.setDiscountStartTime(vipPackModel.getDiscountStartDate()==null?null:DateUtils.parseDate(vipPackModel.getDiscountStartDate()));
    	vipPack.setVipDay(vipPackModel.getVipType()==null?null:VipEnum.VipPackTypeEnum.getValue(vipPackModel.getVipType()));
    	vipPack.setCreateDate(new Date());
    	vipPack.setUpdateDate(new Date());
		return vipPack;
	}
	/**
	 * 订单的转换实体类
	 */
	public static Order fromOrderModelToOrder(OrderModel orderModel) {
		Order order = new Order();
		order.setIsDel(orderModel.getIsDel());
		order.setIsPay(orderModel.getIsPay());
		order.setOrderName(orderModel.getOrderName());
		order.setOrderPrice(orderModel.getOrderPrice());
//		order.setOrderSn(orderModel.getOrderSn());
		order.setOrderType(orderModel.getOrderType());
		order.setPayType(orderModel.getPayType());
		order.setTranscationSn(orderModel.getTranscationSn());
		order.setUserCode(orderModel.getUserCode());
		order.setUuid(orderModel.getUuid());
		order.setCreateDate(new Date());
		order.setUpdateDate(new Date());
		return order;
	}
	/**
	 * 订单的转换实体类
	 */
	public static OrderModel fromOrderToOrderModel( Order order) {
		OrderModel orderModel = new OrderModel();
		orderModel.setId(order.getId());
		orderModel.setIsDel(order.getIsDel());
		orderModel.setIsPay(order.getIsPay());
		orderModel.setOrderName(order.getOrderName());
		orderModel.setOrderPrice(order.getOrderPrice());
		orderModel.setOrderSn(order.getOrderSn());
		orderModel.setOrderType(order.getOrderType());
		orderModel.setPayType(order.getPayType());
		orderModel.setTranscationSn(order.getTranscationSn());
		orderModel.setUserCode(order.getUserCode());
		orderModel.setUuid(order.getUuid());
		orderModel.setCreateDate(order.getCreateDate());
		orderModel.setUpdateDate(order.getUpdateDate());
		return orderModel;
	}
	/**
	 * 订单条目表
	 */
	public static OrderItem fromModelToOrderItem(OrderItemModel orderItemModel) {
		OrderItem item  = new OrderItem();
		item.setPrice(orderItemModel.getPrice()==null?null:orderItemModel.getPrice());
		item.setProductName(orderItemModel.getProductName()==null?null:orderItemModel.getProductName());
		item.setProductUuid(orderItemModel.getProductUuid()==null?null:orderItemModel.getProductUuid());
		item.setQuantity(orderItemModel.getQuantity()==null?null:orderItemModel.getQuantity());
		item.setCreateDate(new Date());
		item.setUpdateDate(new Date());
		item.setPrice(orderItemModel.getPrice()==null?null:orderItemModel.getPrice());
		item.setOrderUuid(orderItemModel.getOrderUuid()==null?null:orderItemModel.getOrderUuid());		
		return item;
	}
	/**
	 * 订单条目表
	 */
	public static  OrderItemModel fromOrderItemToModel(OrderItem orderItem) {
		OrderItemModel orderItemModel = new OrderItemModel();
		orderItemModel.setId(orderItem.getId()==null?null:orderItem.getId());
		orderItemModel.setPrice(orderItem.getPrice()==null?null:orderItem.getPrice());
		orderItemModel.setProductName(orderItem.getProductName()==null?null:orderItem.getProductName());
		orderItemModel.setProductUuid(orderItem.getProductUuid()==null?null:orderItem.getProductUuid());
		orderItemModel.setQuantity(orderItem.getQuantity()==null?null:orderItem.getQuantity());
		orderItemModel.setPrice(orderItem.getPrice());
		orderItemModel.setOrderUuid(orderItem.getOrderUuid()==null?null:orderItem.getOrderUuid());
		orderItemModel.setCreateDate(orderItem.getCreateDate());
		orderItemModel.setUpdateDate(orderItem.getUpdateDate());
		orderItemModel.setUuid(orderItem.getUuid());
		
		return orderItemModel;		
		
	}
	/**
	 * 下载App
	 */
	public static AppUpdateModel fromAppUpdateToModel(AppUpdate appUpdate) {
		AppUpdateModel appUpdateModel = new AppUpdateModel();
		appUpdateModel.setId(appUpdate.getId());
		appUpdateModel.setAppDescribe(appUpdate.getAppDescribe());
		appUpdateModel.setAppName(appUpdate.getAppName());
		appUpdateModel.setAppVersion(appUpdate.getAppVersion());
		appUpdateModel.setAvailable(appUpdate.getAvailable());
		appUpdateModel.setCreateDate(appUpdate.getCreateTime());
		appUpdateModel.setDownUrl(appUpdate.getDownUrl());
		appUpdateModel.setNecessary(appUpdate.getNecessary());
		appUpdateModel.setUuid(appUpdate.getUuid());
		return appUpdateModel;
	}



    public UserEnum.EquipmentType converterSourceEquipmentType(String type) {
        if(org.apache.commons.lang3.StringUtils.isBlank(type)){
            return null;
        }
        switch (type) {
            case "Test":
                return UserEnum.EquipmentType.Test;
            case "Android":
                return UserEnum.EquipmentType.Android;
            case "Ios":
                return UserEnum.EquipmentType.Ios;
            case "Tv":
                return UserEnum.EquipmentType.Tv;
            default:
                return UserEnum.EquipmentType.Web;
        }
    }
    public int converterUserSexType(Integer type) {
        if(type==null){
            return UserEnum.Sex.UnKnow.getValue();
        }
        switch (type) {
            case 0:
                return UserEnum.Sex.Man.getValue();
            case 1:
                return UserEnum.Sex.Woman.getValue();
            case 2:
                return UserEnum.Sex.UnKnow.getValue();
            default:
                return UserEnum.Sex.UnKnow.getValue();
        }
    }
    
    public String converterSourceEventtType(String type) {
        if(org.apache.commons.lang3.StringUtils.isBlank(type)){
            return null;
        }
        switch (type) {
            case "challenge_stand_share":
                return TaskEnum.SpecificTasks.CHALLENGE_STAND_SHARE.getTaskCode();
            case "battle_stand_share":
                return TaskEnum.SpecificTasks.BATTLE_STAND_SHARE.getTaskCode();
            case "rank_share":
                return TaskEnum.SpecificTasks.RANK_SHARE.getTaskCode();
            case "stand_share":
                return TaskEnum.SpecificTasks.STAND_SHARE.getTaskCode();
            default:
                return null;
        }
    }
    /**
     *转换签到实体 
     * @param userSign 
     * @throws ParseException 
     */
	public static UserSignModel fromuserSignToModel(UserSign userSign) throws ParseException {
		UserSignModel userSignModel = new UserSignModel();
		userSignModel.setCreateDate(userSign.getCreateDate());		
		userSignModel.setId(userSign.getId());
		userSignModel.setIntegral(userSign.getIntegral());
		userSignModel.setSignDate(userSign.getSignDate());
		userSignModel.setSignTimes(userSign.getSignTimes());
		userSignModel.setUpdateDate(userSign.getUpdateDate());
		userSignModel.setUserCode(userSign.getUserCode());
		userSignModel.setUuid(userSign.getUuid());
    	int isTodeySign = DateUtils.compareDate(userSign.getSignDate(), new Date(), 5);
    	if (isTodeySign==0) {
			userSignModel.setIsTodeaySign(true);
		}
		return userSignModel;
	}
 
    
}
