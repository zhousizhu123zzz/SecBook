package com.zyd.view;

import java.util.List;

import com.zyd.model.Address;
import com.zyd.model.Book;
import com.zyd.model.Coupon;
import com.zyd.model.Order;
import com.zyd.model.User;

public interface FragmentBookBookView {

	public void setBook(Book book); //设置图书信息

	public Book getBook(); //获取当前Fragment的Book，主要用于传递给FragmentBookDetails

	public void setAddressList(List<Address> addressList); //设置收货地址信息

	public void setCouponList(List<Coupon> couponList);//设置优惠券信息

	public List<Address> getAddressList();//获取当前Fragment的AddressList，主要用于传递给FragmentBookDetails

	public void setProgressBarVisible(int visibility); //设置加载进度条显示与否

	public int getBookId(); //数据获取的图书Id参数

	public void setBookText();

	public void setAddressText();

	public void setCouponText();

	public User getCurrentUser();

	public void toFragmentAddress(); //跳转到收货地址界面

	public void showErrorMsg(String errorMsg);

	public void setOrderList(List<Order> orderList);

}
