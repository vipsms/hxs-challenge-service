package com.eeduspace.challenge.service;

import java.text.ParseException;
import java.util.List;

import com.eeduspace.challenge.model.VipPackModel;
import com.eeduspace.challenge.persist.po.VipPack;



/**
 * vip包
 */
public interface VipPackService extends BaseService<VipPack>{
	/**
	 *vip包查询所有list集合列表 
	 */
	public  List<VipPack> findAll();
	/**
	 *vip包保存
	 */
	public VipPack saveVipPack(VipPack vipPack);
	/**
	 *vip包根据id查询po
	 */
	public VipPack findById(String uuid);
	/**
	 *vip包删除
	 */
	public void deleteVipPack(VipPack vipPack);
	/**
	 *vip包修改
	 * @throws ParseException 
	 */
	public void updateVipPack(VipPack vipPack, VipPackModel vipPackModel) throws ParseException;

	
	
	
	
	
}
