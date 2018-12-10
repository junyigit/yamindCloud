package com.yamind.cloud.modules.sys.service;

import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.modules.sys.entity.SysOrgEntity;

import java.util.List;

/**
 * 组织机构
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年8月17日 上午11:32:55
 */
public interface SysOrgService {

	List<SysOrgEntity> listOrg();
	
	List<SysOrgEntity> listOrgTree();
	
	R saveOrg(SysOrgEntity org);
	
	R getOrg(Long orgId);
	
	R updateOrg(SysOrgEntity org);
	
	R bactchRemoveOrg(Long[] id);
	
}
