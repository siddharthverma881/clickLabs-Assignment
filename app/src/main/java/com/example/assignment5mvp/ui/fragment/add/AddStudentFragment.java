package com.example.assignment5mvp.ui.fragment.add;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment5mvp.R;
import com.example.assignment5mvp.model.Student;
import com.example.assignment5mvp.util.Constants;

public class AddStudentFragment extends Fragment implements AddStudentView{

    private AddStudentFragmentListener mListener;
    private EditText mEtName,mEtRoll,mEtClass;
    private Button mButton;
    private AddStudentPresenter mPresenter;
    private String mAction;
    private Constants constants = new Constants();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AddStudentPresenterImpl(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_student, container, false);

        mEtName = view.findViewById(R.id.et_name);
        mEtRoll = view.findViewById(R.id.et_roll);
        mEtClass = view.findViewById(R.id.et_class);
        mButton = view.findViewById(R.id.addStudent_btn_add);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.buttonTapped(new Student(mEtName.getText().toString(),mEtRoll.getText().toString(),mEtClass.getText().toString()));
            }
        });
        return view;
    }

    public void instantiateListener(AddStudentFragmentListener mListener){
        this.mListener=mListener;
    }

    @Override
    public void editTextError() {
        Toast.makeText(getContext(),getString(R.string.edit_text_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(Student student) {
        mListener.addStudentFragment(student,mAction);
    }

    public void clearEditText(){

        mEtClass.setEnabled(true);
        mEtRoll.setEnabled(true);
        mEtName.setEnabled(true);
        mButton.setVisibility(View.VISIBLE);
        mEtName.setText("");
        mEtRoll.setText("");
        mEtClass.setText("");
        mEtName.requestFocus();
        mButton.setText(getString(R.string.btn_add));
        mAction = constants.VALUE_BTN_ADD;
    }

    public void setEditText(Student student){
        mEtName.requestFocus();
        mEtName.setText(student.getName());
        mEtRoll.setText(student.getRoll());
        mEtClass.setText(student.getClassName());
        mButton.setText(getString(R.string.btn_update));
        mAction = constants.VALUE_BTN_UPDATE;
    }

    public void freezeEditText(Student student){
        mEtName.setText(student.getName());
        mEtRoll.setText(student.getRoll());
        mEtClass.setText(student.getClassName());
        mEtClass.setEnabled(false);
        mEtRoll.setEnabled(false);
        mEtName.setEnabled(false);
        mButton.setVisibility(View.INVISIBLE);
    }

    public interface AddStudentFragmentListener {
        void addStudentFragment(Student student,String valueOfButton);
    }
}
