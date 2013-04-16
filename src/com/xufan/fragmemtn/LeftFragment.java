package com.xufan.fragmemtn;

import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xufan.callingnumber.R;
import com.xufan.data.DBHelper;

public class LeftFragment extends Fragment {
    public List<HashMap<String, Object>> list;
    public String name;
    public int imageId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	Intent intent = getActivity().getIntent();
	String userPhone = intent.getStringExtra("userPhone");
	DBHelper dbHelper = new DBHelper(getActivity());
	list = dbHelper.getUsers(userPhone);
	name = list.get(0).get("name").toString();
	imageId = Integer.valueOf(list.get(0).get("imageid").toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
	View view = inflater.inflate(R.layout.left, null);
	ImageView imageView = (ImageView) view.findViewById(R.id.head_view);
	TextView nameText = (TextView) view.findViewById(R.id.grade_textview);
	nameText.setText(name);
	imageView.setImageResource(imageId);
	return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);
    }

}
