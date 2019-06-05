package com.example.assignment5.services;

import android.app.IntentService;
import android.content.Intent;

import com.example.assignment5.database.DatabaseHelper;
import com.example.assignment5.utilities.Constants;

public class IntentServiceHelper extends IntentService {

    private String mAction,mName,mRoll,mClassName,mAnswer;
    static Constants constants = new Constants();
    DatabaseHelper myDb;
    private static final String mINTENT_SERVICE_NAME = constants.INTENT_SERVICE_NAME;

    public IntentServiceHelper() {
        super(mINTENT_SERVICE_NAME);
    }

    @Override
    public void onCreate(){
        super.onCreate();
        myDb = new DatabaseHelper(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mAction = intent.getStringExtra(constants.KEY);
        mName = intent.getStringExtra(constants.KEY_NAME);
        mRoll = intent.getStringExtra(constants.KEY_ROLL);
        mClassName = intent.getStringExtra(constants.KEY_CLASS);
        if(mAction.equals(constants.VALUE_ADDING)) {
            boolean result = myDb.insertData(mName, mRoll, mClassName);
            if (result) {
                mAnswer = constants.DB_INTENTSERVICE_ADD_SUCCESS;
            } else {
                mAnswer = constants.DB_INTENTSERVICE_ADD_FAIL;
            }
        }
        else if(mAction.equals(constants.VALUE_DELETING)){
            Integer itemsDeleted = myDb.deleteData(mRoll);
            if(itemsDeleted > constants.MIN_ITEMS_DELETED) {
                mAnswer = constants.DB_INTENTSERVICE_DELETE_SUCCESS;
            }
            else {
                mAnswer = constants.DB_INTENTSERVICE_DELETE_FAIL;
            }
        }

        else if(mAction.equals(constants.VALUE_EDITING)){
            boolean resultFromDb = myDb.editData(mName,mRoll,mClassName);
            if(resultFromDb) {
                mAnswer = constants.DB_INTENTSERVICE_EDIT_SUCCESS;
            }
            else {
                mAnswer = constants.DB_INTENTSERVICE_EDIT_FAIL;
            }
        }

        Intent broadcast = new Intent(constants.BROADCAST_INTENTSERVICE_VALUE);
        broadcast.putExtra(constants.RECEIVER_INTENTSERVICE_VALUE , mAnswer);
        this.sendBroadcast(broadcast);
    }
}