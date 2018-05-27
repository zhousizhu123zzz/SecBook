package com.zyd.view;

import com.zyd.model.User;

public interface FragmentUserInfoSexView {

	public void toFragmentUserInfo(); //�����û���Ϣ�޸Ľ���

	public void setUser(); //�ӻ����������û���Ϣ

	public User getUser();

	public void saveUser(); //�����޸���Ϣ

	public void showErrorMsg(String errorMsg); //��ʾ������Ϣ���������������ӣ��������쳣

	public boolean isInfoChanged(); //�û��Ƿ��޸����Ա�

	public void notifyFragmentUserInfo(); //֪ͨFragmentUserInfo�ı�����

}
