package com.zyd.view;

public interface BaseFragmentView {

	public void toFragmentLoading(); //跳转到加载界面

	public void toFragmentServerEx(); //跳转到服务器异常界面

	public void toFragmentNoNet(); //跳转到无网络连接界面

	public void setTransmissionData(Object o); //设置需要传递的数据

	public Object getTransmissionData();//获取需要传递的数据

}
