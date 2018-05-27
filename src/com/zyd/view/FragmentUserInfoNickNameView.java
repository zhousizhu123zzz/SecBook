package com.zyd.view;

import com.zyd.model.User;

public interface FragmentUserInfoNickNameView {

	public void toFragmentUserInfo(); //�����û���Ϣ�޸Ľ���

	public String getNickName(); //��ȡ�û�������ǳ�

	public void setUser(); //�ӻ����������û���Ϣ

	public User getUser();

	public void saveUser(); //�����޸���Ϣ

	public void showErrorMsg(String errorMsg); //��ʾ������Ϣ���������������ӣ��������쳣

	public boolean isInfoChanged(); //�û��Ƿ��޸����ǳ�

	public void notifyFragmentUserInfo(); //֪ͨFragmentUserInfo���ݸı�
}
