/*
 * Copyright (C) 2012 yueyueniao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xufan.fragmemtn;

import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.xufan.callingnumber.R;
import com.xufan.data.DBHelper;

public class PhoneFragment extends Fragment {
    private ListView phoneList;
    public List<HashMap<String, Object>> list;
    SimpleAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	DBHelper helper = new DBHelper(getActivity());
	list = helper.getAllUser();
	adapter = new SimpleAdapter(getActivity(), list, R.layout.listitem,
		new String[] { "imageid", "name", "mobilephone", "email" },
		new int[] { R.id.user_image, R.id.tv_name, R.id.tv_mobilephone,
			R.id.tv_eamil });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
	View view = inflater.inflate(R.layout.phone_list, null);
	phoneList = (ListView) view.findViewById(R.id.lv_userlist);
	phoneList.setAdapter(adapter);

	return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);
    }

}
