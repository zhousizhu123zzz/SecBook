package com.zyd.view;

import java.util.List;

import com.zyd.model.Book;
import com.zyd.model.BookBigType;

public interface FragmentMenuView {

	public void toFragmentHome(); //跳转到主页界面

	public void toFragmentBookList();//跳转到图书列表界面

	public void showErrorMsg(String errorMsg); //显示错误信息

	public void saveBookBigTypeList(List<BookBigType> bookBigTypeList);//保存图书大类信息到缓存中

	public void setBookBigTypeList(List<BookBigType> bookBigTypeList); //设置图书大类集合

	public void setBookList(List<Book> bookList);

	public void refresh(); //获取到数据，刷新列表

}
