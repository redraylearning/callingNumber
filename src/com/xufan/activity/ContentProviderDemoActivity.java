package com.xufan.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.xufan.callingnumber.R;

public class ContentProviderDemoActivity extends Activity {

    private Button getContacts;
    private Button addContacts;
    private Button showContacts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	getContacts = (Button) findViewById(R.id.getContactsButton);
	addContacts = (Button) findViewById(R.id.addContactsButton);
	showContacts = (Button) findViewById(R.id.bn);

	showContacts.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// addContacts();
		Intent intent = new Intent();
		intent.setClass(ContentProviderDemoActivity.this,
			MainActivity.class);
		startActivity(intent);
	    }
	});

	// query all contacts.
	getContacts.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {

		getContacts();
	    }
	});

	// add one contact.
	addContacts.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {

		// addContacts();
		Intent intent = new Intent();
		intent.setClass(ContentProviderDemoActivity.this,
			AddContactsActivity.class);
		startActivity(intent);
	    }
	});
    }

    private void getContacts() {
	// 创建内容抉择器对象
	ContentResolver contentResolver = getContentResolver();
	System.out.println("contentResolver创建");

	// 公共的Uri,这个Uri表示这个ContentResolver所提供的数据
	Uri uri = Uri.parse("content://contacts/phones/16");
	System.out.println("URI地址" + uri);

	// 查询（游标，指示器）
	Cursor cursor = contentResolver.query(uri, null, null, null, null);

	while (cursor.moveToNext()) {
	    int i = 1;
	    // 获取联系人id，姓名
	    StringBuilder sb = new StringBuilder();
	    String contactId = cursor.getString(cursor
		    .getColumnIndex(ContactsContract.Contacts._ID));

	    String name = cursor.getString(cursor
		    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
	    sb.append("ID=").append(contactId).append(",姓名=").append(name);

	    // 获取联系人号码（可能有多个）
	    Cursor phones = contentResolver.query(
		    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
		    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
			    + contactId, null, null);
	    while (phones.moveToNext()) {

		String phone = phones
			.getString(phones
				.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA1));
		sb.append(",电话号码" + i + "=").append(phone);
		i++;
	    }

	    // 获取联系人email
	    i = 1;
	    Cursor emails = contentResolver.query(
		    ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
		    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = "
			    + contactId, null, null);
	    while (emails.moveToNext()) {
		String email = emails
			.getString(emails
				.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA1));
		sb.append(",email" + i + "=").append(email);
		i++;
	    }

	    System.out.println(sb.toString());
	}
    }

}
