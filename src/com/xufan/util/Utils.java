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

import android.content.Context;
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
	WindowManager manager = (WindowManager) context
		.getSystemService(Context.WINDOW_SERVICE);
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
	WindowManager manager = (WindowManager) context
		.getSystemService(Context.WINDOW_SERVICE);
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
	    WindowManager manager = (WindowManager) context
		    .getSystemService(Context.WINDOW_SERVICE);
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
    public static  String codetype(byte[] head) {
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
}
