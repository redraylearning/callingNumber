/**********************************************************************
 * FILE			：LoginActivity.java
 * PACKAGE		：com.xufan.activity
 * AUTHOR		：xufan
 * DATE			：2013-4-16 下午2:06:59
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

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.xufan.callingnumber.R;
import com.xufan.config.Contents;
import com.xufan.data.DBHelper;
import com.xufan.util.CustomToast;
import com.xufan.util.DialogFactory;

/**
 * 项目名称：CallingNumber
 * 类名称：LoginActivity
 * 类描述：登录校验activity
 * 创建人：xufan
 * 创建时间：2013-4-16 下午2:06:59
 * -------------------------------修订历史--------------------------
 * 修改人：xufan
 * 修改时间：2013-4-16 下午2:06:59
 * 修改备注：
 * @version：
*/
public class LoginActivity extends Activity implements OnClickListener {
    private Button mBtnLogin;
    private EditText mAccounts;
    /**点击登录按钮后，弹出验证对话框*/
    private Dialog mDialog = null;
    public String inputAccount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.loginpage);
	initView();
    }

    public void initView() {
	mBtnLogin = (Button) findViewById(R.id.login_btn);
	mBtnLogin.setOnClickListener(this);
	mAccounts = (EditText) findViewById(R.id.lgoin_accounts);
    }

    /**
     * 处理点击事件
     */
    @Override
    public void onClick(View v) {
	switch (v.getId()) {
	case R.id.login_btn:
	    inputAccount = mAccounts.getText().toString();
	    if ("".equals(inputAccount) || inputAccount == null) {
		CustomToast.showToast(LoginActivity.this, "亲,账号不能为空!");
	    } else {
		showRequestDialog();
		new Thread() {
		    @Override
		    public void run() {
			try {
			    sleep(1500L);
			    String result = checkaccount();
			    cancleDialog();
			    if (result.equals(Contents.ADMIN)) {
				Intent intent = new Intent(LoginActivity.this, ModifyContactsActivity.class);
				startActivity(intent);
			    } else if (result.equals(Contents.CUSTOMER)) {
				Intent intent = new Intent(LoginActivity.this, ContactsActivity.class);
				intent.putExtra("userPhone", inputAccount);
				startActivity(intent);
			    } else {
				Looper.prepare();
				CustomToast.showToast(LoginActivity.this, "亲,账号错误!");
				Looper.loop();
			    }
			} catch (Exception e) {
			    e.printStackTrace();
			}
		    }
		}.start();
	    }

	    break;
	default:
	    break;
	}
    }

    /**
     * @方法名：checkaccount
     * @功能描述：验证账号
     * @创建人：xufan
     * @创建时间：2013-4-16 下午2:56:15
     * @参数：@return
     * @返回：String
     * @throws
     */
    public String checkaccount() {
	String account = mAccounts.getText().toString();
	// 判断是否为管理员

	if ("123".equals(account)) {
	    return "admin";
	}
	Boolean isRight = false;
	DBHelper dbHelper = new DBHelper(this);
	List<HashMap<String, Object>> userList = dbHelper.getAllUser();
	for (int i = 0; i < userList.size(); i++) {
	    String phone = userList.get(i).get("mobilephone").toString();
	    if (phone.equals(account)) {
		isRight = true;
		break;
	    }
	}
	if (isRight) {
	    return "customer";
	} else {
	    return "error";
	}
    }

    private void cancleDialog() {
	if (mDialog.isShowing()) {
	    mDialog.dismiss();
	}
    }

    /**
     * @方法名：exitDialog
     * @功能描述：退出时的提示框
     * @创建人：xufan
     * @创建时间：2013-4-16 下午3:04:01
     * @参数：@param context
     * @参数：@param title
     * @参数：@param msg
     * @返回：void
     * @throws
     */
    private void exitDialog(Context context, String title, String msg) {
	new AlertDialog.Builder(context).setTitle(title).setMessage(msg)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
			finish();
		    }
		}).setNegativeButton("取消", null).create().show();
    }

    private void showRequestDialog() {
	if (mDialog != null) {
	    mDialog.dismiss();
	    mDialog = null;
	}
	mDialog = DialogFactory.creatRequestDialog(this, "正在验证账号...");
	mDialog.show();
    }

    @Override
    public void onBackPressed() {// 捕获返回按键
	exitDialog(LoginActivity.this, "退出提示", "亲！您真的要退出吗？");
    }
}
