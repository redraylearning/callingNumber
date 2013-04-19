/**********************************************************************
 * FILE			：MeunActivity.java
 * PACKAGE		：com.xufan.activity
 * AUTHOR		：xufan
 * DATE			：2013-4-18 上午10:27:47
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

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.xufan.callingnumber.R;
import com.xufan.config.Contents;
import com.xufan.util.CustomToast;

/**
 * 项目名称：CallingNumber
 * 类名称：MeunActivity
 * 类描述：底部菜单
 * 创建人：xufan
 * 创建时间：2013-4-18 上午10:27:47
 * -------------------------------修订历史--------------------------
 * 修改人：xufan
 * 修改时间：2013-4-18 上午10:27:47
 * 修改备注：
 * @version：
*/
public class MeunActivity extends ImportAndExportActivity {
    /** 屏幕下方的工具栏*/
    GridView bottomMenuGrid;
    /**存储标记条目的_id号*/
    ArrayList<Integer> deleteId;

    /**加载底部菜单*/
    public void loadBottomMenu() {
	if (bottomMenuGrid == null) {
	    bottomMenuGrid = (GridView) findViewById(R.id.gv_buttom_menu);
	    bottomMenuGrid.setBackgroundResource(R.drawable.channelgallery_bg);// 设置背景
	    bottomMenuGrid.setNumColumns(5);// 设置每行列数
	    bottomMenuGrid.setGravity(Gravity.CENTER);// 位置居中
	    bottomMenuGrid.setVerticalSpacing(10);// 垂直间隔
	    bottomMenuGrid.setHorizontalSpacing(10);// 水平间隔
	    bottomMenuGrid.setAdapter(getMenuAdapter(Contents.BOTTOM_MENU_ITEMNAME,
		    Contents.BOTTOM_MENU_ITEMSOURCE));// 设置菜单Adapter
	    /** 监听底部菜单选项 **/
	    bottomMenuGrid.setOnItemClickListener(new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		    switch (arg2) {
		    case 0: {
			if (bottomMenuGrid.getVisibility() == View.VISIBLE) {
			    bottomMenuGrid.setVisibility(View.GONE);
			}
			Intent intent = new Intent(MeunActivity.this, AddNewActivity.class);
			startActivityForResult(intent, 3);
			break;
		    }
		    case 1:
			if (deleteId == null || deleteId.size() == 0) {
			    CustomToast.showToast(MeunActivity.this, "没有标记任何记录\n长按一条记录即可标记");
			} else {
			    new AlertDialog.Builder(MeunActivity.this)
				    .setTitle("确定要删除标记的" + deleteId.size() + "条记录吗?")
				    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					    helper.deleteMarked(deleteId);
					    resetListView();
					    deleteId.clear();
					}
				    }).setNegativeButton("取消", null).create().show();
			}
			break;
		    case 2:
			new AlertDialog.Builder(MeunActivity.this).setTitle("是否需要导入到手机通讯录中？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
					list = helper.getAllUser();
					for (int i = 0; i < list.size(); i++) {
					    String name = list.get(i).get("name").toString();
					    String phoneNumber = list.get(i).get("mobilephone").toString();
					    String email = list.get(i).get("email").toString();
					    Bitmap photo = (Bitmap) list.get(i).get("image");
					    add(name, phoneNumber, email, photo);
					}
					if (importflag != 0) {
					    if (importflag == list.size()) {
						CustomToast.showToast(MeunActivity.this, "导入失败，全部数据已经存在通讯录中");
					    } else {
						CustomToast.showToast(MeunActivity.this, "有" + importflag
							+ "条记录在通讯录中已经存在");
					    }
					}
				    }
				}).setNegativeButton("取消", null).create().show();
			break;
		    case 3:
			new AlertDialog.Builder(MeunActivity.this).setTitle("是否需要将手机通讯录导出？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
					getAll();
					resetListView();
				    }

				}).setNegativeButton("取消", null).create().show();
			break;
		    case 4:
			finish();
			break;
		    }
		}
	    });
	}
	if (bottomMenuGrid.getVisibility() == View.VISIBLE) {
	    bottomMenuGrid.setVisibility(View.GONE);
	} else {
	    bottomMenuGrid.setVisibility(View.VISIBLE);
	}
    }

    /**菜单adapter*/
    private SimpleAdapter getMenuAdapter(String[] menuNameArray, int[] imageResourceArray) {
	ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
	for (int i = 0; i < menuNameArray.length; i++) {
	    HashMap<String, Object> map = new HashMap<String, Object>();
	    map.put("itemImage", imageResourceArray[i]);
	    map.put("itemText", menuNameArray[i]);
	    data.add(map);
	}
	SimpleAdapter simperAdapter = new SimpleAdapter(this, data, R.layout.item_menu, new String[] {
		"itemImage", "itemText" }, new int[] { R.id.item_image, R.id.item_text });
	return simperAdapter;
    }

}
