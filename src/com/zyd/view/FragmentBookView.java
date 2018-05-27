package com.zyd.view;

import java.util.List;

import com.zyd.model.Book;
import com.zyd.model.Order;
import com.zyd.model.User;

import android.support.v4.app.Fragment;

public interface FragmentBookView {

	public void toFragmentBack();

	public void toFragmentBookProduct();

	public void toFragmentBookDetails();

	public void toFragmentShoppingCart();

	public void toFragmentOrderDetails();

	public User getCurrentUser();

	public void setOrderList(List<Order> orderList);

	public void setShoppingCartCircle();

	public void reloadFragment();//ÖØÐÂ¼ÓÔØFragment

	public Fragment getFragment(int position);

	public void showErrorMsg(String errorMsg);

	public Order getOrder();

	public int getBookId();

	public List<Order> getOrderListToFragmentOrderDetails();

	public void setBook(Book book);

	public void setOrder();
}
