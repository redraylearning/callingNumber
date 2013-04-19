/**********************************************************************
 * FILE			：ImageAdapter.java
 * PACKAGE		：com.xufan.adapter
 * AUTHOR		：xufan
 * DATE			：2013-4-17 上午11:50:12
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

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

/**
 * 项目名称：CallingNumber
 * 类名称：ImageAdapter
 * 类描述：
 * 创建人：xufan
 * 创建时间：2013-4-17 上午11:50:12
 * -------------------------------修订历史--------------------------
 * 修改人：xufan
 * 修改时间：2013-4-17 上午11:50:12
 * 修改备注：
 * @version：
*/
public class ImageAdapter extends BaseAdapter {

    private final Context context;
    private final int[] images;

    public ImageAdapter(Context context, int[] images) {
	this.context = context;
	this.images = images;
    }

    /** 
     * @方法名：getCount
     * @功能描述：
     * @参数：@return
     * @throws
     */
    @Override
    public int getCount() {
	return images.length;
    }

    /** 
     * @方法名：getItem
     * @功能描述：
     * @参数：@param arg0
     * @参数：@return
     * @throws
     */
    @Override
    public Object getItem(int arg0) {
	// TODO Auto-generated method stub
	return null;
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
	// TODO Auto-generated method stub
	return 0;
    }

    /**
     * gallery从这个方法中拿到image
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	ImageView iv = new ImageView(context);
	iv.setImageResource(images[position]);
	iv.setAdjustViewBounds(true);
	iv.setLayoutParams(new Gallery.LayoutParams(80, 80));
	iv.setPadding(15, 10, 15, 10);
	return iv;
    }

}
