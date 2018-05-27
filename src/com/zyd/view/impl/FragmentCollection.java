package com.zyd.view.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.zyd.model.Book;
import com.zyd.model.MData;
import com.zyd.model.User;
import com.zyd.presenter.BookPresenter;
import com.zyd.secbooks.R;
import com.zyd.utils.CacheUtil;
import com.zyd.utils.EqupUtil;
import com.zyd.utils.FragmentBackHandler;
import com.zyd.utils.FragmentController;
import com.zyd.view.FragmentCollectionView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentCollection extends Fragment implements FragmentCollectionView, FragmentBackHandler {

	private View rootView;

	/**
	 * Titlebar
	 */
	private LinearLayout ll_back;

	/**
	 * PB
	 */
	private ProgressBar pb_loading;

	/**
	 * Body
	 */
	private ListView lv_content;

	private BookPresenter bookPresenter = new BookPresenter(this);
	private List<Book> bookList = new ArrayList<Book>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_collection, container, false);
		initView();
		bookPresenter.listBookByBookIds();
		return rootView;
	}

	private void initView() {
		ll_back = (LinearLayout) rootView.findViewById(R.id.fragmentCollection_ll_back);
		lv_content = (ListView) rootView.findViewById(R.id.fragmentCollection_lv_content);
		pb_loading = (ProgressBar) rootView.findViewById(R.id.fragmentCollection_pb_loading);

		ll_back.setOnClickListener(new ClickListener());
	}

	private class ClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.fragmentCollection_ll_back:
				toFragmentMain();
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
		toFragmentMain();
		return true;
	}

	@Override
	public void toFragmentMain() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentMain", new FragmentMain(),
				R.id.container_base, true, true);
	}

	@Override
	public List<Integer> getCollectionDataListFromCache() {
		User currentUser = (User) CacheUtil.getEntityData(getActivity(), "currentUser", new TypeToken<User>() {
		}.getType());
		MData mData = (MData) CacheUtil.getEntityData(getActivity(), "collection_bookList" + currentUser.getUserId(),
				new TypeToken<MData>() {
				}.getType());
		if (mData != null) {
			return mData.getIntegerList();
		} else {
			return null;
		}
	}

	@Override
	public void setLVAdapter() {
		lv_content.setAdapter(new LVAdapter());
		lv_content.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				CacheUtil.put(getActivity(), "bookId", bookList.get(position).getBookId());
				toFragmentBook();
			}
		});
	}

	@Override
	public void showErrorMsg(String errorMsg) {
		EqupUtil.showMyToast(getActivity(), errorMsg, 0, Toast.LENGTH_SHORT);
	}

	@Override
	public void setProgressBarVisible(int visibility) {
		pb_loading.setVisibility(visibility);
	}

	@Override
	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		if (!hidden) {
			lv_content.setAdapter(null);
			bookPresenter.listBookByBookIds();
		}
	}

	@Override
	public void toFragmentBook() {
		CacheUtil.put(getActivity(), "fragmentBackTag", "FragmentCollection");
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

}
