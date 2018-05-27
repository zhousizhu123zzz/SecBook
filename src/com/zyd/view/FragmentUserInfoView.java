package com.zyd.view;

import com.zyd.model.User;

import android.graphics.Bitmap;

public interface FragmentUserInfoView {

	public void toFragmentMain(); //����������

	public void toFragmentUserInfoSex(); //�����޸��Ա����

	public void toFragmentUserInfoNickName(); //�����ǳ��޸Ľ���

	public void toFragmentCropImage();//����ͼƬ�ü�

	public void showChangePicDialog(); //��ʾ����ͷ�������ѡ�����ѡ����ʾ��

	public void showDatePicker();//��ʾ���ڿؼ�

	public void saveUser();

	public void toFragmentLogin(); //�˳���¼,��ת����¼����

	public void setUser(); //�ӻ��������õ�ǰ�û�

	public User getCurrentUser();

	public void showErrorMsg(String errorMsg);

	public Bitmap getSelectedBitmap();

}
