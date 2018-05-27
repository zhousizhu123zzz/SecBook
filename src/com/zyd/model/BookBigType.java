package com.zyd.model;

import java.util.ArrayList;
import java.util.List;

public class BookBigType {

	private int bookBigTypeId;
	private String name;
	private String remark;

	private List<BookSmallType> smallTypeList = new ArrayList<BookSmallType>();

	public int getBookBigTypeId() {
		return bookBigTypeId;
	}

	public void setBookBigTypeId(int bookBigTypeId) {
		this.bookBigTypeId = bookBigTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<BookSmallType> getSmallTypeList() {
		return smallTypeList;
	}

	public void setSmallTypeList(List<BookSmallType> smallTypeList) {
		this.smallTypeList = smallTypeList;
	}

}
