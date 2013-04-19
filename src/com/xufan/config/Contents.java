/**********************************************************************
 * FILE			：Contents.java
 * PACKAGE		：com.xufan.config
 * AUTHOR		：xufan
 * DATE			：2013-4-16 上午9:04:45
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
package com.xufan.config;

import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts.Photo;

import com.xufan.callingnumber.R;

/**
 * 项目名称：CallingNumber
 * 类名称：Contents
 * 类描述：
 * 创建人：xufan
 * 创建时间：2013-4-16 上午9:04:45
 * -------------------------------修订历史--------------------------
 * 修改人：xufan
 * 修改时间：2013-4-16 上午9:04:45
 * 修改备注：
 * @version：
*/
public class Contents {

    /**广播ID*/
    public static final int NOTIFICATION_ID = 0x1123;

    /**定义管理员标识*/
    public static final String ADMIN = "admin";
    /**定义普通游客*/
    public static final String CUSTOMER = "customer";

    /**联系人显示名称*/
    public static final int PHONES_DISPLAY_NAME_INDEX = 0;
    /**电话号码*/
    public static final int PHONES_NUMBER_INDEX = 1;
    /**头像ID*/
    public static final int PHONES_PHOTO_ID_INDEX = 2;
    /**联系人的ID*/
    public static final int PHONES_CONTACT_ID_INDEX = 3;
    /**定义查询通讯录的字段*/
    public static final String[] PHONES_PROJECTION = { Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID,
	    Phone.CONTACT_ID };

    /**取得全部资源 图片*/
    public static final int[] IMAGES = new int[] { R.drawable.f1, R.drawable.f2, R.drawable.f3,
	    R.drawable.f4, R.drawable.f5, R.drawable.f6, R.drawable.f7, R.drawable.f8, R.drawable.f9 };

    /**底部菜单*/
    public static final String[] BOTTOM_MENU_ITEMNAME = { "增加", "删除", "导入", "导出", "退出" };

    /**底部菜单图标*/
    public static final int[] BOTTOM_MENU_ITEMSOURCE = { R.drawable.menu_new_user, R.drawable.menu_delete,
	    R.drawable.menu_backup, R.drawable.menu_restore, R.drawable.menu_exit };

    /**快速搜索*/
    public static final char[] LETTER = { '#', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
	    'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
}
