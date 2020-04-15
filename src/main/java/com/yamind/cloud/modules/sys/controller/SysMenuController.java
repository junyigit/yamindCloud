package com.yamind.cloud.modules.sys.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yamind.cloud.common.annotation.SysLog;
import com.yamind.cloud.common.constant.SystemConstant;
import com.yamind.cloud.common.entity.R;
import com.yamind.cloud.modules.sys.entity.SysMenuEntity;
import com.yamind.cloud.modules.sys.manager.SysMenuManager;
import com.yamind.cloud.modules.sys.service.SysMenuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 系统菜单controller
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年8月10日 上午12:23:44
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {

	@Resource
	private SysMenuService sysMenuService;

	@Autowired
	private SysMenuManager sysMenuManager;
	/**
	 * 用户菜单
	 * @return
	 */
	@RequestMapping("/user")
	public R user(){
		return sysMenuService.listUserMenu(getUserId());
	}

	/**
	 * N - 用户菜单
	 * @param params
	 * @return
	 */
	@RequestMapping("/getUserMenu")
	public R getUserMenu(@RequestBody Map<String, String> params) {
		String userId = params.get("userId");
		if(StringUtils.isEmpty(userId)) {
			return R.error("参数userId为空");
		}
		List<SysMenuEntity> menuList = sysMenuManager.listUserMenu(Long.parseLong(userId));
		if(!CollectionUtils.isEmpty(menuList)) {
			return R.customOk(menuList);
		}
		return R.error("该用户菜单列表为空");
	}

	/**
	 * 菜单列表
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	public List<SysMenuEntity> listMenu(@RequestParam Map<String, Object> params) {
		return sysMenuService.listMenu(params);
	}

	/**
	 * N - 菜单列表
	 * @param params
	 * @return
	 */
	@RequestMapping("/nlist")
	public R nlistMenu(Map<String, Object> params) {
		// listMenu -> SysMenuMapper.list(params) 对应 SQL 没有 where 条件，params 可不传
		List<SysMenuEntity> menuList = sysMenuService.listMenu(params);
		if(!CollectionUtils.isEmpty(menuList)) {
			return R.customOk(menuList);
		}
		return R.error("菜单列表为空");
	}



	/**
	 * 选择菜单(添加、修改)
	 * @return
	 */
	@RequestMapping("/select")
	public R select() {
		return sysMenuService.listNotButton();
	}

	/**
	 * N - 选择菜单(添加、修改
	 */
	@RequestMapping("/nselect")
	public R nselect() {
		return R.customRcover(sysMenuService.listNotButton());
	}

	/**
	 * 新增菜单
	 * @param menu
	 * @return
	 */
	@SysLog("新增菜单")
	@RequestMapping("/save")
	public R save(@RequestBody SysMenuEntity menu) {
		return sysMenuService.saveMenu(menu);
	}

	/**
	 * N - 新增菜单
	 * @param menu
	 * @return
	 */
	@SysLog("N - 新增菜单")
	@RequestMapping("/nsave")
	public R nsave(@RequestBody SysMenuEntity menu) {
		return R.customRcover(sysMenuService.saveMenu(menu));
	}


	/**
	 * 查询详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/info")
	public R info(@RequestBody Long id) {
		return sysMenuService.getMenuById(id);
	}


	/**
	 * N - 查询详情
	 * @param params
	 * @return
	 */
	@RequestMapping("/ninfo")
	public R ninfo(@RequestBody Map<String, String> params) { // String menuId 等前端测试
		String menuId = params.get("menuId");
		if(StringUtils.isEmpty(menuId)) {
			return R.error("参数menuId为空");
		}
		return R.customRcover(sysMenuService.getMenuById(Long.parseLong(menuId)));
	}



	/**
	 * 修改菜单
	 * @param menu
	 * @return
	 */
	@SysLog("修改菜单")
	@RequestMapping("/update")
	public R update(@RequestBody SysMenuEntity menu) {
		return sysMenuService.updateMenu(menu);
	}

	/**
	 * N - 修改菜单
	 * @param menu
	 * @return
	 */
	@SysLog("N - 修改菜单")
	@RequestMapping("/nupdate")
	public R nupdate(@RequestBody SysMenuEntity menu) {
		return R.customRcover(sysMenuService.updateMenu(menu));
	}


	/**
	 * 删除菜单
	 * @param id
	 * @return
	 */
	@SysLog("删除菜单")
	@RequestMapping("/remove")
	public R remove(@RequestBody Long[] id) {
		return sysMenuService.batchRemove(id);
	}


	/**
	 * N - 删除菜单
	 * @param object
	 * @return
	 */
	@SysLog("N - 删除菜单")
	@RequestMapping("/nremove")
	public R nremove(@RequestBody JSONObject object) { // Long[] id 等前端测试
		JSONArray array = object.getJSONArray("id");
		Long[] id = new Long[array.size()];
		for (int i = 0;i < array.size(); i ++) {
			id[i]= array.getLong(i);
		}
		return R.customRcover(sysMenuService.batchRemove(id));
	}

}
