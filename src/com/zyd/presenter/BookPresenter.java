package com.zyd.presenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zyd.biz.MainBiz;
import com.zyd.biz.MainListener;
import com.zyd.biz.impl.MainBizImpl;
import com.zyd.constant.Constant;
import com.zyd.utils.JSONUtils;
import com.zyd.utils.StringUtil;
import com.zyd.view.FragmentBookBookView;
import com.zyd.view.FragmentBookList1View;
import com.zyd.view.FragmentBookListView;
import com.zyd.view.FragmentBookView;
import com.zyd.view.FragmentCollectionView;
import com.zyd.view.FragmentHomeView;
import com.zyd.view.FragmentMenuView;
import com.zyd.view.FragmentMyStoreAddView;
import com.zyd.view.FragmentRankView;
import com.zyd.view.FragmentSearchListView;
import com.zyd.view.FragmentStartView;

import android.util.Log;
import android.view.View;

public class BookPresenter {

	private MainBiz mainBiz;
	private FragmentHomeView homeView;
	private FragmentStartView startView;
	private FragmentBookView bookView;
	private FragmentMenuView menuView;
	private FragmentBookBookView bookBookView;
	private FragmentBookList1View bookList1View;
	private FragmentSearchListView searchListView;
	private FragmentRankView rankView;
	private FragmentBookListView bookListView;
	private FragmentMyStoreAddView myStoreAddView;
	private FragmentCollectionView collectionView;

	public <T> BookPresenter(T t) {
		mainBiz = new MainBizImpl();
		if (t instanceof FragmentHomeView) {
			this.homeView = (FragmentHomeView) t;
		}
		if (t instanceof FragmentMenuView) {
			this.menuView = (FragmentMenuView) t;
		}
		if (t instanceof FragmentStartView) {
			this.startView = (FragmentStartView) t;
		}
		if (t instanceof FragmentBookView) {
			this.bookView = (FragmentBookView) t;
		}
		if (t instanceof FragmentBookBookView) {
			this.bookBookView = (FragmentBookBookView) t;
		}
		if (t instanceof FragmentBookList1View) {
			this.bookList1View = (FragmentBookList1View) t;
		}
		if (t instanceof FragmentSearchListView) {
			this.searchListView = (FragmentSearchListView) t;
		}
		if (t instanceof FragmentRankView) {
			this.rankView = (FragmentRankView) t;
		}
		if (t instanceof FragmentBookListView) {
			this.bookListView = (FragmentBookListView) t;
		}
		if (t instanceof FragmentMyStoreAddView) {
			this.myStoreAddView = (FragmentMyStoreAddView) t;
		}
		if (t instanceof FragmentCollectionView) {
			this.collectionView = (FragmentCollectionView) t;
		}
	}

	/**
	 * 遍历所有图书
	 */
	public void list() {
		if (bookList1View != null) {
			bookList1View.setRefresh(true);
		}
		if (rankView != null) {
			rankView.setRefreshing(true);
		}
		mainBiz.connect(Constant.net.NET_BOOK_LIST, null, new MainListener() {

			@Override
			public <T> void success(String jsonData) {
				if (homeView != null) {
					if (JSONUtils.parseBookList(jsonData) != null) {
						homeView.setBookList(JSONUtils.parseBookList(jsonData));
					}
				}
				if (startView != null) {
					if (JSONUtils.parseBookList(jsonData) != null) {
						startView.setTransmissionData(JSONUtils.parseBookList(jsonData));
						startView.validataUser();
					}
				}
				if (bookList1View != null) {
					bookList1View.setRefresh(false);
					if (JSONUtils.parseBookList(jsonData) != null) {
						bookList1View.setBookList(JSONUtils.parseBookList(jsonData));
						bookList1View.setBookText();
					}
				}
				if (rankView != null) {
					rankView.setRefreshing(false);
					if (JSONUtils.parseBookList(jsonData) != null) {
						rankView.setBookList(JSONUtils.parseBookList(jsonData));
						rankView.setDataAdapter();
					}
				}
				if (menuView != null) {
					if (JSONUtils.parseBookList(jsonData) != null) {
						menuView.setBookList(JSONUtils.parseBookList(jsonData));
						menuView.refresh();
					}
				}
			}

			@Override
			public void serverEx() {
				if (homeView != null) {
					homeView.showErrorMsg(Constant.msg.ERROR_SERVER);
				}
				if (startView != null) {
					startView.showErrorMsg(Constant.msg.ERROR_SERVER);
					System.exit(0);
				}
				if (bookList1View != null) {
					bookList1View.showErrorMsg(Constant.msg.ERROR_SERVER);
				}
				if (rankView != null) {
					rankView.showErrorMsg(Constant.msg.ERROR_SERVER);
				}
			}
		});
	}

	public void searchBook() {
		Map<String, String> params = new HashMap<String, String>();
		if (searchListView != null) {
			searchListView.setLoadingVisible();
			params.put("bookName", searchListView.getSearchText());
			params.put("author", searchListView.getSearchText());
		}
		mainBiz.connect(Constant.net.NET_BOOK_SEARCHBOOK, params, new MainListener() {

			@Override
			public <T> void success(String jsonData) {
				if (JSONUtils.parseBookList(jsonData) != null) {
					if (searchListView != null) {
						if (JSONUtils.parseBookList(jsonData).size() == 0) {
							searchListView.setNodataVisible();
						} else {
							searchListView.setContentVisible();
							searchListView.setBookList(JSONUtils.parseBookList(jsonData));
							searchListView.setLVAdapter();
						}
					} else {
						searchListView.setNodataVisible();
					}
				}
			}

			@Override
			public void serverEx() {
				if (searchListView != null) {
					searchListView.setServerExVisible();
				}
			}
		});
	}

	public void getBook() {
		Map<String, String> params = new HashMap<String, String>();
		if (bookBookView != null) {
			bookBookView.setProgressBarVisible(View.VISIBLE);
			params.put("bookId", String.valueOf(bookBookView.getBookId()));
		}
		if (bookView != null) {
			params.put("bookId", String.valueOf(bookView.getBookId()));
		}
		mainBiz.connect(Constant.net.NET_BOOK_GETBOOK, params, new MainListener() {

			@Override
			public <T> void success(String jsonData) {
				if (JSONUtils.parseBook(jsonData) != null) {
					if (bookBookView != null) {
						bookBookView.setProgressBarVisible(View.GONE);
						bookBookView.setBook(JSONUtils.parseBook(jsonData));
						bookBookView.setBookText();
					}
					if (bookView != null) {
						bookView.setBook(JSONUtils.parseBook(jsonData));
						bookView.setOrder();
					}
				}
			}

			@Override
			public void serverEx() {
				bookBookView.setProgressBarVisible(View.GONE);
			}
		});
	}

	public void listBookByBigTypeId() {
		Map<String, String> param = new HashMap<String, String>();
		if (bookListView != null) {
			bookListView.setRefreshing(true);
			param.put("bookBigTypeId", String.valueOf(bookListView.getBookBigTypeId()));
		}
		mainBiz.connect(Constant.net.NET_BOOK_LISTBOOKBYBIGTYPEID, param, new MainListener() {

			@Override
			public <T> void success(String jsonData) {
				if (JSONUtils.parseBookList(jsonData) != null) {
					if (bookListView != null) {
						bookListView.setRefreshing(false);
						bookListView.setBookList(JSONUtils.parseBookList(jsonData));
						bookListView.setDataAdapter();
					}
				}
			}

			@Override
			public void serverEx() {
				if (bookListView != null) {
					bookListView.setRefreshing(false);
				}
			}
		});
	}

	public void listBookBySmallTypeId() {
		Map<String, String> param = new HashMap<String, String>();
		if (bookListView != null) {
			bookListView.setRefreshing(true);
			param.put("bookSmallTypeId", String.valueOf(bookListView.getBookSmallTypeId()));
		}
		mainBiz.connect(Constant.net.NET_BOOK_LISTBOOKBYSMALLTYPEID, param, new MainListener() {

			@Override
			public <T> void success(String jsonData) {
				if (JSONUtils.parseBookList(jsonData) != null) {
					if (bookListView != null) {
						bookListView.setRefreshing(false);
						bookListView.setBookList(JSONUtils.parseBookList(jsonData));
						bookListView.setDataAdapter();
					}
				}
			}

			@Override
			public void serverEx() {
				if (bookListView != null) {
					bookListView.setRefreshing(false);
				}
			}
		});
	}

	public void saveBook() {
		Map<String, String> params = new HashMap<String, String>();
		if (myStoreAddView != null) {
			myStoreAddView.setProgressBarVisible(View.VISIBLE);
			params = StringUtil.getBookMap(myStoreAddView.getAddedBook());
		}
		mainBiz.connect(Constant.net.NET_BOOK_SAVEBOOK, params, new MainListener() {

			@Override
			public <T> void success(String jsonData) {
				if (JSONUtils.parseIsSuccess(jsonData)) {
					if (myStoreAddView != null) {
						myStoreAddView.showErrorMsg("上架成功!");
						myStoreAddView.toFragmentMyStore();
					}
				} else {
					if (myStoreAddView != null) {
						myStoreAddView.showErrorMsg("上架失败，请重试!");
					}
				}
			}

			@Override
			public void serverEx() {
				if (myStoreAddView != null) {
					myStoreAddView.setProgressBarVisible(View.GONE);
					myStoreAddView.showErrorMsg(Constant.msg.ERROR_SERVER);
				}
			}
		});
	}

	public void listBookByBookIds() {
		Map<String, String> params = new HashMap<String, String>();
		if (collectionView != null) {
			collectionView.setProgressBarVisible(View.VISIBLE);
			List<Integer> bookIdsList = collectionView.getCollectionDataListFromCache();
			if (bookIdsList != null) {
				params.put("bookIdListSize", String.valueOf(bookIdsList.size()));
				for (int i = 0; i < bookIdsList.size(); i++) {
					params.put("bookId" + "-" + i, String.valueOf(bookIdsList.get(i)));
				}
				for (String values : params.values()) {
					Log.i("sssssssssssssss", values);
				}
			}
		}
		mainBiz.connect(Constant.net.NET_BOOK_LISTBOOKBYBOOKIDS, params, new MainListener() {

			@Override
			public <T> void success(String jsonData) {
				if (JSONUtils.parseBookList(jsonData) != null) {
					if (collectionView != null) {
						collectionView.setProgressBarVisible(View.GONE);
						collectionView.setBookList(JSONUtils.parseBookList(jsonData));
						collectionView.setLVAdapter();
					}
				}
			}

			@Override
			public void serverEx() {
				if (collectionView != null) {
					collectionView.setProgressBarVisible(View.GONE);
					collectionView.showErrorMsg(Constant.msg.ERROR_SERVER);
				}
			}
		});
	}

}
