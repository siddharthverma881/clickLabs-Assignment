package com.example.assignment5.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.assignment5.utilities.Constants;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static Constants constants = new Constants();

    private static final String mDATABASE_NAME = constants.DATABASE_NAME;
    private final String mTABLE_NAME = constants.DB_TABLE_NAME;
    private final String mCOL_1 = constants.DB_TABLE_COL1;
    private final String mCOL_2 = constants.DB_TABLE_COL2;
    private final String mCOL_3 = constants.DB_TABLE_COL3;
    private static int mDATABASE_VERSION = constants.DATABASE_VERSION;

    public DatabaseHelper(Context context) {
        super(context, mDATABASE_NAME, null, mDATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + mTABLE_NAME +" (NAME TEXT, ROLL TEXT PRIMARY KEY,CLASS TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + mTABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name,String roll,String className){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(mCOL_1,name);
        contentValues.put(mCOL_2,roll);
        contentValues.put(mCOL_3,className);
        long result = db.insert(mTABLE_NAME,null,contentValues);
        if(result == constants.DB_RESULT_FAIL) {
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + mTABLE_NAME,null);
        return res;
    }

    public Integer deleteData(String roll){
        SQLiteDatabase db = this.getWritableDatabase();
       return db.delete(mTABLE_NAME,"ROLL = ?" , new String[] {roll});
    }

    public boolean editData(String name,String roll,String className){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(mCOL_1,name);
        contentValues.put(mCOL_2,roll);
        contentValues.put(mCOL_3,className);
        int count = db.update(mTABLE_NAME,contentValues, "ROLL = ?" , new String[] { roll });
        if(count>constants.MIN_ITEMS_DELETED) {
            return true;
        }
        else {
            return false;
        }
    }

}
