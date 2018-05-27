package com.zyd.view;

import java.util.List;

import com.zyd.model.Order;
import com.zyd.model.User;

public interface FragmentOrderView {

	public void toFragmentMain();

	public void toFragmentOrderAll();

	public void toFragmentOrderPay();

	public void toFragmentOrderSend();

	public void toFragmentOrderReceipt();

	public void setProgressBarVisible(int visibility);

	public User getCurrentUser();

	public void setOrderList(List<Order> orderList);

	public List<Order> getOrderList();

	public void setVPAdapter();

	public void showErrorMsg(String errorMsg);

	public void setIndexVPFragmentPositionFromCache();

}
