package com.example.assignmentretry5.services;

import android.app.IntentService;
import android.content.Intent;

import com.example.assignmentretry5.R;
import com.example.assignmentretry5.database.DatabaseHelper;
import com.example.assignmentretry5.models.Student;
import com.example.assignmentretry5.utils.Constants;
import com.example.assignmentretry5.utils.Util;

public class IntentServiceHelper extends IntentService {

    private DatabaseHelper myDb;
    private String mResultOfDb;
    Constants constants = new Constants();
    Util util = new Util();
    private static final String classSuperName = "INTENTSERVICE";

    public IntentServiceHelper() {
        super(classSuperName);
    }

    @Override
    public void onCreate(){
        super.onCreate();
        myDb = new DatabaseHelper(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Student student = intent.getParcelableExtra(constants.INTENT_STUDENT_KEY);
        String valueOfButton = intent.getStringExtra(constants.INTENT_VALUE_BUTTON);

        if(valueOfButton.equals(constants.KEY_ADD)){
            mResultOfDb = myDb.insertData(student);
        }
        else if(valueOfButton.equals(constants.KEY_UPDATE)){
            mResultOfDb = myDb.editData(student);
        }
        else if(valueOfButton.equals(constants.KEY_DELETE)){
            mResultOfDb = myDb.deleteData(student.getRoll());
        }

        if(mResultOfDb.equals(constants.ADD_FAIL) || mResultOfDb.equals(constants.EDIT_FAIL) || mResultOfDb.equals(constants.DELETE_FAIL)){
            sendToast();
        }

        Intent broadcast = new Intent(constants.SERVICE_BROADCAST_KEY);
        broadcast.putExtra(constants.BROADCAST_STRING_KEY,mResultOfDb);
        this.sendBroadcast(broadcast);
    }

    public void sendToast(){
        if(mResultOfDb.equals(constants.ADD_FAIL)){
            util.displayToast(this,getString(R.string.toast_intent_service_add_fail));
        }

        else if( mResultOfDb.equals(constants.EDIT_FAIL)){
            util.displayToast(this,getString(R.string.toast_intent_service_edit_fail));
        }
        else if(mResultOfDb.equals(constants.DELETE_FAIL)){
            util.displayToast(this,getString(R.string.toast_intent_service_delete_fail));
        }
    }
}
