package com.zyd.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.zyd.model.Address;
import com.zyd.model.Advertise;
import com.zyd.model.Book;
import com.zyd.model.BookBigType;
import com.zyd.model.BookSmallType;
import com.zyd.model.MData;
import com.zyd.model.Order;
import com.zyd.model.User;

public class StringUtil {

	/**
	 * 是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if ("".equals(str.trim()) || str == null)
			return true;
		return false;
	}

	/**
	 * 删除0-end的集合数据
	 * @param <T>
	 */
	public static <T> List<T> remove(List<T> list, int end) {
		for (int i = 0; i < end; i++) {
			list.remove(0);
		}
		return list;
	}

	public static boolean isExistInStringList(List<String> dataList, String value) {
		if (dataList != null) {
			Iterator<String> it = dataList.iterator();
			while (it.hasNext()) {
				if (value.equals(it.next()))
					return true;
			}
		}
		return false;
	}

	/**
	 * 将User实体信息组装成Map参数
	 * @param user
	 * @return
	 */
	public static Map<String, String> getUserMap(User user) {
		Map<String, String> map = null;
		if (user != null) {
			map = new HashMap<String, String>();
			if (user.getUserId() != 0) {
				map.put("userId", String.valueOf(user.getUserId()));
			}
			if (user.getUserName() != null) {
				map.put("userName", user.getUserName());
			}
			if (user.getPassword() != null) {
				map.put("password", user.getPassword());
			}
			if (user.getNickName() != null) {
				map.put("nickName", user.getNickName());
			}
			if (user.getRealName() != null) {
				map.put("realName", user.getRealName());
			}
			if (user.getSex() != null) {
				map.put("sex", user.getSex());
			}
			if (user.getBirthday() != null) {
				map.put("birthday", user.getBirthday());
			}
			if (user.getPoint() != null) {
				map.put("point", user.getPoint());
			}
			if (user.getBalance() != null) {
				map.put("balance", user.getBalance());
			}
			if (user.getGrade() != 0) {
				map.put("grade", String.valueOf(user.getGrade()));
			}
			if (user.getGradeName() != null) {
				map.put("gradeName", user.getGradeName());
			}
			if (user.getFlag() != 0) {
				map.put("flag", String.valueOf(user.getFlag()));
			}
			if (user.getUserPic() != null) {
				map.put("userPic", String.valueOf(user.getUserPic()));
			}
		}
		return map;
	}

	/**
	 * 将Address实体信息组装成Map参数
	 * @param user
	 * @return
	 */
	public static Map<String, String> getAddressMap(Address address) {
		Map<String, String> map = null;
		if (address != null) {
			map = new HashMap<String, String>();
			if (address.getAddressId() != 0) {
				map.put("addressId", String.valueOf(address.getAddressId()));
			}
			if (address.getReceiptName() != null) {
				map.put("receiptName", address.getReceiptName());
			}
			if (address.getTelNo() != null) {
				map.put("telNo", address.getTelNo());
			}
			if (address.getAddress() != null) {
				map.put("address", address.getAddress());
			}
			map.put("isIndex", String.valueOf(address.isIndex()));
			if (address.getUserId() != 0) {
				map.put("userId", String.valueOf(address.getUserId()));
			}
		}
		return map;
	}

	/**
	 * 将Address实体信息组装成Map参数
	 * @param user
	 * @return
	 */
	public static Map<String, String> getOrderMap(Order order) {
		Map<String, String> map = null;
		if (order != null) {
			map = new HashMap<String, String>();
			if (order.getUserId() != 0) {
				map.put("userId", String.valueOf(order.getUserId()));
			}
			if (order.getBook().getBookId() != 0) {
				map.put("bookId", String.valueOf(order.getBook().getBookId()));
			}
			if (order.getCost() != null) {
				map.put("cost", order.getCost());
			}
			if (order.getCounts() != 0) {
				map.put("counts", String.valueOf(order.getCounts()));
			}
			if (order.getCreateTime() != null) {
				map.put("createTime", order.getCreateTime());
			}
			if (order.getStatus() != 0) {
				map.put("status", String.valueOf(order.getStatus()));
			}
		}
		return map;
	}

	/**
	 * 将Advertise的List提取其中的url转换成String的List
	 * @param adsList
	 * @return
	 */
	public static List<String> parseAdsListToStringList(List<Advertise> adsList) {
		if (adsList != null) {
			List<String> resultList = new ArrayList<String>();
			Iterator<Advertise> it = adsList.iterator();
			while (it.hasNext()) {
				resultList.add(((Advertise) it.next()).getUrl());
			}
			return resultList;
		} else {
			return null;
		}
	}

	public static List<String> parseMDataListToStringList(List<MData> mDataList) {
		if (mDataList != null) {
			List<String> resultList = new ArrayList<String>();
			Iterator<MData> it = mDataList.iterator();
			while (it.hasNext()) {
				resultList.add(((MData) it.next()).getName());
			}
			return resultList;
		} else {
			return null;
		}
	}

	public static List<String> parseBookBigTypeListToStringList(List<BookBigType> bookBigTypeList) {
		if (bookBigTypeList != null) {
			List<String> resultList = new ArrayList<String>();
			Iterator<BookBigType> it = bookBigTypeList.iterator();
			while (it.hasNext()) {
				resultList.add(((BookBigType) it.next()).getName());
			}
			return resultList;
		} else {
			return null;
		}
	}

	public static List<String> parseBookSmallTypeListToStringList(List<BookSmallType> bookSmallTypeList) {
		if (bookSmallTypeList != null) {
			List<String> resultList = new ArrayList<String>();
			resultList.add("全部");
			Iterator<BookSmallType> it = bookSmallTypeList.iterator();
			while (it.hasNext()) {
				resultList.add(((BookSmallType) it.next()).getName());
			}
			return resultList;
		} else {
			return null;
		}
	}

	public static List<String> parseBookListToStringList(List<Book> bookList) {
		if (bookList != null) {
			List<String> resultList = new ArrayList<String>();
			Iterator<Book> it = bookList.iterator();
			while (it.hasNext()) {
				resultList.add(((Book) it.next()).getBookName());
			}
			return resultList;
		} else {
			return null;
		}
	}

	public static List<Order> limitOrderList(List<Order> orderList, int status) {
		List<Order> resultList = null;
		if (orderList != null && orderList.size() != 0) {
			resultList = new ArrayList<Order>();
			for (int i = 0; i < orderList.size(); i++) {
				if (orderList.get(i).getStatus() == status)
					resultList.add(orderList.get(i));
			}
		}
		return resultList;
	}

	/**
	 * 将所有图书集合，摘取为相应的tag的图书集合
	 * @param bookList
	 * @param tag
	 * @return
	 */
	public static List<Book> limitBookList(List<Book> bookList, String tag) {
		List<Book> resultList = null;
		if (bookList != null) {
			if ("recom".equals(tag)) {
				resultList = new ArrayList<Book>();
				Random random = new Random();
				for (int i = 0; i < 3; i++) {
					resultList.add(bookList.get(random.nextInt(bookList.size())));
				}
			}
			if ("hot".equals(tag)) {
				resultList = new ArrayList<Book>();
				for (int i = 0; i < bookList.size(); i++) {
					if (bookList.get(i).getIsHot() == 1) {
						resultList.add(bookList.get(i));
					}
					if (resultList.size() == 3)
						return resultList;
				}
			}
			if ("special".equals(tag)) {
				resultList = new ArrayList<Book>();
				for (int i = 0; i < bookList.size(); i++) {
					if (bookList.get(i).getIsSpecial() == 1) {
						resultList.add(bookList.get(i));
					}
					if (resultList.size() == 3)
						return resultList;
				}
			}
			if ("new".equals(tag)) {
				resultList = new ArrayList<Book>();
				for (int i = bookList.size() - 1; i >= 0; i--) {
					resultList.add(bookList.get(i));
					if (resultList.size() == 3)
						return resultList;
				}
			}
		}
		return resultList;
	}

	/**
	 * 将所有图书集合，摘取为相应的tag的图书集合
	 * @param bookList
	 * @param tag
	 * @return
	 */
	public static List<Book> limitBookList2(List<Book> bookList, String tag) {
		List<Book> resultList = null;
		if (bookList != null) {
			if ("hot".equals(tag)) {
				resultList = new ArrayList<Book>();
				for (int i = 0; i < bookList.size(); i++) {
					if (bookList.get(i).getIsHot() == 1) {
						resultList.add(bookList.get(i));
					}
				}
			}
			if ("special".equals(tag)) {
				resultList = new ArrayList<Book>();
				for (int i = 0; i < bookList.size(); i++) {
					if (bookList.get(i).getIsSpecial() == 1) {
						resultList.add(bookList.get(i));
					}
				}
			}
			if ("new".equals(tag)) {
				resultList = new ArrayList<Book>();
				for (int i = bookList.size() - 1; i >= 0; i--) {
					resultList.add(bookList.get(i));
				}
			}
		}
		return resultList;
	}

	public static Map<String, String> getBookMap(Book book) {
		Map<String, String> map = null;
		if (book != null) {
			map = new HashMap<String, String>();
			if (book.getUserId() != 0) {
				map.put("userId", String.valueOf(book.getUserId()));
			}
			if (book.getBookId() != 0) {
				map.put("bookId", String.valueOf(book.getBookId()));
			}
			if (book.getBookName() != null) {
				map.put("bookName", book.getBookName());
			}
			if (book.getAuthor() != null) {
				map.put("author", book.getAuthor());
			}
			if (book.getBookDesc() != null) {
				map.put("bookDesc", book.getBookDesc());
			}
			if (book.getPrice() != null) {
				map.put("price", book.getPrice());
			}
			if (book.getBookPic() != null) {
				map.put("bookPic", book.getBookPic());
			}
		}
		return map;
	}

}
