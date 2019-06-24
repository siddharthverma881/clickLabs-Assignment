package com.example.assignmentretry5.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.assignmentretry5.R;
import com.example.assignmentretry5.models.Student;
import com.example.assignmentretry5.services.AsyncTaskHelper;
import com.example.assignmentretry5.utils.Constants;
import com.example.assignmentretry5.utils.Util;

import java.util.concurrent.ConcurrentSkipListMap;

public class AddStudentFragment extends Fragment {

    private AddStudentFragmentListener mListener;
    private EditText mEtName,mEtRoll,mEtClass;
    private Button mButton;
    private String mValueOfButton;
    Constants constants = new Constants();
    Util util = new Util();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_add_student, container, false);

        mEtName = view.findViewById(R.id.et_name);
        mEtRoll = view.findViewById(R.id.et_roll);
        mEtClass = view.findViewById(R.id.et_class);
        mButton = view.findViewById(R.id.addStudent_btn_add);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mValueOfButton = mButton.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle(getString(R.string.dialog_title));
                String[] options = { getString(R.string.dialog_message_async_task) , getString(R.string.dialog_message_service) ,
                                    getString(R.string.dialog_message_intent_service) };
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Student student = new Student(mEtName.getText().toString(), mEtRoll.getText().toString(), mEtClass.getText().toString());
                        if(util.checkDetails(getContext(),student)) {
                            mListener.addStudentFragment(student ,mValueOfButton, which);
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        if(getArguments() != null) {
            Bundle viewBundle = getArguments();
            Student student = viewBundle.getParcelable(constants.VIEW_BUNDLE_KEY);
                mEtName.setEnabled(false);
                mEtRoll.setEnabled(false);
                mEtClass.setEnabled(false);
                mEtName.setText(student.getName());
                mEtRoll.setText(student.getRoll());
                mEtClass.setText(student.getClassName());
                mButton.setVisibility(View.INVISIBLE);
        }

        return view;
    }

    public static AddStudentFragment newInstance(Bundle bundle){
        AddStudentFragment fragment = new AddStudentFragment();
        if(bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

//  clearing the edit texts on click of add button in first fragment
    public void clearEditText(){
        mEtRoll.setEnabled(true);
        mEtName.setText("");
        mEtRoll.setText("");
        mEtClass.setText("");
        mEtName.requestFocus();
        mButton.setText(getString(R.string.add_student_btn_add));
    }

//  setting the values of name,roll and class respectively to the edit texts when edit dialog option is selected
    public void editEditText(Student student){
        mEtName.setText(student.getName());
        mEtRoll.setText(student.getRoll());
        mEtClass.setText(student.getClassName());
        mEtName.requestFocus();
        mEtRoll.setEnabled(false);
        mButton.setText(getString(R.string.add_student_btn_update));
    }

//  setting the values of name,roll and class respectively when there is an error in adding a new student.
    public void setEditText(Student student){
        mEtName.setText(student.getName());
        mEtRoll.setText(student.getRoll());
        mEtClass.setText(student.getClassName());
        mEtRoll.requestFocus();
        mButton.setText(getString(R.string.add_student_btn_add));
    }

    public void instantiateAddListener(AddStudentFragmentListener mListener){
        this.mListener=mListener;
    }

    public interface AddStudentFragmentListener {
        void addStudentFragment(Student student,String valueOfButton,int dialogPosition);
    }
}
