package com.zyd.view.impl;

import com.zyd.model.Book;
import com.zyd.secbooks.R;
import com.zyd.utils.FragmentBackHandler;
import com.zyd.view.FragmentBookDetailsView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 图书详情界面
 * @author 朱永地
 *
 */
public class FragmentBookDetails extends Fragment implements FragmentBookDetailsView, FragmentBackHandler {

	private View rootView;

	private TextView tv_bookName, tv_author, tv_bookDesc, tv_storage, tv_menu, tv_price;

	private Book book;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_bookdetails, container, false);
		initView();
		return rootView;
	}

	/**
	 * 初始化view
	 */
	private void initView() {
		tv_bookName = (TextView) rootView.findViewById(R.id.fragmentBookDetails_tv_bookName);
		tv_author = (TextView) rootView.findViewById(R.id.fragmentBookDetails_tv_author);
		tv_bookDesc = (TextView) rootView.findViewById(R.id.fragmentBookDetails_tv_bookDesc);
		tv_storage = (TextView) rootView.findViewById(R.id.fragmentBookDetails_tv_storage);
		tv_menu = (TextView) rootView.findViewById(R.id.fragmentBookDetails_tv_menu);
		tv_price = (TextView) rootView.findViewById(R.id.fragmentBookDetails_tv_price);

	}

	@Override
	public boolean onBackPressed() {
		return true;
	}

	@Override
	public void setBook(Book book) {
		this.book = book;
	}

	/**
	 * 给View设置数据
	 */
	@Override
	public void setBookText() {
		if (book != null) {
			tv_bookName.setText(String.valueOf(book.getBookName()));
			tv_author.setText(String.valueOf(book.getAuthor()));
			tv_bookDesc.setText(String.valueOf(book.getBookDesc()));
			tv_storage.setText(String.valueOf(book.getStorage()));
			tv_menu.setText(String.valueOf(book.getBookSmallType().getName()));
			tv_price.setText(String.valueOf(book.getPrice()));
		}
	}

}
