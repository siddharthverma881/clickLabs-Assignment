package com.example.assignmentretry5.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.assignmentretry5.models.Student;
import com.example.assignmentretry5.utils.Constants;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Students.db";
    private final String TABLE_NAME = "Students";
    private final String COL_NAME = "NAME";
    private final String COL_ROLL = "ROLL";
    private final String COL_CLASS = "CLASS";
    private static int DATABASE_VERSION = 1;
    private Context context;
    private Constants constants = new Constants();
    private String resultOfDb;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (NAME TEXT, ROLL TEXT PRIMARY KEY,CLASS TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public String insertData(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "Select * from " + TABLE_NAME + " where " + COL_ROLL + " = " + student.getRoll();
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() == constants.MIN_ITEMS_DELETED){
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_NAME,student.getName());
            contentValues.put(COL_ROLL,student.getRoll());
            contentValues.put(COL_CLASS,student.getClassName());
            long result = db.insert(TABLE_NAME,null,contentValues);
            cursor.close();
            if(result > constants.MIN_ITEMS_DELETED){
                resultOfDb = constants.ADD_SUCCESS;
            }
            else{
                resultOfDb = constants.ADD_FAIL;
            }
        }
        else{
            resultOfDb = constants.ROLL_NUMBER_EXIST;
        }
        return resultOfDb;
    }

    public ArrayList<Student> getAllData(){
        ArrayList<Student> studentList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            while(cursor.moveToNext()){
                studentList.add(new Student(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
            }
        }
        cursor.close();
        db.close();
        return studentList;
    }

    public String deleteData(String roll){
        SQLiteDatabase db = this.getWritableDatabase();
        int count = db.delete(TABLE_NAME,"ROLL = ?" , new String[] {roll});
        if(count > constants.MIN_ITEMS_DELETED){
            resultOfDb = constants.DELETE_SUCCESS;
        }
        else{
            resultOfDb = constants.DELETE_FAIL;
        }
        return resultOfDb;
    }

    public String editData(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME,student.getName());
        contentValues.put(COL_CLASS,student.getClassName());
        int count = db.update(TABLE_NAME,contentValues, "ROLL = ?" , new String[] { student.getRoll() });
        if(count>constants.MIN_ITEMS_DELETED) {
            resultOfDb = constants.EDIT_SUCCESS;
        }
        else {
            resultOfDb = constants.EDIT_FAIL;
        }
        return resultOfDb;
    }
}
