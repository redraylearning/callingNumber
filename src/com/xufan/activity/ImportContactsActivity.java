/**********************************************************************
 * FILE			：ImportContactsActivity.java
 * PACKAGE		：com.xufan.activity
 * AUTHOR		：xufan
 * DATE			：2013-4-16 上午9:59:46
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.xufan.callingnumber.R;

/**
 * 项目名称：CallingNumber
 * 类名称：ImportContactsActivity
 * 类描述：
 * 创建人：xufan
 * 创建时间：2013-4-16 上午9:59:46
 * -------------------------------修订历史--------------------------
 * 修改人：xufan
 * 修改时间：2013-4-16 上午9:59:46
 * 修改备注：
 * @version：
*/
public class ImportContactsActivity<myAdapters> extends Activity {
    List li = new ArrayList();
    String mimetype = "";
    private ArrayList<Map<String, Object>> list;
    private final String mString = "";
    private final ArrayList<Map<String, Object>> mContactsName = new ArrayList<Map<String, Object>>();
    /** 联系人头像 **/
    private final ArrayList<String> mContactsNumber = new ArrayList<String>();
    ListView mListView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.importcontacts);
	Button but = (Button) findViewById(R.id.imp);
	// 这句话放到软件启动的位置，是用来初始化数据库的
	try {
	} catch (Exception e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
    }

    // public void test(View v) {
    // // TODO Auto-generated method stub
    // switch (v.getId()) {
    // case R.id.imp:
    // dialog2();
    // break;
    // }
    // }

    // private void getContact() throws SQLException, Exception {
    // String string = "";
    // String strName = null;
    // String strPhoneNumber = null;
    // ContentResolver cr = getContentResolver();
    // Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
    // null, null, null);
    // while (cursor.moveToNext()) {
    // // 设置相关属性.
    // Contact contact = new Contact();
    // int nameFieldColumnIndex = cursor
    // .getColumnIndex(PhoneLookup.DISPLAY_NAME);
    // strName = cursor.getString(nameFieldColumnIndex);
    // // 保存到数据库
    // contact.setName(strName);
    // String contactId = cursor.getString(cursor
    // .getColumnIndex(ContactsContract.Contacts._ID)); //
    // 获取联系人的ID号，在SQLite中的数据库ID
    // Cursor phone = cr.query(
    // ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
    // ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
    // + contactId, null, null);
    // int ks = 0;
    // while (phone.moveToNext()) {
    // String phoneNumber = phone
    // .getString(phone
    // .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
    // int phoneType = phone
    // .getInt(phone
    // .getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
    // if (phoneType == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE) {
    // string = phoneNumber;
    // contact.setMobile(string);
    //
    // contact.store(EUser.getCurrentUser());
    //
    // }
    // // 家庭电话
    // else if (phoneType == Phone.TYPE_HOME) {
    // string = phoneNumber;
    // contact.setTelHome(string);
    // contact.store(EUser.getCurrentUser());
    //
    // }
    // // 单位电话
    // else if (phoneType == Phone.TYPE_WORK) {
    // string = phoneNumber;
    // System.out.println(string + "8");
    // contact.setTel(string);
    //
    // contact.store(EUser.getCurrentUser());
    //
    // }
    //
    // }
    // // 获取该联系人邮箱
    // Cursor emails = getContentResolver().query(
    // ContactsContract.CommonDataKinds.Email.CONTENT_URI,
    // null,
    // ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
    // + contactId, null, null);
    // if (emails.moveToFirst()) {
    // do {
    // // 遍历所有的Email
    // String emailValue = emails
    // .getString(emails
    // .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
    // contact.setEmail(emailValue);
    // contact.store(EUser.getCurrentUser());
    // } while (emails.moveToNext());
    // }
    // // 获取该联系人地址
    // Cursor address = getContentResolver()
    // .query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
    // null,
    // ContactsContract.CommonDataKinds.Phone.CONTACT_ID
    // + " = " + contactId, null, null);
    // if (address.moveToFirst()) {
    // do {
    // // 遍历所有的地址 street接到 city城市 region地区 postCode邮政
    // String street = address
    // .getString(address
    // .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
    // String city = address
    // .getString(address
    // .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
    // String region = address
    // .getString(address
    // .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
    // String postCode = address
    // .getString(address
    // .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
    // String formatAddress = address
    // .getString(address
    // .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS));
    // contact.setAddress(city + region + street + postCode
    // + formatAddress);
    // contact.store(EUser.getCurrentUser());
    // } while (address.moveToNext());
    // }
    //
    // // 获取备注信息
    // Cursor notes = getContentResolver().query(
    // Data.CONTENT_URI,
    // new String[] { Data._ID, Note.NOTE },
    // Data.CONTACT_ID + "=?" + " AND " + Data.MIMETYPE + "='"
    // + Note.CONTENT_ITEM_TYPE + "'",
    // new String[] { contactId }, null);
    // if (notes.moveToFirst()) {
    // do {
    // String noteinfo = notes.getString(notes
    // .getColumnIndex(Note.NOTE));
    // contact.setComment(noteinfo);
    // contact.store(EUser.getCurrentUser());
    //
    // } while (notes.moveToNext());
    // }
    //
    // phone.close();
    // }
    // cursor.close();
    // }
    //
    // private void getContactSimka() throws SQLException, Exception {
    // String string = "";
    // String strName = null;
    // String strPhoneNumber = null;
    // ContentResolver resolver = ImportContactsActivity.this
    // .getContentResolver();
    // // 获取Sims卡联系人 icc/adn sim/adn
    // Uri uri = Uri.parse("content://icc/adn");
    // Cursor cursor = resolver.query(uri, null, null, null, null);
    // while (cursor.moveToNext()) {
    // // 设置相关属性
    // Contact contact = new Contact();
    // int nameFieldColumnIndex = cursor
    // .getColumnIndex(PhoneLookup.DISPLAY_NAME);
    // strName = cursor.getString(nameFieldColumnIndex);
    // // 保存到数据库
    // contact.setName(strName);
    // String contactId = cursor.getString(cursor
    // .getColumnIndex(ContactsContract.Contacts._ID)); //
    // 获取联系人的ID号，在SQLite中的数据库ID
    // Cursor phone = resolver.query(
    // ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
    // ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
    // + contactId, null, null);
    // int ks = 0;
    // while (phone.moveToNext()) {
    // String phoneNumber = phone
    // .getString(phone
    // .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
    // int phoneType = phone
    // .getInt(phone
    // .getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
    // if (phoneType == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE) {
    // string = phoneNumber;
    // contact.setMobile(string);
    // contact.store(EUser.getCurrentUser());
    //
    // }
    // // 家庭电话
    // else if (phoneType == Phone.TYPE_HOME) {
    // string = phoneNumber;
    // contact.setTelHome(string);
    // contact.store(EUser.getCurrentUser());
    // }
    // // 单位电话
    // else if (phoneType == Phone.TYPE_WORK) {
    // string = phoneNumber;
    // System.out.println(string + "8");
    // contact.setTel(string);
    // contact.store(EUser.getCurrentUser());
    //
    // }
    //
    // }
    // // 获取该联系人邮箱
    // Cursor emails = getContentResolver().query(
    // ContactsContract.CommonDataKinds.Email.CONTENT_URI,
    // null,
    // ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
    // + contactId, null, null);
    // if (emails.moveToFirst()) {
    // do {
    // // 遍历所有的Email
    // String emailValue = emails
    // .getString(emails
    // .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
    // contact.setEmail(emailValue);
    // contact.store(EUser.getCurrentUser());
    // } while (emails.moveToNext());
    // }
    // // 获取该联系人地址
    // Cursor address = getContentResolver()
    // .query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
    // null,
    // ContactsContract.CommonDataKinds.Phone.CONTACT_ID
    // + " = " + contactId, null, null);
    // if (address.moveToFirst()) {
    // do {
    // // 遍历所有的地址 street接到 city城市 region地区 postCode邮政
    // String street = address
    // .getString(address
    // .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
    // String city = address
    // .getString(address
    // .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
    // String region = address
    // .getString(address
    // .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
    // String postCode = address
    // .getString(address
    // .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
    // String formatAddress = address
    // .getString(address
    // .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS));
    // contact.setAddress(city + region + street + postCode
    // + formatAddress);
    // contact.store(EUser.getCurrentUser());
    // } while (address.moveToNext());
    // }
    //
    // // 获取备注信息
    // Cursor notes = getContentResolver().query(
    // Data.CONTENT_URI,
    // new String[] { Data._ID, Note.NOTE },
    // Data.CONTACT_ID + "=?" + " AND " + Data.MIMETYPE + "='"
    // + Note.CONTENT_ITEM_TYPE + "'",
    // new String[] { contactId }, null);
    // if (notes.moveToFirst()) {
    // do {
    // String noteinfo = notes.getString(notes
    // .getColumnIndex(Note.NOTE));
    // contact.setComment(noteinfo);
    // contact.store(EUser.getCurrentUser());
    // } while (notes.moveToNext());
    // }
    //
    // phone.close();
    // }
    // cursor.close();
    // }
    //
    // public void dialog2() {
    // AlertDialog dialog = new AlertDialog.Builder(this)
    // .setIcon(R.drawable.ic_launcher).setTitle("导入联系人")
    // .setMessage("是否导入联系人？")
    // .setPositiveButton("导入手机中联系人", new OnClickListener() {
    // @Override
    // public void onClick(DialogInterface dialog, int which) {
    // try {
    // getContact();
    // } catch (SQLException e) {
    // e.printStackTrace();
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
    // }).setNegativeButton("导入Sim卡中联系人", new OnClickListener() {
    // @Override
    // public void onClick(DialogInterface arg0, int arg1) {
    // try {
    // getContactSimka();
    // } catch (SQLException e) {
    // e.printStackTrace();
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
    // }).create();
    // dialog.show();
    // }
}
