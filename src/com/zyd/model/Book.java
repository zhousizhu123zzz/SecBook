package com.zyd.model;

import java.io.Serializable;

public class Book implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int bookId;
	private String bookName;
	private String price;
	private int storage;
	private String bookPic;
	private String bookDesc;
	private int isHot;
	private String hotTime;
	private int isSpecial;
	private String specialTime;
	private String author;

	private int userId;
	private BookSmallType bookSmallType;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public BookSmallType getBookSmallType() {
		return bookSmallType;
	}

	public void setBookSmallType(BookSmallType bookSmallType) {
		this.bookSmallType = bookSmallType;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getStorage() {
		return storage;
	}

	public void setStorage(int storage) {
		this.storage = storage;
	}

	public String getBookPic() {
		return bookPic;
	}

	public void setBookPic(String bookPic) {
		this.bookPic = bookPic;
	}

	public String getBookDesc() {
		return bookDesc;
	}

	public void setBookDesc(String bookDesc) {
		this.bookDesc = bookDesc;
	}

	public int getIsHot() {
		return isHot;
	}

	public void setIsHot(int isHot) {
		this.isHot = isHot;
	}

	public String getHotTime() {
		return hotTime;
	}

	public void setHotTime(String hotTime) {
		this.hotTime = hotTime;
	}

	public int getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(int isSpecial) {
		this.isSpecial = isSpecial;
	}

	public String getSpecialTime() {
		return specialTime;
	}

	public void setSpecialTime(String specialTime) {
		this.specialTime = specialTime;
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", bookName=" + bookName + ", price=" + price + ", storage=" + storage
				+ ", bookPic=" + bookPic + ", bookDesc=" + bookDesc + ", isHot=" + isHot + ", hotTime=" + hotTime
				+ ", isSpecial=" + isSpecial + ", specialTime=" + specialTime + ", author=" + author
				+ ", bookSmallType=" + bookSmallType + "]";
	}

}
