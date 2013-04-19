package com.xufan.fragment;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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

public class LeftFragment extends Fragment implements ViewFactory {
    DBHelper dbHelper = new DBHelper(getActivity());
    private List<HashMap<String, Object>> list;
    private int _id;
    private String name;
    private String phoneNum;
    private String email;
    private Bitmap image;
    private ImageView imageView;
    private EditText nameText;
    private EditText phoneText;
    private EditText emailText;
    private Button editButton;
    /**用flag来判断按钮的状态   false表示查看点击修改状态  true表示点击修改保存状态*/
    boolean flag = false;
    View imageChooseView;
    Gallery gallery;
    ImageSwitcher is;
    int currentImagePosition;
    int previousImagePosition;
    boolean imageChanged = false;
    /**图片选择的对话框*/
    AlertDialog imageChooseDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	Intent intent = getActivity().getIntent();
	String userPhone = intent.getStringExtra("userPhone");
	list = dbHelper.getUsers(userPhone);
	name = list.get(0).get("name").toString();
	_id = Integer.valueOf(list.get(0).get("_id").toString());
	phoneNum = list.get(0).get("mobilephone").toString();
	email = list.get(0).get("email").toString();
	if (list.get(0).get("image") == null) {
	    InputStream is = getResources().openRawResource(R.drawable.head_default);
	    image = BitmapFactory.decodeStream(is);
	} else {
	    image = (Bitmap) list.get(0).get("image");
	}

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View view = inflater.inflate(R.layout.left, null);
	imageView = (ImageView) view.findViewById(R.id.head_view);
	nameText = (EditText) view.findViewById(R.id.name);
	phoneText = (EditText) view.findViewById(R.id.phoneNum);
	emailText = (EditText) view.findViewById(R.id.email);
	editButton = (Button) view.findViewById(R.id.edit);

	init();
	return view;
    }

    /** 
     * @方法名：makeView
     * @功能描述：
     * @参数：@return
     * @throws
     */
    @Override
    public View makeView() {
	ImageView view = new ImageView(getActivity());
	view.setBackgroundColor(0xff000000);
	view.setScaleType(ScaleType.FIT_CENTER);
	view.setLayoutParams(new ImageSwitcher.LayoutParams(90, 90));
	return view;
    }

    /**
     * @方法名：init
     * @功能描述：初始化数据
     * @创建人：xufan
     * @创建时间：2013-4-17 下午2:13:47
     * @参数：
     * @返回：void
     * @throws
     */
    private void init() {
	nameText.setText(name);
	phoneText.setText(phoneNum);
	emailText.setText(email);
	imageView.setImageBitmap(image);
	setEditTextDisable();

	editButton.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		if (!flag) {
		    editButton.setText("保存");
		    setEditTextEnable();
		    flag = true;
		} else {
		    editButton.setText("编辑");
		    modify();
		    setEditTextDisable();
		    flag = false;
		}
	    }
	});
	imageView.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		loadImage();// 加载imageChooseView，只加载一次
		initImageChooseDialog();// 加载imageChooseDialog，只加载一次
		imageChooseDialog.show();
	    }
	});
    }

    /**
     * @方法名：modify
     * @功能描述：修改数据库内容
     * @创建人：xufan
     * @创建时间：2013-4-17 上午11:43:35
     * @参数：
     * @返回：void
     * @throws
     */
    private void modify() {
	User user = new User();
	user.setUsername(nameText.getText().toString());
	user.setMobilePhone(phoneText.getText().toString());
	user.setEmail(emailText.getText().toString());
	user.set_id(_id);
	if (imageChanged) {
	    InputStream is = getResources().openRawResource(Contents.IMAGES[currentImagePosition]);
	    user.setImage(Utils.bmpToByteArray(BitmapFactory.decodeStream(is)));
	}
	dbHelper.modify(user);
    }

    /**
     * @方法名：loadImage
     * @功能描述：装载图片
     * @创建人：xufan
     * @创建时间：2013-4-17 下午2:12:33
     * @参数：
     * @返回：void
     * @throws
     */
    public void loadImage() {
	if (imageChooseView == null) {
	    LayoutInflater li = LayoutInflater.from(getActivity());
	    imageChooseView = li.inflate(R.layout.imageswitch, null);
	    gallery = (Gallery) imageChooseView.findViewById(R.id.gallery);
	    gallery.setAdapter(new ImageAdapter(getActivity(), Contents.IMAGES));
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

    /**
     * @方法名：initImageChooseDialog
     * @功能描述：初始化图片选择对话框
     * @创建人：xufan
     * @创建时间：2013-4-17 下午2:12:47
     * @参数：
     * @返回：void
     * @throws
     */
    public void initImageChooseDialog() {
	if (imageChooseDialog == null) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    builder.setTitle("请选择图像").setView(imageChooseView)
		    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			    imageChanged = true;
			    previousImagePosition = currentImagePosition;
			    imageView.setImageResource(Contents.IMAGES[currentImagePosition]);
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

    private void setEditTextDisable() {
	imageView.setEnabled(false);
	nameText.setEnabled(false);
	phoneText.setEnabled(false);
	emailText.setEnabled(false);
    }

    private void setEditTextEnable() {
	imageView.setEnabled(true);
	nameText.setEnabled(true);
	phoneText.setEnabled(true);
	emailText.setEnabled(true);
    }

    @Override
    public void onDestroyView() {
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
	super.onDestroyView();
    }

}
