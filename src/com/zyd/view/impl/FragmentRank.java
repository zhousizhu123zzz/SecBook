package com.zyd.view.impl;

import java.util.ArrayList;
import java.util.List;

import com.squareup.picasso.Picasso;
import com.zyd.model.Book;
import com.zyd.presenter.BookPresenter;
import com.zyd.secbooks.R;
import com.zyd.utils.CacheUtil;
import com.zyd.utils.EqupUtil;
import com.zyd.utils.FragmentBackHandler;
import com.zyd.utils.FragmentController;
import com.zyd.utils.StringUtil;
import com.zyd.view.FragmentRankView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentRank extends Fragment implements FragmentRankView, FragmentBackHandler {

	private View rootView;

	/**
	 * Titlebar
	 */
	private LinearLayout ll_back;

	/**
	 * Refresh
	 */
	private SwipeRefreshLayout swipeRefreshLayout;

	/**
	 * Menu
	 */
	private TextView tv_hot, tv_new, tv_clear;

	/**
	 * Content
	 */
	private ListView lv_content;

	/**
	 * Data
	 */
	private String selectedTag = "热销";
	private BookPresenter bookPresenter = new BookPresenter(this);
	private List<Book> bookList = new ArrayList<Book>();
	private List<Book> menuBookList = new ArrayList<Book>();
	private boolean willToMain = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_rank, container, false);
		initView();
		bookPresenter.list();
		setSelectedTag();
		return rootView;
	}

	/**
	 * 初始化view
	 */
	private void initView() {
		ll_back = (LinearLayout) rootView.findViewById(R.id.fragmentRank_ll_back);
		tv_hot = (TextView) rootView.findViewById(R.id.fragmentRank_tv_hot);
		tv_new = (TextView) rootView.findViewById(R.id.fragmentRank_tv_new);
		tv_clear = (TextView) rootView.findViewById(R.id.fragmentRank_tv_clear);
		lv_content = (ListView) rootView.findViewById(R.id.fragmentRank_lv_content);
		swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fragmentRank_swipeRefreshLayout);

		swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				bookPresenter.list();
			}
		});

		ll_back.setOnClickListener(new ClickListener());
		tv_hot.setOnClickListener(new ClickListener());
		tv_new.setOnClickListener(new ClickListener());
		tv_clear.setOnClickListener(new ClickListener());
	}

	/**
	 * 点击事件监听内部类
	 * @author 朱永地
	 *
	 */
	private class ClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.fragmentRank_ll_back:
				toFragmentMain();
				break;

			case R.id.fragmentRank_tv_hot:
				selectedTag = "热销";
				setSelectedTag();
				setSeletedBookList();
				((BaseAdapter) lv_content.getAdapter()).notifyDataSetChanged();
				break;

			case R.id.fragmentRank_tv_new:
				selectedTag = "新书";
				setSelectedTag();
				setSeletedBookList();
				((BaseAdapter) lv_content.getAdapter()).notifyDataSetChanged();
				break;

			case R.id.fragmentRank_tv_clear:
				selectedTag = "清仓";
				setSelectedTag();
				setSeletedBookList();
				((BaseAdapter) lv_content.getAdapter()).notifyDataSetChanged();
				break;

			}
		}

	}

	/**
	 * 数据适配器内部类
	 * @author 朱永地
	 *
	 */
	private class LVAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return menuBookList.size();
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
			if (menuBookList != null && menuBookList.size() != 0) {
				Picasso.with(getActivity()).load(menuBookList.get(position).getBookPic()).into(holder.iv_pic);
				holder.tv_title.setText(String.valueOf(menuBookList.get(position).getBookName()));
				holder.tv_price.setText(String.valueOf(menuBookList.get(position).getPrice()));
				holder.tv_desc.setText(String.valueOf(menuBookList.get(position).getBookDesc()));
				holder.tv_author.setText(String.valueOf(menuBookList.get(position).getBookName()));
				holder.tv_menu.setText(String.valueOf(menuBookList.get(position).getBookSmallType().getName()));
			}
			return convertView;
		}

		class ViewHolder {
			ImageView iv_pic;
			TextView tv_title, tv_price, tv_desc, tv_author, tv_menu;
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
		CacheUtil.put(getActivity(), "fragmentBackTag", "FragmentRank");
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
	public void setRefreshing(final boolean isRefreshing) {
		swipeRefreshLayout.post(new Runnable() {

			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(isRefreshing);
			}
		});
	}

	@Override
	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
		this.menuBookList = StringUtil.limitBookList(bookList, "hot");
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		if (!hidden && willToMain) {
			bookPresenter.list();
		}
	}

	@Override
	public void setDataAdapter() {
		if (lv_content.getAdapter() == null) {
			lv_content.setAdapter(new LVAdapter());
			lv_content.setOnScrollListener(new OnScrollListener() {

				@Override
				public void onScrollStateChanged(AbsListView view, int scrollState) {
				}

				@Override
				public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
					if ((visibleItemCount > 0) && (firstVisibleItem == 0)) {
						if (view.getChildAt(0).getTop() >= 0) {
							swipeRefreshLayout.setEnabled(true);
						}
					} else {
						swipeRefreshLayout.setEnabled(false);
					}
				}
			});
			lv_content.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					CacheUtil.put(getActivity(), "bookId", bookList.get(position).getBookId());
					toFragmentBook();
				}
			});
		}

	}

	@Override
	public void showErrorMsg(String errorMsg) {
		EqupUtil.showMyToast(getActivity(), errorMsg, 0, Toast.LENGTH_SHORT);
	}

	private void setSelectedTag() {
		clearSelected();
		if ("热销".equals(selectedTag)) {
			tv_hot.setBackgroundColor(0xFF436EEE);
		}
		if ("新书".equals(selectedTag)) {
			tv_new.setBackgroundColor(0xFF436EEE);
		}
		if ("清仓".equals(selectedTag)) {
			tv_clear.setBackgroundColor(0xFF436EEE);
		}
	}

	private void clearSelected() {
		tv_clear.setBackgroundColor(0xFFFFFFFF);
		tv_hot.setBackgroundColor(0xFFFFFFFF);
		tv_new.setBackgroundColor(0xFFFFFFFF);
	}

	private void setSeletedBookList() {
		menuBookList.clear();
		if ("热销".equals(selectedTag)) {
			menuBookList.addAll(StringUtil.limitBookList(bookList, "hot"));
		}
		if ("新书".equals(selectedTag)) {
			menuBookList.addAll(StringUtil.limitBookList(bookList, "new"));
		}
		if ("清仓".equals(selectedTag)) {
			menuBookList.addAll(StringUtil.limitBookList(bookList, "special"));
		}
	}
}
