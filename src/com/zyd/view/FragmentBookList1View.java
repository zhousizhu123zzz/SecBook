package com.zyd.view;

import java.util.List;

import com.zyd.model.Book;

public interface FragmentBookList1View {

	public void toFragmentMain();

	public void toFragmentBook();

	public void setBookList(List<Book> bookList);

	public void setRefresh(boolean refreshable);

	public void setTitle();

	public void clearBookText();

	public void setBookText();

	public void showErrorMsg(String errorMsg);

}
