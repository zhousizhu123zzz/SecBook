package com.zyd.view;

import com.zyd.model.Book;

public interface FragmentMyStoreAddView {

	public void toFragmentMyStore(); //�����ҵĵ��̽���

	public void showErrorMsg(String errorMsg); //��ʾ������Ϣ

	public void setCurrentUser(); //���õ�ǰ�û���Ϣ

	public Book getAddedBook();//��ȡ�Ѿ���ӵ�ͼ��

	public boolean validateInput(); //��֤����

	public void setProgressBarVisible(int visibility); //��ʾ���ؿ�

	public void showChooseBookPicDialog();

	public String getBookName();

	public String getAuthor();

	public String getBookDesc();

	public String getPrice();

}
