package com.example.android.assignment4.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.assignment4.R;
import com.example.android.assignment4.Utilities.Constants;
import com.example.android.assignment4.Utilities.util;
import com.example.android.assignment4.models.Student;

import java.util.ArrayList;

public class AddStudentFragment extends Fragment {

    /**
     *@param mName,mRoll,mClassName denotes the values of name,rollno and class filled in the edit text views
     *@param etName,etRoll,etClassName denotes the values of edit text in the layout
     *@param btnAdd denotes the button Add Button
     * @param fragmentCaller is the object of interface onAddFragmentListener
     */

    private String mName,mRoll,mClassName;
    private EditText etName,etRoll,etClassName;
    private Button btnAdd;
    private OnAddFragmentListener fragmentCaller;
    Constants constants = new Constants();
    util utl = new util();

    public AddStudentFragment() {
        // Required empty public constructor
    }

    public static AddStudentFragment newInstance(Bundle bundle){
        AddStudentFragment fragment = new AddStudentFragment();
        if(bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_add_student, container, false);

        init(view);
        final Context context =getActivity();

        btnAdd = view.findViewById(R.id.addStudent_btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etName.requestFocus();
                mName = etName.getText().toString();
                mRoll = etRoll.getText().toString();
                mClassName = etClassName.getText().toString();
            //for validating whether the fields entered are correct or not
                if(utl.validName(context,mName,mRoll,mClassName)) {
                    Bundle bundle = new Bundle();
                    bundle.putString(constants.KEY_NAME, mName);
                    bundle.putString(constants.KEY_ROLL, mRoll);
                    bundle.putString(constants.KEY_CLASS, mClassName);
                    fragmentCaller.addFragmentListener(bundle);
                }
            }
        });
        Bundle viewBundle = getArguments();
        if(viewBundle != null) {
            String result = viewBundle.getString(constants.KEY);
            if (result.equals(getString(R.string.value_viewing))) {
                etName.setEnabled(false);
                etRoll.setEnabled(false);
                etClassName.setEnabled(false);
                mName = viewBundle.getString(constants.KEY_NAME);
                mRoll = viewBundle.getString(constants.KEY_ROLL);
                mClassName = viewBundle.getString(constants.KEY_CLASS);
                etName.setText(mName);
                etRoll.setText(mRoll);
                etClassName.setText(mClassName);
                btnAdd.setVisibility(View.INVISIBLE);
            }
        }
        return view;
    }

    //this is self defined method for getting the edit texts from the layouts
    public void init(View view){
        util utl = new util();
        etName = view.findViewById(R.id.et_name);
        etName.requestFocus();
        etRoll = view.findViewById(R.id.et_roll);
        etClassName = view.findViewById(R.id.et_class);
    }

    public void instantiateAddListener(OnAddFragmentListener fragmentCaller){
        this.fragmentCaller=fragmentCaller;
    }

//for clearing the edit text when new user is to be added
    public void clearEditText(){
        etName.getText().clear();
        etRoll.getText().clear();
        etClassName.getText().clear();
    }

//for setting the text of the view into the edit texts
    public void setEditText(Bundle bundle) {
        if (bundle != null) {
            etName.setText(bundle.getString(constants.KEY_NAME));
            etRoll.setText(bundle.getString(constants.KEY_ROLL));
            etClassName.setText(bundle.getString(constants.KEY_CLASS));

        }
    }

    public interface OnAddFragmentListener {
        void addFragmentListener(Bundle bundle);
    }
}
