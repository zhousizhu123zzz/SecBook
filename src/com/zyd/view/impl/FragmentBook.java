package com.zyd.view.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.zyd.model.Book;
import com.zyd.model.MData;
import com.zyd.model.Order;
import com.zyd.model.User;
import com.zyd.presenter.BookPresenter;
import com.zyd.presenter.OrderPresenter;
import com.zyd.secbooks.R;
import com.zyd.utils.CacheUtil;
import com.zyd.utils.EqupUtil;
import com.zyd.utils.FragmentBackHandler;
import com.zyd.utils.FragmentController;
import com.zyd.view.FragmentBookView;
import com.zyd.widget.ViewCircleNumber;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 图书购买界面
 * @author 朱永地
 *
 */
public class FragmentBook extends Fragment implements FragmentBookView, FragmentBackHandler {

	private View rootView;

	/**
	 * Title
	 */
	private RelativeLayout rl_back;
	private View v_scroller; //商品,详情 ,下面的可以跟着viewpgaer滑动的view
	private TextView tv_product, tv_details;

	/**
	 * Body
	 */
	private ViewPager vp_content;

	/**
	 * Footer
	 */
	private LinearLayout ll_collection, ll_shippingCart;
	private TextView tv_joinToShoppingCart, tv_buy;
	private ViewCircleNumber mShoppingCartCountHint; //购物数量的小圆圈提示

	private List<Fragment> fragmentList = new ArrayList<Fragment>();
	private int titleWidth, firstTitleX;

	private BookPresenter bookPresenter = new BookPresenter(this);
	private OrderPresenter orderPresenter = new OrderPresenter(this);
	private List<Order> orderList;
	private Book book;
	private Order order;
	private User currentUser;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_book, container, false);
		initView();
		orderPresenter.list();
		bookPresenter.getBook();
		return rootView;
	}

	/**
	 * 初始化view
	 */
	private void initView() {
		vp_content = (ViewPager) rootView.findViewById(R.id.fragmentBook_vp_content);
		rl_back = (RelativeLayout) rootView.findViewById(R.id.fragmentBook_rl_back);
		v_scroller = rootView.findViewById(R.id.fragmentBook_v_scroller);
		tv_product = (TextView) rootView.findViewById(R.id.fragmentBook_tv_product);
		tv_details = (TextView) rootView.findViewById(R.id.fragmentBook_tv_details);
		ll_collection = (LinearLayout) rootView.findViewById(R.id.fragmentBook_ll_collection);
		ll_shippingCart = (LinearLayout) rootView.findViewById(R.id.fragmentBook_ll_shoppingCart);
		tv_joinToShoppingCart = (TextView) rootView.findViewById(R.id.fragmentBook_tv_add2ShoppingCart);
		tv_buy = (TextView) rootView.findViewById(R.id.fragmentBook_tv_buy);
		mShoppingCartCountHint = (ViewCircleNumber) rootView.findViewById(R.id.fragmentBook_shoppingCartCountHint);

		initScrollView();
		initFragmentList();
		vp_content.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));
		vp_content.setOnPageChangeListener(new PageChangeListener());

		rl_back.setOnClickListener(new ClickListener());
		tv_product.setOnClickListener(new ClickListener());
		tv_details.setOnClickListener(new ClickListener());
		ll_collection.setOnClickListener(new ClickListener());
		ll_shippingCart.setOnClickListener(new ClickListener());
		tv_joinToShoppingCart.setOnClickListener(new ClickListener());
		tv_buy.setOnClickListener(new ClickListener());
	}

	/**
	 * 初始化ViewPager的Fragment集合
	 */
	private void initFragmentList() {
		fragmentList.add(new FragmentBookBook());
		fragmentList.add(new FragmentBookDetails());
	}

	/**
	 * 初始化 商品,详情,评价  下面的可以跟着viewpager滑动的view
	 */
	private void initScrollView() {

		tv_product.post(new Runnable() {

			@Override
			public void run() {
				/**
				 * 初始化scrollview的宽度
				 */
				titleWidth = tv_product.getWidth();
				v_scroller.getLayoutParams().width = titleWidth;
				v_scroller.setLayoutParams(v_scroller.getLayoutParams());

				/**
				 * 初始化scrollview的坐标
				 */
				int titleX = (int) ((LinearLayout) tv_product.getParent()).getX();
				firstTitleX = titleX;
				v_scroller.setX(titleX);
			}
		});

	}

	/**
	 * ViewPager的适配器
	 * @author 朱永地
	 *
	 */
	private class ViewPagerAdapter extends FragmentPagerAdapter {

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return fragmentList.get(position);
		}

		@Override
		public int getCount() {
			return fragmentList.size();
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

	}

	/**
	 * ViewPager的滑动监听内部类
	 * @author 朱永地
	 *
	 */
	private class PageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			v_scroller.setX(firstTitleX + titleWidth * arg0);
		}

	}

	/**
	 * 点击监听内部类
	 * @author 朱永地
	 *
	 */
	private class ClickListener implements View.OnClickListener {

		@Override
		public void onClick(View view) {
			switch (view.getId()) {

			case R.id.fragmentBook_tv_product:
				toFragmentBookProduct();
				break;

			case R.id.fragmentBook_tv_details:
				toFragmentBookDetails();
				break;

			case R.id.fragmentBook_rl_back:
				toFragmentBack();
				break;
			case R.id.fragmentBook_ll_collection:
				int bookId = (Integer) CacheUtil.get(getActivity(), "bookId", 0);
				MData mData = (MData) CacheUtil.getEntityData(getActivity(),
						"collection_bookList" + currentUser.getUserId(), new TypeToken<MData>() {
						}.getType());
				if (mData != null && mData.getIntegerList() != null) {
					List<Integer> bookList = mData.getIntegerList();
					if (bookList != null) {
						for (int i = 0; i < bookList.size(); i++) {
							if (bookId == bookList.get(i)) {
								EqupUtil.showMyToast(getActivity(), "您已经收藏过该书了!", 0, Toast.LENGTH_SHORT);
								return;
							}
						}
						bookList.add(bookId);
						mData.setIntegerList(bookList);
					}
				} else {
					mData = new MData();
					List<Integer> list = new ArrayList<Integer>();
					list.add(bookId);
					mData.setIntegerList(list);
				}
				CacheUtil.setEntityData(getActivity(), "collection_bookList" + currentUser.getUserId(), mData);
				EqupUtil.showMyToast(getActivity(), "收藏成功!", 0, Toast.LENGTH_SHORT);
				break;
			case R.id.fragmentBook_ll_shoppingCart:
				toFragmentShoppingCart();
				break;
			case R.id.fragmentBook_tv_add2ShoppingCart:
				if (orderList != null) {
					for (int i = 0; i < orderList.size(); i++) {
						if (getBookId() == orderList.get(i).getBook().getBookId()) {
							EqupUtil.showMyToast(getActivity(), "您已经添加过了哦!", 0, Toast.LENGTH_SHORT);
							return;
						}
					}
					orderPresenter.add();
				} else {
					EqupUtil.showMyToast(getActivity(), "加入购物车失败!请重试", 0, Toast.LENGTH_SHORT);
				}
				break;
			case R.id.fragmentBook_tv_buy:
				toFragmentOrderDetails();
				orderPresenter.add();
				break;
			}
		}

	}

	@Override
	public boolean onBackPressed() {
		toFragmentBack();
		return true;
	}

	@Override
	public void toFragmentBookProduct() {
		vp_content.setCurrentItem(0);
	}

	@Override
	public void toFragmentBookDetails() {
		vp_content.setCurrentItem(1);
	}

	@Override
	public void setShoppingCartCircle() {
		if (orderList != null) {
			mShoppingCartCountHint.setVisibility(View.VISIBLE);
			mShoppingCartCountHint.setText(String.valueOf(orderList.size()));
		} else {
			mShoppingCartCountHint.setVisibility(View.VISIBLE);
			mShoppingCartCountHint.setText("0");
		}
	}

	@Override
	public void reloadFragment() {
		((FragmentPagerAdapter) vp_content.getAdapter()).notifyDataSetChanged();
	}

	@Override
	public Fragment getFragment(int position) {
		return ((FragmentPagerAdapter) vp_content.getAdapter()).getItem(position);
	}

	@Override
	public void toFragmentBack() {
		String fragmentBackTag = (String) CacheUtil.get(getActivity(), "fragmentBackTag", "");
		if (null == fragmentBackTag) {
			fragmentBackTag = "FragmentMain";
		}
		Fragment fragment = FragmentController.getFragmentByTag(getActivity().getSupportFragmentManager(),
				fragmentBackTag);
		FragmentController.show(getActivity().getSupportFragmentManager(), fragmentBackTag, fragment,
				R.id.container_base, true, true);
	}

	@Override
	public void toFragmentShoppingCart() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentMain", new FragmentMain(),
				R.id.container_base, false, false);
		FragmentMain fragmentMain = (FragmentMain) FragmentController
				.getFragmentByTag(getActivity().getSupportFragmentManager(), "FragmentMain");
		fragmentMain.toFragmentShoppingCartWithAnim();
	}

	@Override
	public User getCurrentUser() {
		currentUser = (User) CacheUtil.getEntityData(getActivity(), "currentUser", new TypeToken<User>() {
		}.getType());
		return currentUser;
	}

	@Override
	public void showErrorMsg(String errorMsg) {
		EqupUtil.showMyToast(getActivity(), errorMsg, 0, Toast.LENGTH_SHORT);
	}

	@Override
	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	@Override
	public Order getOrder() {
		return order;
	}

	@Override
	public int getBookId() {
		return (Integer) CacheUtil.get(getActivity(), "bookId", 0);
	}

	@Override
	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public void setOrder() {
		if (book != null) {
			order = new Order();
			order.setBook(book);
			if (getCurrentUser() != null) {
				order.setUserId(getCurrentUser().getUserId());
			}
			order.setCost(book.getPrice());
			order.setCounts(1);
			order.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
			order.setStatus(1);
		}
	}

	@Override
	public void toFragmentOrderDetails() {
		CacheUtil.put(getActivity(), "fragmentBackTag", "FragmentBook");
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentOrderDetails",
				new FragmentOrderDetails(), R.id.container_base, false, true);
	}

	@Override
	public List<Order> getOrderListToFragmentOrderDetails() {
		if (order != null) {
			List<Order> orderList = new ArrayList<Order>();
			orderList.add(order);
			return orderList;
		} else {
			return null;
		}
	}

}
