/**********************************************************************
 * FILE			：SaveBitmap.java
 * PACKAGE		：com.xufan.util
 * AUTHOR		：xufan
 * DATE			：2013-4-18 下午4:58:34
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

/**
 * 项目名称：CallingNumber
 * 类名称：SaveBitmap
 * 类描述：
 * 创建人：xufan
 * 创建时间：2013-4-18 下午4:58:34
 * -------------------------------修订历史--------------------------
 * 修改人：xufan
 * 修改时间：2013-4-18 下午4:58:34
 * 修改备注：
 * @version：
*/
public class SaveBitmap {
    private final static String CACHE = "/CallingNumber";

    /** 
     * 保存图片的方法 保存到sdcard 
     *  
     * @throws Exception 
     *  
     */
    public static void saveImage(Bitmap bitmap, String imageName)  {
	String filePath = isExistsFilePath();
	FileOutputStream fos = null;
	File file = new File(filePath, imageName);
	try {
	    fos = new FileOutputStream(file);
	    if (null != fos) {
		bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
		fos.flush();
		fos.close();
	    }
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /** 
     * 获取sd卡的缓存路径， 一般在卡中sdCard就是这个目录 
     *  
     * @return SDPath 
     */
    public static String getSDPath() {
	File sdDir = null;
	boolean sdCardExist = Environment.getExternalStorageState().equals(
		android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
	if (sdCardExist) {
	    sdDir = Environment.getExternalStorageDirectory();// 获取根目录
	} else {
	    Log.e("ERROR", "没有内存卡");
	}
	return sdDir.toString();
    }

    /** 
     * 获取缓存文件夹目录 如果不存在创建 否则则创建文件夹 
     *  
     * @return filePath 
     */
    private static String isExistsFilePath() {
	String filePath = getSDPath() + CACHE;
	File file = new File(filePath);
	if (!file.exists()) {
	    file.mkdirs();
	}
	return filePath;
    }

    /** 
     * 获取SDCard文件 
     *  
     * @return Bitmap 
     */
    public static Bitmap getImageFromSDCard(String imageName) {
	String filepath = getSDPath() + CACHE + "/" + imageName;
	File file = new File(filepath);
	if (file.exists()) {
	    Bitmap bm = BitmapFactory.decodeFile(filepath);
	    return bm;
	}
	return null;
    }
}
