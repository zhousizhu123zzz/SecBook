package com.zyd.view;

public interface FragmentSearchView {

	public void toFragmentMain();//����������

	public void toFragmentSearchList();//������Ʒ�б����

	public void saveSearchTextToCache(String searchText);

	public void setHistorySearchVisible(int visibility);

	public void setHistorySearchListFromCache();

	public void setHotSearchFromCache();

	public String getSearchText();

	public void showErrorMsg(String errorMsg);

}
