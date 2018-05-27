package com.zyd.view;

import java.util.List;

import com.zyd.model.Book;

public interface FragmentRankView {

	public void toFragmentMain(); //������ҳ

	public void toFragmentBook(); //��ת��ͼ���������

	public void setRefreshing(boolean isRefreshing);//�����Ƿ�ˢ��

	public void setBookList(List<Book> bookList); //��������

	public void setDataAdapter();//��������������

	public void showErrorMsg(String errorMsg); //��ʾ������Ϣ

}
