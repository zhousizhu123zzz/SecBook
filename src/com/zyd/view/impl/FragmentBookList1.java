package com.zyd.view.impl;

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
import com.zyd.view.FragmentBookList1View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

/**
 * ��������ֵ�ͼ���б�
 * @author ������
 *
 */
public class FragmentBookList1 extends Fragment implements FragmentBookList1View, FragmentBackHandler {

	private View rootView;

	/**
	 * Titlebar
	 */
	private LinearLayout ll_back;
	private TextView tv_title;

	/**
	 * SwipeRefreshLayout
	 */
	private SwipeRefreshLayout swipeRefreshLayout;

	/**
	 * Body
	 */
	private ListView lv_content;

	private BookPresenter bookPresenter = new BookPresenter(this);
	private List<Book> bookList;

	private boolean willToMain = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_booklist1, container, false);
		initView();
		setTitle();
		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		bookPresenter.list();
	}

	/**
	 * ��ʼ��view
	 */
	private void initView() {
		ll_back = (LinearLayout) rootView.findViewById(R.id.fragmentBookList1_ll_back);
		tv_title = (TextView) rootView.findViewById(R.id.fragmentBookList1_tv_title);
		swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fragmentBookList1_swipeRefreshLayout);
		lv_content = (ListView) rootView.findViewById(R.id.fragmentBookList_lv_content);

		ll_back.setOnClickListener(new ClickListener());
		swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);// ����ԲȦת��ʱ�����ɫ˳��
		swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				bookPresenter.list();
			}
		});
	}

	/**
	 * ����¼��ڲ���
	 * @author ������
	 *
	 */
	private class ClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.fragmentBookList1_ll_back:
				toFragmentMain();
				break;

			}
		}

	}

	/**
	 * �����������ڲ���
	 * @author ������
	 *
	 */
	private class BookLVAdapter extends BaseAdapter {

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
		willToMain = true;
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentMain", new FragmentMain(),
				R.id.container_base, true, true);
	}

	@Override
	public void toFragmentBook() {
		willToMain = false;
		CacheUtil.put(getActivity(), "fragmentBackTag", "FragmentBookList1");
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
		if ("�ذ�����".equals(tv_title.getText().toString())) {
			this.bookList = StringUtil.limitBookList2(bookList, "hot");
		} else if ("�����ϼ�".equals(tv_title.getText().toString())) {
			this.bookList = StringUtil.limitBookList2(bookList, "new");
		} else if ("���˦��".equals(tv_title.getText().toString())) {
			this.bookList = StringUtil.limitBookList2(bookList, "special");
		} else {
			this.bookList = bookList;
		}
	}

	@Override
	public void setRefresh(final boolean refreshable) {
		swipeRefreshLayout.post(new Runnable() {

			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(refreshable);
			}
		});
	}

	@Override
	public void setTitle() {
		String title = (String) CacheUtil.get(getActivity(), "bookList1_title", "");
		if (null != title) {
			tv_title.setText(title);
		} else {
			tv_title.setText("ͼ���б�");
		}
	}

	@Override
	public void clearBookText() {
		lv_content.setAdapter(null);
	}

	@Override
	public void setBookText() {
		lv_content.setAdapter(new BookLVAdapter());
		lv_content.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				CacheUtil.put(getActivity(), "bookId", bookList.get(position).getBookId());
				toFragmentBook();
			}
		});
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
	}

	@Override
	public void showErrorMsg(String errorMsg) {
		EqupUtil.showMyToast(getActivity(), errorMsg, 0, Toast.LENGTH_SHORT);
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		if (!hidden && willToMain) {
			clearBookText();
			setTitle();
			bookPresenter.list();
		}
	}

}
