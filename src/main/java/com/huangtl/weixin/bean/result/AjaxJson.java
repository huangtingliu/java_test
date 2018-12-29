package com.huangtl.weixin.bean.result;

/**
 * $.ajax公共返回类
 */
public class AjaxJson {

	private Integer code;//自定义code
	private boolean success = true;// 是否成功
	private String msg = "操作成功";// 提示信息
	private Object obj = null;// 其他信息

	public AjaxJson() {
	}

	public AjaxJson(boolean success, String msg, Object obj,Integer code) {
		this.code = code;
		this.success = success;
		this.msg = msg;
		this.obj = obj;
	}

	public static AjaxJson success(Object obj){
		return new AjaxJson(true,"操作成功",obj,AjaxJsonCode.OK);
	}
	public static AjaxJson success(Object obj,String msg){
		return new AjaxJson(true,msg,obj,AjaxJsonCode.OK);
	}
	public static AjaxJson success(Object obj,String msg,Integer code){
		return new AjaxJson(true,msg,obj,code);
	}
	public static AjaxJson fail(){
		return new AjaxJson(false,"操作失败","",AjaxJsonCode.FAIL);
	}
	public static AjaxJson fail(Object obj,String msg){
		return new AjaxJson(false,msg,obj,AjaxJsonCode.FAIL);
	}
	public static AjaxJson fail(String msg){
		return new AjaxJson(false,msg,null,AjaxJsonCode.FAIL);
	}
	public static AjaxJson fail(String msg,Integer code){
		return new AjaxJson(false,msg,null,code);
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}
