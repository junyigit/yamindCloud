package com.yamind.cloud.modules.sys.service;

import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.modules.sys.entity.SysOrgEntity;

import java.util.List;

/**
 * 组织机构
 */
public interface SysOrgService {

	List<SysOrgEntity> listOrg();
	
	List<SysOrgEntity> listOrgTree();
	
	R saveOrg(SysOrgEntity org);
	
	R getOrg(Long orgId);
	
	R updateOrg(SysOrgEntity org);
	
	R bactchRemoveOrg(Long[] id);
	
}
