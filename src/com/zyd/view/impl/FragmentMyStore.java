package com.zyd.view.impl;

import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.zyd.model.Book;
import com.zyd.model.User;
import com.zyd.secbooks.R;
import com.zyd.utils.CacheUtil;
import com.zyd.utils.FragmentBackHandler;
import com.zyd.utils.FragmentController;
import com.zyd.view.FragmentMyStoreView;

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
import android.widget.TextView;

public class FragmentMyStore extends Fragment implements FragmentMyStoreView, FragmentBackHandler {

	private View rootView;

	/**
	 * TitleBar
	 */
	private LinearLayout ll_back;

	/**
	 * Body
	 */
	private ListView lv_content;

	/**
	 * Foot
	 */
	private LinearLayout ll_add;

	private List<Book> bookList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_mystore, container, false);
		initView();
		setBookList();
		setLVAdapter();
		return rootView;
	}

	/**
	 * ≥ı ºªØview
	 */
	private void initView() {
		ll_back = (LinearLayout) rootView.findViewById(R.id.fragmentMyStore_ll_back);
		ll_add = (LinearLayout) rootView.findViewById(R.id.fragmentMyStore_ll_add);
		lv_content = (ListView) rootView.findViewById(R.id.fragmentMyStore_lv_content);

		ll_back.setOnClickListener(new ClickListener());
		ll_add.setOnClickListener(new ClickListener());
	}

	private class ClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.fragmentMyStore_ll_back:
				toFragmentMain();
				break;
			case R.id.fragmentMyStore_ll_add:
				toFragmentMyStoreAdd();
				break;
			}
		}

	}

	private class LVAdapter extends BaseAdapter {

		private final int HASDATA = 1;
		private final int NODATA = 2;

		@Override
		public int getCount() {
			if (bookList == null || bookList.size() == 0) {
				return 1;
			} else {
				return bookList.size();
			}
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public int getItemViewType(int position) {
			if (bookList == null || bookList.size() == 0) {
				return NODATA;
			} else {
				return HASDATA;
			}
		}

		@Override
		public int getViewTypeCount() {
			return 1;
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
					convertView = LayoutInflater.from(getActivity()).inflate(R.layout.page_nodata_mystore, null);
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
	public void toFragmentMyStoreAdd() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentMyStoreAdd",
				new FragmentMyStoreAdd(), R.id.container_base, false, true);
	}

	@Override
	public void setBookList() {
		User user = (User) CacheUtil.getEntityData(getActivity(), "currentUser", new TypeToken<User>() {
		}.getType());
		if (user != null) {
			bookList = user.getBookList();
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
	public void onHiddenChanged(boolean hidden) {
		if (!hidden) {
			setBookList();
			setLVAdapter();
		}
	}

	@Override
	public void toFragmentBook() {
		CacheUtil.put(getActivity(), "fragmentBackTag", "FragmentMyStore");
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
