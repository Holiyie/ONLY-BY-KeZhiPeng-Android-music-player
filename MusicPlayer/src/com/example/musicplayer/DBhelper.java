package com.example.musicplayer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;//�������ݿ�İ汾��
    private static final String DBNAME = "Music.db";//�������ݿ������
    public DBhelper(Context context) {//���幹�캯��
        super(context, DBNAME, null, VERSION);// ��д���๹�캯��
    }

    //�������ݿ�
    @Override
    public void onCreate(SQLiteDatabase db) {

        /**
         *     �����û�db_User��
         *     _id  �˺ţ�pwd ���룬name �ǳƣ� sex �Ա�
          */

        db.execSQL("create table db_User (_id varchar(10)," +
                "pwd varchar(20),name varchar(50),sex varchar(10))");

        
    }

    //��д�����onUpgrade() �������Ա������ݿ�汾�ĸ���
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}