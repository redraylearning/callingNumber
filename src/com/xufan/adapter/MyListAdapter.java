/**********************************************************************
 * FILE			：MyListAdapter.java
 * PACKAGE		：com.xufan.adapter
 * AUTHOR		：xufan
 * DATE			：2013-4-18 下午5:50:34
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
package com.xufan.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xufan.callingnumber.R;

/**
 * 项目名称：CallingNumber
 * 类名称：MyListAdapter
 * 类描述：
 * 创建人：xufan
 * 创建时间：2013-4-18 下午5:50:34
 * -------------------------------修订历史--------------------------
 * 修改人：xufan
 * 修改时间：2013-4-18 下午5:50:34
 * 修改备注：
 * @version：
*/
public class MyListAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<HashMap<String, Object>> list;

    public MyListAdapter(Context context, ArrayList<HashMap<String, Object>> list) {
	this.mContext = context;
	this.list = list;
    }

    /** 
     * @方法名：getCount
     * @功能描述：
     * @参数：@return
     * @throws
     */
    @Override
    public int getCount() {
	return list.size();
    }

    /** 
     * @方法名：getItem
     * @功能描述：
     * @参数：@param position
     * @参数：@return
     * @throws
     */
    @Override
    public Object getItem(int position) {
	return list.get(position);
    }

    /** 
     * @方法名：getItemId
     * @功能描述：
     * @参数：@param position
     * @参数：@return
     * @throws
     */
    @Override
    public long getItemId(int position) {
	return Long.valueOf(position);
    }

    /** 
     * @方法名：getView
     * @功能描述：
     * @参数：@param position
     * @参数：@param convertView
     * @参数：@param parent
     * @参数：@return
     * @throws
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	final ViewHolder holder;
	if (convertView == null) {
	    convertView = LayoutInflater.from(mContext).inflate(R.layout.listitem, null);
	    holder = new ViewHolder();
	    holder.name = (TextView) convertView.findViewById(R.id.tv_name);
	    holder.phone = (TextView) convertView.findViewById(R.id.tv_mobilephone);
	    holder.email = (TextView) convertView.findViewById(R.id.tv_eamil);
	    holder.image = (ImageView) convertView.findViewById(R.id.user_image);
	    convertView.setTag(holder);
	} else {
	    holder = (ViewHolder) convertView.getTag();
	}
	holder.name.setText(list.get(position).get("name").toString());
	holder.phone.setText(list.get(position).get("mobilephone").toString());
	holder.email.setText(list.get(position).get("email").toString());
	if (list.get(position).get("image") == null) {
	    holder.image.setImageResource(R.drawable.head_default);
	} else {
	    holder.image.setImageBitmap((Bitmap) list.get(position).get("image"));
	}

	return convertView;
    }

    static class ViewHolder {
	ImageView image;// 头像
	TextView name;// 姓名
	TextView phone;// 电话
	TextView email;// email
    }
}
