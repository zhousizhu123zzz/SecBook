package com.zyd.view;

import java.util.List;

import com.zyd.model.Book;

public interface FragmentSearchListView {

	public void toFragmentSearch();

	public void toFragmentBook();

	public void setSearchTextFromFragmentSearch();

	public String getSearchText();

	public String getSearchTextFromFragmentSearch();

	public void setLVAdapter();

	public void setBookList(List<Book> bookList);

	public void setContentVisible();

	public void setLoadingVisible();

	public void setNodataVisible();

	public void setServerExVisible();

	public void setNoNetVisible();

	public void showErrorMsg(String errorMsg);

}
