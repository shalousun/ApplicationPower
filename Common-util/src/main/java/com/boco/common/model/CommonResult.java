package com.boco.common.model;

import java.io.Serializable;

/**
 * 公共返回结果
 * @author sunyu
 *
 */
public class CommonResult<T> implements Serializable {
	
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = -7268040542410707954L;

	/**
	 * 是否成功
	 */
	private boolean success = false;

	/**
	 * 返回信息
	 */
	private String message;

	/**
	 * 装在数据
	 */
	private T data;

	/**
	 * 错误代码
	 */
	private int code;

	/**
	 * 默认构造器
	 */
	public CommonResult(){
		
	}
	/**
	 * 
	 * @param success
	 * 			是否成功
	 * @param message
	 * 			返回的消息
	 */
	public CommonResult(boolean success, String message){
		this.success = success;
		this.message = message;
	}
	/**
	 * 
	 * @param success
	 * 			是否成功
	 */
	public CommonResult(boolean success){
		this.success = success;
	}

	/**
	 *
	 * @param code
	 * @param message
	 */
	public CommonResult(int code,String message){
		this.code = code;
		this.message = message;
	}
	/**
	 * 
	 * @param success
	 * 			是否成功
	 * @param message
	 * 			消息
	 * @param data
	 * 			数据
	 */
	public CommonResult(boolean success, String message, T data){
		this.success = success;
		this.message = message;
		this.data = data;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
