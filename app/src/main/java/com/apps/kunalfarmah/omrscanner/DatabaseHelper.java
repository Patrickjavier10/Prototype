package com.apps.kunalfarmah.omrscanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Classes.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CLASSES = "classes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CLASS_NAME = "className";
    private static final String COLUMN_YEAR_LEVEL = "yearLevel";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_CLASSES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_CLASS_NAME + " TEXT,"
                + COLUMN_YEAR_LEVEL + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASSES);
        onCreate(db);
    }

    public void addClass(String className, String yearLevel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLASS_NAME, className);
        values.put(COLUMN_YEAR_LEVEL, yearLevel);

        db.insert(TABLE_CLASSES, null, values);
        db.close();
    }

    public List<String[]> getAllClasses() {
        List<String[]> classes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CLASSES, null);

        if (cursor.moveToFirst()) {
            do {
                String[] classData = new String[2];
                classData[0] = cursor.getString(cursor.getColumnIndex(COLUMN_CLASS_NAME));
                classData[1] = cursor.getString(cursor.getColumnIndex(COLUMN_YEAR_LEVEL));
                classes.add(classData);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return classes;
    }

    public void deleteClass(String className, String yearLevel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CLASSES, COLUMN_CLASS_NAME + "=? AND " + COLUMN_YEAR_LEVEL + "=?", new String[]{className, yearLevel});
        db.close();
    }
}