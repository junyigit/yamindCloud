package com.yamind.cloud.modules.sys.controller;

import com.yamind.cloud.modules.sys.entity.SysLogEntity;
import com.yamind.cloud.modules.sys.service.SysLogService;

import com.yamind.cloud.common.annotation.SysLog;
import com.yamind.cloud.common.constant.SystemConstant;
import com.yamind.cloud.common.entity.Page;
import com.yamind.cloud.common.entity.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 系统日志
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年8月14日 下午10:01:36
 */
@RestController
@RequestMapping("/sys/log")
public class SysLogController extends AbstractController {

	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 日志列表
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	public Page<SysLogEntity> listLog(@RequestBody Map<String, Object> params) {
		return sysLogService.listLog(params);
	}
	
	/**
	 * 删除日志
	 * @param id
	 * @return
	 */
	@SysLog("删除日志")
	@RequestMapping("/remove")
	public R batchRemove(@RequestBody Long[] id) {
		return sysLogService.batchRemove(id);
	}
	
	/**
	 * 清空日志
	 * @return
	 */
	@SysLog("清空日志")
	@RequestMapping("/clear")
	public R batchRemoveAll() {
		return sysLogService.batchRemoveAll();
	}

}




