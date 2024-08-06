package com.apps.kunalfarmah.omrscanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ClassesDatabase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "ASD";
    private  static final  int DATABASE_VERSION =    1;


    public ClassesDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String qry2 = "create table class (classname text, year text )";
        sqLiteDatabase.execSQL(qry2);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    void addClass(String classname, String year){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues gv = new ContentValues();

        gv.put("classname", classname);
        gv.put("year", year);
        long result = db.insert("class", null, gv);

        if(result==-1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();



        }else {
            Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show();
        }

    }

}
