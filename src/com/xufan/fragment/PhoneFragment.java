/**********************************************************************
 * FILE			：PhoneFragment.java
 * PACKAGE		：com.xufan.fragmemtn
 * AUTHOR		：xufan
 * DATE			：2013-4-17 下午2:45:00
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
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.xufan.adapter.MyListAdapter;
import com.xufan.callingnumber.R;
import com.xufan.data.DBHelper;

/**
 * 项目名称：CallingNumber
 * 类名称：PhoneFragment
 * 类描述：
 * 创建人：xufan
 * 创建时间：2013-4-17 下午2:45:00
 * -------------------------------修订历史--------------------------
 * 修改人：xufan
 * 修改时间：2013-4-17 下午2:45:00
 * 修改备注：
 * @version：
*/
public class PhoneFragment extends Fragment {
    private ListView phoneList;
    public ArrayList<HashMap<String, Object>> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	DBHelper helper = new DBHelper(getActivity());
	Intent intent = getActivity().getIntent();
	String userPhone = intent.getStringExtra("userPhone");
	List<HashMap<String, Object>> id = helper.getUsers(userPhone);
	list = helper.getOtherUser(id.get(0).get("_id").toString());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View view = inflater.inflate(R.layout.phone_list, null);
	phoneList = (ListView) view.findViewById(R.id.lv_userlist);
	phoneList.setAdapter(new MyListAdapter(getActivity(), list));

	return view;
    }

    @Override
    public void onResume() {
	super.onResume();
	DBHelper helper = new DBHelper(getActivity());
	Intent intent = getActivity().getIntent();
	String userPhone = intent.getStringExtra("userPhone");
	List<HashMap<String, Object>> id = helper.getUsers(userPhone);
	list = helper.getOtherUser(id.get(0).get("_id").toString());
    }

}
