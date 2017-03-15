package com.eeduspace.challenge.service.impl;



import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeduspace.challenge.enumeration.VipEnum;
import com.eeduspace.challenge.model.VipPackModel;
import com.eeduspace.challenge.persist.dao.VipPackMapper;
import com.eeduspace.challenge.persist.po.VipPack;
import com.eeduspace.challenge.service.VipPackService;
import com.eeduspace.challenge.util.Order;
import com.eeduspace.uuims.comm.util.base.DateUtils;

@Service("VipPackServiceImpl")
public class VipPackServiceImpl extends BaseServiceImpl<VipPack> implements VipPackService{
	@Resource
	private VipPackMapper vipPackMapperImpl;
	@Autowired
	public VipPackServiceImpl(VipPackMapper questionMapper) {
		this.baseDao = questionMapper;
	}
	@Override
	public List<VipPack> findAll() {
		
	Map<String, Object> queryMap = new HashMap<String, Object>();
	Order order =Order.asc("vip_day");
	List<VipPack> content = this.findByCondition(queryMap, order );
	return content;
	}
	/**
	 *vip包保存
	 */
	@Override
	public VipPack saveVipPack(VipPack vipPack) {
		return vipPackMapperImpl.saveVipPack(vipPack);
	}
	/**
	 *vip包根据id查询po
	 */
	@Override
	public VipPack findById(String uuid) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("uuid", uuid);
		return this.findByCondition(queryMap).get(0);
	}
	/**
	 *vip包删除
	 */
	@Override
	public void deleteVipPack(VipPack vipPack) {
	this.delete(vipPack);
	}
	/**
	 *vip包修改
	 * @throws ParseException 
	 */
	@Override
	public void updateVipPack(VipPack vipPack, VipPackModel vipPackModel) throws ParseException {
		
		if (StringUtils.isNotBlank(String.valueOf(vipPackModel.getVipPrice()))) {
			vipPack.setVipPrice(Double.valueOf(vipPackModel.getVipPrice()));
		}
		if (StringUtils.isNotBlank(vipPackModel.getVipType())) {
	    	vipPack.setVipType(vipPackModel.getVipType());
	    	vipPack.setVipDesc(VipEnum.VipPackTypeEnum.getDesc(vipPackModel.getVipType()));
	    	vipPack.setVipDay(VipEnum.VipPackTypeEnum.getValue(vipPackModel.getVipType()));
		}
		if (StringUtils.isNotBlank(vipPackModel.getOperationManager())) {
	    	vipPack.setOperationManager(vipPackModel.getOperationManager());
		}
		if (StringUtils.isNotBlank(String.valueOf(vipPackModel.getDiscountPrice()))) {
	    	vipPack.setDiscountPrice(vipPackModel.getDiscountPrice());
		}
		if (StringUtils.isNotBlank(vipPackModel.getOperationManager())) {
	    	vipPack.setOperationManager(vipPackModel.getOperationManager());
		}
		if (StringUtils.isNotBlank(vipPackModel.getDiscountEndDate())) {
	    	vipPack.setDiscountEndTime(DateUtils.parseDate(vipPackModel.getDiscountEndDate()));
		}
		if (StringUtils.isNotBlank(vipPackModel.getDiscountStartDate())) {
	    	vipPack.setDiscountStartTime(DateUtils.parseDate(vipPackModel.getDiscountStartDate()));
		}
		vipPack.setIsDiscount((byte) (vipPackModel.getIsDiscount()==false?0:1));
    	vipPack.setUpdateDate(new Date());
		this.update(vipPack);;		
	}
}
