package com.xufan.activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Window;

import com.xufan.callingnumber.R;
import com.xufan.config.Contents;
import com.xufan.data.DBHelper;
import com.xufan.data.User;
import com.xufan.util.DialogFactory;

public class MainActivity extends Activity {

    final int PICK_CONTACT = 0;
    private Dialog mDialog = null;
    SharedPreferences preferences;
    DBHelper helper = new DBHelper(this);
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_main);
	showNotification();// 显示一下一个notification
	helper.openDatabase(); // 打开数据库
	// 读取SharedPreferences中需要的数据
	preferences = getSharedPreferences("count", MODE_WORLD_READABLE);
	int count = preferences.getInt("count", 0);
	// 判断程序与第几次运行，如果是第一次运行则导入数据
	if (count == 0) {
	    showRequestDialog();// 显示正在连接对话框
	    new Thread() {
		@Override
		public void run() {
		    init();// 初始化数据
		    try {
			sleep(1500L);
			if (mDialog.isShowing())
			    mDialog.dismiss();// 关闭显示框
			clearNotification();// 清空notification
			Intent intent = new Intent(MainActivity.this,
				LoginActivity.class);
			startActivity(intent);
			finish();
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		}
	    }.start();
	} else {
	    new Thread() {
		@Override
		public void run() {
		    try {
			sleep(1000L);
			clearNotification();// 清空notification
			Intent intent = new Intent(MainActivity.this,
				LoginActivity.class);
			startActivity(intent);
			finish();
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		}
	    }.start();

	}
	Editor editor = preferences.edit();
	// 存入数据
	editor.putInt("count", ++count);
	// 提交修改
	editor.commit();

    }

    /**
     * @方法名：init
     * @功能描述：初始化通讯录 这里是简单的插入固定数据
     * @创建人：xufan
     * @创建时间：2013-4-16 上午11:49:38
     * @参数：
     * @返回：void
     * @throws
     */
    public void init() {
	List<User> userList = getPhoneNum();
	for (User user : userList) {
	    helper.insert(user, true);
	}
    }

    /**
     * 在状态栏显示通知
     */
    private void showNotification() {
	// 创建一个NotificationManager的引用
	NotificationManager notificationManager = (NotificationManager) this
		.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
	Notification notification = new Notification.Builder(
		getApplicationContext()).setTicker("欢迎使用！")
		.setSmallIcon(R.drawable.ic_launcher).getNotification();

	notificationManager.notify(Contents.NOTIFICATION_ID, notification);
    }

    /** 删除通知 */
    private void clearNotification() {
	// 启动后删除之前我们定义的通知
	NotificationManager notificationManager = (NotificationManager) this
		.getSystemService(NOTIFICATION_SERVICE);
	notificationManager.cancel(Contents.NOTIFICATION_ID);
    }

    /** 显示框 */
    private void showRequestDialog() {
	if (mDialog != null) {
	    mDialog.dismiss();
	    mDialog = null;
	}
	mDialog = DialogFactory.creatRequestDialog(this, "亲,正在初始化数据...");
	mDialog.show();
    }

    private List<User> getPhoneNum() {
	InputStream in = null;
	List<User> result = new ArrayList<User>();
	try {
	    in = getResources().getAssets().open("phone.txt");
	    // 获得输入流的长度
	    int length = in.available();
	    // 创建字节输入
	    byte[] buffer = new byte[length];
	    // 放入字节输入中
	    in.read(buffer);
	    // 设置编码格式读取TXT
	    String res = EncodingUtils.getString(buffer, "utf-8");
	    String[] results = res.split("\\n");
	    for (int i = 0; i < results.length; i++) {
		String[] users = results[i].split(":");
		User user = new User();
		user.setUsername(users[0]);
		user.setMobilePhone(users[1]);
		user.setEmail(users[2]);
		result.add(user);
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    // 关闭输入流
	    try {
		in.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	return result;
    }
}
