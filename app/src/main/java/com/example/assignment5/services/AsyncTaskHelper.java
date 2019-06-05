package com.example.assignment5.services;

import android.content.Context;
import android.os.AsyncTask;
import com.example.assignment5.database.DatabaseHelper;
import com.example.assignment5.fragments.AddStudentFragment;
import com.example.assignment5.utilities.Constants;

public class AsyncTaskHelper extends AsyncTask<String,Void,String> {

    private Context mContext;
    private Constants mConstants = new Constants();
    private String mResult;
    static private AddStudentFragment mFragment = new AddStudentFragment();

    public AsyncTaskHelper(Context context){
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        DatabaseHelper myDb = new DatabaseHelper(mContext);
        String actionOnDatabase = params[0];
        String name = params[mConstants.ASYNC_PARAM1];
        String roll = params[mConstants.ASYNC_PARAM2];
        String className = params[mConstants.ASYNC_PARAM3];
        if(actionOnDatabase.equals(mConstants.VALUE_ADDING)) {
            boolean isInserted = myDb.insertData(name, roll, className);
            if (isInserted) {
                mResult = mConstants.DB_ASYNCTASK_ADD_SUCCESS;
            }
            else {
                mResult = mConstants.DB_ASYNCTASK_ADD_FAIL;
            }
        }
        else if(actionOnDatabase.equals(mConstants.VALUE_DELETING)){
            Integer itemsDeleted = myDb.deleteData(roll);
            if(itemsDeleted > mConstants.MIN_ITEMS_DELETED) {
                mResult = mConstants.DB_ASYNCTASK_DELETE_SUCCESS;
            }
            else {
                mResult = mConstants.DB_ASYNCTASK_DELETE_FAIL;
            }
        }
        else if(actionOnDatabase.equals(mConstants.VALUE_EDITING)){
            boolean mResultFromDb = myDb.editData(name,roll,className);
            if(mResultFromDb) {
                mResult = mConstants.DB_ASYNCTASK_EDIT_SUCCESS;
            }
            else {
                mResult = mConstants.DB_ASYNCTASK_EDIT_FAIL;
            }
        }
        return mResult;
    }

    @Override
    protected void onPostExecute(String mResult) {
        mFragment.resultFromAsync(mContext,mResult);
    }
}
