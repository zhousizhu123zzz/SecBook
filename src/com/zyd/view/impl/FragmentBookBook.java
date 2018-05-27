package com.zyd.view.impl;

import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.zyd.constant.Constant;
import com.zyd.model.Address;
import com.zyd.model.Book;
import com.zyd.model.Coupon;
import com.zyd.model.Order;
import com.zyd.model.User;
import com.zyd.presenter.AddressPresenter;
import com.zyd.presenter.BookPresenter;
import com.zyd.presenter.CouponPresenter;
import com.zyd.secbooks.R;
import com.zyd.utils.CacheUtil;
import com.zyd.utils.EqupUtil;
import com.zyd.utils.FragmentBackHandler;
import com.zyd.utils.FragmentController;
import com.zyd.view.FragmentBookBookView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 图书界面
 * @author 朱永地
 *
 */
public class FragmentBookBook extends Fragment implements FragmentBookBookView, FragmentBackHandler {

	private View rootView;

	/**
	 * Content
	 */
	private LinearLayout ll_content;
	private ImageView iv_bookPic;
	private TextView tv_bookName, tv_author, tv_desc, tv_price, tv_address, tv_coupon;

	/**
	 * ProgressBar
	 */
	private ProgressBar pb_loading;

	private BookPresenter bookPresenter = new BookPresenter(this);
	private AddressPresenter addressPresenter = new AddressPresenter(this);
	private CouponPresenter couponPresenter = new CouponPresenter(this);

	private Book book; //图书信息
	private List<Address> addressList; //收货地址信息
	private List<Coupon> couponList;//优惠券信息

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_bookbook, container, false);
		initView();
		bookPresenter.getBook();
		addressPresenter.listAddress();
		couponPresenter.listCoupon();
		return rootView;
	}

	/**
	 * 初始化view
	 */
	private void initView() {
		ll_content = (LinearLayout) rootView.findViewById(R.id.fragmentBookBook_ll_content);
		iv_bookPic = (ImageView) rootView.findViewById(R.id.fragmentBookBook_iv_bookPic);
		tv_bookName = (TextView) rootView.findViewById(R.id.fragmentBookBook_tv_bookName);
		tv_author = (TextView) rootView.findViewById(R.id.fragmentBookBook_tv_author);
		tv_desc = (TextView) rootView.findViewById(R.id.fragmentBookBook_tv_bookDesc);
		tv_price = (TextView) rootView.findViewById(R.id.fragmentBookBook_tv_price);
		tv_address = (TextView) rootView.findViewById(R.id.fragmentBookBook_tv_address);
		tv_coupon = (TextView) rootView.findViewById(R.id.fragmentBookBook_tv_coupon);
		pb_loading = (ProgressBar) rootView.findViewById(R.id.fragmentBookBook_pb_loading);
	}

	@Override
	public void setAddressText() {
		if (addressList != null && addressList.size() != 0) {
			for (Address address : addressList) {
				if (address.isIndex()) {
					tv_address.setText("送至:    " + String.valueOf(address.getAddress()) + "   "
							+ String.valueOf(address.getReceiptName()) + "   " + String.valueOf(address.getTelNo()));
				}
			}
		} else {
			tv_address.setText(Constant.msg.ERROR_NOADDRESS);
		}
		tv_address.setOnClickListener(new ClickListener());
	}

	@Override
	public void setBookText() {
		if (book != null) {
			FragmentBookDetails fragmentBookDetails = (FragmentBookDetails) ((FragmentBook) getParentFragment())
					.getFragment(1);
			fragmentBookDetails.setBook(book);
			fragmentBookDetails.setBookText();

			Picasso.with(getActivity()).load(book.getBookPic()).into(iv_bookPic);
			tv_bookName.setText(String.valueOf(book.getBookName()));
			tv_author.setText(String.valueOf(book.getAuthor()));
			tv_desc.setText(String.valueOf(book.getBookDesc()));
			tv_price.setText(String.valueOf(book.getPrice()));
		}
	}

	@Override
	public void setCouponText() {
		if (couponList != null && couponList.size() != 0) {
			tv_coupon.setText("您有" + couponList.size() + "个可用优惠券");
		} else {
			tv_coupon.setText(Constant.msg.ERROR_NOCOUPON);
		}
	}

	@Override
	public boolean onBackPressed() {
		return true;
	}

	@Override
	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public void setAddressList(List<Address> addressList) {
		this.addressList = addressList;
	}

	@Override
	public void setCouponList(List<Coupon> couponList) {
		this.couponList = couponList;
	}

	@Override
	public Book getBook() {
		return book;
	}

	@Override
	public List<Address> getAddressList() {
		return addressList;
	}

	@Override
	public void toFragmentAddress() {
		CacheUtil.put(getActivity(), "fragmentBackTag", "FragmentBook");
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentAddress", new FragmentAddress(),
				R.id.container_base, false, true);
	}

	@Override
	public void setProgressBarVisible(int visibility) {
		if (visibility == View.GONE) {
			ll_content.setVisibility(View.VISIBLE);
			pb_loading.setVisibility(View.GONE);
		}
		if (visibility == View.VISIBLE) {
			ll_content.setVisibility(View.GONE);
			pb_loading.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public int getBookId() {
		return (Integer) CacheUtil.get(getActivity(), "bookId", 0);
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

	}

	private class ClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.fragmentBookBook_tv_address:
				toFragmentAddress();
				break;

			}
		}

	}

}
