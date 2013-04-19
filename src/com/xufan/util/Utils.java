/**********************************************************************
 * FILE			：Utils.java
 * PACKAGE		：com.xufan.util
 * AUTHOR		：xufan
 * DATE			：2013-4-16 上午11:47:35
 * FUNCTION		：
 *
 * 杭州思伟版权所有
 *======================================================================
 * CHANGE HISTORY LOG
 *----------------------------------------------------------------------
 * MOD. NO.|  DATE    | NAME           | REASON            | CHANGE REQ.
 *----------------------------------------------------------------------
 *         |          | xufan       | Created           |
 *
 * DESCRIPTION:
 *
 ***********************************************************************/
package com.xufan.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * 项目名称：CallingNumber
 * 类名称：Utils
 * 类描述：
 * 创建人：xufan
 * 创建时间：2013-4-16 上午11:47:35
 * -------------------------------修订历史--------------------------
 * 修改人：xufan
 * 修改时间：2013-4-16 上午11:47:35
 * 修改备注：
 * @version：
*/
public class Utils {

    /**
     * @方法名：getScreenWidth
     * @功能描述：获取屏幕宽度
     * @创建人：xufan
     * @创建时间：2013-4-16 上午11:47:44
     * @参数：@param context
     * @参数：@return
     * @返回：int
     * @throws
     */
    public static int getScreenWidth(Context context) {
	WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	Display display = manager.getDefaultDisplay();
	return display.getWidth();
    }

    /**
     * @方法名：getScreenHeight
     * @功能描述：获取屏幕高度
     * @创建人：xufan
     * @创建时间：2013-4-16 上午11:48:03
     * @参数：@param context
     * @参数：@return
     * @返回：int
     * @throws
     */
    public static int getScreenHeight(Context context) {
	WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	Display display = manager.getDefaultDisplay();
	return display.getHeight();
    }

    /**
     * @方法名：getScreenDensity
     * @功能描述：获取屏幕密度
     * @创建人：xufan
     * @创建时间：2013-4-16 上午11:49:05
     * @参数：@param context
     * @参数：@return
     * @返回：float
     * @throws
     */
    public static float getScreenDensity(Context context) {
	try {
	    DisplayMetrics dm = new DisplayMetrics();
	    WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	    manager.getDefaultDisplay().getMetrics(dm);
	    return dm.density;
	} catch (Exception ex) {

	}
	return 1.0f;
    }

    /**
     * @方法名：codetype
     * @功能描述：获取编码格式
     * @创建人：xufan
     * @创建时间：2013-4-16 下午3:29:07
     * @参数：@param head
     * @参数：@return
     * @返回：String
     * @throws
     */
    public static String codetype(byte[] head) {
	byte[] codehead = new byte[4];
	// 截取数组
	System.arraycopy(head, 0, codehead, 0, 4);
	String code = "";
	if (head[0] == -1 && head[1] == -2) {
	    code = "UTF-16";
	} else if (head[0] == -2 && head[1] == -1) {
	    code = "Unicode";
	} else if (head[0] == -17 && head[1] == -69 && head[2] == -65)
	    code = "UTF-8";
	else {
	    code = "gb2312";
	}
	return code;
    }

    /**
     * @方法名：getSDPath
     * @功能描述：获取SD卡路径
     * @创建人：xufan
     * @创建时间：2013-4-18 下午4:49:31
     * @参数：@return
     * @返回：String
     * @throws
     */
    public static String getSDPath() {
	File sdDir = null;
	boolean sdCardExist = Environment.getExternalStorageState().equals(
		android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
	if (sdCardExist) {
	    sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
	}
	return sdDir.toString();
    }

    /**
     * @方法名：saveFile
     * @功能描述：保存到SD卡
     * @创建人：xufan
     * @创建时间：2013-4-18 下午4:49:37
     * @参数：@param bm
     * @参数：@param fileName
     * @参数：@throws IOException
     * @返回：void
     * @throws
     */
    public void saveFile(Bitmap bm, String fileName) throws IOException {
	String path = getSDPath() + "/revoeye/";
	File dirFile = new File(path);
	if (!dirFile.exists()) {
	    dirFile.mkdir();
	}
	File myCaptureFile = new File(path + fileName);
	BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
	bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
	bos.flush();
	bos.close();
    }

    /**
     * @方法名：bmpToByteArray
     * @功能描述：把bitmap变成数组
     * @创建人：xufan
     * @创建时间：2013-4-19 上午9:26:33
     * @参数：@param bmp
     * @参数：@return
     * @返回：byte[]
     * @throws
     */
    public static byte[] bmpToByteArray(Bitmap bmp) {
	// Default size is 32 bytes
	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	try {
	    bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
	    bos.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return bos.toByteArray();
    }

    /**
     * @方法名：cursorToBmp
     * @功能描述：数据库中取出bitmap
     * @创建人：xufan
     * @创建时间：2013-4-19 上午9:28:13
     * @参数：@param c
     * @参数：@param columnIndex
     * @参数：@return
     * @返回：Bitmap
     * @throws
     */
    public static Bitmap cursorToBmp(Cursor c, int columnIndex) {
	byte[] data = c.getBlob(columnIndex);
	try {
	    return BitmapFactory.decodeByteArray(data, 0, data.length);
	} catch (Exception e) {
	    return null;
	}
    }
}
