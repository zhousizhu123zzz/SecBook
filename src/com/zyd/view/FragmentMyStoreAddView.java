package com.zyd.view;

import com.zyd.model.Book;

public interface FragmentMyStoreAddView {

	public void toFragmentMyStore(); //返回我的店铺界面

	public void showErrorMsg(String errorMsg); //显示错误信息

	public void setCurrentUser(); //设置当前用户信息

	public Book getAddedBook();//获取已经添加的图书

	public boolean validateInput(); //验证输入

	public void setProgressBarVisible(int visibility); //显示加载框

	public void showChooseBookPicDialog();

	public String getBookName();

	public String getAuthor();

	public String getBookDesc();

	public String getPrice();

}
