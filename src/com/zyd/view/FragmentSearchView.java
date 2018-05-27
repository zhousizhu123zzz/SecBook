package com.zyd.view;

public interface FragmentSearchView {

	public void toFragmentMain();//返回主界面

	public void toFragmentSearchList();//进入商品列表界面

	public void saveSearchTextToCache(String searchText);

	public void setHistorySearchVisible(int visibility);

	public void setHistorySearchListFromCache();

	public void setHotSearchFromCache();

	public String getSearchText();

	public void showErrorMsg(String errorMsg);

}
