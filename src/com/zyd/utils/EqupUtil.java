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
 * �豸������
 * @author 90450
 *
 */
public class EqupUtil {

	private static TextView mToast_tv_msg;

	/**
	 * ���µ���ͼƬ�Ĵ�С
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
	 * �Զ���Toast,����Դ�ļ�,�����ַ�����Ϊ����
	 */
	public static void showMyToast(Context context, String msg, int resid, int duration) {
		/* ��ʼ��My Toast View */
		View toastRoot = LayoutInflater.from(context).inflate(R.layout.viewer_toast, null);
		/* ��ʼ����� */
		mToast_tv_msg = (TextView) toastRoot.findViewById(R.id.toast_tv_content);
		if (msg == null) {
			mToast_tv_msg.setText(resid);
		} else if (resid == 0) {
			mToast_tv_msg.setText(msg);
		} else {
			mToast_tv_msg.setText("����������һ������!");
		}
		/* ʵ����һ��Toast */
		Toast toast = new Toast(context);
		/* ��ȡ��Ļ�߶� */
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);
		/* ����Toast������ */
		toast.setGravity(Gravity.TOP, 0, ((dm.heightPixels / 5) * 4)); // ����Toast��ʾ����Ļ��4/5��λ��
		toast.setDuration(duration);// ������ʾ��ʱ�䳤��
		toast.setView(toastRoot);// ��Toast����view
		toast.show();// ��ʾToast
	}

	public static int dip2px(@Nullable Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * ��Uriת��ΪBitmap�����гߴ��������ѹ��
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
		InputStream in = activity.getContentResolver().openInputStream(uri);//�������ݽ�����ContentResolver����һ��InputStream
		BitmapFactory.Options originalOptions = new BitmapFactory.Options();
		originalOptions.inJustDecodeBounds = true;//����Ϊtrue��������ʵ�ʵ�Bitmap,���ǿ��Բ���Bitmap����Ϣ,��Ч������OOM
		originalOptions.inDither = true; //���ö�������
		originalOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//ָ������ʱ����ɫģʽ,Ĭ��Ҳ��ARGB_8888
		BitmapFactory.decodeStream(in, null, originalOptions);
		in.close();
		int originalWidth = originalOptions.outWidth;
		int originalHeight = originalOptions.outHeight;
		if (originalWidth == -1 || originalHeight == -1) {
			return null;
		}
		int be = 1; //be=1,��ʾ������,�����ʹ�õ�
		if (originalWidth > originalHeight && originalWidth > resizedWidth) {
			be = (int) (originalWidth / resizedWidth);
		} else if (originalHeight > originalWidth && originalHeight > resizedHeight) {
			be = (int) (originalHeight / resizedHeight);
		}
		if (be <= 0) {
			be = 1;
		}

		/**
		 * �ߴ�ѹ��
		 */
		BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inSampleSize = be; //�������ű���
		bitmapOptions.inDither = true; //����
		bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
		in = activity.getContentResolver().openInputStream(uri);
		Bitmap bitmap = BitmapFactory.decodeStream(in, null, bitmapOptions);
		in.close();
		return compressBitmap(bitmap);
	}

	/**
	 * ��Bitmap��������ѹ��
	 * @param bitmap
	 * @return
	 */
	public static Bitmap compressBitmap(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //100��ʾ��ѹ��
		int options = 100;
		/**
		 * ѭ���ж�ѹ����������Ƿ����100k,���ڼ���ѹ��
		 */
		while (baos.toByteArray().length / 1024 > 100) {
			baos.reset();//����baos,��Ϊ�������baos����ֵ
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//ѹ��
			options -= 10;
		}
		ByteArrayInputStream baIn = new ByteArrayInputStream(baos.toByteArray());
		return BitmapFactory.decodeStream(baIn, null, null);
	}
}
