package com.example.sqlite_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyHelper extends SQLiteOpenHelper {
    private  static  final  String database_name="SQliteApp_data";
    private  static  final int database_version=1;

    public MyHelper(Context context) {
        super(context,database_name,null,database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table sqApp_data(name varchar(20),email varchar(30), mbNumber varchar(11))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
