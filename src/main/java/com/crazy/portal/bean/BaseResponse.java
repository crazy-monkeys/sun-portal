package com.crazy.portal.bean;

import com.crazy.portal.util.I18NUtils;

public class BaseResponse {
	private int code;
	private String msg;
	private Object data;

    public BaseResponse(){
	}

    public BaseResponse(int code, String msg, Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public BaseResponse(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void success(){
		this.code = 1;
		this.msg = I18NUtils.getMsg("system.success");
	}

	public void success(Object data) {
    	this.success();
		this.data = data;
	}
}
