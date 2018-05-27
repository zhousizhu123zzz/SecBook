package com.zyd.view.impl;

import java.util.List;

import com.squareup.picasso.Picasso;
import com.zyd.constant.Constant;
import com.zyd.model.Book;
import com.zyd.presenter.BookPresenter;
import com.zyd.secbooks.R;
import com.zyd.utils.CacheUtil;
import com.zyd.utils.EqupUtil;
import com.zyd.utils.FragmentBackHandler;
import com.zyd.utils.FragmentController;
import com.zyd.utils.StringUtil;
import com.zyd.view.FragmentSearchListView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentSearchList extends Fragment implements FragmentSearchListView, FragmentBackHandler {

	private View rootView;

	/**
	 * TitleBar
	 */
	private LinearLayout ll_back;
	private RelativeLayout rl_search;
	private EditText et_search;
	private TextView tv_search;

	/**
	 * Body
	 */
	private RelativeLayout rl_loading;
	private RelativeLayout rl_content;
	private ListView lv_content;
	private TextView tv_nodata;
	private TextView tv_nonet;
	private LinearLayout ll_serverEx;

	private BookPresenter bookPresenter = new BookPresenter(this);
	private List<Book> bookList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_searchlist, container, false);
		initView();
		setSearchTextFromFragmentSearch();
		bookPresenter.searchBook();
		return rootView;
	}

	/**
	 * ≥ı ºªØview
	 */
	private void initView() {
		ll_back = (LinearLayout) rootView.findViewById(R.id.fragmentSearch_ll_back);
		rl_search = (RelativeLayout) rootView.findViewById(R.id.fragmentSearch_rl_search);
		et_search = (EditText) rootView.findViewById(R.id.fragmentSearch_et_search);
		tv_search = (TextView) rootView.findViewById(R.id.fragmentSearch_tv_search);
		rl_content = (RelativeLayout) rootView.findViewById(R.id.fragmentSearchList_rl_content);
		lv_content = (ListView) rootView.findViewById(R.id.fragmentSearchList_lv_content);
		tv_nodata = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.page_nodata_search, null);
		tv_nonet = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.page_nonet, null);
		ll_serverEx = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.page_serverex, null);
		rl_loading = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.page_loading, null);

		ll_back.setOnClickListener(new ClickListener());
		rl_search.setOnClickListener(new ClickListener());
		tv_search.setOnClickListener(new ClickListener());
	}

	private class ClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.fragmentSearch_ll_back:
				toFragmentSearch();
				break;
			case R.id.fragmentSearch_tv_search:
				if (StringUtil.isEmpty(getSearchText())) {
					showErrorMsg(Constant.msg.ERROR_NOSEARCHTEXT);
				} else {
					bookPresenter.searchBook();
				}
				break;
			case R.id.fragmentSearch_rl_search:
				et_search.performClick();
				break;
			}
		}

	}

	private class LVAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return bookList.size();
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
			if (bookList != null && bookList.size() != 0) {
				Picasso.with(getActivity()).load(bookList.get(position).getBookPic()).into(holder.iv_pic);
				holder.tv_title.setText(String.valueOf(bookList.get(position).getBookName()));
				holder.tv_price.setText(String.valueOf(bookList.get(position).getPrice()));
				holder.tv_desc.setText(String.valueOf(bookList.get(position).getBookDesc()));
				holder.tv_author.setText(String.valueOf(bookList.get(position).getBookName()));
				holder.tv_menu.setText(String.valueOf(bookList.get(position).getBookSmallType().getName()));
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
		toFragmentSearch();
		return true;
	}

	@Override
	public void toFragmentSearch() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentSearch", new FragmentSearch(),
				R.id.container_base, true, true);
	}

	@Override
	public void toFragmentBook() {
		CacheUtil.put(getActivity(), "fragmentBackTag", "FragmentSearchList");
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
	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

	@Override
	public void setContentVisible() {
		rl_content.removeAllViews();
		rl_content.addView(lv_content);
	}

	@Override
	public void setLoadingVisible() {
		rl_content.removeAllViews();
		rl_content.addView(rl_loading);
	}

	@Override
	public void setNodataVisible() {
		rl_content.removeAllViews();
		rl_content.addView(tv_nodata);
	}

	@Override
	public void setServerExVisible() {
		rl_content.removeAllViews();
		rl_content.addView(ll_serverEx);
	}

	@Override
	public void setNoNetVisible() {
		rl_content.removeAllViews();
		rl_content.addView(tv_nonet);
	}

	@Override
	public String getSearchText() {
		return et_search.getText().toString();
	}

	@Override
	public void setLVAdapter() {
		lv_content.setAdapter(new LVAdapter());
		((BaseAdapter) lv_content.getAdapter()).notifyDataSetChanged();
		lv_content.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				CacheUtil.put(getActivity(), "bookId", bookList.get(position).getBookId());
				toFragmentBook();
			}
		});
	}

	@Override
	public void setSearchTextFromFragmentSearch() {
		if (getSearchTextFromFragmentSearch() != null) {
			et_search.setText(getSearchTextFromFragmentSearch());
		}
	}

	@Override
	public String getSearchTextFromFragmentSearch() {
		return (String) CacheUtil.get(getActivity(), "fragmentSearch_tv_search", "");
	}

	@Override
	public void showErrorMsg(String errorMsg) {
		EqupUtil.showMyToast(getActivity(), errorMsg, 0, Toast.LENGTH_SHORT);
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		if (!hidden) {
			setSearchTextFromFragmentSearch();
			bookPresenter.searchBook();
		}
	}

}
