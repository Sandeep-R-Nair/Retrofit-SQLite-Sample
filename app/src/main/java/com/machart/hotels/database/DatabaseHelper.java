package com.machart.hotels.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Hotels.db";
    public static final String TABLE_NAME = "hotels_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "ARRAY_STR";
    public static final String COL_3 = "LATITUDE";
    public static final String COL_4 = "LONGITUDE";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, ARRAY_STR TEXT, LATITUDE TEXT, LONGITUDE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //write data to table
    public boolean insertData(String id, String array_str, String lat, String lon) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, array_str);
        contentValues.put(COL_3, lat);
        contentValues.put(COL_4, lon);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    //update table
    public boolean updateData(String id, String array_str, String lat, String lon) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, array_str);
        contentValues.put(COL_3, lat);
        contentValues.put(COL_4, lon);
        long result = db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return result != -1;
    }

    //read all data from table
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    //clear table
    public Integer deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, null, null);
    }

    //get table size
    public int getTableSizeCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    //check whether the latitude/{longitude} is present in table
    public boolean CheckIsDataAlreadyIn(String lat, String lon) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = String.format("Select * from %s where LATITUDE = %s", TABLE_NAME, lat);
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        } else {
            cursor.close();
            return true;
        }
    }

    //read array list string from table
    public String getArrayListString() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectedQty;
        Cursor cursor = db.rawQuery(String.format("select ARRAY_STR from %s where ID = 1", TABLE_NAME), null);

        cursor.moveToFirst();
        selectedQty = cursor.getString(0);
        cursor.close();
        Log.v("qtyamt-->", selectedQty);
        return selectedQty;

    }
}
