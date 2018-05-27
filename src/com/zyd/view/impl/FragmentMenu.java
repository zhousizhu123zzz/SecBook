package com.zyd.view.impl;

import java.util.ArrayList;
import java.util.List;

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
import com.zyd.view.FragmentMenuView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 分类
 * @author 朱永地
 *
 */
public class FragmentMenu extends Fragment implements FragmentMenuView, FragmentBackHandler {

	private View rootView;

	/**
	 * Body
	 */
	private ListView lv_bigtype;
	private GridView gv_smalltype;

	private BigTypePresenter bigTypePresenter = new BigTypePresenter(this);
	private BookPresenter bookPresenter = new BookPresenter(this);

	private List<BookBigType> bookBigTypeList = new ArrayList<BookBigType>();
	private List<BookSmallType> bookSmallTypeList = new ArrayList<BookSmallType>();
	private List<Book> bookList = new ArrayList<Book>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_menu, container, false);
		bigTypePresenter.list();
		bookPresenter.list();
		initView();
		return rootView;
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		lv_bigtype = (ListView) rootView.findViewById(R.id.fragmentMenu_lv_bigType);
		gv_smalltype = (GridView) rootView.findViewById(R.id.fragmentMenu_gv_smallType);

		final BigTypeLVAdapter bigTypeAdapter = new BigTypeLVAdapter();
		final SmallTypeGVAdapter smallTypeAdapter = new SmallTypeGVAdapter();
		CacheUtil.put(getActivity(), "fragmentMenu_selectedBookBigTypePosition", 0);
		lv_bigtype.setAdapter(bigTypeAdapter);
		gv_smalltype.setAdapter(smallTypeAdapter);

		lv_bigtype.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				CacheUtil.put(getActivity(), "fragmentMenu_selectedBookBigTypePosition", position);
				bigTypeAdapter.setSelectedItem(position);
				bookSmallTypeList = bookBigTypeList.get(position).getSmallTypeList();
				bigTypeAdapter.notifyDataSetChanged();
				smallTypeAdapter.notifyDataSetChanged();
			}
		});
		gv_smalltype.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				CacheUtil.put(getActivity(), "fragmentMenu_selectedBookSmallTypePosition", position);
				toFragmentBookList();
			}
		});

	}

	/**
	 * 图书大类适配器内部类
	 * @author 朱永地
	 *
	 */
	private class BigTypeLVAdapter extends BaseAdapter {

		int selectedItem = 0; //默认选中第一条

		public void setSelectedItem(int selectedItem) {
			this.selectedItem = selectedItem;
		}

		@Override
		public int getCount() {
			return bookBigTypeList.size();
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
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_menu_bigtype, null);
				holder.tv_title = (TextView) convertView.findViewById(R.id.fragmentMenu_bigType_tv_title);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tv_title.setText(bookBigTypeList.get(position).getName()); //设置大类名称
			if (selectedItem == position) {
				((RelativeLayout) holder.tv_title.getParent()).setBackgroundColor(0xFFFFFFFF);
			} else {
				((RelativeLayout) holder.tv_title.getParent()).setBackgroundColor(0xFFDEDEDE);
			}
			return convertView;
		}

		class ViewHolder {
			TextView tv_title;
		}

	}

	/**
	 * 图书小类适配器内部类
	 * @author 朱永地
	 *
	 */
	private class SmallTypeGVAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return bookSmallTypeList.size();
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
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_menu_smalltype, null);
				holder.tv_title = (TextView) convertView.findViewById(R.id.fragmentMenu_smalltype_tv_title);
				holder.iv_bookPic = (ImageView) convertView.findViewById(R.id.fragmentMenu_smalltype_iv_pic);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tv_title.setText(bookSmallTypeList.get(position).getName());
			if (bookList != null) {
				for (int i = 0; i < bookList.size(); i++) {
					if (bookList.get(i).getBookSmallType().getSmallTypeId() == bookSmallTypeList.get(position)
							.getSmallTypeId()) {
						Picasso.with(getActivity()).load(bookList.get(i).getBookPic()).fit().into(holder.iv_bookPic);
						break;
					}
				}
			}
			return convertView;
		}

		class ViewHolder {
			TextView tv_title;
			ImageView iv_bookPic;
		}

	}

	@Override
	public void toFragmentHome() {
		FragmentController.show(getFragmentManager(), "FragmentHome", new FragmentHome(), R.id.container_home, true,
				true);
		((FragmentMain) getParentFragment()).setFragmentIconAlpha("FragmentHome");
	}

	@Override
	public boolean onBackPressed() {
		toFragmentHome();
		return true;
	}

	@Override
	public void showErrorMsg(String errorMsg) {
		EqupUtil.showMyToast(getActivity(), errorMsg, 0, Toast.LENGTH_SHORT);
	}

	@Override
	public void setBookBigTypeList(List<BookBigType> bookBigTypeList) {
		this.bookBigTypeList = bookBigTypeList;
		this.bookSmallTypeList = bookBigTypeList.get(0).getSmallTypeList();
	}

	@Override
	public void refresh() {
		((BaseAdapter) lv_bigtype.getAdapter()).notifyDataSetChanged();
		((BaseAdapter) gv_smalltype.getAdapter()).notifyDataSetChanged();
	}

	@Override
	public void toFragmentBookList() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentBookList", new FragmentBookList(),
				R.id.container_base, false, true);
	}

	@Override
	public void saveBookBigTypeList(List<BookBigType> bookBigTypeList) {
		CacheUtil.setDataList(getActivity(), "bookBigTypeList", bookBigTypeList);
	}

	@Override
	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

}
