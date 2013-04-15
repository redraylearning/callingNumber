package com.xufan.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 项目名称：CallingNumber
 * 类名称：SQLiteUtil
 * 类描述：数据库公共类，提供基本数据库操作
 * 创建人：xufan
 * 创建时间：2013-4-15 下午5:36:55
 * -------------------------------修订历史--------------------------
 * 修改人：xufan
 * 修改时间：2013-4-15 下午5:36:55
 * 修改备注：
 * @version：
 */
public class SQLite {
    /**默认数据库*/
    private static final String DB_NAME = "phone_address.db";

    /** 数据库版本*/
    private static final int DB_VERSION = 1;

    /**执行open()打开数据库时，保存返回的数据库对象*/
    private SQLiteDatabase mSQLiteDatabase = null;

    /** 由SQLiteOpenHelper继承过来*/
    private DatabaseHelper mDatabaseHelper = null;

    /**本地Context对象*/
    private Context mContext = null;

    private static SQLite dbConn = null;

    /**查询游标对象*/
    private Cursor cursor;

    /**
     * 项目名称：CallingNumber
     * 类名称：DatabaseHelper
     * 类描述：SQLiteOpenHelper内部类
     * 创建人：xufan
     * 创建时间：2013-4-15 下午5:42:54
     * -------------------------------修订历史--------------------------
     * 修改人：xufan
     * 修改时间：2013-4-15 下午5:42:54
     * 修改备注：
     * @version：
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {

	DatabaseHelper(Context context) {
	    // 当调用getWritableDatabase()或 getReadableDatabase()方法时,创建一个数据库
	    super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	    db.execSQL("CREATE TABLE IF NOT EXISTS seaway_phone_address(id PRIMARY KEY, name TEXT, face TEXT,phone TEXT,email TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    db.execSQL("DROP TABLE IF EXISTS seaway_phone_address");
	    onCreate(db);
	}
    }

    /**
     * Title: 
     * Description: 构造函数
     * @param mContext
     */
    private SQLite(Context mContext) {
	super();
	this.mContext = mContext;
    }

    /**
     * @方法名：getInstance
     * @功能描述：
     * @创建人：xufan
     * @创建时间：2013-4-15 下午5:45:25
     * @参数：@param mContext
     * @参数：@return
     * @返回：SQLiteUtil
     * @throws
     */
    public static SQLite getInstance(Context mContext) {
	if (null == dbConn) {
	    dbConn = new SQLite(mContext);
	}
	return dbConn;
    }

    /**
     * @方法名：open
     * @功能描述：打开数据库
     * @创建人：xufan
     * @创建时间：2013-4-15 下午5:45:35
     * @参数：
     * @返回：void
     * @throws
     */
    public void open() {
	mDatabaseHelper = new DatabaseHelper(mContext);
	mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
    }

    /**
     * @方法名：close
     * @功能描述：关闭数据库
     * @创建人：xufan
     * @创建时间：2013-4-15 下午5:45:42
     * @参数：
     * @返回：void
     * @throws
     */
    public void close() {
	if (null != mDatabaseHelper) {
	    mDatabaseHelper.close();
	}
	if (null != cursor) {
	    cursor.close();
	}
    }

    /**
     * @方法名：insert
     * @功能描述：插入数据
     * @创建人：xufan
     * @创建时间：2013-4-15 下午5:45:52
     * @参数：@param tableName
     * @参数：@param nullColumn
     * @参数：@param contentValues 名值对
     * @参数：@return 新插入数据的ID，错误返回-1
     * @参数：@throws Exception
     * @返回：long
     * @throws
     */
    public long insert(String tableName, String nullColumn,
	    ContentValues contentValues) throws Exception {
	try {
	    return mSQLiteDatabase.insert(tableName, nullColumn, contentValues);
	} catch (Exception e) {
	    throw e;
	}
    }

    /**
     * @方法名：delete
     * @功能描述：通过主键ID删除数据
     * @创建人：xufan
     * @创建时间：2013-4-15 下午5:46:33
     * @参数：@param tableName
     * @参数：@param key 主键名
     * @参数：@param id 主键值
     * @参数：@return 受影响的记录数
     * @参数：@throws Exception
     * @返回：long
     * @throws
     */
    public long delete(String tableName, String key, int id) throws Exception {
	try {
	    return mSQLiteDatabase.delete(tableName, key + " = " + id, null);
	} catch (Exception e) {
	    throw e;
	}
    }

    /**
     * @方法名：findAll
     * @功能描述：查找表的所有数据
     * @创建人：xufan
     * @创建时间：2013-4-15 下午5:47:25
     * @参数：@param tableName
     * @参数：@param columns
     * @参数：@return 如果返回所有列，则填null
     * @参数：@throws Exception
     * @返回：Cursor
     * @throws
     */
    public Cursor findAll(String tableName, String[] columns) throws Exception {
	try {
	    cursor = mSQLiteDatabase.query(tableName, columns, null, null,
		    null, null, null);
	    cursor.moveToFirst();
	    return cursor;
	} catch (Exception e) {
	    throw e;
	}
    }

    /**
     * @方法名：findById
     * @功能描述：根据主键查找数据
     * @创建人：xufan
     * @创建时间：2013-4-15 下午5:47:38
     * @参数：@param tableName
     * @参数：@param key
     * @参数：@param id
     * @参数：@param columns 如果返回所有列，则填null
     * @参数：@return Cursor游标
     * @参数：@throws Exception
     * @返回：Cursor
     * @throws
     */
    public Cursor findById(String tableName, String key, int id,
	    String[] columns) throws Exception {
	try {
	    return mSQLiteDatabase.query(tableName, columns, key + " = " + id,
		    null, null, null, null);
	} catch (Exception e) {
	    throw e;
	}
    }

    /**
     * @方法名：find
     * @功能描述：根据条件查询数据
     * @创建人：xufan
     * @创建时间：2013-4-15 下午5:47:59
     * @参数：@param tableName
     * @参数：@param names  查询条件
     * @参数：@param values 查询条件值
     * @参数：@param columns
     * @参数：@param orderColumn    排序的列
     * @参数：@param limit 限制返回数
     * @参数：@return Cursor游标
     * @参数：@throws Exception
     * @返回：Cursor
     * @throws
     */
    public Cursor find(String tableName, String[] names, String[] values,
	    String[] columns, String orderColumn, String limit)
	    throws Exception {
	try {
	    StringBuffer selection = new StringBuffer();
	    for (int i = 0; i < names.length; i++) {
		selection.append(names[i]);
		selection.append(" = ?");
		if (i != names.length - 1) {
		    selection.append(",");
		}
	    }
	    cursor = mSQLiteDatabase.query(true, tableName, columns,
		    selection.toString(), values, null, null, orderColumn,
		    limit);
	    cursor.moveToFirst();
	    return cursor;
	} catch (Exception e) {
	    throw e;
	}
    }

    /**
     * @方法名：udpate
     * @功能描述：更新数据库
     * @创建人：xufan
     * @创建时间：2013-4-15 下午5:48:36
     * @参数：@param tableName
     * @参数：@param names
     * @参数：@param values
     * @参数：@param args  更新列-值对
     * @参数：@return true或false
     * @参数：@throws Exception
     * @返回：boolean
     * @throws
     */
    public boolean udpate(String tableName, String[] names, String[] values,
	    ContentValues args) throws Exception {
	try {
	    StringBuffer selection = new StringBuffer();
	    for (int i = 0; i < names.length; i++) {
		selection.append(names[i]);
		selection.append(" = ?");
		if (i != names.length - 1) {
		    selection.append(",");
		}
	    }
	    return mSQLiteDatabase.update(tableName, args,
		    selection.toString(), values) > 0;
	} catch (Exception e) {
	    throw e;
	}
    }

    /**
     * @方法名：executeSql
     * @功能描述：执行sql语句，包括创建表、删除、插入
     * @创建人：xufan
     * @创建时间：2013-4-15 下午5:49:01
     * @参数：@param sql
     * @返回：void
     * @throws
     */
    public void executeSql(String sql) {
	mSQLiteDatabase.execSQL(sql);
    }

}