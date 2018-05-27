package com.zyd.view.impl;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.zyd.model.User;
import com.zyd.presenter.UserPresenter;
import com.zyd.secbooks.R;
import com.zyd.utils.CacheUtil;
import com.zyd.utils.EqupUtil;
import com.zyd.utils.FragmentBackHandler;
import com.zyd.utils.FragmentController;
import com.zyd.view.FragmentUserInfoView;
import com.zyd.widget.CircleImageView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 用户信息修改界面
 * @author 朱永地
 *
 */
public class FragmentUserInfo extends Fragment implements FragmentUserInfoView, FragmentBackHandler {

	private View rootView;

	/**
	 * Titlebar
	 */
	private LinearLayout ll_back;

	/**
	 * Body
	 */
	private CircleImageView circleImageView_userPic;
	private RelativeLayout rl_userpic, rl_userName, rl_nickName, rl_sex, rl_birthday;
	private TextView tv_userName, tv_nickName, tv_sex, tv_birthday;

	/**
	 * Foot
	 */
	private TextView tv_exitLogin;

	private Dialog mDialogToChooseImageWay;

	private UserPresenter userPresenter = new UserPresenter(this);

	private User currentUser;

	private String selectedBirthday;

	private final int IMAGE_REQUEST_CODE = 1;
	private final int CAMERA_REQUEST_CODE = 2;

	private Bitmap selectedBitmap;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_userinfo, container, false);
		initView();
		return rootView;
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		ll_back = (LinearLayout) rootView.findViewById(R.id.fragmentUserInfo_ll_back);
		rl_userpic = (RelativeLayout) rootView.findViewById(R.id.fragmentUserInfo_rl_userPic);
		rl_nickName = (RelativeLayout) rootView.findViewById(R.id.fragmentUserInfo_rl_nickName);
		rl_userName = (RelativeLayout) rootView.findViewById(R.id.fragmentUserInfo_rl_userName);
		rl_sex = (RelativeLayout) rootView.findViewById(R.id.fragmentUserInfo_rl_sex);
		circleImageView_userPic = (CircleImageView) rootView.findViewById(R.id.fragmentUserInfo_iv_userPic);
		rl_birthday = (RelativeLayout) rootView.findViewById(R.id.fragmentUserInfo_rl_birthday);
		tv_exitLogin = (TextView) rootView.findViewById(R.id.fragmentUserInfo_tv_exitLogin);
		tv_userName = (TextView) rootView.findViewById(R.id.fragmentUserInfo_tv_userName);
		tv_nickName = (TextView) rootView.findViewById(R.id.fragmentUserInfo_tv_nickName);
		tv_sex = (TextView) rootView.findViewById(R.id.fragmentUserInfo_tv_sex);
		tv_birthday = (TextView) rootView.findViewById(R.id.fragmentUserInfo_tv_birthday);

		setUser();
		setValue();

		ll_back.setOnClickListener(new ClickListener());
		rl_userpic.setOnClickListener(new ClickListener());
		rl_nickName.setOnClickListener(new ClickListener());
		rl_userName.setOnClickListener(new ClickListener());
		rl_sex.setOnClickListener(new ClickListener());
		rl_birthday.setOnClickListener(new ClickListener());
		tv_exitLogin.setOnClickListener(new ClickListener());
	}

	/**
	 * 给View设置值
	 */
	public void setValue() {
		setUserPic();
		tv_userName.setText(currentUser.getUserName());
		tv_nickName.setText(currentUser.getNickName());
		tv_sex.setText(currentUser.getSex());
		tv_birthday.setText(currentUser.getBirthday());
		selectedBirthday = String.valueOf(currentUser.getBirthday());
	}

	/**
	 * 点击事件监听内部类
	 * @author 朱永地
	 *
	 */
	private class ClickListener implements View.OnClickListener {

		@Override
		public void onClick(View view) {
			switch (view.getId()) {

			case R.id.fragmentUserInfo_ll_back:
				toFragmentMain();
				break;

			case R.id.fragmentUserInfo_rl_userPic:
				showChangePicDialog();
				break;

			case R.id.fragmentUserInfo_rl_userName:
				EqupUtil.showMyToast(getActivity(), "用户名不能修改哦！", 0, Toast.LENGTH_SHORT);
				break;

			case R.id.fragmentUserInfo_rl_nickName:
				toFragmentUserInfoNickName();
				break;

			case R.id.fragmentUserInfo_rl_sex:
				toFragmentUserInfoSex();
				break;

			case R.id.fragmentUserInfo_rl_birthday:
				showDatePicker();
				break;

			case R.id.fragmentUserInfo_tv_exitLogin:
				toFragmentLogin();
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
		toFragmentMain();
		return true;
	}

	@Override
	public void toFragmentMain() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentMain", new FragmentMain(),
				R.id.container_base, true, true);
	}

	@Override
	public void toFragmentUserInfoSex() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentUserInfoSex",
				new FragmentUserInfoSex(), R.id.container_base, false, true);
	}

	@Override
	public void toFragmentUserInfoNickName() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentUserInfoNickName",
				new FragmentUserInfoNickName(), R.id.container_base, false, true);
	}

	@Override
	public void toFragmentLogin() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentLogin", new FragmentLogin(),
				R.id.container_base, false, true);
	}

	@Override
	public void setUser() {
		this.currentUser = (User) CacheUtil.getEntityData(getActivity(), "currentUser", new TypeToken<User>() {
		}.getType());
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		if (!hidden) {
			FragmentCropImage fragmentCropImage = (FragmentCropImage) FragmentController
					.getFragmentByTag(getActivity().getSupportFragmentManager(), "FragmentCropImageView");
			if (fragmentCropImage != null) {
				Bitmap bitmap = fragmentCropImage.getCropBitmap();
				if (bitmap != null) {
					circleImageView_userPic.setImageBitmap(bitmap);
				}
			}
			setUser();
			setValue(); //重新设置值
		}
	}

	@Override
	public void showDatePicker() {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
		dialogBuilder.setTitle("请选择时间");
		DatePicker datePicker = new DatePicker(getActivity());
		datePicker.init(1995, 6, 6, new OnDateChangedListener() {

			@Override
			public void onDateChanged(DatePicker arg0, int year, int month, int day) {
				selectedBirthday = year + "年" + (month + 1) + "月" + day + "日";
			}
		});
		dialogBuilder.setView(datePicker);
		dialogBuilder.setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				if (!selectedBirthday.equals(tv_birthday.getText().toString())) {
					tv_birthday.setText(selectedBirthday);
					currentUser.setBirthday(selectedBirthday);
					userPresenter.save();
				}
			}
		});
		dialogBuilder.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});
		dialogBuilder.create().show();
	}

	@Override
	public void saveUser() {
		CacheUtil.setEntityData(getActivity(), "currentUser", currentUser);
	}

	@Override
	public User getCurrentUser() {
		return currentUser;
	}

	@Override
	public void showErrorMsg(String errorMsg) {
		EqupUtil.showMyToast(getActivity(), errorMsg, 0, Toast.LENGTH_SHORT);
	}

	@Override
	public void toFragmentCropImage() {
		FragmentController.show(getActivity().getSupportFragmentManager(), "FragmentCropImage", new FragmentCropImage(),
				R.id.container_base, false, true);
	}

	@Override
	public void showChangePicDialog() {
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
					toFragmentCropImage();
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
					toFragmentCropImage();
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
	public Bitmap getSelectedBitmap() {
		return selectedBitmap;
	}

	private void setUserPic() {
		if (currentUser != null && currentUser.getUserPic() != null) {
			Picasso.with(getActivity()).load(currentUser.getUserPic()).into(circleImageView_userPic);
		}
	}
}
