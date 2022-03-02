package com.example.musicplayer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;//定义数据库的版本号
    private static final String DBNAME = "Music.db";//定义数据库的名字
    public DBhelper(Context context) {//定义构造函数
        super(context, DBNAME, null, VERSION);// 重写基类构造函数
    }

    //创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {

        /**
         *     创建用户db_User表
         *     _id  账号，pwd 密码，name 昵称， sex 性别
          */

        db.execSQL("create table db_User (_id varchar(10)," +
                "pwd varchar(20),name varchar(50),sex varchar(10))");

        
    }

    //重写基类的onUpgrade() 方法，以便于数据库版本的更新
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}