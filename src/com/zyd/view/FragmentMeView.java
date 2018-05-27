package com.zyd.view;

import com.zyd.model.User;

public interface FragmentMeView extends BaseFragmentView {

	public void toFragmentHome(); //��ת����ҳ����

	public void toFragmentUserInfo(); //��ת���û���Ϣ�޸Ľ���

	public void toFragmentCoupon(); //��ת���Ż�ȯ����

	public void toFragmentAddress(); //��ת����ַ�������

	public void toFragmentLogin(); //δ��¼����ת����¼����

	public void toFragmentMyStore(); //��ת���ҵĵ��̽���

	public void toFragmentCollection();//��ת���ҵ��ղؽ���

	public void toFragmentOrder(); //��ת���ҵĶ�������

	public void validateLogin(); //��֤�Ƿ��¼

	public void setUser(); //��ȡ�û���Ϣ,�ӻ����л�ȡ

	public void showIsToLoginDialog(); //��ʾ�Ƿ�ǰ����¼����ʾ�� 

	public User getUser(); //�ӻ����л�ȡ�û����ݣ���Ϊ��ת����Fragment�Ĳ���

}
