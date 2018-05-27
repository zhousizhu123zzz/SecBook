package com.zyd.utils;

import java.util.ArrayList;
import java.util.List;

import com.zyd.model.Address;
import com.zyd.model.Advertise;
import com.zyd.model.Book;
import com.zyd.model.BookBigType;
import com.zyd.model.BookSmallType;
import com.zyd.model.Coupon;
import com.zyd.model.MData;
import com.zyd.model.Order;
import com.zyd.model.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSONUtils {

	/**
	 * 解析MData实体的数据
	 * @param jsonData
	 * @return
	 */
	public static MData parseMData(String jsonData) {
		JSONObject rootJson = JSONObject.fromObject(jsonData);
		if ("false".equals(rootJson.getString("success")))
			return null;
		JSONArray bookDataArray = rootJson.getJSONArray("bookData");
		JSONArray adsArray = rootJson.getJSONArray("advertiseData");
		List<Book> bookList = new ArrayList<Book>();
		List<Advertise> adsList = new ArrayList<Advertise>();
		for (int i = 0; i < bookDataArray.size(); i++) {
			Book book = new Book();
			JSONObject bookJson = bookDataArray.getJSONObject(i);
			book.setBookId(bookJson.getInt("bookId"));
			book.setBookName(bookJson.getString("bookName"));
			book.setBookDesc(bookJson.getString("bookDesc"));
			book.setBookPic(bookJson.getString("bookPic"));
			book.setHotTime(bookJson.getString("hotTime"));
			book.setIsHot(bookJson.getInt("isHot"));
			book.setIsSpecial(bookJson.getInt("isSpecial"));
			book.setPrice(bookJson.getString("price"));
			book.setSpecialTime(bookJson.getString("specialTime"));
			book.setStorage(bookJson.getInt("storage"));
			JSONObject smallTypeJson = bookJson.getJSONObject("smallType");
			BookSmallType bookSmallType = new BookSmallType();
			bookSmallType.setName(smallTypeJson.getString("name"));
			bookSmallType.setRemark(smallTypeJson.getString("remark"));
			bookSmallType.setSmallTypeId(smallTypeJson.getInt("smallTypeId"));
			book.setBookSmallType(bookSmallType);
			bookList.add(book);
		}
		for (int i = 0; i < adsArray.size(); i++) {
			JSONObject adsObject = adsArray.getJSONObject(i);
			Advertise advertise = new Advertise();
			advertise.setAdvertiseId(adsObject.getInt("advertiseId"));
			advertise.setUrl(adsObject.getString("url"));
			adsList.add(advertise);
		}
		MData mData = new MData();
		mData.setBookList(bookList);
		mData.setAdvertiseList(adsList);
		return mData;
	}

	/**
	 * 解析User实体的JSON数据
	 * @param jsonData
	 * @return
	 */
	public static User parseUser(String jsonData) {
		JSONObject rootJson = JSONObject.fromObject(jsonData);
		if ("false".equals(rootJson.getString("success")))
			return null;
		JSONObject userJson = rootJson.getJSONObject("user");
		User user = new User();
		user.setUserId(userJson.getInt("userId"));
		user.setUserName(userJson.getString("userName"));
		user.setPassword(userJson.getString("password"));
		user.setNickName(userJson.getString("nickName"));
		user.setRealName(userJson.getString("realName"));
		user.setSex(userJson.getString("sex"));
		user.setBirthday(userJson.getString("birthday"));
		user.setPoint(userJson.getString("point"));
		user.setBalance(userJson.getString("balance"));
		user.setGrade(userJson.getInt("grade"));
		user.setGradeName(userJson.getString("gradeName"));
		user.setFlag(userJson.getInt("flag"));
		user.setUserPic(userJson.getString("userPic"));
		JSONArray bookListArray = userJson.getJSONArray("bookList");
		List<Book> bookList = new ArrayList<Book>();
		for (int i = 0; i < bookListArray.size(); i++) {
			Book book = new Book();
			JSONObject bookJson = bookListArray.getJSONObject(i);
			book.setBookId(bookJson.getInt("bookId"));
			book.setBookName(bookJson.getString("bookName"));
			book.setBookDesc(bookJson.getString("bookDesc"));
			book.setBookPic(bookJson.getString("bookPic"));
			book.setHotTime(bookJson.getString("hotTime"));
			book.setAuthor(bookJson.getString("author"));
			book.setIsHot(bookJson.getInt("isHot"));
			book.setIsSpecial(bookJson.getInt("isSpecial"));
			book.setPrice(bookJson.getString("price"));
			book.setSpecialTime(bookJson.getString("specialTime"));
			book.setStorage(bookJson.getInt("storage"));
			JSONObject smallTypeJson = bookJson.getJSONObject("smallType");
			BookSmallType bookSmallType = new BookSmallType();
			bookSmallType.setName(smallTypeJson.getString("name"));
			bookSmallType.setRemark(smallTypeJson.getString("remark"));
			bookSmallType.setSmallTypeId(smallTypeJson.getInt("smallTypeId"));
			book.setBookSmallType(bookSmallType);
			bookList.add(book);
		}
		user.setBookList(bookList);
		return user;
	}

	public static Book parseBook(String jsonData) {
		JSONObject rootJson = JSONObject.fromObject(jsonData);
		if ("false".equals(rootJson.getString("success")))
			return null;
		JSONObject bookJson = rootJson.getJSONObject("data");
		Book book = new Book();
		book.setBookId(bookJson.getInt("bookId"));
		book.setBookName(bookJson.getString("bookName"));
		book.setBookDesc(bookJson.getString("bookDesc"));
		book.setBookPic(bookJson.getString("bookPic"));
		book.setHotTime(bookJson.getString("hotTime"));
		book.setAuthor(bookJson.getString("author"));
		book.setIsHot(bookJson.getInt("isHot"));
		book.setIsSpecial(bookJson.getInt("isSpecial"));
		book.setPrice(bookJson.getString("price"));
		book.setSpecialTime(bookJson.getString("specialTime"));
		book.setStorage(bookJson.getInt("storage"));
		JSONObject smallTypeJson = bookJson.getJSONObject("smallType");
		BookSmallType bookSmallType = new BookSmallType();
		bookSmallType.setName(smallTypeJson.getString("name"));
		bookSmallType.setRemark(smallTypeJson.getString("remark"));
		bookSmallType.setSmallTypeId(smallTypeJson.getInt("smallTypeId"));
		book.setBookSmallType(bookSmallType);
		return book;
	}

	public static List<Book> parseBookList(String jsonData) {
		List<Book> resultList = null;
		JSONObject rootJson = JSONObject.fromObject(jsonData);
		if ("false".equals(rootJson.getString("success")))
			return null;
		if ("null".equals(rootJson.getString("data")))
			return null;
		JSONArray bigTypeArray = rootJson.getJSONArray("data");
		resultList = new ArrayList<Book>();
		for (int i = 0; i < bigTypeArray.size(); i++) {
			Book book = new Book();
			JSONObject bookJson = bigTypeArray.getJSONObject(i);
			book.setBookId(bookJson.getInt("bookId"));
			book.setBookName(bookJson.getString("bookName"));
			book.setBookDesc(bookJson.getString("bookDesc"));
			book.setBookPic(bookJson.getString("bookPic"));
			book.setHotTime(bookJson.getString("hotTime"));
			book.setAuthor(bookJson.getString("author"));
			book.setIsHot(bookJson.getInt("isHot"));
			book.setIsSpecial(bookJson.getInt("isSpecial"));
			book.setPrice(bookJson.getString("price"));
			book.setSpecialTime(bookJson.getString("specialTime"));
			book.setStorage(bookJson.getInt("storage"));
			JSONObject smallTypeJson = bookJson.getJSONObject("smallType");
			BookSmallType bookSmallType = new BookSmallType();
			bookSmallType.setName(smallTypeJson.getString("name"));
			bookSmallType.setRemark(smallTypeJson.getString("remark"));
			bookSmallType.setSmallTypeId(smallTypeJson.getInt("smallTypeId"));
			book.setBookSmallType(bookSmallType);
			resultList.add(book);
		}
		return resultList;
	}

	/**
	 * 解析BookBigTypeList的JSON数据
	 * @param jsonData
	 * @return
	 */
	public static List<BookBigType> parseBookBigTypeList(String jsonData) {
		List<BookBigType> resultList = null;
		JSONObject rootJson = JSONObject.fromObject(jsonData);
		if ("false".equals(rootJson.getString("success")))
			return null;
		JSONArray bigTypeArray = rootJson.getJSONArray("data");
		resultList = new ArrayList<BookBigType>();
		for (int i = 0; i < bigTypeArray.size(); i++) {
			BookBigType bookBigType = new BookBigType();
			JSONObject bigTypeJson = bigTypeArray.getJSONObject(i);
			bookBigType.setBookBigTypeId(bigTypeJson.getInt("bookBigTypeId"));
			bookBigType.setName(bigTypeJson.getString("name"));
			bookBigType.setRemark(bigTypeJson.getString("remark"));
			JSONArray smallTypeArray = bigTypeJson.getJSONArray("smallTypeList");
			List<BookSmallType> smallTypeList = new ArrayList<BookSmallType>();
			for (int j = 0; j < smallTypeArray.size(); j++) {
				BookSmallType smallType = new BookSmallType();
				JSONObject smallTypeJson = smallTypeArray.getJSONObject(j);
				smallType.setSmallTypeId(smallTypeJson.getInt("smallTypeId"));
				smallType.setName(smallTypeJson.getString("name"));
				smallType.setRemark(smallTypeJson.getString("remark"));
				smallTypeList.add(smallType);
			}
			bookBigType.setSmallTypeList(smallTypeList);
			resultList.add(bookBigType);
		}
		return resultList;
	}

	/**
	 * 解析Advertise的JSON数据
	 * @param jsonData
	 * @return
	 */
	public static List<Advertise> parseAdsList(String jsonData) {
		List<Advertise> resultList = null;
		JSONObject rootJson = JSONObject.fromObject(jsonData);
		if ("false".equals(rootJson.getString("success")))
			return null;
		JSONArray adsArray = rootJson.getJSONArray("data");
		resultList = new ArrayList<Advertise>();
		for (int i = 0; i < adsArray.size(); i++) {
			JSONObject adsObject = adsArray.getJSONObject(i);
			Advertise advertise = new Advertise();
			advertise.setAdvertiseId(adsObject.getInt("advertiseId"));
			advertise.setUrl(adsObject.getString("url"));
			resultList.add(advertise);
		}
		return resultList;
	}

	/**
	 * 解析Coupon的JSON数据
	 * @param jsonData
	 * @return
	 */
	public static List<Coupon> parseCouponList(String jsonData) {
		List<Coupon> resultList = null;
		JSONObject rootJson = JSONObject.fromObject(jsonData);
		if ("false".equals(rootJson.getString("success")))
			return null;
		if ("null".equals(rootJson.getString("data")))
			return null;
		JSONArray adsArray = rootJson.getJSONArray("data");
		resultList = new ArrayList<Coupon>();
		for (int i = 0; i < adsArray.size(); i++) {
			JSONObject couponObject = adsArray.getJSONObject(i);
			Coupon coupon = new Coupon();
			coupon.setCouponId(couponObject.getInt("couponId"));
			coupon.setCouponCondition(couponObject.getString("couponCondition"));
			coupon.setCouponMsg(couponObject.getString("couponMsg"));
			coupon.setCouponPrice(couponObject.getString("couponPrice"));
			resultList.add(coupon);
		}
		return resultList;
	}

	/**
	 * 解析Address的JSON数据
	 * @param jsonData
	 * @return
	 */
	public static List<Order> parseOrderList(String jsonData) {
		List<Order> resultList = null;
		JSONObject rootJson = JSONObject.fromObject(jsonData);
		if ("false".equals(rootJson.getString("success")))
			return null;
		if ("null".equals(rootJson.getString("data")))
			return null;
		JSONArray adsArray = rootJson.getJSONArray("data");
		resultList = new ArrayList<Order>();
		for (int i = 0; i < adsArray.size(); i++) {
			JSONObject orderObject = adsArray.getJSONObject(i);
			Order order = new Order();
			order.setOrderId(Integer.valueOf(orderObject.getString("orderId")));
			order.setCost(orderObject.getString("cost"));
			order.setCreateTime(orderObject.getString("createTime"));
			order.setOrderNo(orderObject.getString("orderNo"));
			order.setStatus(Integer.valueOf(orderObject.getString("status")));
			resultList.add(order);
			JSONObject bookJson = orderObject.getJSONObject("book");
			Book book = new Book();
			book.setBookId(Integer.valueOf(bookJson.getString("bookId")));
			book.setBookName(bookJson.getString("bookName"));
			book.setBookDesc(bookJson.getString("bookDesc"));
			book.setBookPic(bookJson.getString("bookPic"));
			book.setHotTime(bookJson.getString("hotTime"));
			book.setAuthor(bookJson.getString("author"));
			book.setIsHot(bookJson.getInt("isHot"));
			book.setIsSpecial(bookJson.getInt("isSpecial"));
			book.setPrice(bookJson.getString("price"));
			book.setSpecialTime(bookJson.getString("specialTime"));
			book.setStorage(bookJson.getInt("storage"));
			JSONObject bookSmallTypeJson = bookJson.getJSONObject("smallType");
			BookSmallType bookSmallType = new BookSmallType();
			bookSmallType.setName(bookSmallTypeJson.getString("name"));
			bookSmallType.setRemark(bookSmallTypeJson.getString("remark"));
			bookSmallType.setSmallTypeId(bookSmallTypeJson.getInt("smallTypeId"));
			book.setBookSmallType(bookSmallType);
			order.setBook(book);
		}
		return resultList;
	}

	/**
	 * 解析Address的JSON数据
	 * @param jsonData
	 * @return
	 */
	public static List<Address> parseAddressList(String jsonData) {
		List<Address> resultList = null;
		JSONObject rootJson = JSONObject.fromObject(jsonData);
		if ("false".equals(rootJson.getString("success")))
			return null;
		if ("null".equals(rootJson.getString("data")))
			return null;
		JSONArray adsArray = rootJson.getJSONArray("data");
		resultList = new ArrayList<Address>();
		for (int i = 0; i < adsArray.size(); i++) {
			JSONObject addressObject = adsArray.getJSONObject(i);
			Address address = new Address();
			address.setAddressId(addressObject.getInt("addressId"));
			address.setAddress(addressObject.getString("name"));
			address.setIndex(addressObject.getBoolean("addressIndex"));
			address.setReceiptName(addressObject.getString("receiptName"));
			address.setTelNo(addressObject.getString("telNo"));
			resultList.add(address);
		}
		return resultList;
	}

	/**
	 * 解析只有SUCCESS的JSON数据，判断是否成功
	 * @param jsonData
	 * @return
	 */
	public static boolean parseIsSuccess(String jsonData) {
		JSONObject rootJson = JSONObject.fromObject(jsonData);
		if ("false".equals(rootJson.getString("success")))
			return false;
		return true;
	}

	/**
	 * 判断字符串是否为JSON格式
	 * @param data
	 * @return
	 */
	public static boolean isJSON(String data) {
		try {
			JSONObject.fromObject(data);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
