package com.zyd.view;

import java.util.List;

import com.zyd.model.Address;
import com.zyd.model.Book;
import com.zyd.model.Coupon;
import com.zyd.model.Order;
import com.zyd.model.User;

public interface FragmentBookBookView {

	public void setBook(Book book); //����ͼ����Ϣ

	public Book getBook(); //��ȡ��ǰFragment��Book����Ҫ���ڴ��ݸ�FragmentBookDetails

	public void setAddressList(List<Address> addressList); //�����ջ���ַ��Ϣ

	public void setCouponList(List<Coupon> couponList);//�����Ż�ȯ��Ϣ

	public List<Address> getAddressList();//��ȡ��ǰFragment��AddressList����Ҫ���ڴ��ݸ�FragmentBookDetails

	public void setProgressBarVisible(int visibility); //���ü��ؽ�������ʾ���

	public int getBookId(); //���ݻ�ȡ��ͼ��Id����

	public void setBookText();

	public void setAddressText();

	public void setCouponText();

	public User getCurrentUser();

	public void toFragmentAddress(); //��ת���ջ���ַ����

	public void showErrorMsg(String errorMsg);

	public void setOrderList(List<Order> orderList);

}
