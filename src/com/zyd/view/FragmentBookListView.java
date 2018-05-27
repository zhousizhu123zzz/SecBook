package com.zyd.view;

import java.util.List;

import com.zyd.model.Book;
import com.zyd.model.BookBigType;

public interface FragmentBookListView {

	public void toFragmentMain(); //返回主界面

	public void toFragmentBook();//进入图书详情页

	public void setBookBigTypeList(); //设置从缓存中获取的图书分类集合

	public void setBookSmallTypeList(int position); //设置图书小类集合

	public void setBookList(List<Book> bookList); //设置从服务器获取的图书集合

	public void setDataAdapter();//设置数据适配器

	public void setRefreshing(boolean isRefreshing); //设置是否刷新

	public void saveBookBigTypeList(List<BookBigType> bookBigTypeList); //保存从服务器获取的图书大类数据

	public void refreshBookList();//刷新界面图书集合

	public int getBookSmallTypeId();

	public int getBookBigTypeId();

}
