package com.zyd.biz;

/**
 * 网络请求回调接口
 * @author Administrator
 *
 */
public interface MainListener {

	public <T> void success(String jsonData);

	public void serverEx();

}
