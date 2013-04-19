/**********************************************************************
 * FILE			：ViewPageFragment.java
 * PACKAGE		：com.xufan.fragmemtn
 * AUTHOR		：xufan
 * DATE			：2013-4-17 下午2:20:00
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
package com.xufan.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.xufan.activity.ContactsActivity;
import com.xufan.callingnumber.R;

/**
 * 项目名称：CallingNumber
 * 类名称：ViewPageFragment
 * 类描述：
 * 创建人：xufan
 * 创建时间：2013-4-17 下午2:20:00
 * -------------------------------修订历史--------------------------
 * 修改人：xufan
 * 修改时间：2013-4-17 下午2:20:00
 * 修改备注：
 * @version：
*/
public class ViewPageFragment extends Fragment {

    private Button showLeft;
    private MyAdapter mAdapter;
    private ViewPager mPager;
    private final ArrayList<Fragment> pagerItemList = new ArrayList<Fragment>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
	View mView = inflater.inflate(R.layout.view_pager, null);
	showLeft = (Button) mView.findViewById(R.id.showLeft);
	mPager = (ViewPager) mView.findViewById(R.id.pager);
	PhoneFragment Phone = new PhoneFragment();
	pagerItemList.add(Phone);
	mAdapter = new MyAdapter(getFragmentManager());
	mPager.setAdapter(mAdapter);
	mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
	    @Override
	    public void onPageSelected(int position) {
		if (myPageChangeListener != null)
		    myPageChangeListener.onPageSelected(position);
	    }

	    @Override
	    public void onPageScrolled(int arg0, float arg1, int arg2) {

	    }

	    @Override
	    public void onPageScrollStateChanged(int position) {

	    }
	});

	return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);
	showLeft.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		((ContactsActivity) getActivity()).showLeft();
	    }
	});

    }

    public boolean isFirst() {
	if (mPager.getCurrentItem() == 0)
	    return true;
	else
	    return false;
    }

    public boolean isEnd() {
	if (mPager.getCurrentItem() == pagerItemList.size() - 1)
	    return true;
	else
	    return false;
    }

    public class MyAdapter extends FragmentPagerAdapter {
	public MyAdapter(FragmentManager fm) {
	    super(fm);
	}

	@Override
	public int getCount() {
	    return pagerItemList.size();
	}

	@Override
	public Fragment getItem(int position) {
	    Fragment fragment = null;
	    if (position < pagerItemList.size())
		fragment = pagerItemList.get(position);
	    else
		fragment = pagerItemList.get(0);
	    return fragment;
	}
    }

    private MyPageChangeListener myPageChangeListener;

    public void setMyPageChangeListener(MyPageChangeListener l) {
	myPageChangeListener = l;
    }

    public interface MyPageChangeListener {
	public void onPageSelected(int position);
    }

}
