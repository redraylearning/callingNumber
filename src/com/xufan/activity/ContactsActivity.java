package com.xufan.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.xufan.adapter.SlidingMenu;
import com.xufan.callingnumber.R;
import com.xufan.fragmemtn.LeftFragment;
import com.xufan.fragmemtn.ViewPageFragment;
import com.xufan.fragmemtn.ViewPageFragment.MyPageChangeListener;

public class ContactsActivity extends FragmentActivity {
    SlidingMenu mSlidingMenu;
    LeftFragment leftFragment;
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
	mSlidingMenu.setLeftView(getLayoutInflater().inflate(
		R.layout.left_frame, null));
	mSlidingMenu.setCenterView(getLayoutInflater().inflate(
		R.layout.center_frame, null));

	FragmentTransaction t = this.getSupportFragmentManager()
		.beginTransaction();
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
