package com.konstant.tool.ui.activity.toolactivity.traffic

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * 描述:用来保存流量统计数据的工具
 * 创建人:菜籽
 * 创建时间:2018/3/1 下午3:01
 * 备注:
 */

class SQLite(context: Context, name: String) : SQLiteOpenHelper(context, name, null, 1) {


    companion object {

        val TABLE_NAME = "traffic"
        val START_TIME = "startTime"
        val END_TIME = "endtTme"
        val USED_TRAFFIC = "usedTraffic"
        val USEDID = "id"


        val DATABASE_NAME = "traffic"
        private var mSQLite: SQLite? = null

        @Synchronized
        fun instance(context: Context): SQLite {
            if (mSQLite == null) {
                mSQLite = SQLite(context, DATABASE_NAME)
            }
            return mSQLite!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        val sql = "create table if not exists " + TABLE_NAME + " (id integer primary key autoincrement, " +
                START_TIME + " long," + END_TIME + " long," + USED_TRAFFIC + " long)"
        db.execSQL(sql)
    }

    // 插入数据
    fun insert(startTime: Long, endTime: Long, usedTraffic: Long) {
        val values = ContentValues()
        values.put(START_TIME, startTime)
        values.put(END_TIME, endTime)
        values.put(USED_TRAFFIC, usedTraffic)

        writableDatabase.insert(TABLE_NAME, null, values)
        writableDatabase.close()

    }

    // 删除数据
    fun delete(ids: Array<String>) {
        ids.forEach{ println() }
        writableDatabase.delete(TABLE_NAME,"id=?",ids)
    }


    // 更新数据库版本
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}