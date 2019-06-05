package com.example.assignment5.fragments;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.assignment5.R;
import com.example.assignment5.database.DatabaseHelper;
import com.example.assignment5.services.AsyncTaskHelper;
import com.example.assignment5.services.IntentServiceHelper;
import com.example.assignment5.services.ServiceHelper;
import com.example.assignment5.utilities.Constants;
import com.example.assignment5.utilities.Util;

public class AddStudentFragment extends Fragment {
    private EditText mEtName,mEtRoll,mEtClassName;
    private Button mBtnAdd;
    private OnAddFragmentListener mFragmentCaller;
    Constants constants = new Constants();
    Util utl = new Util();
    DatabaseHelper myDb;
    BroadcastReceiver serviceBroadcastReceiver,intentServiceBroadcastReceiver;
    IntentFilter intentFilter = new IntentFilter();
    private String mName,mRoll,mClassName,mButtonValue,mActionForAsync,mActionOfButton = constants.BUTTON_ACTION_ASYNCTASK,mResultFromBroadcast;

    public AddStudentFragment() {
        // Required empty public constructor
    }

    public static AddStudentFragment newInstance(Bundle bundle){
        AddStudentFragment fragment = new AddStudentFragment();
        if(bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_add_student, container, false);

        init(view);

        final Context context =getActivity();

        myDb = new DatabaseHelper(context);

        intentFilter.addAction(constants.BROADCAST_SERVICE_VALUE);
        intentFilter.addAction(constants.BROADCAST_INTENTSERVICE_VALUE);
        context.registerReceiver(serviceBroadcastReceiver, intentFilter);
        context.registerReceiver(intentServiceBroadcastReceiver,intentFilter);

        serviceBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent resultIntent) {
                String action = resultIntent.getAction();
                if(action.equals(constants.BROADCAST_SERVICE_VALUE)){
                    mResultFromBroadcast = resultIntent.getStringExtra(constants.RECEIVER_SERVICE_VALUE);
                    utl.displayToast(context,mResultFromBroadcast);
                }
            }
        };

        intentServiceBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent resultIntent) {
                String action = resultIntent.getAction();
                if(action.equals(constants.BROADCAST_INTENTSERVICE_VALUE)){
                    mResultFromBroadcast = resultIntent.getStringExtra(constants.RECEIVER_INTENTSERVICE_VALUE);
                    utl.displayToast(context,mResultFromBroadcast);
                }
            }
        };

        mEtName.requestFocus();
        mBtnAdd = view.findViewById(R.id.addStudent_btn_add);
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonValue = mBtnAdd.getText().toString();
                if(mButtonValue.equals(constants.BUTTON_VALUE_ADD)) {
                    getValues();
                    //for validating whether the fields entered are correct or not
                    if(utl.validName(context,mName,mRoll,mClassName)) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setTitle(getString(R.string.builder_title));
                            String[] options = {getString(R.string.dialog_btn_async),getString(R.string.dialog_btn_service),getString(R.string.dialog_btn_intent_service)};
                            builder.setItems(options, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which){
                                        case 0 :
                                            mActionOfButton = constants.BUTTON_ACTION_ASYNCTASK;
                                            mActionForAsync = constants.VALUE_ADDING;
                                            AsyncTaskHelper asyncTaskHelper = new AsyncTaskHelper(context);
                                            asyncTaskHelper.execute(mActionForAsync,mName,mRoll,mClassName);
                                            Bundle bundle = new Bundle();
                                            bundle.putString(constants.BUTTON_ACTION_KEY,mActionOfButton);
                                            bundle.putString(constants.KEY_NAME, mName);
                                            bundle.putString(constants.KEY_ROLL, mRoll);
                                            bundle.putString(constants.KEY_CLASS, mClassName);
                                            mFragmentCaller.addFragmentListener(bundle);
                                            break;
                                        case 1:
                                            mActionOfButton = constants.BUTTON_ACTION_SERVICE;
                                            bundle = new Bundle();
                                            bundle.putString(constants.BUTTON_ACTION_KEY,mActionOfButton);
                                            bundle.putString(constants.KEY_NAME, mName);
                                            bundle.putString(constants.KEY_ROLL, mRoll);
                                            bundle.putString(constants.KEY_CLASS, mClassName);
                                            Intent intent = new Intent(context,ServiceHelper.class);
                                            intent.putExtra(constants.KEY,constants.VALUE_ADDING);
                                            intent.putExtra(constants.KEY_NAME, mName);
                                            intent.putExtra(constants.KEY_ROLL, mRoll);
                                            intent.putExtra(constants.KEY_CLASS, mClassName);
                                            context.startService(intent);
                                            mFragmentCaller.addFragmentListener(bundle);
                                            break;
                                        case 2:
                                            mActionOfButton = constants.BUTTON_ACTION_INTENTSERVICE;
                                            bundle = new Bundle();
                                            bundle.putString(constants.BUTTON_ACTION_KEY,mActionOfButton);
                                            bundle.putString(constants.KEY_NAME, mName);
                                            bundle.putString(constants.KEY_ROLL, mRoll);
                                            bundle.putString(constants.KEY_CLASS, mClassName);
                                            Intent intent1 = new Intent(context, IntentServiceHelper.class);
                                            intent1.putExtra(constants.KEY,constants.VALUE_ADDING);
                                            intent1.putExtra(constants.KEY_NAME, mName);
                                            intent1.putExtra(constants.KEY_ROLL, mRoll);
                                            intent1.putExtra(constants.KEY_CLASS, mClassName);
                                            context.startService(intent1);
                                            mFragmentCaller.addFragmentListener(bundle);
                                            break;
                                    }
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                }
                else if(mButtonValue.equals(constants.BUTTON_VALUE_UPDATE)) {
                    getValues();
                    //for validating whether the fields entered are correct or not
                    if(utl.validName(context,mName,mRoll,mClassName)) {
                        Bundle bundle = new Bundle();
                        bundle.putString(constants.BUTTON_ACTION_KEY,mActionOfButton);
                        bundle.putString(constants.KEY_NAME, mName);
                        bundle.putString(constants.KEY_CLASS, mClassName);
                        mFragmentCaller.addFragmentListener(bundle);
                    }
                }
            }
        });
        Bundle viewBundle = getArguments();
        if(viewBundle != null) {
            String result = viewBundle.getString(constants.KEY);
            if (result.equals(constants.VALUE_VIEWING)) {
                mEtName.setEnabled(false);
                mEtRoll.setEnabled(false);
                mEtClassName.setEnabled(false);
                mName = viewBundle.getString(constants.KEY_NAME);
                mRoll = viewBundle.getString(constants.KEY_ROLL);
                mClassName = viewBundle.getString(constants.KEY_CLASS);
                mEtName.setText(mName);
                mEtRoll.setText(mRoll);
                mEtClassName.setText(mClassName);
                mBtnAdd.setVisibility(View.INVISIBLE);
            }
        }
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        getContext().registerReceiver(serviceBroadcastReceiver,intentFilter);
        getContext().registerReceiver(intentServiceBroadcastReceiver,intentFilter);
    }

    @Override
    public void onDetach(){
        super.onDetach();
        getContext().unregisterReceiver(serviceBroadcastReceiver);
        getContext().unregisterReceiver(intentServiceBroadcastReceiver);
    }

    //function for getting the result from asyncTask and displaying a toast
    public void resultFromAsync(Context context,String result){
        utl.displayToast(context,result);
    }

    //this is self defined method for getting the edit texts from the layouts
    public void init(View view){
        mEtName = view.findViewById(R.id.et_name);
        mEtName.requestFocus();
        mEtRoll = view.findViewById(R.id.et_roll);
        mEtClassName = view.findViewById(R.id.et_class);
    }


    //function for getting the values stored in the edit texts
    public void getValues(){
        mName = mEtName.getText().toString();
        mRoll = mEtRoll.getText().toString();
        mClassName = mEtClassName.getText().toString();
    }

    public void instantiateAddListener(OnAddFragmentListener mFragmentCaller){
        this.mFragmentCaller=mFragmentCaller;
    }

    //for clearing the edit text when new user is to be added
    public void clearEditText(){
        mEtName.getText().clear();
        mEtRoll.getText().clear();
        mEtClassName.getText().clear();
        mEtName.requestFocus();
        mEtRoll.setEnabled(true);
        mBtnAdd.setText(getString(R.string.add_student_btn_add));
    }

    //for setting the text of the view into the edit texts
    public void setEditText(Bundle bundle) {
        if (bundle != null) {
            mEtName.setText(bundle.getString(constants.KEY_NAME));
            mEtRoll.setText(bundle.getString(constants.KEY_ROLL));
            mEtClassName.setText(bundle.getString(constants.KEY_CLASS));
            mEtRoll.setEnabled(false);
            mBtnAdd.setText(getString(R.string.add_student_btn_update));
        }
    }

    public interface OnAddFragmentListener {
        void addFragmentListener(Bundle bundle);
    }
}
