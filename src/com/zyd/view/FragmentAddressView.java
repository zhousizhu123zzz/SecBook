package com.zyd.view;

import java.util.List;

import com.zyd.model.Address;
import com.zyd.model.User;

public interface FragmentAddressView {

	public void toFragmentBack();

	public void toFragmentAddressAdd(); //进入添加收货地址界面

	public void setAddressList(List<Address> addressList);

	public void showDeleteDialog(int position); //显示是否删除的提示框

	public Address getDeleteAddress(); //获取删除的参数

	public User getCurrentUser();

	public void setProgressBarVisible(int visibility);

	public void refresh(); //删除成功，刷新界面

	public void showErrorMsg(String errorMsg); //显示错误信息

	public void setViewText();

}
