package com.example.android.assignment3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import Utilities.util;

public class AddStudentActivity extends AppCompatActivity {

    /**
     * @param etName,etRoll,etClass contain the id of the edit texts of the name,rollno and class respectively
     * @param etName,etRoll,etClass contain the id of the edit texts when the new values of name,roll and class are filled respectively on clicking the edit option in previous activity
     * @param studentName,studentClass,studentRoll contain the string value that we fill in the edit texts
     * @param btnAddStudent represent the working of button while adding a new student in activity_add_student.xml file
     * @param backImage consists the id of the back button image
     * @param Util is the object of class util for calling its functions
     * @param context gives the application's current context
     * @param result checks for what reason the intent is called
     */

    protected EditText etName,etRoll,etClass,newEtName,newEtRoll,newEtClass;
    protected String studentName,studentClass,studentRoll;
    protected Button btnAddStudent;
    protected ImageView backImage;
    protected util Util;
    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String result = bundle.getSerializable(getString(R.string.builder_key)).toString();

    //function for getting all the id's of the edittext,button and image View
        init();

        if (result.equals(getString(R.string.intent_key_add))) {
        //setting the functionality if back button is pressed
            init2();

            btnAddStudent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                //for getting the values in the edit texts
                    getValues(etName,etRoll,etClass);
                    if (Util.validName(context, studentName, studentRoll, studentClass)) {
                        Intent returnIntent = getIntent();
                        returnIntent.putExtra(getString(R.string.intent_key_name), studentName);
                        returnIntent.putExtra(getString(R.string.intent_key_roll), studentRoll);
                        returnIntent.putExtra(getString(R.string.intent_key_class), studentClass);
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                    } else return;
                }
            });
        }

        else if(result.equals(getString(R.string.intent_key_view))){
            init2();
            studentName = bundle.getSerializable(getString(R.string.dialog_view_name)).toString();
            studentRoll = bundle.getSerializable(getString(R.string.dialog_view_roll)).toString();
            studentClass = bundle.getSerializable(getString(R.string.dialog_view_class)).toString();
            etName.setText(studentName);
            etName.setEnabled(false);
            etRoll.setText(studentRoll);
            etRoll.setEnabled(false);
            etClass.setText(studentClass);
            etClass.setEnabled(false);
            btnAddStudent.setVisibility(View.INVISIBLE);
        }

        else if(result.equals(getString(R.string.intent_key_edit))){
            studentName = bundle.getSerializable(getString(R.string.dialog_edit_name)).toString();
            studentRoll = bundle.getSerializable(getString(R.string.dialog_edit_roll)).toString();
            studentClass = bundle.getSerializable(getString(R.string.dialog_edit_class)).toString();
            etName.setText(studentName);
            etRoll.setText(studentRoll);
            etClass.setText(studentClass);
            backImage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent returnIntent = getIntent();
                    returnIntent.putExtra(getString(R.string.intent_key_name), studentName);
                    returnIntent.putExtra(getString(R.string.intent_key_roll), studentRoll);
                    returnIntent.putExtra(getString(R.string.intent_key_class), studentClass);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            });
            etRoll.setEnabled(false);
            btnAddStudent.setText(getString(R.string.btn_update_student));
            btnAddStudent.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    newEtName = (EditText) findViewById(R.id.et_name);
                    newEtRoll = (EditText) findViewById(R.id.et_roll);
                    newEtClass = (EditText) findViewById(R.id.et_class);
                    getValues(newEtName,newEtRoll,newEtClass);
                    if (Util.validName(context, studentName, studentRoll, studentClass)) {
                        Intent returnIntent = getIntent();
                        returnIntent.putExtra(getString(R.string.intent_key_name), studentName);
                        returnIntent.putExtra(getString(R.string.intent_key_roll), studentRoll);
                        returnIntent.putExtra(getString(R.string.intent_key_class), studentClass);
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                    } else return;
                }
            });
        }
    }
    public void init(){

        Util = new util();

        context = getApplicationContext();

        etName = (EditText) findViewById(R.id.et_name);
        etName.requestFocus();

        etRoll = (EditText) findViewById(R.id.et_roll);

        etClass = (EditText) findViewById(R.id.et_class);

        btnAddStudent = (Button) findViewById(R.id.add_student_btn_add);

        backImage = (ImageView) findViewById(R.id.iv_back);

    }
    public void init2(){

        backImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AddStudentActivity.this.finish();
            }
        });
    }
    public void getValues(EditText editTextName,EditText editTextRoll,EditText editTextClass){
        studentName = editTextName.getText().toString();
        studentRoll= editTextRoll.getText().toString();
        studentClass = editTextClass.getText().toString();
    }
}
