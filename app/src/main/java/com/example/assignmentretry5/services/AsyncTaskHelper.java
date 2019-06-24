package com.example.assignmentretry5.services;

import android.os.AsyncTask;
import com.example.assignmentretry5.activities.StudentListActivity;
import com.example.assignmentretry5.database.DatabaseHelper;
import com.example.assignmentretry5.models.Student;
import com.example.assignmentretry5.utils.Constants;

import java.util.ArrayList;

public class AsyncTaskHelper extends AsyncTask<Student,Void,Student> {

    private DatabaseHelper myDb;
    private String mValue,result;
    private Student mStudent;
    private int gotofunction;
    private ArrayList<Student> mStudentArrayList = new ArrayList<>();
    private StudentListActivity studentListActivity;
    private Constants constants = new Constants();

    public AsyncTaskHelper(StudentListActivity studentListActivity,String value){
        this.studentListActivity = studentListActivity;
        mValue = value;
    }

    @Override
    protected Student doInBackground(Student... student) {
        mStudent = student[constants.STUDENT_FIRST];
        myDb = new DatabaseHelper(studentListActivity);
        if(mValue.equals(constants.KEY_ADD)) {
            gotofunction = constants.FUNCTION_ONE;
            result = myDb.insertData(mStudent);
        }
        else if(mValue.equals(constants.KEY_UPDATE)){
            gotofunction = constants.FUNCTION_ONE;
            result = myDb.editData(mStudent);
        }
        else if(mValue.equals(constants.KEY_DELETE)){
            gotofunction = constants.FUNCTION_ONE;
            result = myDb.deleteData(mStudent.getRoll());
        }
        else if(mValue.equals(constants.ASYNC_KEY_GETALLDATA)){
            gotofunction = constants.FUNCTION_TWO;
            mStudentArrayList = myDb.getAllData();
        }
        return mStudent;
    }

    @Override
    protected void onPostExecute(Student student){
        if(gotofunction == constants.FUNCTION_ONE) {
            studentListActivity.asyncTaskStatus(student, result);
        }
        else {
            studentListActivity.showAllData(mStudentArrayList);
        }
    }
}
