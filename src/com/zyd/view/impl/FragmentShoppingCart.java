package com.zyd.view.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.zyd.model.Book;
import com.zyd.model.Order;
import com.zyd.model.User;
import com.zyd.presenter.OrderPresenter;
import com.zyd.secbooks.R;
import com.zyd.utils.CacheUtil;
import com.zyd.utils.EqupUtil;
import com.zyd.utils.FragmentBackHandler;
import com.zyd.utils.FragmentController;
import com.zyd.utils.StringUtil;
import com.zyd.view.FragmentShoppingCartView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 购物车Fragment
 * @author 朱永地
 *
 */
public class FragmentShoppingCart extends Fragment implements FragmentShoppingCartView, FragmentBackHandler {

	private View rootView;

	private ListView lv_content;

	private View v_divider;
	private RelativeLayout rl_buy;
	private TextView tv_allCost, tv_buy;

	private OrderPresenter orderPresenter = new OrderPresenter(this);
	private List<Order> orderList = new ArrayList<Order>();
	private List<Book> recomBookList = new ArrayList<Book>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		recomBookList = CacheUtil.getDataList(getActivity(), "RecomBookList", new TypeToken<List<Book>>() {
		}.getType());
		rootView = inflater.inflate(R.layout.fragment_shoppingcart, container, false);
		initView();
		orderPresenter.list();
		return rootView;
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		lv_content = (ListView) rootView.findViewById(R.id.fragmentShoppingCart_lv_content);
		v_divider = rootView.findViewById(R.id.fragmentShoppingCart_v_divider);
		rl_buy = (RelativeLayout) rootView.findViewById(R.id.fragmentShoppingCart_rl_buy);
		tv_allCost = (TextView) rootView.findViewById(R.id.fragmentShoppingCart_tv_allCoas);
		tv_buy = (TextView) rootView.findViewById(R.id.fragmentShoppingCart_tv_buy);

		tv_buy.setOnClickListener(new ClickListener());
	}

	/**
	 * 点击事件内部类
	 * @author 朱永地
	 *
	 */
	private class ClickListener implements View.OnClickListener {

		@Override
		public void onClick(View view) {
			switch (view.getId()) {

			case R.id.page_nodata_shoppingCart_tv_go2Mall:
				toFragmentBack();
				break;

			case R.id.page_nodata_shoppingCart_tv_trySearch:
				toFragmentSearch();
				break;

			case R.id.fragmentShoppingCart_tv_buy:
				break;

			}
		}

	}

	/**
	 * 适配器内部类
	 * @author 朱永地
	 *
	 */
	private class LVAdapter extends BaseAdapter {

		final int NODATA = -1; //无购物记录
		final int NORMAL = 1; //有购物记录
		final int RECO = 2; //为您推荐

		@Override
		public int getCount() {
			if (orderList == null || orderList.size() == 0) {
				setBuyLayoutVisible(View.GONE);
				return 2;
			} else {
				setBuyLayoutVisible(View.VISIBLE);
				return orderList.size() + 1;
			}
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public int getItemViewType(int position) {
			if (orderList == null || orderList.size() == 0) {
				if (position == 0) {
					return NODATA;
				} else {
					return RECO;
				}
			} else {
				if (position == getCount() - 1) {
					return RECO;
				} else {
					return NORMAL;
				}
			}
		}

		@Override
		public int getViewTypeCount() {
			return 2;
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
					convertView = LayoutInflater.from(getActivity()).inflate(R.layout.page_nodata_shoppingcart, null);
					holder.tv_go2Mall = (TextView) convertView.findViewById(R.id.page_nodata_shoppingCart_tv_go2Mall);
					holder.tv_trySearch = (TextView) convertView
							.findViewById(R.id.page_nodata_shoppingCart_tv_trySearch);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				holder.tv_go2Mall.setOnClickListener(new ClickListener());
				holder.tv_trySearch.setOnClickListener(new ClickListener());
			} else if (getItemViewType(position) == RECO) {
				if (convertView == null) {
					holder = new ViewHolder();
					convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_reco2, null);
					holder.lv_reco = (ListView) convertView.findViewById(R.id.item_reco2_lv_content);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				holder.lv_reco.setAdapter(new RecoLVAdapter());
			} else if (getItemViewType(position) == NORMAL) {
				if (convertView == null) {
					holder = new ViewHolder();
					convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_order, null);
					holder.iv_pic = (ImageView) convertView.findViewById(R.id.itemClear_iv_pic);
					holder.tv_title = (TextView) convertView.findViewById(R.id.itemClear_tv_title);
					holder.tv_price = (TextView) convertView.findViewById(R.id.itemClear_tv_price);
					holder.tv_desc = (TextView) convertView.findViewById(R.id.itemClear_tv_desc);
					holder.tv_author = (TextView) convertView.findViewById(R.id.itemClear_tv_author);
					holder.tv_menu = (TextView) convertView.findViewById(R.id.itemClear_tv_menu);
					holder.tv_counts = (TextView) convertView.findViewById(R.id.itemOrder_tv_counts);
					holder.tv_cost = (TextView) convertView.findViewById(R.id.itemOrder_tv_cost);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				Book book = orderList.get(position).getBook();
				Picasso.with(getActivity()).load(book.getBookPic()).into(holder.iv_pic);
				holder.tv_title.setText(String.valueOf(book.getBookName()));
				holder.tv_price.setText(String.valueOf(book.getPrice()));
				holder.tv_desc.setText(String.valueOf(book.getBookDesc()));
				holder.tv_author.setText(String.valueOf(book.getBookName()));
				holder.tv_menu.setText(String.valueOf(book.getBookSmallType().getName()));
				holder.tv_counts.setText("数量:  " + String.valueOf(orderList.get(position).getCounts()));
				holder.tv_cost.setText("总价格:  " + String.valueOf(orderList.get(position).getCost()));
			}
			return convertView;
		}

		class ViewHolder {
			TextView tv_go2Mall, tv_trySearch; //购物车无内容时的跳转TextView
			ListView lv_reco; //为您推荐ListView

			ImageView iv_pic;
			TextView tv_title, tv_price, tv_desc, tv_author, tv_menu;
			TextView tv_counts, tv_cost;
		}

	}

	/**
	 * 为您推荐适配器内部类
	 * @author 朱永地
	 *
	 */
	private class RecoLVAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (recomBookList != null && recomBookList.size() != 0) {
				return recomBookList.size();
			} else {
				return 0;
			}
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
			if (recomBookList != null && recomBookList.size() != 0) {
				Picasso.with(getActivity()).load(recomBookList.get(position).getBookPic()).into(holder.iv_pic);
				holder.tv_title.setText(String.valueOf(recomBookList.get(position).getBookName()));
				holder.tv_price.setText(String.valueOf(recomBookList.get(position).getPrice()));
				holder.tv_desc.setText(String.valueOf(recomBookList.get(position).getBookDesc()));
				holder.tv_author.setText(String.valueOf(recomBookList.get(position).getBookName()));
				holder.tv_menu.setText(String.valueOf(recomBookList.get(position).getBookSmallType().getName()));
			}
			return convertView;
		}

		class ViewHolder {
			ImageView iv_pic;
			TextView tv_title, tv_price, tv_desc, tv_author, tv_menu;
		}

	}

	@Override
	public void toFragmentBack() {
		String fragmentBackTag = (String) CacheUtil.get(getActivity(), "fragmentBackTag", "");
		if ("FragmentHome".equals(fragmentBackTag)) {
			FragmentController.show(getFragmentManager(), "FragmentHome", new FragmentHome(), R.id.container_home, true,
					true);
			((FragmentMain) getParentFragment()).setFragmentIconAlpha("FragmentHome");
		} else {
			FragmentController.show(getActivity().getSupportFragmentManager(), fragmentBackTag,
					FragmentController.getFragmentByTag(getActivity().getSupportFragmentManager(), fragmentBackTag),
					R.id.container_base, true, true);
		}
	}

	@Override
	public void toFragmentSearch() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentSearch", new FragmentSearch(),
				R.id.container_base, false, true);
	}

	@Override
	public boolean onBackPressed() {
		toFragmentBack();
		return true;
	}

	@Override
	public User getCurrentUser() {
		return (User) CacheUtil.getEntityData(getActivity(), "currentUser", new TypeToken<User>() {
		}.getType());
	}

	@Override
	public void showErrorMsg(String errorMsg) {
		EqupUtil.showMyToast(getActivity(), errorMsg, 0, Toast.LENGTH_SHORT);
	}

	@Override
	public void setOrderList(List<Order> orderList) {
		this.orderList = StringUtil.limitOrderList(orderList, 1);
	}

	@Override
	public void setLVAdapter() {
		lv_content.setAdapter(new LVAdapter());
		if (orderList != null && orderList.size() != 0) {
			double allCost = 0;
			for (int i = 0; i < orderList.size(); i++) {
				allCost += Double.valueOf(orderList.get(i).getCost());
			}
			tv_allCost.setText("总价格:   " + String.valueOf(allCost));
		}
		lv_content.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				toFragmentOrderDetails();
			}
		});
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		if (!hidden) {
			orderPresenter.list();
		}
	}

	@Override
	public void setBuyLayoutVisible(int visibility) {
		if (visibility == View.GONE) {
			v_divider.setVisibility(View.INVISIBLE);
			rl_buy.setVisibility(View.GONE);
		}
		if (visibility == View.VISIBLE) {
			v_divider.setVisibility(View.VISIBLE);
			rl_buy.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void toFragmentOrderDetails() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentOrderDetails",
				new FragmentOrderDetails(), R.id.container_base, false, true);
	}

	@Override
	public List<Order> getOrderList() {
		return orderList;
	}

}
