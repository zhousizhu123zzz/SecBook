package com.zyd.view.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.zyd.model.Book;
import com.zyd.model.BookBigType;
import com.zyd.model.BookSmallType;
import com.zyd.presenter.BigTypePresenter;
import com.zyd.presenter.BookPresenter;
import com.zyd.secbooks.R;
import com.zyd.utils.CacheUtil;
import com.zyd.utils.EqupUtil;
import com.zyd.utils.FragmentBackHandler;
import com.zyd.utils.FragmentController;
import com.zyd.utils.StringUtil;
import com.zyd.view.FragmentBookListView;
import com.zyd.widget.AutoLineTextView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 全部分类页面
 * @author 90450
 *
 */
public class FragmentBookList extends Fragment implements FragmentBookListView, FragmentBackHandler {

	private View rootView;

	/**
	 * Titlebar
	 */
	private LinearLayout ll_back;

	/**
	 * BookBigType,BookSmallType
	 */
	private AutoLineTextView altv_bookBigType, altv_bookSmallType;

	/**
	 * Refresh
	 */
	private SwipeRefreshLayout swipeRefreshLayout;

	/**
	 * Content 
	 */
	private ListView lv_content;

	/**
	 * Data
	 */
	private List<BookBigType> bookBigTypeList;
	private List<BookSmallType> bookSmallTypeList;
	private List<Book> bookList = new ArrayList<Book>();
	private BigTypePresenter bigTypePresenter = new BigTypePresenter(this);
	private BookPresenter bookPresenter = new BookPresenter(this);

	private int selectedBookBigTypePosition = 0;
	private int selectedBookSmallTypePosition = 0;
	private boolean willToMain = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_booklist, container, false);
		selectedBookBigTypePosition = (Integer) CacheUtil.get(getActivity(), "fragmentMenu_selectedBookBigTypePosition",
				0);
		selectedBookSmallTypePosition = (Integer) CacheUtil.get(getActivity(),
				"fragmentMenu_selectedBookSmallTypePosition", 0);
		setBookBigTypeList();
		setBookSmallTypeList(selectedBookBigTypePosition);
		initView();
		bookPresenter.listBookBySmallTypeId();
		return rootView;
	}

	/**
	 * 初始化view
	 */
	private void initView() {
		ll_back = (LinearLayout) rootView.findViewById(R.id.fragmentBookList_ll_back);
		altv_bookBigType = (AutoLineTextView) rootView.findViewById(R.id.fragmentBookList_bigtypeList);
		altv_bookSmallType = (AutoLineTextView) rootView.findViewById(R.id.fragmentBookList_smallTypeList);
		swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fragmentBookList_swipeRefreshLayout);
		lv_content = (ListView) rootView.findViewById(R.id.fragmentBookList_lv_content);

		genBookBigTypeATTV();
		genBookSmallTypeATTV();

		swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				bigTypePresenter.list();
			}
		});

		ll_back.setOnClickListener(new ClickListener());
	}

	private class ClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.fragmentBookList_ll_back:
				toFragmentMain();
				break;

			}
		}

	}

	/**
	 * 数据适配器内部类
	 * @author 朱永地
	 *
	 */
	private class BookLVAdapter extends BaseAdapter {

		private final int NODATA = -1;
		private final int HASDATA = 1;

		@Override
		public int getCount() {
			if (bookList.size() == 0) {
				return 1;
			} else {
				return bookList.size();
			}
		}

		@Override
		public int getItemViewType(int position) {
			if (bookList.size() == 0) {
				return NODATA;
			} else {
				return HASDATA;
			}
		}

		@Override
		public int getViewTypeCount() {
			return 2;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (getItemViewType(position) == NODATA) {
				if (convertView == null) {
					holder = new ViewHolder();
					convertView = LayoutInflater.from(getActivity()).inflate(R.layout.page_nodata_book, null);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
			} else if (getItemViewType(position) == HASDATA) {
				if (convertView == null) {
					holder = new ViewHolder();
					convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_clear_item, null);
					holder.iv_pic = (ImageView) convertView.findViewById(R.id.itemClear_iv_pic);
					holder.tv_title = (TextView) convertView.findViewById(R.id.itemClear_tv_title);
					holder.tv_price = (TextView) convertView.findViewById(R.id.itemClear_tv_price);
					holder.tv_desc = (TextView) convertView.findViewById(R.id.itemClear_tv_desc);
					holder.tv_author = (TextView) convertView.findViewById(R.id.itemClear_tv_author);
					holder.tv_menu = (TextView) convertView.findViewById(R.id.itemClear_tv_menu);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				if (bookList != null && bookList.size() != 0) {
					Picasso.with(getActivity()).load(bookList.get(position).getBookPic()).into(holder.iv_pic);
					holder.tv_title.setText(String.valueOf(bookList.get(position).getBookName()));
					holder.tv_price.setText(String.valueOf(bookList.get(position).getPrice()));
					holder.tv_desc.setText(String.valueOf(bookList.get(position).getBookDesc()));
					holder.tv_author.setText(String.valueOf(bookList.get(position).getBookName()));
					holder.tv_menu.setText(String.valueOf(bookList.get(position).getBookSmallType().getName()));
				}
			}
			return convertView;
		}

		class ViewHolder {
			ImageView iv_pic;
			TextView tv_title, tv_price, tv_desc, tv_author, tv_menu;
		}

	}

	/**
	 * 生成图书大类
	 */
	private void genBookBigTypeATTV() {
		if (bookBigTypeList != null && bookBigTypeList.size() != 0) {
			altv_bookBigType.post(new Runnable() {

				@Override
				public void run() {
					int measureWidth = altv_bookBigType.getMeasuredWidth();
					altv_bookBigType.setDataList(StringUtil.parseBookBigTypeListToStringList(bookBigTypeList));
					altv_bookBigType.genView(measureWidth); //生成AutoLineTextView
					final List<TextView> tvList = altv_bookBigType.getAllTextView();
					((TextView) tvList.get(selectedBookBigTypePosition))
							.setBackgroundResource(R.drawable.style_fragment_shoppingcart_tv_tag_selected);
					for (int i = 0; i < tvList.size(); i++) {
						final TextView tv = tvList.get(i);
						final int j = i;
						tv.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								if (selectedBookBigTypePosition != j) {
									clearAllBackgroundColorForTextView(tvList);
									tv.setBackgroundResource(R.drawable.style_fragment_shoppingcart_tv_tag_selected);
									selectedBookBigTypePosition = j;
									selectedBookSmallTypePosition = -1;
									setBookSmallTypeList(selectedBookBigTypePosition);
									altv_bookSmallType.removeAllViews();
									genBookSmallTypeATTV();
									bookPresenter.listBookByBigTypeId();
								}
							}
						});
					}
				}
			});
		}

	}

	/**
	 * 生成图书小类
	 */
	private void genBookSmallTypeATTV() {
		if (bookSmallTypeList != null && bookSmallTypeList.size() != 0) {
			altv_bookSmallType.post(new Runnable() {

				@Override
				public void run() {
					int measureWidth = altv_bookSmallType.getMeasuredWidth();
					altv_bookSmallType.setDataList(StringUtil.parseBookSmallTypeListToStringList(bookSmallTypeList));
					altv_bookSmallType.genView(measureWidth); //生成AutoLineTextView
					final List<TextView> tvList = altv_bookSmallType.getAllTextView();
					((TextView) tvList.get(selectedBookSmallTypePosition + 1))
							.setBackgroundResource(R.drawable.style_fragment_shoppingcart_tv_tag_selected);
					for (int i = 0; i < tvList.size(); i++) {
						final int j = i;
						final TextView tv = tvList.get(i);
						tv.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								if (selectedBookSmallTypePosition + 1 != j) {
									if (j == 0) {
										clearAllBackgroundColorForTextView(tvList);
										tv.setBackgroundResource(
												R.drawable.style_fragment_shoppingcart_tv_tag_selected);
										bookPresenter.listBookByBigTypeId();
									} else {
										selectedBookSmallTypePosition = j - 1;
										clearAllBackgroundColorForTextView(tvList);
										tv.setBackgroundResource(
												R.drawable.style_fragment_shoppingcart_tv_tag_selected);
										bookPresenter.listBookBySmallTypeId();
									}
								}
							}
						});
					}
				}
			});
		}
	}

	@Override
	public boolean onBackPressed() {
		toFragmentMain();
		return true;
	}

	@Override
	public void toFragmentMain() {
		willToMain = true;
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentMain", new FragmentMain(),
				R.id.container_base, true, true);
	}

	@Override
	public void toFragmentBook() {
		willToMain = false;
		CacheUtil.put(getActivity(), "fragmentBackTag", "FragmentBookList");
		FragmentBook fragmentBook = (FragmentBook) FragmentController
				.getFragmentByTag(getActivity().getSupportFragmentManager(), "FragmentBook");
		if (fragmentBook != null) {
			FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentBook", new FragmentBook(),
					R.id.container_base, false, true);
			fragmentBook.reloadFragment();
		} else {
			FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentBook", new FragmentBook(),
					R.id.container_base, false, true);
		}
	}

	@Override
	public void setBookBigTypeList() {
		List<BookBigType> bigTypeList = CacheUtil.getDataList(getActivity(), "bookBigTypeList",
				new TypeToken<List<BookBigType>>() {
				}.getType());
		if (bigTypeList != null) {
			bookBigTypeList = bigTypeList;
		}
	}

	@Override
	public void setBookSmallTypeList(int position) {
		if (bookBigTypeList != null) {
			bookSmallTypeList = bookBigTypeList.get(position).getSmallTypeList();
		}
	}

	@Override
	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

	@Override
	public void setRefreshing(final boolean isRefreshing) {
		swipeRefreshLayout.post(new Runnable() {

			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(isRefreshing);
			}
		});
	}

	@Override
	public void saveBookBigTypeList(List<BookBigType> bookBigTypeList) {
		CacheUtil.setDataList(getActivity(), "bookBigTypeList", bookBigTypeList);
	}

	@Override
	public void refreshBookList() {
		setBookSmallTypeList(selectedBookBigTypePosition);
		altv_bookBigType.removeAllViews();
		altv_bookSmallType.removeAllViews();
		genBookBigTypeATTV();
		genBookSmallTypeATTV();
		((BaseAdapter) lv_content.getAdapter()).notifyDataSetChanged();
	}

	@Override
	public int getBookSmallTypeId() {
		if (bookSmallTypeList != null && bookSmallTypeList.size() != 0) {
			return bookSmallTypeList.get(selectedBookSmallTypePosition).getSmallTypeId();
		} else {
			return 1;
		}
	}

	@Override
	public int getBookBigTypeId() {
		if (bookBigTypeList != null && bookBigTypeList.size() != 0) {
			return bookBigTypeList.get(selectedBookBigTypePosition).getBookBigTypeId();
		} else {
			return 1;
		}
	}

	@Override
	public void setDataAdapter() {
		lv_content.setAdapter(new BookLVAdapter());
		lv_content.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				CacheUtil.put(getActivity(), "bookId", bookList.get(position).getBookId());
				toFragmentBook();
			}
		});
	}

	private void clearAllBackgroundColorForTextView(List<TextView> tvList) {
		Iterator<TextView> it = tvList.iterator();
		while (it.hasNext()) {
			((TextView) it.next()).setBackgroundResource(R.drawable.style_fragment_shoppingcart_tv_tag);
		}
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		if (!hidden && willToMain) {
			selectedBookBigTypePosition = (Integer) CacheUtil.get(getActivity(),
					"fragmentMenu_selectedBookBigTypePosition", 0);
			selectedBookSmallTypePosition = (Integer) CacheUtil.get(getActivity(),
					"fragmentMenu_selectedBookSmallTypePosition", 0);
			EqupUtil.showMyToast(getActivity(), "" + selectedBookSmallTypePosition, 0, Toast.LENGTH_SHORT);
			setBookSmallTypeList(selectedBookBigTypePosition);
			altv_bookBigType.removeAllViews();
			altv_bookSmallType.removeAllViews();
			genBookBigTypeATTV();
			genBookSmallTypeATTV();
			bookPresenter.listBookBySmallTypeId();
		}
	}

}
