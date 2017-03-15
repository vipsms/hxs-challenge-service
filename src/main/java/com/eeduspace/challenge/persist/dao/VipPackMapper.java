package com.eeduspace.challenge.persist.dao;

import com.eeduspace.challenge.persist.po.VipPack;

public interface VipPackMapper extends BaseDao<VipPack>{

	VipPack saveVipPack(VipPack vipPack);
  
}