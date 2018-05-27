package com.zyd.view;

import java.util.List;

import com.zyd.model.Book;
import com.zyd.model.BookBigType;

public interface FragmentMenuView {

	public void toFragmentHome(); //��ת����ҳ����

	public void toFragmentBookList();//��ת��ͼ���б����

	public void showErrorMsg(String errorMsg); //��ʾ������Ϣ

	public void saveBookBigTypeList(List<BookBigType> bookBigTypeList);//����ͼ�������Ϣ��������

	public void setBookBigTypeList(List<BookBigType> bookBigTypeList); //����ͼ����༯��

	public void setBookList(List<Book> bookList);

	public void refresh(); //��ȡ�����ݣ�ˢ���б�

}
