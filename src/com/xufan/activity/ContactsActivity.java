/**********************************************************************
 * FILE			：ContactsActivity.java
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

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.xufan.adapter.SlidingMenu;
import com.xufan.callingnumber.R;
import com.xufan.fragment.LeftFragment;
import com.xufan.fragment.ViewPageFragment;
import com.xufan.fragment.ViewPageFragment.MyPageChangeListener;

/**
 * 项目名称：CallingNumber
 * 类名称：ContactsActivity
 * 类描述：普通用户进入activity界面
 * 创建人：xufan
 * 创建时间：2013-4-17 下午4:03:15
 * -------------------------------修订历史--------------------------
 * 修改人：xufan
 * 修改时间：2013-4-17 下午4:03:15
 * 修改备注：
 * @version：
*/
public class ContactsActivity extends FragmentActivity {
    /**滑动菜单*/
    SlidingMenu mSlidingMenu;
    /**菜单fragment*/
    LeftFragment leftFragment;
    /**主显示fragement*/
    ViewPageFragment viewPageFragment;

    @Override
    protected void onCreate(Bundle arg0) {
	super.onCreate(arg0);
	setContentView(R.layout.sliding_menu);
	init();
	initListener();
    }

    private void init() {
	mSlidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);
	mSlidingMenu.setLeftView(getLayoutInflater().inflate(R.layout.left_frame, null));
	mSlidingMenu.setCenterView(getLayoutInflater().inflate(R.layout.center_frame, null));

	FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
	leftFragment = new LeftFragment();
	t.replace(R.id.left_frame, leftFragment);

	viewPageFragment = new ViewPageFragment();
	t.replace(R.id.center_frame, viewPageFragment);
	t.commit();
    }

    private void initListener() {
	viewPageFragment.setMyPageChangeListener(new MyPageChangeListener() {
	    @Override
	    public void onPageSelected(int position) {
		if (viewPageFragment.isFirst()) {
		    mSlidingMenu.setCanSliding(true, false);
		} else if (viewPageFragment.isEnd()) {
		    mSlidingMenu.setCanSliding(false, true);
		} else {
		    mSlidingMenu.setCanSliding(false, false);
		}
	    }
	});
    }

    public void showLeft() {
	mSlidingMenu.showLeftView();
    }

}
