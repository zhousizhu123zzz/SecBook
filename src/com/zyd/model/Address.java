package com.zyd.model;

public class Address {

	private int addressId;
	private String address;//��ַ��Ϣ
	private String telNo; //�ջ��绰
	private String receiptName;//�ջ���
	private boolean isIndex = false;// �Ƿ�ΪĬ�ϵ�ַ

	private int userId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public boolean isIndex() {
		return isIndex;
	}

	public void setIndex(boolean isIndex) {
		this.isIndex = isIndex;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getReceiptName() {
		return receiptName;
	}

	public void setReceiptName(String receiptName) {
		this.receiptName = receiptName;
	}

	@Override
	public String toString() {
		return "Address [addressId=" + addressId + ", address=" + address + ", telNo=" + telNo + ", receiptName="
				+ receiptName + ", isIndex=" + isIndex + "]";
	}

}
