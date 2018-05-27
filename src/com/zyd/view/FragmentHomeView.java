package com.zyd.view;

import java.util.List;

import com.zyd.model.Advertise;
import com.zyd.model.Book;

public interface FragmentHomeView extends BaseFragmentView {

	public void toFragmentSearch(); //跳转到搜索界面

	public void toFragmentBook(); //跳转到图书购买界面

	public void toFragmentMenu(); //跳转到全部分类界面

	public void toFragmentBookList(); //跳转到图书列表界面

	public void toFragmentRank();//跳转到排行榜界面

	public void toFragmentBookList1(String title); //跳转到图书列表界面

	public void toActivityCapture(); //跳转到二维码扫描界面

	public void setAdsList(List<Advertise> advertiseList); //设置广告数据

	public void setBookList(List<Book> bookList); //设置图书数据

	public void setRefreshing(boolean isRefresing); //设置是否需要刷新

	public void initBanner(); //初始化轮播图

	public void showErrorMsg(String errorMsg); //显示错误信息

}
