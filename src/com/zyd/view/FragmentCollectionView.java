package com.zyd.view;

import java.util.List;

import com.zyd.model.Book;

public interface FragmentCollectionView {

	public void toFragmentMain();

	public void toFragmentBook();

	public void setBookList(List<Book> bookList);

	public void setLVAdapter();

	public List<Integer> getCollectionDataListFromCache();

	public void showErrorMsg(String errorMsg);

	public void setProgressBarVisible(int visibility);

}
