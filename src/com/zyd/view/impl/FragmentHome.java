package com.zyd.view.impl;

import java.util.ArrayList;
import java.util.List;

import com.squareup.picasso.Picasso;
import com.zyd.adapter.BannerAdapter;
import com.zyd.model.Advertise;
import com.zyd.model.Book;
import com.zyd.model.MData;
import com.zyd.presenter.HomePresenter;
import com.zyd.secbooks.R;
import com.zyd.utils.CacheUtil;
import com.zyd.utils.EqupUtil;
import com.zyd.utils.FragmentBackHandler;
import com.zyd.utils.FragmentController;
import com.zyd.utils.StringUtil;
import com.zyd.view.FragmentHomeView;
import com.zyd.widget.ViewBannerCircle;
import com.zyd.widget.ViewGridInListView;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * FragmentHome
 * @author 朱永地
 *
 */
public class FragmentHome extends BaseFragment implements FragmentHomeView, FragmentBackHandler {

	private View rootView;

	/**
	 * Titlebar
	 */
	private RelativeLayout rl_search;
	private ImageView iv_search;
	private EditText et_search;
	private ImageView iv_camera;

	/**
	 * SwipeRefreshLayout
	 */
	private SwipeRefreshLayout swipeRefreshLayout;

	/**
	 * Body
	 */
	private ListView lv_content;

	/**
	 * Banner
	 */
	private ViewPager banner;
	private ViewBannerCircle circleView;

	private HomePresenter homePresenter = new HomePresenter(this);
	private List<String> advertiseList = new ArrayList<String>();
	private List<Book> allBookList = new ArrayList<Book>();
	private List<Book> recomBookList = new ArrayList<Book>();
	private List<Book> hotBookList = new ArrayList<Book>();
	private List<Book> newBookList = new ArrayList<Book>();
	private List<Book> clearBookList = new ArrayList<Book>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_home, container, false);
		initView();
		return rootView;
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fragmentHome_swipeRefreshLayout);
		rl_search = (RelativeLayout) rootView.findViewById(R.id.fragmentHome_titlebar_rl_search);
		iv_search = (ImageView) rootView.findViewById(R.id.fragmentHome_iv_search);
		et_search = (EditText) rootView.findViewById(R.id.fragmentHome_et_search);
		iv_camera = (ImageView) rootView.findViewById(R.id.fragmentHome_titlebar_iv_camera);
		lv_content = (ListView) rootView.findViewById(R.id.fragmentHome_lv_content);

		swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);// 设置圆圈转的时候的颜色顺序
		swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				homePresenter.list();
			}
		});

		View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home_header, null);
		banner = (ViewPager) headerView.findViewById(R.id.fragmentHome_banner_vp);
		circleView = (ViewBannerCircle) headerView.findViewById(R.id.fragmentHome_banner_circleView);
		LinearLayout ll_bookList = (LinearLayout) headerView.findViewById(R.id.fragmentHome_ll_bookList);
		LinearLayout ll_novel = (LinearLayout) headerView.findViewById(R.id.fragmentHome_ll_novel);
		LinearLayout ll_lvyou = (LinearLayout) headerView.findViewById(R.id.fragmentHome_ll_lvyou);
		LinearLayout ll_education = (LinearLayout) headerView.findViewById(R.id.fragmentHome_ll_education);

		FragmentStart fragmentStart = (FragmentStart) FragmentController
				.getFragmentByTag(getActivity().getSupportFragmentManager(), "FragmentStart");

		MData data = (MData) fragmentStart.getTransmissionData();
		if (data == null) {
			homePresenter.list();
		} else {
			setBookList(data.getBookList());
			setAdsList(data.getAdvertiseList());
		}

		ll_bookList.setOnClickListener(new ClickListener());
		ll_novel.setOnClickListener(new ClickListener());
		ll_lvyou.setOnClickListener(new ClickListener());
		ll_education.setOnClickListener(new ClickListener());

		lv_content.addHeaderView(headerView); //给ListView添加Header,setHeaderView尽量在setAdapter之前

		rl_search.setOnClickListener(new ClickListener());
		et_search.setOnClickListener(new ClickListener()); //拦截EditText的点击事件交给外层RelativeLayout来处理
		iv_search.setOnClickListener(new ClickListener()); //拦截ImageView的点击事件交给外层RelativeLayout来处理
		iv_camera.setOnClickListener(new ClickListener());
	}

	/**
	 * 跳转到FragmentSearch
	 */
	@Override
	public void toFragmentSearch() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentSearch", new FragmentSearch(),
				R.id.container_base, false, true);
	}

	/**
	 * 跳转到ActivityCapture
	 */
	@Override
	public void toActivityCapture() {

	}

	/**
	 * 点击事件内部类
	 * @author Administrator
	 *
	 */
	private class ClickListener implements View.OnClickListener {

		@Override
		public void onClick(View view) {

			switch (view.getId()) {

			case R.id.fragmentHome_titlebar_rl_search:
				toFragmentSearch();
				break;

			case R.id.fragmentHome_et_search:
				rl_search.performClick();
				break;

			case R.id.fragmentHome_iv_search:
				rl_search.performClick();
				break;

			case R.id.fragmentHome_titlebar_iv_camera:
				toActivityCapture();
				break;

			case R.id.fragmentHome_ll_bookList:
				toFragmentMenu();
				break;

			case R.id.fragmentHome_ll_novel:
				CacheUtil.put(getActivity(), "fragmentMenu_selectedBookBigTypePosition", 1);
				toFragmentBookList();
				break;

			case R.id.fragmentHome_ll_education:

				break;
			}

		}

	}

	/**
	 * ListView适配器内部类
	 * @author 朱永地
	 *
	 */
	private class FragmentHomeLVApdater extends BaseAdapter {

		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public int getItemViewType(int position) {
			return position;
		}

		@Override
		public int getViewTypeCount() {
			return 4;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		/**
		 * 今日推荐--重磅推销--新书上架--清仓甩卖
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;

			if (getItemViewType(position) == 0) {
				if (convertView == null) {
					holder = new ViewHolder();
					convertView = View.inflate(getActivity(), R.layout.item_reco, null);
					holder.gv_content = (GridView) convertView.findViewById(R.id.itemReco_gv_content);
					holder.ll_change = (LinearLayout) convertView.findViewById(R.id.itemReco_ll_change);
					holder.iv_change = (ImageView) convertView.findViewById(R.id.itemReco_iv_change);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				holder.gv_content.setAdapter(new ItemRecomGVAdapter());
				holder.setRecomListener();
			} else if (getItemViewType(position) == 1) {
				if (convertView == null) {
					holder = new ViewHolder();
					convertView = View.inflate(getActivity(), R.layout.item_hot, null);
					holder.ll_no1 = (LinearLayout) convertView.findViewById(R.id.fragmentHome_ll_no1);
					holder.rl_no2 = (RelativeLayout) convertView.findViewById(R.id.fragmentHome_rl_no2);
					holder.rl_no3 = (RelativeLayout) convertView.findViewById(R.id.fragmentHome_rl_no3);
					holder.iv_pic1 = (ImageView) convertView.findViewById(R.id.itemHot_iv_pic1);
					holder.iv_pic2 = (ImageView) convertView.findViewById(R.id.itemHot_iv_pic2);
					holder.iv_pic3 = (ImageView) convertView.findViewById(R.id.itemHot_iv_pic3);
					holder.tv_title1 = (TextView) convertView.findViewById(R.id.itemHot_tv_title1);
					holder.tv_title2 = (TextView) convertView.findViewById(R.id.itemHot_tv_title2);
					holder.tv_title3 = (TextView) convertView.findViewById(R.id.itemHot_tv_title3);
					holder.tv_desc1 = (TextView) convertView.findViewById(R.id.itemHot_tv_desc1);
					holder.tv_price1 = (TextView) convertView.findViewById(R.id.itemHot_tv_price1);
					holder.tv_price2 = (TextView) convertView.findViewById(R.id.itemHot_tv_price2);
					holder.tv_price3 = (TextView) convertView.findViewById(R.id.itemHot_tv_price3);
					holder.tv_viewMore = (TextView) convertView.findViewById(R.id.fragmentHome_tv_viewMore);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				if (hotBookList != null && hotBookList.size() != 0) {
					Picasso.with(getActivity()).load(hotBookList.get(0).getBookPic()).into(holder.iv_pic1);
					Picasso.with(getActivity()).load(hotBookList.get(1).getBookPic()).into(holder.iv_pic2);
					Picasso.with(getActivity()).load(hotBookList.get(2).getBookPic()).into(holder.iv_pic3);
					holder.tv_desc1.setText(String.valueOf(hotBookList.get(0).getBookDesc()));
					holder.tv_title1.setText(String.valueOf(hotBookList.get(0).getBookName()));
					holder.tv_title2.setText(String.valueOf(hotBookList.get(1).getBookName()));
					holder.tv_title3.setText(String.valueOf(hotBookList.get(2).getBookName()));
					holder.tv_price1.setText(String.valueOf(hotBookList.get(0).getPrice()));
					holder.tv_price2.setText(String.valueOf(hotBookList.get(1).getPrice()));
					holder.tv_price3.setText(String.valueOf(hotBookList.get(2).getPrice()));
					holder.setPosition(position);
					holder.setHotClickListener();
				}
			} else if (getItemViewType(position) == 2) {
				if (convertView == null) {
					holder = new ViewHolder();
					convertView = View.inflate(getActivity(), R.layout.item_new, null);
					holder.gv_new = (ViewGridInListView) convertView.findViewById(R.id.fragmentHome_itemNew_gv_content);
					holder.tv_viewMore = (TextView) convertView.findViewById(R.id.itemNew_tv_viewMore);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				holder.gv_new.setAdapter(new ItemNewGVAdapter());
				holder.setNewListener();
			} else {
				if (convertView == null) {
					holder = new ViewHolder();
					convertView = View.inflate(getActivity(), R.layout.item_clear, null);
					holder.lv_clear = (ListView) convertView.findViewById(R.id.fragmentHome_itemClear_lv_content);
					holder.tv_viewMore = (TextView) convertView.findViewById(R.id.itemClear_tv_viewMore);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				holder.lv_clear.setAdapter(new ItemClearLVAdapter());
				holder.setClearListener();
			}
			return convertView;
		}

		final class ViewHolder {

			int position;

			/**
			 * Recom
			 */
			GridView gv_content;
			LinearLayout ll_change;
			ImageView iv_change;

			/**
			 * Hot
			 */
			LinearLayout ll_no1;
			RelativeLayout rl_no2, rl_no3;
			TextView tv_viewMore;
			ImageView iv_pic1, iv_pic2, iv_pic3;
			TextView tv_title1, tv_title2, tv_title3;
			TextView tv_desc1;
			TextView tv_price1, tv_price2, tv_price3;

			/**
			 * New
			 */
			ViewGridInListView gv_new;

			/**
			 * Clear
			 */
			ListView lv_clear;

			void setPosition(int position) {
				this.position = position;
			}

			void setHotClickListener() {
				ll_no1.setOnClickListener(new HomeClickListener());
				rl_no2.setOnClickListener(new HomeClickListener());
				rl_no3.setOnClickListener(new HomeClickListener());
				tv_viewMore.setOnClickListener(new HomeClickListener());
			}

			class HomeClickListener implements View.OnClickListener {

				@Override
				public void onClick(View v) {
					switch (v.getId()) {
					case R.id.fragmentHome_ll_no1:
						CacheUtil.put(getActivity(), "bookId", hotBookList.get(position).getBookId());
						toFragmentBook();
						break;
					case R.id.fragmentHome_rl_no2:
						CacheUtil.put(getActivity(), "bookId", hotBookList.get(position).getBookId());
						toFragmentBook();
						break;
					case R.id.fragmentHome_rl_no3:
						CacheUtil.put(getActivity(), "bookId", hotBookList.get(position).getBookId());
						toFragmentBook();
						break;
					case R.id.fragmentHome_tv_viewMore:
						toFragmentBookList1("重磅推销");
						break;
					}
				}

			}

			void setNewListener() {
				tv_viewMore.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						toFragmentBookList1("新书上架");
					}
				});
				gv_new.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						CacheUtil.put(getActivity(), "bookId", newBookList.get(position).getBookId());
						toFragmentBook();
					}
				});
			}

			void setClearListener() {
				tv_viewMore.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						toFragmentBookList1("清仓甩卖");
					}
				});
				lv_clear.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						CacheUtil.put(getActivity(), "bookId", clearBookList.get(position).getBookId());
						toFragmentBook();
					}
				});
			}

			void setRecomListener() {
				ll_change.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						iv_change.animate().rotationBy(360).setDuration(1000).start();
						recomBookList = StringUtil.limitBookList(allBookList, "recom");
						((BaseAdapter) gv_content.getAdapter()).notifyDataSetChanged();
					}
				});
				gv_content.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						CacheUtil.put(getActivity(), "bookId", recomBookList.get(position).getBookId());
						toFragmentBook();
					}

				});

			}
		}
	}

	/**
	 * 推荐图书适配器内部类
	 * @author 朱永地
	 *
	 */
	private class ItemRecomGVAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return recomBookList.size();
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
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_new_item, null);
				holder.iv_pic = (ImageView) convertView.findViewById(R.id.itemNew_iv_pic);
				holder.tv_title = (TextView) convertView.findViewById(R.id.itemNew_tv_title);
				holder.tv_price = (TextView) convertView.findViewById(R.id.itemNew_tv_price);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (recomBookList != null && recomBookList.size() != 0) {
				Picasso.with(getActivity()).load(recomBookList.get(position).getBookPic()).into(holder.iv_pic);
				holder.tv_title.setText(String.valueOf(recomBookList.get(position).getBookName()));
				holder.tv_price.setText(String.valueOf(recomBookList.get(position).getPrice()));
			}
			return convertView;
		}
	}

	class ViewHolder {
		ImageView iv_pic;
		TextView tv_title, tv_price;
	}

	/**
	 * 新书上架适配器内部类
	 * @author 朱永地
	 *
	 */
	private class ItemNewGVAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return newBookList.size();
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
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_new_item, null);
				holder.iv_pic = (ImageView) convertView.findViewById(R.id.itemNew_iv_pic);
				holder.tv_title = (TextView) convertView.findViewById(R.id.itemNew_tv_title);
				holder.tv_price = (TextView) convertView.findViewById(R.id.itemNew_tv_price);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Picasso.with(getActivity()).load(newBookList.get(position).getBookPic()).into(holder.iv_pic);
			holder.tv_title.setText(String.valueOf(newBookList.get(position).getBookName()));
			holder.tv_price.setText(String.valueOf(newBookList.get(position).getPrice()));
			return convertView;
		}

		class ViewHolder {
			ImageView iv_pic;
			TextView tv_title, tv_price;
		}
	}

	/**
	 * 清仓甩卖适配器内部类
	 * @author 朱永地
	 *
	 */
	private class ItemClearLVAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return clearBookList.size();
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
			if (clearBookList != null && clearBookList.size() != 0) {
				Picasso.with(getActivity()).load(clearBookList.get(position).getBookPic()).into(holder.iv_pic);
				holder.tv_title.setText(String.valueOf(clearBookList.get(position).getBookName()));
				holder.tv_price.setText(String.valueOf(clearBookList.get(position).getPrice()));
				holder.tv_desc.setText(String.valueOf(clearBookList.get(position).getBookDesc()));
				holder.tv_author.setText(String.valueOf(clearBookList.get(position).getBookName()));
				holder.tv_menu.setText(String.valueOf(clearBookList.get(position).getBookSmallType().getName()));
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
		return false;
	}

	@Override
	public void setAdsList(List<Advertise> advertiseList) {
		this.advertiseList = StringUtil.parseAdsListToStringList(advertiseList);
		initBanner();
	}

	@Override
	public void setBookList(List<Book> bookList) {
		this.allBookList = bookList;
		this.recomBookList = StringUtil.limitBookList(bookList, "recom");
		this.hotBookList = StringUtil.limitBookList(bookList, "hot");
		this.clearBookList = StringUtil.limitBookList(bookList, "special");
		this.newBookList = StringUtil.limitBookList(bookList, "new");
		lv_content.setAdapter(new FragmentHomeLVApdater());
		CacheUtil.setDataList(getActivity(), "RecomBookList", recomBookList);
	}

	@Override
	public void initBanner() {
		circleView.setCircleCount(advertiseList.size()); //设置小圆圈的数量
		circleView.setSelectedCircle(0); //默认显示第一条轮播图

		BannerAdapter bannerAdapter = new BannerAdapter(banner, advertiseList, circleView);
		banner.setAdapter(bannerAdapter);
		bannerAdapter.startViewPager(); //开启自动轮播
	}

	@Override
	public void toFragmentMenu() {
		((FragmentMain) getParentFragment()).toFragmentMenu();
	}

	@Override
	public void toFragmentBookList1(String title) {
		CacheUtil.put(getActivity(), "bookList1_title", title);
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentBookList1", new FragmentBookList1(),
				R.id.container_base, false, true);
	}

	@Override
	public void toFragmentRank() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentRank", new FragmentRank(),
				R.id.container_base, false, true);
	}

	@Override
	public void toFragmentBook() {
		CacheUtil.put(getActivity(), "fragmentBackTag", "FragmentMain");
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
	public void toFragmentBookList() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentBookList", new FragmentBookList(),
				R.id.container_base, false, true);
	}

	@Override
	public void toFragmentLoading() {
		super.toFragmentLoading();
	}

	@Override
	public void toFragmentServerEx() {
		super.toFragmentServerEx();
	}

	@Override
	public void toFragmentNoNet() {
		super.toFragmentNoNet();
	}

	@Override
	public void setTransmissionData(Object o) {
		super.setTransmissionData(o);
	}

	@Override
	public Object getTransmissionData() {
		return super.getTransmissionData();
	}

	@Override
	public void showErrorMsg(String errorMsg) {
		EqupUtil.showMyToast(getActivity(), errorMsg, 0, Toast.LENGTH_SHORT);
	}

	@Override
	public void setRefreshing(boolean isRefresing) {
		swipeRefreshLayout.setRefreshing(isRefresing);
	}

}
