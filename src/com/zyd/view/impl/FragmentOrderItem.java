package com.zyd.view.impl;

import java.util.List;

import com.squareup.picasso.Picasso;
import com.zyd.model.Book;
import com.zyd.model.Order;
import com.zyd.secbooks.R;
import com.zyd.utils.CacheUtil;
import com.zyd.utils.FragmentController;
import com.zyd.utils.StringUtil;
import com.zyd.view.FragmentOrderItemView;

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
import android.widget.TextView;

public class FragmentOrderItem extends Fragment implements FragmentOrderItemView {

	private View rootView;

	/**
	 * Body
	 */
	private ListView lv_content;

	private List<Order> orderList;

	private String fragmentTag;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_order_item, container, false);
		initView();
		return rootView;
	}

	public FragmentOrderItem(String fragmentTag) {
		this.fragmentTag = fragmentTag;
	}

	private void initView() {
		lv_content = (ListView) rootView.findViewById(R.id.fragmentOrder_lv_item);

		setOrderListFromFragmentOrder();
		lv_content.setAdapter(new LVAdapter());
		lv_content.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				CacheUtil.put(getActivity(), "bookId", orderList.get(position).getBook().getBookId());
				toFragmentBook();
			}
		});
	}

	/**
	 * 适配器内部类
	 * @author 朱永地
	 *
	 */
	private class LVAdapter extends BaseAdapter {

		final int NODATA = -1; //无购物记录
		final int NORMAL = 1; //有购物记录

		@Override
		public int getCount() {
			if (orderList == null || orderList.size() == 0) {
				return 1;
			} else {
				return orderList.size();
			}
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public int getItemViewType(int position) {
			if (orderList == null || orderList.size() == 0) {
				return NODATA;
			} else {
				return NORMAL;
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
					convertView = LayoutInflater.from(getActivity()).inflate(R.layout.page_nodata_order, null);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
			} else if (getItemViewType(position) == NORMAL) {
				if (convertView == null) {
					holder = new ViewHolder();
					convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_order, null);
					holder.iv_pic = (ImageView) convertView.findViewById(R.id.itemClear_iv_pic);
					holder.tv_title = (TextView) convertView.findViewById(R.id.itemClear_tv_title);
					holder.tv_price = (TextView) convertView.findViewById(R.id.itemClear_tv_price);
					holder.tv_desc = (TextView) convertView.findViewById(R.id.itemClear_tv_desc);
					holder.tv_author = (TextView) convertView.findViewById(R.id.itemClear_tv_author);
					holder.tv_counts = (TextView) convertView.findViewById(R.id.itemOrder_tv_counts);
					holder.tv_cost = (TextView) convertView.findViewById(R.id.itemOrder_tv_cost);
					holder.tv_menu = (TextView) convertView.findViewById(R.id.itemClear_tv_menu);
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
			ImageView iv_pic;
			TextView tv_title, tv_price, tv_desc, tv_author, tv_menu;
			TextView tv_counts, tv_cost;
		}

	}

	@Override
	public void setOrderListFromFragmentOrder() {
		List<Order> orderList = ((FragmentOrder) getParentFragment()).getOrderList();
		if (orderList != null) {
			if ("fragmentOrderAll".equals(fragmentTag)) {
				this.orderList = orderList;
			}
			if ("fragmentOrderPay".equals(fragmentTag)) {
				this.orderList = StringUtil.limitOrderList(orderList, 1);
			}
			if ("fragmentOrderSend".equals(fragmentTag)) {
				this.orderList = StringUtil.limitOrderList(orderList, 2);
			}
			if ("fragmentOrderReceipt".equals(fragmentTag)) {
				this.orderList = StringUtil.limitOrderList(orderList, 3);
			}
		}
	}

	@Override
	public void toFragmentBook() {
		CacheUtil.put(getActivity(), "fragmentBackTag", "FragmentOrder");
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
