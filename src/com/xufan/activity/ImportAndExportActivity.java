/**********************************************************************
 * FILE			：ImportContactsActivity.java
 * PACKAGE		：com.xufan.activity
 * AUTHOR		：xufan
 * DATE			：2013-4-18 下午4:03:15
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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.TextUtils;
import android.widget.ListView;

import com.xufan.adapter.MyListAdapter;
import com.xufan.callingnumber.R;
import com.xufan.config.Contents;
import com.xufan.data.DBHelper;
import com.xufan.data.User;
import com.xufan.util.Utils;

/**
 * 项目名称：CallingNumber
 * 类名称：ImportContactsActivity
 * 类描述：导入到本地通讯录
 * 创建人：xufan
 * 创建时间：2013-4-18 下午4:03:15
 * -------------------------------修订历史--------------------------
 * 修改人：xufan
 * 修改时间：2013-4-18 下午4:03:15
 * 修改备注：
 * @version：
*/
public class ImportAndExportActivity extends Activity {
    public ListView listView;
    public ArrayList<HashMap<String, Object>> list;
    DBHelper helper = new DBHelper(this);
    /**联系人名称**/
    private final ArrayList<String> mContactsName = new ArrayList<String>();

    /**联系人电话**/
    private final ArrayList<String> mContactsNumber = new ArrayList<String>();

    /**联系人EMAIL**/
    private final ArrayList<String> mContactsEmail = new ArrayList<String>();

    /**联系人头像**/
    private final ArrayList<Bitmap> mContactsPhonto = new ArrayList<Bitmap>();

    public static int importflag = 0;// 记录导入重复数

    public static int exportflag = 0;// 记录导出重复数

    /**
     * @方法名：add
     * @功能描述：导入到手机通讯录
     * @创建人：xufan
     * @创建时间：2013-4-18 下午4:03:16
     * @参数：@param familyName
     * @参数：@param givenName
     * @参数：@param phoneNumber
     * @参数：@param email
     * @参数：@param photoId
     * @返回：void
     * @throws
     */
    public void add(String name, String phoneNumber, String email, Bitmap photo) {
	// 检查重复
	if (checkImportContact(phoneNumber)) {
	    // 插入联系人（占位模版）s

	    ContentValues values = new ContentValues();
	    // 所有操作都是先用getContentResolver方法获得一个内容抉择器，然后调用query或者insert方法。¨
	    Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
	    // 先给模版一个id
	    long rawContactId = ContentUris.parseId(rawContactUri);

	    // 插入姓名
	    values.clear();
	    values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
	    values.put(ContactsContract.Data.MIMETYPE,
		    ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);// 设置MIMETYPE

	    values.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name);

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
	    final ByteArrayOutputStream os = new ByteArrayOutputStream();
	    // 将Bitmap压缩成PNG编码，质量为100%存储
	    photo.compress(Bitmap.CompressFormat.PNG, 100, os);
	    byte[] avatar = os.toByteArray();
	    values.clear();
	    values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
	    values.put(ContactsContract.Data.MIMETYPE,
		    ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE);
	    values.put(ContactsContract.CommonDataKinds.Photo.PHOTO, avatar);
	    getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
	} else {
	    importflag++;
	}

    }

    /**
     * @方法名：getAll
     * @功能描述：导出全部手机通讯录信息
     * @创建人：xufan
     * @创建时间：2013-4-18 下午3:19:25
     * @参数：
     * @返回：void
     * @throws
     */
    public void getAll() {
	ContentResolver resolver = getContentResolver();

	// 获取手机联系人
	Cursor phoneCursor = resolver.query(Phone.CONTENT_URI, Contents.PHONES_PROJECTION, null, null, null);

	if (phoneCursor != null) {
	    while (phoneCursor.moveToNext()) {
		// 得到手机号码
		String phoneNumber = phoneCursor.getString(Contents.PHONES_NUMBER_INDEX);
		// 当手机号码为空的或者为空字段 跳过当前循环
		if (TextUtils.isEmpty(phoneNumber))
		    continue;
		if (checkExportContact(phoneNumber)) {
		    exportflag++;
		    continue;
		}

		// 得到联系人名称
		String contactName = phoneCursor.getString(Contents.PHONES_DISPLAY_NAME_INDEX);

		// 得到联系人ID
		Long contactid = phoneCursor.getLong(Contents.PHONES_CONTACT_ID_INDEX);
		// 得到email
		String email = "";
		Cursor emailCursor = resolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
			ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactid, null, null);
		while (emailCursor.moveToNext()) {
		    email = emailCursor.getString(emailCursor
			    .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA1));
		}

		// 得到联系人头像ID
		Long photoid = phoneCursor.getLong(Contents.PHONES_PHOTO_ID_INDEX);
		// 得到联系人头像Bitamp
		Bitmap contactPhoto = null;

		// photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
		if (photoid > 0) {
		    Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactid);
		    InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
		    contactPhoto = BitmapFactory.decodeStream(input);
		} else {
		    contactPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.head_default);
		}
		mContactsName.add(contactName);
		mContactsEmail.add(email);
		mContactsNumber.add(phoneNumber);
		mContactsPhonto.add(contactPhoto);
	    }
	    phoneCursor.close();
	}
	insertDB();
    }

    /**
     * @方法名：checkContact
     * @功能描述：检查插入电话是否在通讯录中已经存在
     * @创建人：xufan
     * @创建时间：2013-4-18 下午2:37:20
     * @参数：@param phoneNum
     * @参数：@return
     * @返回：Boolean
     * @throws
     */
    public Boolean checkImportContact(String phoneNum) {
	boolean flag = true;
	ContentResolver resolver = getContentResolver();
	// 获取手机联系人
	Cursor phoneCursor = resolver.query(Phone.CONTENT_URI, new String[] { "data1" }, null, null, null);
	if (phoneCursor != null) {
	    while (phoneCursor.moveToNext()) {
		// 得到手机号码
		String phone = phoneCursor.getString(0);
		if (phone.equals(phoneNum)) {
		    flag = false;
		}
	    }
	}
	phoneCursor.close();
	return flag;
    }

    /**
     * @方法名：checkExportContact
     * @功能描述：检查导出手机通讯录是否重复
     * @创建人：xufan
     * @创建时间：2013-4-18 下午3:28:53
     * @参数：@param phoneNum
     * @参数：@return
     * @返回：Boolean
     * @throws
     */
    public Boolean checkExportContact(String phoneNum) {
	boolean flag = true;
	ArrayList<HashMap<String, Object>> list = helper.getUsers(phoneNum);
	if (list.size() == 0) {
	    flag = false;
	}
	return flag;
    }

    public void insertDB() {
	int num = mContactsName.size();
	User user = new User();
	for (int i = 0; i < num; i++) {
	    user.setUsername(mContactsName.get(i));
	    user.setEmail(mContactsEmail.get(i));
	    user.setMobilePhone(mContactsNumber.get(i));
	    user.setImage(Utils.bmpToByteArray(mContactsPhonto.get(i)));
	    helper.insert(user, false);
	}
    }

    /**重置listview*/
    public void resetListView() {
	list = helper.getAllUser();
	listView = (ListView) findViewById(R.id.userlist);

	listView.setAdapter(new MyListAdapter(this, list));
    }

}
