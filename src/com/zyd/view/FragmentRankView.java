package com.zyd.view;

import java.util.List;

import com.zyd.model.Book;

public interface FragmentRankView {

	public void toFragmentMain(); //返回主页

	public void toFragmentBook(); //跳转到图书详情界面

	public void setRefreshing(boolean isRefreshing);//设置是否刷新

	public void setBookList(List<Book> bookList); //设置数据

	public void setDataAdapter();//设置数据适配器

	public void showErrorMsg(String errorMsg); //显示错误信息

}
