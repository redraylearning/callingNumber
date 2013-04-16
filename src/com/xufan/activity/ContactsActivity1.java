/**********************************************************************
 * FILE			：ContactsActivity.java
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

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

/**
 * 项目名称：CallingNumber
 * 类名称：ContactsActivity
 * 类描述：
 * 创建人：xufan
 * 创建时间：2013-4-15 下午6:03:15
 * -------------------------------修订历史--------------------------
 * 修改人：xufan
 * 修改时间：2013-4-15 下午6:03:15
 * 修改备注：
 * @version：
*/
public class ContactsActivity1 extends Activity {
    /**
     * @方法名：add
     * @功能描述：添加本地通讯录
     * @创建人：xufan
     * @创建时间：2013-4-16 上午9:54:04
     * @参数：@param familyName
     * @参数：@param givenName
     * @参数：@param phoneNumber
     * @参数：@param email
     * @参数：@param photoId
     * @返回：void
     * @throws
     */
    public void add(String familyName, String givenName, String phoneNumber,
	    String email, int photoId) {

	// 插入联系人（占位模版）s

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
		ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);// 设置MIMETYPE

	values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,
		givenName);
	values.put(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,
		familyName);

	getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);

	// 插入电话号码

	values.clear(); // 清空
	values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId); // 设置id
	values.put(ContactsContract.Data.MIMETYPE,
		ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
	values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber); // 设置你要插入的列的值
	values.put(ContactsContract.CommonDataKinds.Phone.TYPE,
		ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE); // 设置这个值的所输分类
	getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values); // insert

	// 插入email
	values.clear();
	values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
	values.put(ContactsContract.Data.MIMETYPE,
		ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
	values.put(ContactsContract.CommonDataKinds.Email.DATA, email);
	values.put(ContactsContract.CommonDataKinds.Email.TYPE,
		ContactsContract.CommonDataKinds.Email.TYPE_HOME);
	getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);

	// 插入头像
	Bitmap sourceBitmap = BitmapFactory.decodeResource(getResources(),
		photoId);
	final ByteArrayOutputStream os = new ByteArrayOutputStream();
	// 将Bitmap压缩成PNG编码，质量为100%存储
	sourceBitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
	byte[] avatar = os.toByteArray();
	values.clear();
	values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
	values.put(ContactsContract.Data.MIMETYPE,
		ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE);
	values.put(ContactsContract.CommonDataKinds.Photo.PHOTO, avatar);
	getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
    }
    // if (resultCode == Activity.RESULT_OK) {
    // ContentResolver contentResolver = getContentResolver();
    // // 获取返回的数据
    // Uri contactData = data.getData();
    // // System.out.println(contactData.toString());
    // // 查询联系人信息
    // Cursor cursor = managedQuery(contactData, null, null, null,
    // null);
    // // 如果查询到指定的联系人
    // if (cursor.moveToFirst()) {
    // String contactId = cursor.getString(cursor
    // .getColumnIndex(ContactsContract.Contacts._ID));
    // // 联系人的名字
    // String name = cursor
    // .getString(cursor
    // .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
    //
    // StringBuffer phoneNumber = new StringBuffer();
    // // 根据联系人查询该联系人的详细信息
    // Cursor phones = contentResolver.query(
    // ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
    // null, ContactsContract.CommonDataKinds.Phone._ID
    // + "=" + contactId, null, null);
    // System.out.println("ID=" + contactId + ",姓名=" + name);
    // // 取出第一行
    // while (phones.moveToNext()) {
    // // 取出电话号码
    // phoneNumber
    // .append(phones.getString(phones
    // .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA1)));
    // phoneNumber.append("||");
    //
    // }
    // // 关闭游标
    // if (Integer.parseInt(Build.VERSION.SDK) < 14) {
    // phones.close();
    // }
    // EditText show = (EditText) findViewById(R.id.show);
    // // 显示联系人姓名
    // show.setText(name);
    // EditText phone = (EditText) findViewById(R.id.phone);
    // // 显示联系人电话号码
    // phone.setText(phoneNumber.toString());
    // }
    // // 关闭游标
    // if (Integer.parseInt(Build.VERSION.SDK) < 14) {
    // cursor.close();
    // }
    // }
    // }
}
