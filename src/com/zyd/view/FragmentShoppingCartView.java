package com.zyd.view;

import java.util.List;

import com.zyd.model.Order;
import com.zyd.model.User;

public interface FragmentShoppingCartView {

	public void toFragmentBack();

	public void toFragmentSearch();

	public void toFragmentOrderDetails();

	public User getCurrentUser();

	public void setLVAdapter();

	public void showErrorMsg(String errorMsg);

	public void setOrderList(List<Order> orderList);

	public List<Order> getOrderList();

	public void setBuyLayoutVisible(int visibility);

}
