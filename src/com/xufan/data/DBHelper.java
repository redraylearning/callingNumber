/**********************************************************************
 * FILE			：DBHelper.java
 * PACKAGE		：com.xufan.data
 * AUTHOR		：xufan
 * DATE			：2013-4-16 上午11:32:59
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

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 项目名称：CallingNumber
 * 类名称：DBHelper
 * 类描述：
 * 创建人：xufan
 * 创建时间：2013-4-16 上午11:32:59
 * -------------------------------修订历史--------------------------
 * 修改人：xufan
 * 修改时间：2013-4-16 上午11:32:59
 * 修改备注：
 * @version：
*/
public class DBHelper {

    public static final String DB_DBNAME = "contact";

    public static final String DB_TABLENAME = "user";

    public static final int VERSION = 1;

    public static SQLiteDatabase dbInstance;

    private MyDBHelper myDBHelper;

    private StringBuffer tableCreate;

    private final Context context;

    public DBHelper(Context context) {
	this.context = context;
    }

    public void openDatabase() {
	if (dbInstance == null) {
	    myDBHelper = new MyDBHelper(context, DB_DBNAME, VERSION);
	    dbInstance = myDBHelper.getWritableDatabase();
	}
    }

    public void deleteDateBase() {
	dbInstance.execSQL("DROP TABLE IF EXISTS user");
    }

    /**
     * @方法名：insert
     * @功能描述：往数据库里面的user表插入一条数据，若失败返回-1
     * @创建人：xufan
     * @创建时间：2013-4-16 上午11:34:30
     * @参数：@param user isFirstTime
     * @参数：@return 失败返回-1
     * @返回：long
     * @throws
     */
    public long insert(User user, Boolean isFirstTime) {
	ContentValues values = new ContentValues();
	values.put("name", user.getUsername());
	values.put("mobilephone", user.getMobilePhone());
	values.put("email", user.getEmail());
	if (isFirstTime) {
	    values.put("imageid", 0x7f02000a);
	} else {
	    values.put("imageid", user.getImageId());
	}
	return dbInstance.insert(DB_TABLENAME, null, values);
    }

    /**
     * @方法名：getAllUser
     * @功能描述：获得数据库中所有的用户，将每一个用户放到一个map中去，然后再将map放到list里面去返回
     * @创建人：xufan
     * @创建时间：2013-4-16 上午11:34:47
     * @参数：@return
     * @返回：ArrayList
     * @throws
     */
    public ArrayList<HashMap<String, Object>> getAllUser() {
	ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
	Cursor cursor = null;
	cursor = dbInstance.query(DB_TABLENAME, new String[] { "_id", "name",
		"mobilephone", "email", "imageid" }, null, null, null, null,
		null);

	while (cursor.moveToNext()) {
	    HashMap<String, Object> item = new HashMap<String, Object>();
	    item.put("_id", cursor.getInt(cursor.getColumnIndex("_id")));
	    item.put("name", cursor.getString(cursor.getColumnIndex("name")));
	    item.put("mobilephone",
		    cursor.getString(cursor.getColumnIndex("mobilephone")));
	    item.put("email", cursor.getString(cursor.getColumnIndex("email")));
	    item.put("imageid", cursor.getInt(cursor.getColumnIndex("imageid")));
	    list.add(item);
	}

	return list;
    }

    /**
     * @方法名：modify
     * @功能描述：修改用户
     * @创建人：xufan
     * @创建时间：2013-4-16 上午11:35:04
     * @参数：@param user
     * @返回：void
     * @throws
     */
    public void modify(User user) {
	ContentValues values = new ContentValues();
	values.put("name", user.username);
	values.put("mobilephone", user.mobilePhone);
	values.put("email", user.email);
	values.put("imageid", user.imageId);

	dbInstance.update(DB_TABLENAME, values, "_id=?",
		new String[] { String.valueOf(user._id) });
    }

    /**
     * @方法名：delete
     * @功能描述：删除指定用户
     * @创建人：xufan
     * @创建时间：2013-4-16 上午11:35:27
     * @参数：@param _id
     * @返回：void
     * @throws
     */
    public void delete(int _id) {
	dbInstance.delete(DB_TABLENAME, "_id=?",
		new String[] { String.valueOf(_id) });
    }

    /**
     * @方法名：deleteAll
     * @功能描述：删除全部
     * @创建人：xufan
     * @创建时间：2013-4-16 上午11:37:56
     * @参数：
     * @返回：void
     * @throws
     */
    public void deleteAll() {
	dbInstance.delete(DB_TABLENAME, null, new String[] { null });
    }

    /**
     * @方法名：getTotalCount
     * @功能描述：获得全部数量
     * @创建人：xufan
     * @创建时间：2013-4-16 上午11:43:08
     * @参数：@return
     * @返回：int
     * @throws
     */
    public int getTotalCount() {
	Cursor cursor = dbInstance.query(DB_TABLENAME,
		new String[] { "count(*)" }, null, null, null, null, null);
	cursor.moveToNext();
	return cursor.getInt(0);
    }

    /**
     * @方法名：getUsers
     * @功能描述：条件查询
     * @创建人：xufan
     * @创建时间：2013-4-16 上午11:43:28
     * @参数：@param condition
     * @参数：@return
     * @返回：ArrayList
     * @throws
     */
    public ArrayList<HashMap<String, Object>> getUsers(String condition) {
	ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

	String sql = "select * from " + DB_TABLENAME
		+ " where 1=1 and (name like '%" + condition + "%' "
		+ "or mobilephone like '%" + condition + "%')";
	Cursor cursor = dbInstance.rawQuery(sql, null);
	while (cursor.moveToNext()) {
	    HashMap<String, Object> item = new HashMap<String, Object>();
	    item.put("_id", cursor.getInt(cursor.getColumnIndex("_id")));
	    item.put("name", cursor.getString(cursor.getColumnIndex("name")));
	    item.put("mobilephone",
		    cursor.getString(cursor.getColumnIndex("mobilephone")));
	    item.put("email", cursor.getString(cursor.getColumnIndex("email")));
	    item.put("imageid", cursor.getInt(cursor.getColumnIndex("imageid")));
	    list.add(item);
	}
	return list;
    }

    /**
     * @方法名：deleteMarked
     * @功能描述：删除标记用户
     * @创建人：xufan
     * @创建时间：2013-4-16 上午11:43:43
     * @参数：@param deleteId
     * @返回：void
     * @throws
     */
    public void deleteMarked(ArrayList<Integer> deleteId) {
	StringBuffer strDeleteId = new StringBuffer();
	strDeleteId.append("_id=");
	for (int i = 0; i < deleteId.size(); i++) {
	    if (i != deleteId.size() - 1) {
		strDeleteId.append(deleteId.get(i) + " or _id=");
	    } else {
		strDeleteId.append(deleteId.get(i));
	    }

	}
	dbInstance.delete(DB_TABLENAME, strDeleteId.toString(), null);
	Log.d("Tag", strDeleteId.toString());

    }

    class MyDBHelper extends SQLiteOpenHelper {

	public MyDBHelper(Context context, String name, int version) {
	    super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	    tableCreate = new StringBuffer();
	    tableCreate.append("create table ").append(DB_TABLENAME)
		    .append(" (")
		    .append("_id integer primary key autoincrement,")
		    .append("name text,").append("mobilephone text,")
		    .append("email text,").append("imageid int").append(")");
	    Log.d("Tag", tableCreate.toString());
	    db.execSQL(tableCreate.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    String sql = "drop table if exists " + DB_TABLENAME;
	    db.execSQL(sql);
	    myDBHelper.onCreate(db);
	}

    }

}
