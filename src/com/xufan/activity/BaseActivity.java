/**********************************************************************
 * FILE			：BaseActivity.java
 * PACKAGE		：com.xufan.activity
 * AUTHOR		：xufan
 * DATE			：2013-4-15 下午6:03:15
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
package com.xufan.activity;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.ContactsContract;

/**
 * 项目名称：CallingNumber
 * 类名称：BaseActivity
 * 类描述：
 * 创建人：xufan
 * 创建时间：2013-4-15 下午6:03:15
 * -------------------------------修订历史--------------------------
 * 修改人：xufan
 * 修改时间：2013-4-15 下午6:03:15
 * 修改备注：
 * @version：
*/
public class BaseActivity extends Activity {
    public Boolean add() {

   	// 插入联系人（占位模版）

   	ContentValues values = new ContentValues();
   	// 所有操作都是先用getContentResolver方法获得一个内容抉择器，然后调用query或者insert方法。¨
   	Uri rawContactUri = getContentResolver().insert(
   		ContactsContract.RawContacts.CONTENT_URI, values);
   	// 先给模版一个id
   	long rawContactId = ContentUris.parseId(rawContactUri);

   	// 插入姓名
   	values.clear();
   	values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
   	values.put(
   		ContactsContract.Data.MIMETYPE,
   		ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);

   	values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,
   		givenName.getText().toString());
   	values.put(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,
   		familyName.getText().toString());

   	getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);

   	// 插入电话号码

   	values.clear(); // 清空
   	values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId); // 设置id
   	values.put(ContactsContract.Data.MIMETYPE,
   		ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE); // 设置MIMETYPE
   	values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, number
   		.getText().toString()); // 设置你要插入的列的值
   	values.put(ContactsContract.CommonDataKinds.Phone.TYPE,
   		ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE); // 设置这个值的所输分类
   	getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values); // insert

   	// 插入email
   	values.clear();
   	values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
   	values.put(ContactsContract.Data.MIMETYPE,
   		ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
   	values.put(ContactsContract.CommonDataKinds.Email.DATA, email.getText()
   		.toString());
   	values.put(ContactsContract.CommonDataKinds.Email.TYPE,
   		ContactsContract.CommonDataKinds.Email.TYPE_HOME);
   	getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);

       }
}
