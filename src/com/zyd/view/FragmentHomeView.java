package com.zyd.view;

import java.util.List;

import com.zyd.model.Advertise;
import com.zyd.model.Book;

public interface FragmentHomeView extends BaseFragmentView {

	public void toFragmentSearch(); //��ת����������

	public void toFragmentBook(); //��ת��ͼ�鹺�����

	public void toFragmentMenu(); //��ת��ȫ���������

	public void toFragmentBookList(); //��ת��ͼ���б����

	public void toFragmentRank();//��ת�����а����

	public void toFragmentBookList1(String title); //��ת��ͼ���б����

	public void toActivityCapture(); //��ת����ά��ɨ�����

	public void setAdsList(List<Advertise> advertiseList); //���ù������

	public void setBookList(List<Book> bookList); //����ͼ������

	public void setRefreshing(boolean isRefresing); //�����Ƿ���Ҫˢ��

	public void initBanner(); //��ʼ���ֲ�ͼ

	public void showErrorMsg(String errorMsg); //��ʾ������Ϣ

}
