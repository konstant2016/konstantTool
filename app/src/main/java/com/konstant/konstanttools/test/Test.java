package com.konstant.konstanttools.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.TrafficStats;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by konstant on 2018/2/28.
 */

public class Test {

    private long timeStamp = System.currentTimeMillis();  // 当前时间戳,从系统中读取的

    private long stampFlow = readLocalFlow();             // 读取到的已用流量，从系统中读取的

    private Context context;


    public void getUsedFlow() {
        // 获取当前时间
        long currentTime = System.currentTimeMillis();

        // 读取实时流量
        long currentFlow = readLocalFlow();
        // 计算流量差值
        long gapFlow = currentFlow - stampFlow;
        // 保存当前的流量
        stampFlow = currentFlow;

        saveFlow(timeStamp, currentTime, gapFlow);

        timeStamp = currentTime;

    }

    // 保存到本地数据库中
    public void saveFlow(long startTime, long endTime, long usedFlow) {

    }

    // 读取系统已保存的流量
    public long readLocalFlow() {
        // 获取手机网络下载的流量
        long download = TrafficStats.getMobileRxBytes();
        // 获取手机网络上载的流量
        long upload = TrafficStats.getMobileTxBytes();

        return download + upload;
    }


    class SQLite extends SQLiteOpenHelper {

        private final String TABLE_NAME = "flow";

        private final String START_TIME = "startTime";
        private final String END_TIME = "endtTme";
        private final String USED_FLOW = "usedFlow";

        private Context context;

        public SQLite(Context context, String name) {
            this(context, name, null, 1);
            this.context = context;
        }

        public SQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public SQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
            super(context, name, factory, version, errorHandler);
        }


        // 创建数据库
        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql = "create table flow(id integer primary key autoincrement," +
                    START_TIME + " long," + END_TIME + " long," + USED_FLOW + ")";
            db.execSQL(sql);

        }

        // 连接数据库
        public SQLiteDatabase connectDataBase() {
            return getWritableDatabase();
        }

        // 更新数据库版本
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        // 关闭数据库
        @Override
        public synchronized void close() {
            super.close();
        }

        // 插入数据
        public void insert(SQLiteDatabase database, long startTime, long endTime, long usedFlow) {
            ContentValues values = new ContentValues();
            values.put(START_TIME, startTime);
            values.put(END_TIME, endTime);
            values.put(USED_FLOW, usedFlow);

            database.insert(TABLE_NAME, null, values);
        }

        // 删除数据
        public void delete(SQLiteDatabase database, String[] ids) {
            database.delete(TABLE_NAME, "id=?", ids);
        }

        // 查询数据
        public Cursor query(SQLiteDatabase database) {
            return database.query(TABLE_NAME, null, null, null, null, null, null);
        }

        // 删除数据库
        public void deleteDataBase(SQLiteDatabase database) {
            String sql = "drop table " + TABLE_NAME;
            database.execSQL(sql);
        }

    }

}
