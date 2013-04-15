package com.xufan.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.xufan.callingnumber.R;

public class MainActivity extends Activity {

    final int PICK_CONTACT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	Button getPeople = (Button) findViewById(R.id.bn);
	getPeople.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.setType("vnd.android.cursor.item/phone");
		startActivityForResult(intent, PICK_CONTACT);
	    }

	});
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	switch (requestCode) {
	case PICK_CONTACT:
	    if (resultCode == Activity.RESULT_OK) {
		ContentResolver contentResolver = getContentResolver();
		// 获取返回的数据
		Uri contactData = data.getData();
		// System.out.println(contactData.toString());
		// 查询联系人信息
		Cursor cursor = managedQuery(contactData, null, null, null,
			null);
		// 如果查询到指定的联系人
		if (cursor.moveToFirst()) {
		    String contactId = cursor.getString(cursor
			    .getColumnIndex(ContactsContract.Contacts._ID));
		    // 联系人的名字
		    String name = cursor
			    .getString(cursor
				    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

		    StringBuffer phoneNumber = new StringBuffer();
		    // 根据联系人查询该联系人的详细信息
		    Cursor phones = contentResolver.query(
			    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
			    null, ContactsContract.CommonDataKinds.Phone._ID
				    + "=" + contactId, null, null);
		    System.out.println("ID=" + contactId + ",姓名=" + name);
		    // 取出第一行
		    while (phones.moveToNext()) {
			// 取出电话号码
			phoneNumber
				.append(phones.getString(phones
					.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA1)));
			phoneNumber.append("||");

		    }
		    // 关闭游标
		    if (Integer.parseInt(Build.VERSION.SDK) < 14) {
			phones.close();
		    }
		    EditText show = (EditText) findViewById(R.id.show);
		    // 显示联系人姓名
		    show.setText(name);
		    EditText phone = (EditText) findViewById(R.id.phone);
		    // 显示联系人电话号码
		    phone.setText(phoneNumber.toString());
		}
		// 关闭游标
		if (Integer.parseInt(Build.VERSION.SDK) < 14) {
		    cursor.close();
		}
	    }
	    break;
	}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.activity_main, menu);
	return true;
    }

}
