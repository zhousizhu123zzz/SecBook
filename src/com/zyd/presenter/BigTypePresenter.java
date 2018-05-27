package com.zyd.presenter;

import java.util.List;

import com.zyd.biz.MainBiz;
import com.zyd.biz.MainListener;
import com.zyd.biz.impl.MainBizImpl;
import com.zyd.constant.Constant;
import com.zyd.model.BookBigType;
import com.zyd.utils.JSONUtils;
import com.zyd.view.FragmentBookListView;
import com.zyd.view.FragmentHomeView;
import com.zyd.view.FragmentMenuView;

public class BigTypePresenter {

	private MainBiz mainBiz;
	private FragmentMenuView menuView;
	private FragmentHomeView homeView;
	private FragmentBookListView bookListView;

	public <T> BigTypePresenter(T t) {
		mainBiz = new MainBizImpl();
		if (t instanceof FragmentMenuView) {
			this.menuView = (FragmentMenuView) t;
		}
		if (t instanceof FragmentHomeView) {
			this.homeView = (FragmentHomeView) t;
		}
		if (t instanceof FragmentBookListView) {
			this.bookListView = (FragmentBookListView) t;
		}
	}

	/**
	 * 遍历所有图书大类
	 */
	public void list() {
		if (homeView != null) {
			homeView.toFragmentLoading();
		}
		mainBiz.connect(Constant.net.NET_BIGTYPE_LIST, null, new MainListener() {

			@Override
			public <T> void success(String jsonData) {
				List<BookBigType> resultList = JSONUtils.parseBookBigTypeList(jsonData);
				if (resultList != null) {
					if (menuView != null) {
						menuView.setBookBigTypeList(resultList);
						menuView.refresh();
						menuView.saveBookBigTypeList(resultList);
					}
					if (homeView != null) {
						homeView.setTransmissionData(resultList);
						homeView.toFragmentBookList();
					}
					if (bookListView != null) {
						bookListView.setRefreshing(false);
						bookListView.saveBookBigTypeList(resultList);
						bookListView.setBookBigTypeList();
						bookListView.refreshBookList();
					}
				} else {
				}
			}

			@Override
			public void serverEx() {
				if (menuView != null) {
					menuView.showErrorMsg(Constant.msg.ERROR_SERVER);
				}
				if (homeView != null) {
					homeView.toFragmentServerEx();
				}
			}
		});
	}

}
