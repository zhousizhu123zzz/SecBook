package com.zyd.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.zyd.secbooks.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 设备工具类
 * @author 90450
 *
 */
public class EqupUtil {

	private static TextView mToast_tv_msg;

	/**
	 * 重新调整图片的大小
	 * @return
	 */
	public static Bitmap resizeBitmap(Bitmap bitmap, int newW, int newH) {
		Bitmap oldBitmap = bitmap;
		int width = oldBitmap.getWidth();
		int height = oldBitmap.getHeight();
		int newWidth = newW;
		int newHeight = newH;

		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newBitmap = Bitmap.createBitmap(oldBitmap, 0, 0, width, height, matrix, true);
		return newBitmap;
	}

	/**
	 * 自定义Toast,以资源文件,或者字符串作为内容
	 */
	public static void showMyToast(Context context, String msg, int resid, int duration) {
		/* 初始化My Toast View */
		View toastRoot = LayoutInflater.from(context).inflate(R.layout.viewer_toast, null);
		/* 初始化组件 */
		mToast_tv_msg = (TextView) toastRoot.findViewById(R.id.toast_tv_content);
		if (msg == null) {
			mToast_tv_msg.setText(resid);
		} else if (resid == 0) {
			mToast_tv_msg.setText(msg);
		} else {
			mToast_tv_msg.setText("请最少设置一个参数!");
		}
		/* 实例化一个Toast */
		Toast toast = new Toast(context);
		/* 获取屏幕高度 */
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);
		/* 设置Toast的属性 */
		toast.setGravity(Gravity.TOP, 0, ((dm.heightPixels / 5) * 4)); // 设置Toast显示在屏幕的4/5的位置
		toast.setDuration(duration);// 设置显示的时间长短
		toast.setView(toastRoot);// 给Toast设置view
		toast.show();// 显示Toast
	}

	public static int dip2px(@Nullable Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 把Uri转换为Bitmap并进行尺寸和质量的压缩
	 * @param activity
	 * @param uri
	 * @param resizedWidth
	 * @param resizedHeight
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public static Bitmap getBitmapFromUri(Activity activity, Uri uri, float resizedWidth, float resizedHeight)
			throws FileNotFoundException, IOException {
		InputStream in = activity.getContentResolver().openInputStream(uri);//利用内容解析器ContentResolver来打开一个InputStream
		BitmapFactory.Options originalOptions = new BitmapFactory.Options();
		originalOptions.inJustDecodeBounds = true;//设置为true将不返回实际的Bitmap,但是可以查阅Bitmap的信息,有效避免了OOM
		originalOptions.inDither = true; //设置抖动处理
		originalOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//指定解码时的颜色模式,默认也是ARGB_8888
		BitmapFactory.decodeStream(in, null, originalOptions);
		in.close();
		int originalWidth = originalOptions.outWidth;
		int originalHeight = originalOptions.outHeight;
		if (originalWidth == -1 || originalHeight == -1) {
			return null;
		}
		int be = 1; //be=1,表示不缩放,后面会使用到
		if (originalWidth > originalHeight && originalWidth > resizedWidth) {
			be = (int) (originalWidth / resizedWidth);
		} else if (originalHeight > originalWidth && originalHeight > resizedHeight) {
			be = (int) (originalHeight / resizedHeight);
		}
		if (be <= 0) {
			be = 1;
		}

		/**
		 * 尺寸压缩
		 */
		BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inSampleSize = be; //设置缩放比例
		bitmapOptions.inDither = true; //抖动
		bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
		in = activity.getContentResolver().openInputStream(uri);
		Bitmap bitmap = BitmapFactory.decodeStream(in, null, bitmapOptions);
		in.close();
		return compressBitmap(bitmap);
	}

	/**
	 * 对Bitmap进行质量压缩
	 * @param bitmap
	 * @return
	 */
	public static Bitmap compressBitmap(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //100表示不压缩
		int options = 100;
		/**
		 * 循环判断压缩后的质量是否大于100k,大于继续压缩
		 */
		while (baos.toByteArray().length / 1024 > 100) {
			baos.reset();//重置baos,因为下面会往baos里塞值
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//压缩
			options -= 10;
		}
		ByteArrayInputStream baIn = new ByteArrayInputStream(baos.toByteArray());
		return BitmapFactory.decodeStream(baIn, null, null);
	}
}
