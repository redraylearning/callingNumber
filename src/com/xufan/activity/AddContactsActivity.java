package com.xufan.activity;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xufan.callingnumber.R;

public class AddContactsActivity extends Activity {

	private EditText familyName;
	private EditText givenName;
	private EditText number;
	private EditText email;
	private Button addButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcontacts);

		//姓
		familyName = (EditText) findViewById(R.id.FAMILY_NAME);
		//名
		givenName = (EditText) findViewById(R.id.GIVEN_NAME);
		number = (EditText) findViewById(R.id.NUMBER);
		email = (EditText) findViewById(R.id.EMAIL);
		addButton = (Button) findViewById(R.id.addContactsButton);

		addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (familyName.getText().toString() != null
						&& !"".equals(familyName.getText().toString())) {
					addContacts();
					Toast toast=Toast.makeText(getApplicationContext(), "增加成功!", Toast.LENGTH_SHORT);
					toast.show();
					Intent intent = new Intent();
					intent.setClass(AddContactsActivity.this,
							ContentProviderDemoActivity.class);
					startActivity(intent);
				}else
				{
					Toast toast=Toast.makeText(getApplicationContext(), "失败!", Toast.LENGTH_SHORT);
					toast.show();
				}
			}
		});
	}

	private void addContacts() {
		// 插入联系人（占位模版）

		ContentValues values = new ContentValues();
		//所有操作都是先用getContentResolver方法获得一个内容抉择器，然后调用query或者insert方法。¨
		Uri rawContactUri = getContentResolver().insert(
				ContactsContract.RawContacts.CONTENT_URI, values);
		//先给模版一个id
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
		
		values.clear();	//清空
		values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);	//设置id
		values.put(ContactsContract.Data.MIMETYPE,
				ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);	//设置MIMETYPE
		values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, number
				.getText().toString());	//设置你要插入的列的值
		values.put(ContactsContract.CommonDataKinds.Phone.TYPE,
				ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);	//设置这个值的所输分类
		getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);	//insert
		
		//插入email
		values.clear();
		values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
		values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
		values.put(ContactsContract.CommonDataKinds.Email.DATA, email.getText().toString());
		values.put(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_HOME);
		getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
	}

}
