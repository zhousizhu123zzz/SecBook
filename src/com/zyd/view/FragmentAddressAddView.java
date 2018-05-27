package com.zyd.view;

import com.zyd.model.Address;

public interface FragmentAddressAddView {

	public void toFragmentAddress(); //返回收货地址界面

	public String getReceiptName(); //获取收货人

	public String getTelNo(); //获取收货人电话

	public String getAddress(); //获取收货地址

	public boolean validate(); //验证是否为空

	public void showErrorMsg(String errorMsg); //显示错误信息，验证信息，服务器信息等等

	public Address getUploadAdress(); //获取上传服务器的参数

	public void setEditAddress(); //设置需要编辑的地址

}
