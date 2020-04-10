package com.yamind.cloud.common.entity;

import com.yamind.cloud.common.constant.SystemConstant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 页面响应entity
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年8月8日 上午11:40:42
 */
public class R extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public static final int CUSTOM_OK_CODE = 200;

	public static final String CUSTOM_OK_MSG = "请求成功";

	public R() {
		put("code", 0);
	}

	/** error 通用 */
	public static R error() {
		return error(500, "未知异常，请联系管理员");
	}

	public static R error(String msg) {
		return error(500, msg);
	}

	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}


	/** customOk 用于前后端分离接口、app 接口*/

	/**
	 * code 200
	 * @return
	 */
	public static R customOk() {
		return customOk(CUSTOM_OK_MSG);
	}

	/**
	 * code 200 ,自定义 msg
	 * @param msg
	 * @return
	 */
	public static R customOk(String msg) {
		return customOk(CUSTOM_OK_CODE, msg);
	}

	public static R customOk(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	/**
	 * code 200 请求成功 返回数据
	 * @param data
	 * @return
	 */
	public static R customOk(Object data) {
		return customOk(CUSTOM_OK_MSG, data);
	}
	/**
	 * code 200 自定义 msg 并返回 data
	 * @param msg
	 * @param data
	 * @return
	 */
	public static R customOk(String msg, Object data) {
		return customOk(CUSTOM_OK_CODE, msg, data); // code: default 200
	}

	public static R customOk(int code, String msg, Object data) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		r.put("data", data);
		return r;
	}

	public static R customRcover(R r) {
		int code = (int) r.get("code");
		if(code == 0) { // 转换为 200
			r.put("code",200);
			if(r.containsKey(SystemConstant.DATA_ROWS)) { // 包含 rows 数据
				Object obj = r.get(SystemConstant.DATA_ROWS);
				r.put("data",obj);
				r.remove(SystemConstant.DATA_ROWS);
			}
		}
		return r;
	}

	/** 原来的后端接口 */
	public static R ok(String msg) {
		R r = new R();
		//r.put("code", 200);
		r.put("msg", msg);
		return r;
	}

	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}

	public static R ok() {
		return new R();
	}

	@Override
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}