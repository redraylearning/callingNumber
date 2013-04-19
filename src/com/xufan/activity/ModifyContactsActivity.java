/**********************************************************************
 * FILE			：ModifyContactsActivity.java
 * PACKAGE		：com.xufan.activity
 * AUTHOR		：xufan
 * DATE			：2013-4-17 上午10:27:47
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
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xufan.callingnumber.R;
import com.xufan.data.User;
import com.xufan.util.Utils;

/**
 * 项目名称：CallingNumber
 * 类名称：ModifyContactsActivity
 * 类描述：管理员进入的activity
 * 创建人：xufan
 * 创建时间：2013-4-17 上午10:27:47
 * -------------------------------修订历史--------------------------
 * 修改人：xufan
 * 修改时间：2013-4-17 上午10:27:47
 * 修改备注：
 * @version：
*/
public class ModifyContactsActivity extends MeunActivity {
    @SuppressWarnings("rawtypes")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.admin_contacts);
	resetListView();

	listView.setOnItemClickListener(new OnItemClickListener() {
	    @Override
	    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		HashMap item = (HashMap) arg0.getItemAtPosition(arg2);
		Intent intent = new Intent(ModifyContactsActivity.this, UserDetailActivity.class);
		User user = new User();
		user._id = Integer.parseInt(String.valueOf(item.get("_id")));
		user.email = String.valueOf(item.get("email"));
		user.mobilePhone = String.valueOf(item.get("mobilephone"));
		user.username = String.valueOf(item.get("name"));
		if (item.get("image") == null) {
		    InputStream is = getResources().openRawResource(R.drawable.head_default);
		    user.image = Utils.bmpToByteArray(BitmapFactory.decodeStream(is));
		} else {
		    user.image = Utils.bmpToByteArray((Bitmap) item.get("image"));
		}
		intent.putExtra("user", user);

		// 将arg2作为请求码传过去 用于标识修改项的位置
		startActivityForResult(intent, arg2);
	    }
	});

	listView.setCacheColorHint(Color.TRANSPARENT); // 设置ListView的背景为透明
	listView.setOnItemLongClickListener(new OnItemLongClickListener() {
	    @Override
	    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (deleteId == null) {
		    deleteId = new ArrayList<Integer>();
		}
		HashMap item = (HashMap) arg0.getItemAtPosition(arg2);
		Integer _id = Integer.parseInt(String.valueOf(item.get("_id")));

		RelativeLayout r = (RelativeLayout) arg1;
		ImageView markedView = (ImageView) r.getChildAt(2);
		if (markedView.getVisibility() == View.VISIBLE) {
		    markedView.setVisibility(View.GONE);
		    deleteId.remove(_id);
		} else {
		    markedView.setVisibility(View.VISIBLE);
		    deleteId.add(_id);
		}
		return true;
	    }
	});
	// 为list添加item选择器
	Drawable bgDrawable = getResources().getDrawable(R.drawable.list_bg);
	listView.setSelector(bgDrawable);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	// 清除deleteId的内容
	if (deleteId != null) {
	    deleteId.clear();
	}
	// 当resultCode==3时代表添加了一个用户返回，当resultCode==4的时候代表修改了用户，或者删除了用户，其他条件代表数据没有变化
	if (resultCode == 3 || resultCode == 4) {
	    resetListView();
	}
	/**
	 * resultCode只有3、4、5 
	 * 当等于4或者5的时候，代表由UserDetailActivity转过来的。在转向UserDetailActivity的时候，requestCode的值设置的是选中项的位置
	 */
	if (resultCode == 3) {
	    listView.setSelection(list.size());
	} else {
	    listView.setSelection(requestCode);
	}
    }

    /**
     * 响应点击Menu按钮时的事件，用于设置底部菜单是否可见
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
	if (keyCode == KeyEvent.KEYCODE_MENU) {
	    loadBottomMenu();
	}
	return super.onKeyDown(keyCode, event);
    }

}
