/**********************************************************************
 * FILE			：UserDetailActivity.java
 * PACKAGE		：com.xufan.activity
 * AUTHOR		：xufan
 * DATE			：2013-4-17 下午3:08:37
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

import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ViewSwitcher.ViewFactory;

import com.xufan.adapter.ImageAdapter;
import com.xufan.callingnumber.R;
import com.xufan.config.Contents;
import com.xufan.data.DBHelper;
import com.xufan.data.User;
import com.xufan.util.Utils;

/**
 * 项目名称：CallingNumber
 * 类名称：UserDetailActivity
 * 类描述：用户详情activity
 * 创建人：xufan
 * 创建时间：2013-4-17 下午3:08:37
 * -------------------------------修订历史--------------------------
 * 修改人：xufan
 * 修改时间：2013-4-17 下午3:08:37
 * 修改备注：
 * @version：
*/
public class UserDetailActivity extends Activity implements ViewFactory {
    DBHelper helper = new DBHelper(this);
    User user;
    EditText et_name;
    EditText et_mobilePhone;
    EditText et_email;

    Button btn_save;
    Button btn_return;
    Button btn_delete;
    // 头像的按钮
    ImageView imageButton;

    // 用flag来判断按钮的状态 false表示查看点击修改状态 true表示点击修改保存状态
    boolean flag = false;
    boolean isDataChanged = false;
    boolean imageChanged = false;
    View imageChooseView;
    Gallery gallery;
    ImageSwitcher is;
    int currentImagePosition;
    int previousImagePosition;

    /**图片选择的对话框*/
    AlertDialog imageChooseDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.userdetail);
	Intent intent = getIntent();
	user = (User) intent.getSerializableExtra("user");
	// 加载数据,往控件上赋值
	loadUserData();
	// 设置EditText不可编辑
	setEditTextDisable();

	// 为按钮添加监听类
	btn_save.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View arg0) {
		if (!flag) {
		    btn_save.setText("保存修改");
		    setEditTextAble();
		    flag = true;
		} else {
		    // 往数据库里面更新数据
		    modify();
		    setEditTextDisable();
		    btn_save.setText("修改");
		    flag = false;
		}
	    }
	});

	btn_delete.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		new AlertDialog.Builder(UserDetailActivity.this)
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
				delete();
				setResult(4);
				finish();
			    }
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			    }
			}).setTitle("是否要删除?").create().show();
	    }
	});

	btn_return.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		if (isDataChanged) {
		    setResult(4);
		} else {
		    setResult(5);
		}
		finish();
	    }
	});

	imageButton.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		loadImage();// 加载imageChooseView，只加载一次
		initImageChooseDialog();// 加载imageChooseDialog，只加载一次
		imageChooseDialog.show();

	    }
	});
    }

    public void loadImage() {
	if (imageChooseView == null) {
	    LayoutInflater li = LayoutInflater.from(this);
	    imageChooseView = li.inflate(R.layout.imageswitch, null);
	    gallery = (Gallery) imageChooseView.findViewById(R.id.gallery);
	    gallery.setAdapter(new ImageAdapter(this, Contents.IMAGES));
	    gallery.setSelection(Contents.IMAGES.length / 2);
	    is = (ImageSwitcher) imageChooseView.findViewById(R.id.imageswitch);
	    is.setFactory(this);
	    gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		    currentImagePosition = arg2;
		    is.setImageResource(Contents.IMAGES[arg2]);
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	    });
	}
    }

    public void initImageChooseDialog() {
	if (imageChooseDialog == null) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setTitle("请选择图像").setView(imageChooseView)
		    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			    imageChanged = true;
			    previousImagePosition = currentImagePosition;
			    imageButton.setImageResource(Contents.IMAGES[currentImagePosition]);
			}
		    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			    currentImagePosition = previousImagePosition;
			}
		    });
	    imageChooseDialog = builder.create();
	}
    }

    public void loadUserData() {
	et_name = (EditText) findViewById(R.id.name);
	et_mobilePhone = (EditText) findViewById(R.id.phoneNum);
	et_email = (EditText) findViewById(R.id.email);
	btn_save = (Button) findViewById(R.id.save);
	btn_return = (Button) findViewById(R.id.btn_return);
	btn_delete = (Button) findViewById(R.id.delete);
	imageButton = (ImageView) findViewById(R.id.head_view);
	// 为控件赋值
	et_name.setText(user.username);
	et_mobilePhone.setText(user.mobilePhone);
	et_email.setText(user.email);
	imageButton.setImageBitmap(BitmapFactory.decodeByteArray(user.image, 0, user.image.length));
    }

    /**删除*/
    private void delete() {
	helper.delete(user._id);
    }

    /**修改*/
    private void modify() {
	user.setUsername(et_name.getText().toString());
	user.setMobilePhone(et_mobilePhone.getText().toString());
	user.setEmail(et_email.getText().toString());
	if (imageChanged) {
	    InputStream is = getResources().openRawResource(Contents.IMAGES[currentImagePosition]);
	    user.setImage(Utils.bmpToByteArray(BitmapFactory.decodeStream(is)));
	} else {

	}
	helper.modify(user);
	isDataChanged = true;
    }

    private void setEditTextDisable() {
	et_name.setEnabled(false);
	et_mobilePhone.setEnabled(false);
	et_email.setEnabled(false);
	imageButton.setEnabled(false);
    }

    private void setEditTextAble() {
	et_name.setEnabled(true);
	et_mobilePhone.setEnabled(true);
	et_email.setEnabled(true);
	imageButton.setEnabled(true);
    }

    /** 
     * @方法名：makeView
     * @功能描述：
     * @参数：@return
     * @throws
     */
    @Override
    public View makeView() {
	ImageView view = new ImageView(this);
	view.setBackgroundColor(0xff000000);
	view.setScaleType(ScaleType.FIT_CENTER);
	view.setLayoutParams(new ImageSwitcher.LayoutParams(90, 90));
	return view;
    }
}
