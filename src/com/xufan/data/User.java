/**********************************************************************
 * FILE			：User.java
 * PACKAGE		：com.xufan.data
 * AUTHOR		：xufan
 * DATE			：2013-4-16 上午11:44:26
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
package com.xufan.data;

import java.io.Serializable;

/**
 * 项目名称：CallingNumber
 * 类名称：User
 * 类描述：
 * 创建人：xufan
 * 创建时间：2013-4-16 上午11:44:26
 * -------------------------------修订历史--------------------------
 * 修改人：xufan
 * 修改时间：2013-4-16 上午11:44:26
 * 修改备注：
 * @version：
*/
public class User implements Serializable {

    /** 
     * @Fields serialVersionUID : TODO 
    */
    private static final long serialVersionUID = 1L;

    public int _id;

    public String username;

    public String mobilePhone;

    public String email;

    public byte[] image;

    public int get_id() {
	return _id;
    }

    public void set_id(int _id) {
	this._id = _id;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getMobilePhone() {
	return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
	this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public byte[] getImage() {
	return image;
    }

    public void setImage(byte[] image) {
	this.image = image;
    }

}
