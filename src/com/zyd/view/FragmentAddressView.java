package com.zyd.view;

import java.util.List;

import com.zyd.model.Address;
import com.zyd.model.User;

public interface FragmentAddressView {

	public void toFragmentBack();

	public void toFragmentAddressAdd(); //��������ջ���ַ����

	public void setAddressList(List<Address> addressList);

	public void showDeleteDialog(int position); //��ʾ�Ƿ�ɾ������ʾ��

	public Address getDeleteAddress(); //��ȡɾ���Ĳ���

	public User getCurrentUser();

	public void setProgressBarVisible(int visibility);

	public void refresh(); //ɾ���ɹ���ˢ�½���

	public void showErrorMsg(String errorMsg); //��ʾ������Ϣ

	public void setViewText();

}
