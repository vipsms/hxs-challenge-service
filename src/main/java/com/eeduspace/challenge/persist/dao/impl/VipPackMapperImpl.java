
package com.eeduspace.challenge.persist.dao.impl;


import org.springframework.stereotype.Repository;

import com.eeduspace.challenge.persist.dao.VipPackMapper;
import com.eeduspace.challenge.persist.po.VipPack;
import com.google.gson.Gson;
@Repository("VipPackMapperImpl")
public class VipPackMapperImpl extends BaseDaoImpl<VipPack> implements VipPackMapper{

	@SuppressWarnings("unused")
	@Override
	public VipPack saveVipPack(VipPack vipPack) {
		Gson gson = new   Gson();
		this.save(vipPack);
	    return this.get(vipPack.getId());
	}


}
