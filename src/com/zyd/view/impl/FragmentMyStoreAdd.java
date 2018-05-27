package com.zyd.view.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.gson.reflect.TypeToken;
import com.zyd.model.Book;
import com.zyd.model.User;
import com.zyd.presenter.BookPresenter;
import com.zyd.secbooks.R;
import com.zyd.utils.CacheUtil;
import com.zyd.utils.EqupUtil;
import com.zyd.utils.FragmentBackHandler;
import com.zyd.utils.FragmentController;
import com.zyd.utils.StringUtil;
import com.zyd.view.FragmentMyStoreAddView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

public class FragmentMyStoreAdd extends Fragment implements FragmentMyStoreAddView, FragmentBackHandler {

	private View rootView;

	/**
	 * Titlebar
	 */
	private LinearLayout ll_back;

	/**
	 * Body
	 */
	private EditText et_bookName;
	private EditText et_author;
	private EditText et_price;
	private EditText et_bookDesc;
	private RelativeLayout rl_bookPic;
	private ImageView iv_bookPic;
	/**
	 * PB
	 */
	private ProgressBar pb_loading;

	/**
	 * Foot
	 */
	private LinearLayout ll_add;

	private BookPresenter bookPresenter = new BookPresenter(this);
	private Book addedBook;
	private User currentUser;

	private final int IMAGE_REQUEST_CODE = 1;
	private final int CAMERA_REQUEST_CODE = 2;
	private Dialog mDialogToChooseImageWay;
	private Bitmap selectedBitmap;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_mystoreadd, container, false);
		setCurrentUser();
		initView();
		return rootView;
	}

	/**
	 * 初始化view
	 */
	private void initView() {
		ll_back = (LinearLayout) rootView.findViewById(R.id.fragmentMyStoreAdd_ll_back);
		ll_add = (LinearLayout) rootView.findViewById(R.id.fragmentMyStoreAdd_ll_save);
		et_bookName = (EditText) rootView.findViewById(R.id.fragmentMyStoreAdd_et_bookName);
		et_author = (EditText) rootView.findViewById(R.id.fragmentMyStoreAdd_et_author);
		et_price = (EditText) rootView.findViewById(R.id.fragmentMyStoreAdd_et_price);
		et_bookDesc = (EditText) rootView.findViewById(R.id.fragmentMyStoreAdd_et_bookDesc);
		rl_bookPic = (RelativeLayout) rootView.findViewById(R.id.fragmentMyStoreAdd_rl_bookPic);
		iv_bookPic = (ImageView) rootView.findViewById(R.id.fragmentMyStoreAdd_iv_bookPic);
		pb_loading = (ProgressBar) rootView.findViewById(R.id.fragmentMyStoreAdd_pb_loading);

		ll_back.setOnClickListener(new ClickListener());
		ll_add.setOnClickListener(new ClickListener());
		rl_bookPic.setOnClickListener(new ClickListener());
	}

	private class ClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.fragmentMyStoreAdd_ll_back:
				toFragmentMyStore();
				break;

			case R.id.fragmentMyStoreAdd_ll_save:
				if (!validateInput()) {
					showErrorMsg("请填写完整的信息!");
				} else {
					addedBook = new Book();
					addedBook.setUserId(currentUser.getUserId());
					addedBook.setBookName(getBookName());
					addedBook.setBookDesc(getBookDesc());
					addedBook.setPrice(getPrice());
					addedBook.setAuthor(getAuthor());
					addedBook.setBookPic(getUploadImage());
					bookPresenter.saveBook();
				}
				break;

			case R.id.fragmentMyStoreAdd_rl_bookPic:
				showChooseBookPicDialog();
				break;

			case R.id.viewer_photo_tv_takeAPhoto:
				Intent intent2 = new Intent("android.media.action.IMAGE_CAPTURE");
				startActivityForResult(intent2, CAMERA_REQUEST_CODE);
				break;

			case R.id.viewer_photo_tv_chooseAPhoto:
				Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				intent.setType("image/*");
				startActivityForResult(intent, IMAGE_REQUEST_CODE);
				break;

			case R.id.viewer_photo_tv_cancel:
				mDialogToChooseImageWay.dismiss();
				break;
			}
		}

	}

	@Override
	public boolean onBackPressed() {
		toFragmentMyStore();
		return true;
	}

	@Override
	public void toFragmentMyStore() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentMyStore", new FragmentMyStore(),
				R.id.container_base, true, true);
	}

	@Override
	public void showErrorMsg(String errorMsg) {
		EqupUtil.showMyToast(getActivity(), errorMsg, 0, Toast.LENGTH_SHORT);
	}

	@Override
	public Book getAddedBook() {
		return addedBook;
	}

	@Override
	public boolean validateInput() {
		if (StringUtil.isEmpty(getBookName()) || StringUtil.isEmpty(getAuthor()) || StringUtil.isEmpty(getPrice())
				|| selectedBitmap == null)
			return false;
		return true;
	}

	@Override
	public void setProgressBarVisible(int visibility) {
		if (visibility == View.VISIBLE) {
			pb_loading.setVisibility(View.VISIBLE);
		}
		if (visibility == View.GONE) {
			pb_loading.setVisibility(View.GONE);
		}
	}

	@Override
	public void showChooseBookPicDialog() {
		mDialogToChooseImageWay = new Dialog(getActivity(), R.style.ActionSheetDialogStyle);
		View dialogContentView = View.inflate(getActivity(), R.layout.viewer_photo, null);
		dialogContentView.findViewById(R.id.viewer_photo_tv_takeAPhoto).setOnClickListener(new ClickListener());
		dialogContentView.findViewById(R.id.viewer_photo_tv_chooseAPhoto).setOnClickListener(new ClickListener());
		dialogContentView.findViewById(R.id.viewer_photo_tv_cancel).setOnClickListener(new ClickListener());

		mDialogToChooseImageWay.setContentView(dialogContentView);
		Window dialogWindow = mDialogToChooseImageWay.getWindow();
		dialogWindow.setGravity(Gravity.BOTTOM);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.y = 20;
		dialogWindow.setAttributes(lp);
		mDialogToChooseImageWay.show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {

		case IMAGE_REQUEST_CODE:
			try {
				selectedBitmap = EqupUtil.getBitmapFromUri(getActivity(), data.getData(), 400f, 800f);
				if (selectedBitmap != null) {
					iv_bookPic.setLayoutParams(
							new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
					iv_bookPic.setBackground(null);
					iv_bookPic.setImageBitmap(selectedBitmap);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case CAMERA_REQUEST_CODE:
			try {
				selectedBitmap = EqupUtil.getBitmapFromUri(getActivity(), data.getData(), 400f, 800f);
				if (selectedBitmap != null) {
					iv_bookPic.setLayoutParams(
							new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
					iv_bookPic.setBackground(null);
					iv_bookPic.setImageBitmap(selectedBitmap);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
		mDialogToChooseImageWay.dismiss();
	}

	@Override
	public String getBookName() {
		return et_bookName.getText().toString();
	}

	@Override
	public String getAuthor() {
		return et_author.getText().toString();
	}

	@Override
	public String getBookDesc() {
		return et_bookDesc.getText().toString();
	}

	@Override
	public String getPrice() {
		return et_price.getText().toString();
	}

	private String getUploadImage() {
		if (selectedBitmap != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			//将bitmap一字节流输出 Bitmap.CompressFormat.PNG 压缩格式，100：压缩率，baos：字节流  
			selectedBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
			try {
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			byte[] buffer = baos.toByteArray();
			return Base64.encodeToString(buffer, 0, buffer.length, Base64.DEFAULT);
		} else {
			return null;
		}
	}

	@Override
	public void setCurrentUser() {
		this.currentUser = (User) CacheUtil.getEntityData(getActivity(), "currentUser", new TypeToken<User>() {
		}.getType());
	}

}
