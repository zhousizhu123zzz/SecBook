package com.zyd.view;

import java.util.List;

import com.zyd.model.Book;
import com.zyd.model.BookBigType;

public interface FragmentBookListView {

	public void toFragmentMain(); //����������

	public void toFragmentBook();//����ͼ������ҳ

	public void setBookBigTypeList(); //���ôӻ����л�ȡ��ͼ����༯��

	public void setBookSmallTypeList(int position); //����ͼ��С�༯��

	public void setBookList(List<Book> bookList); //���ôӷ�������ȡ��ͼ�鼯��

	public void setDataAdapter();//��������������

	public void setRefreshing(boolean isRefreshing); //�����Ƿ�ˢ��

	public void saveBookBigTypeList(List<BookBigType> bookBigTypeList); //����ӷ�������ȡ��ͼ���������

	public void refreshBookList();//ˢ�½���ͼ�鼯��

	public int getBookSmallTypeId();

	public int getBookBigTypeId();

}
