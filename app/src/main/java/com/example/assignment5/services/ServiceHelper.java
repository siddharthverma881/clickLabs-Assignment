package com.example.assignment5.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.assignment5.database.DatabaseHelper;
import com.example.assignment5.utilities.Constants;

public class ServiceHelper extends Service {

    private String mName,mRoll,mClassName,mAction,mAnswer;
    Constants constants = new Constants();
    protected DatabaseHelper myDb;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate(){
        super.onCreate();
        myDb = new DatabaseHelper(this);

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mAction = intent.getStringExtra(constants.KEY);
        mName = intent.getStringExtra(constants.KEY_NAME);
        mRoll = intent.getStringExtra(constants.KEY_ROLL);
        mClassName = intent.getStringExtra(constants.KEY_CLASS);
        if(mAction.equals(constants.VALUE_ADDING)) {
            boolean result = myDb.insertData(mName, mRoll, mClassName);
            if (result) {
                mAnswer = constants.DB_SERVICE_ADD_SUCCESS;
            } else {
                mAnswer = constants.DB_SERVICE_ADD_FAIL;
            }
        }

        else if(mAction.equals(constants.VALUE_DELETING)){
            Integer itemsDeleted = myDb.deleteData(mRoll);
            if(itemsDeleted > constants.MIN_ITEMS_DELETED) {
                mAnswer = constants.DB_SERVICE_DELETE_SUCCESS;
            }
            else {
                mAnswer = constants.DB_SERVICE_DELETE_FAIL;
            }
        }

        else if(mAction.equals(constants.VALUE_EDITING)){
            boolean resultFromDb = myDb.editData(mName,mRoll,mClassName);
            if(resultFromDb) {
                mAnswer = constants.DB_SERVICE_EDIT_SUCCESS;
            }
            else {
                mAnswer = constants.DB_SERVICE_EDIT_FAIL;
            }
        }

        Intent broadcast = new Intent(constants.BROADCAST_SERVICE_VALUE);
        broadcast.putExtra(constants.RECEIVER_SERVICE_VALUE , mAnswer);
        this.sendBroadcast(broadcast);

        return super.onStartCommand(intent,flags,startId);
    }
}
