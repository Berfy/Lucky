package cn.berfy.demo.lucky.db.tab;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.berfy.demo.lucky.db.DBHelper;
import cn.berfy.demo.lucky.db.DbConstants;
import cn.berfy.demo.lucky.ui.lucky.model.Lucky;
import cn.berfy.framework.utils.GsonUtil;
import cn.berfy.framework.utils.LogUtil;
import cn.berfy.framework.utils.TimeUtil;

/**
 * Created by Berfy on 2017/4/10.
 * 内容表
 */

public class Tab {

    private final String TAG = "Tab";
    private static Tab mTabUrls;
    private SQLiteDatabase mDb;
    private DBHelper mDbHelper;

    synchronized public static Tab getInstances() {
        synchronized (Tab.class) {
            if (null == mTabUrls) {
                mTabUrls = new Tab();
            }
            return mTabUrls;
        }
    }

    private Tab() {
        mDbHelper = DBHelper.getInstance();
        mDb = mDbHelper.getDb();
    }

    public void add(Lucky lucky) {
        try {
            mDb.beginTransaction();
            Lucky old = isHas(lucky);
            if (null != old) {
                LogUtil.e(TAG, "更新 " + GsonUtil.getInstance().toJson(lucky));
                mDb.update(DbConstants.TAB, getValues(lucky), "_id = ? or name = ?"
                        , new String[]{lucky.getId() + ""});
            } else {
                LogUtil.e(TAG, "插入 " + GsonUtil.getInstance().toJson(lucky));
                mDb.insert(DbConstants.TAB, null, getValues(lucky));
            }
            mDb.setTransactionSuccessful();
            mDb.endTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            mDb.endTransaction();
        }
    }

    public void delete(Lucky lucky) {
        LogUtil.e(TAG, "删除内容 " + GsonUtil.getInstance().toJson(lucky));
        mDb.delete(DbConstants.TAB, "_id = ? or name = ?"
                , new String[]{lucky.getId() + "", lucky.getName()});
    }

    public Lucky isHas(Lucky lucky) {
        LogUtil.e(TAG, "isHas " + GsonUtil.getInstance().toJson(lucky));
        Cursor cursor = mDb.query(DbConstants.TAB, null, "_id = ? or name = ? "
                , new String[]{lucky.getId() + "", lucky.getName()}, null, null, null);
        try {
            Lucky lucky1 = new Lucky();
            if (cursor.moveToFirst()) {
                lucky1.setId(cursor.getInt(cursor.getColumnIndex(DbConstants.KEYS_TAB[0])));
                lucky1.setName(cursor.getString(cursor.getColumnIndex(DbConstants.KEYS_TAB[1])));
                lucky1.setImgPath(cursor.getString(cursor.getColumnIndex(DbConstants.KEYS_TAB[2])));
                lucky1.setColor(cursor.getInt(cursor.getColumnIndex(DbConstants.KEYS_TAB[3])));
                lucky1.setUpdateTime(TimeUtil.timeFormat(cursor.getLong(cursor.getColumnIndex(DbConstants.KEYS_TAB[4]))
                        , "yyyy-MM-dd HH:mm:ss"));
                return lucky1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }
        return null;
    }

    public List<Lucky> getAllData() {
        LogUtil.e(TAG, "getAllData ");
        Cursor cursor = mDb.query(DbConstants.TAB, null, null
                , null, null, null, DbConstants.KEYS_TAB[2] + " desc");
        List<Lucky> luckies = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                Lucky lucky = new Lucky();
                lucky.setId(cursor.getInt(cursor.getColumnIndex(DbConstants.KEYS_TAB[0])));
                lucky.setName(cursor.getString(cursor.getColumnIndex(DbConstants.KEYS_TAB[1])));
                lucky.setImgPath(cursor.getString(cursor.getColumnIndex(DbConstants.KEYS_TAB[2])));
                lucky.setColor(cursor.getInt(cursor.getColumnIndex(DbConstants.KEYS_TAB[3])));
                lucky.setUpdateTime(TimeUtil.timeFormat(cursor.getLong(cursor.getColumnIndex(DbConstants.KEYS_TAB[4]))
                        , "yyyy-MM-dd HH:mm:ss"));
                luckies.add(lucky);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }
        LogUtil.e(TAG, "所有数据" + GsonUtil.getInstance().toJson(luckies));
        return luckies;
    }

    public void deleteAllData() {
        LogUtil.e(TAG, "删除所有内容");
        mDb.delete(DbConstants.TAB, null, null);
    }

    private ContentValues getValues(Lucky lucky) {
        ContentValues cv = new ContentValues();
        cv.put(DbConstants.KEYS_TAB[1], lucky.getName());
        cv.put(DbConstants.KEYS_TAB[2], lucky.isShowImg() ? lucky.getImgPath() : "");
        cv.put(DbConstants.KEYS_TAB[3], lucky.getColor());
        cv.put(DbConstants.KEYS_TAB[4], System.currentTimeMillis());
        return cv;
    }
}
