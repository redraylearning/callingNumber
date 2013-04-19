/**********************************************************************
 * FILE			：AddNewActivity.java
 * PACKAGE		：com.xufan.activity
 * AUTHOR		：xufan
 * DATE			：2013-4-17 下午3:28:45
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
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
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
import com.xufan.util.CustomToast;
import com.xufan.util.Utils;

/**
 * 项目名称：CallingNumber
 * 类名称：AddNewActivity
 * 类描述：
 * 创建人：xufan
 * 创建时间：2013-4-17 下午3:28:45
 * -------------------------------修订历史--------------------------
 * 修改人：xufan
 * 修改时间：2013-4-17 下午3:28:45
 * 修改备注：
 * @version：
*/
public class AddNewActivity extends Activity implements ViewFactory {

    EditText et_name;
    EditText et_mobilePhone;
    EditText et_email;
    Button btn_save;
    Button btn_return;

    int privacy;// 用于判断添加的用户是不是保密的
    ImageView imageButton;// 头像按钮
    View imageChooseView;// 图像选择的视图
    AlertDialog imageChooseDialog;// 头像选择对话框
    Gallery gallery;// 头像的Gallery
    ImageSwitcher is;// 头像的ImageSwitcher
    int currentImagePosition;// 用于记录当前选中图像在图像数组中的位置
    int previousImagePosition;// 用于记录上一次图片的位置
    boolean imageChanged;// 判断头像有没有变化

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.addnew);
	initView();

	/**
	 * 响应点击事件
	 */
	btn_save.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		// 判断姓名是否为空
		String name = et_name.getText().toString();
		if (name.trim().equals("")) {
		    CustomToast.showToast(AddNewActivity.this, "姓名不许为空");
		    return;
		}
		// 从表单上获取数据
		User user = new User();
		user.username = name;
		user.email = et_email.getText().toString();
		user.mobilePhone = et_mobilePhone.getText().toString();
		// 判断头像是否改变，若改变，则用当前的位置，若没有改变，则用前一回的位置
		if (imageChanged) {
		    InputStream is = getResources().openRawResource(Contents.IMAGES[currentImagePosition]);
		    user.image = Utils.bmpToByteArray(BitmapFactory.decodeStream(is));
		} else {
		    InputStream is = getResources().openRawResource(Contents.IMAGES[previousImagePosition]);
		    user.image = Utils.bmpToByteArray(BitmapFactory.decodeStream(is));
		}
		DBHelper helper = new DBHelper(AddNewActivity.this);
		helper.openDatabase();
		long result = helper.insert(user, false);

		// 通过结果来判断是否插入成功，若为1，则表示插入数据失败
		if (result == -1) {
		    CustomToast.showToast(AddNewActivity.this, "添加失败!");
		} else {
		    CustomToast.showToast(AddNewActivity.this, "用户添加成功!");
		    // 返回到上一个Activity，也就是Main.activity
		    setResult(3);
		    // 销毁当前视图
		    finish();
		}
	    }
	});

	btn_return.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		finish();
	    }
	});

	imageButton.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {

		loadImage();// 为gallery装载图片
		initImageChooseDialog();// 初始化imageChooseDialog
		imageChooseDialog.show();
	    }
	});

    }

    public void loadImage() {
	if (imageChooseView == null) {
	    LayoutInflater li = LayoutInflater.from(AddNewActivity.this);
	    imageChooseView = li.inflate(R.layout.imageswitch, null);

	    // 通过渲染xml文件，得到一个视图（View），再拿到这个View里面的Gallery
	    gallery = (Gallery) imageChooseView.findViewById(R.id.gallery);
	    // 为Gallery装载图片
	    gallery.setAdapter(new ImageAdapter(this, Contents.IMAGES));
	    gallery.setSelection(Contents.IMAGES.length / 2);
	    is = (ImageSwitcher) imageChooseView.findViewById(R.id.imageswitch);
	    is.setFactory(this);
	    is.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
	    // 卸载图片的动画效果
	    is.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
	    gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		    // 当前的头像位置为选中的位置
		    currentImagePosition = arg2;
		    // 为ImageSwitcher设置图像
		    is.setImageResource(Contents.IMAGES[arg2]);

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}
	    });
	}

    }

    @Override
    public View makeView() {
	ImageView view = new ImageView(this);
	view.setBackgroundColor(0xff000000);
	view.setScaleType(ScaleType.FIT_CENTER);
	view.setLayoutParams(new ImageSwitcher.LayoutParams(90, 90));
	return view;
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

    public void initView() {
	et_name = (EditText) findViewById(R.id.name);
	et_mobilePhone = (EditText) findViewById(R.id.phoneNum);
	et_email = (EditText) findViewById(R.id.email);
	btn_save = (Button) findViewById(R.id.save);
	btn_return = (Button) findViewById(R.id.btn_return);
	imageButton = (ImageView) findViewById(R.id.head_view);
    }

    /**
     * 当退出的时候，回收资源
     */
    @Override
    protected void onDestroy() {
	if (is != null) {
	    is = null;
	}
	if (gallery != null) {
	    gallery = null;
	}
	if (imageChooseDialog != null) {
	    imageChooseDialog = null;
	}
	if (imageChooseView != null) {
	    imageChooseView = null;
	}
	if (imageButton != null) {
	    imageButton = null;
	}

	super.onDestroy();
    }

}
