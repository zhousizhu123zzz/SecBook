package com.zyd.constant;

public final class Constant {

	public static final class msg {
		public static final String ERROR_SAVE = "保存失败！";
		public static final String SUCCESS_SAVE = "保存成功！";
		public static final String ERROR_SERVER = "服务器异常！";
		public static final String ERROR_USERNAME_PASSWORD = "用户名或者密码错误！";
		public static final String ERROR_NOLOGIN = "点击登录";
		public static final String ERROR_NOFULL = "信息要填写完整哦！";
		public static final String ERROR_DELETE = "删除失败！请重试！";
		public static final String SUCCESS_DELETE = "删除成功!";
		public static final String ERROR_NOADDRESS = "您还没有任何收货地址，赶快点击添加吧!";
		public static final String ERROR_NOCOUPON = "无可用优惠券";
		public static final String ERROR_NOSEARCHTEXT = "请输入搜索内容!";
	}

	public static final class net {
		public static final String NET_USER_LOGIN = "http://secbook.nat123.net/SecBookServer/user_login.action";
		public static final String NET_USER_SAVE = "http://secbook.nat123.net/SecBookServer/user_save.action";
		public static final String NET_BIGTYPE_LIST = "http://secbook.nat123.net/SecBookServer/bigType_list.action";
		public static final String NET_ADVERTISE_LIST = "http://secbook.nat123.net/SecBookServer/ads_list.action";
		public static final String NET_ADDRESS_LIST = "http://secbook.nat123.net/SecBookServer/address_list.action";
		public static final String NET_ADDRESS_SAVE = "http://secbook.nat123.net/SecBookServer/address_save.action";
		public static final String NET_ADDRESS_DELETE = "http://secbook.nat123.net/SecBookServer/address_delete.action";
		public static final String NET_COUPON_LIST = "http://secbook.nat123.net/SecBookServer/app_listCouponData.action";
		public static final String NET_BOOK_LIST = "http://secbook.nat123.net/SecBookServer/books_list2.action";
		public static final String NET_HOME_LIST = "http://secbook.nat123.net/SecBookServer/app_listHomeData.action";
		public static final String NET_BOOK_GETBOOK = "http://secbook.nat123.net/SecBookServer/app_listBookByBookId.action";
		public static final String NET_BOOK_SEARCHBOOK = "http://secbook.nat123.net/SecBookServer/app_listBookByBook.action";
		public static final String NET_BOOK_LISTBOOKBYSMALLTYPEID = "http://secbook.nat123.net/SecBookServer/app_listBookBySmallTypeId.action";
		public static final String NET_BOOK_LISTBOOKBYBIGTYPEID = "http://secbook.nat123.net/SecBookServer/app_listBookByBigTypeId.action";
		public static final String NET_ORDER_LIST = "http://secbook.nat123.net/SecBookServer/app_listOrderByUserId.action";
		public static final String NET_ORDER_ADD = "http://secbook.nat123.net/SecBookServer/app_saveOrder.action";
		public static final String NET_BOOK_SAVEBOOK = "http://secbook.nat123.net/SecBookServer/app_saveBook.action";
		public static final String NET_BOOK_LISTBOOKBYBOOKIDS = "http://secbook.nat123.net/SecBookServer/app_listBooksByBookId.action";
	}

}
