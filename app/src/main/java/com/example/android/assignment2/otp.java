package com.example.android.assignment2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import javax.net.ssl.SNIHostName;

public class otp extends AppCompatActivity {

    protected EditText firstText,secondText,thirdText,fourthText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        //for setting the text in the textview whose id is fetched below
        //text is mentioned in the strings.xml file
        TextView otpText = (TextView) findViewById(R.id.Otp_tv_otp);
        otpText.setText(getString(R.string.otpText));

        //for setting the text in the textview whose id is fetched below
        //text is mentioned in the strings.xml file
        TextView belowOtpText = (TextView) findViewById(R.id.Otp_tv_enterOtp);
        belowOtpText.setText(getString(R.string.belowOtpText));

        //for setting the text in the button whose id is fetched below
        //text is mentioned in the strings.xml file
        Button resendButton = (Button) findViewById(R.id.resend_button);
        resendButton.setText(getString(R.string.resendButton));

        //for setting the text in the button whose id is fetched below
        //text is mentioned in the strings.xml file
        Button submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setText(getString(R.string.submitButton));

        //when resend button is pressed this function operates
        resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //for hiding the keyboard
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                //  clearing all data in the edit text fields present
                firstText.setText("");
                secondText.setText("");
                thirdText.setText("");
                fourthText.setText("");
                firstText.requestFocus();

                //for displaying  message
                Snackbar snackbar = Snackbar.make(view, "otp sent again", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        //when submit button is pressed, this function operates
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //here validate function checks if the edit text fields are filled or not
                    //if they are not filled then an eroor will be shown using snackbar
                    //if valid then the login activity will be opened
                    if (validate(view)) {
                        Intent i = new Intent(otp.this, Login.class);
                        startActivity(i);
                    }
                    else{
                        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        firstText.setText("");
                        secondText.setText("");
                        thirdText.setText("");
                        fourthText.setText("");
                        firstText.requestFocus();
                        Snackbar snackbar = Snackbar.make(view, "wrong otp entered", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });

        //these are the variables declared of type edit text and fetching the data that is stored in the respective edit fields
        firstText = (EditText) findViewById(R.id.Otp_et_first);
        secondText = (EditText) findViewById(R.id.Otp_et_second);
        thirdText = (EditText) findViewById(R.id.Otp_et_third);
        fourthText = (EditText) findViewById(R.id.Otp_et_fourth);

        //if the first edit text field has been filled then, the focus will shift to the next field
        firstText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (firstText.getText().toString().length() == 1)     //size as per your requirement
                {
                    secondText.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
            }
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

        //if the second edit text field has been filled then, the focus will shift to the next field
        secondText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (secondText.getText().toString().length() == 1)     //size as per your requirement
                {
                    thirdText.requestFocus();
                }
                //if the second field is empty and delete button is pressed on the keyboard, then the focus will automatically shift to the previous field
                else if(secondText.getText().toString().length() == 0){
                    firstText.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
            }
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

        //if the first edit text field has been filled then, the focus will shift to the next field
        thirdText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (thirdText.getText().toString().length() == 1)     //size as per your requirement
                {
                    fourthText.requestFocus();
                }
                //if the third field is empty and delete button is pressed on the keyboard, then the focus will automatically shift to the previous field
                else if(thirdText.getText().toString().length() == 0){
                    secondText.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
            }
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

        //if the fourth edit text field is empty and the delete button is pressed on the keyboard then, the focus will shift to the previous field
        fourthText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (fourthText.getText().toString().length() == 0)     //size as per your requirement
                {
                    thirdText.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
            }
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
    }

    //for checking whether all the edit text fields contain atleast one number
    //if it contains then it returns true else it returns false
        private boolean validate (View view){
            String first = firstText.getEditableText().toString();
            String second = secondText.getEditableText().toString();
            String third = thirdText.getEditableText().toString();
            String fourth = fourthText.getEditableText().toString();
            if (first.length() == 1 && second.length() == 1 && third.length() == 1 && fourth.length() == 1)
                return true;
            else {
                Snackbar snackbar = Snackbar.make(view, "wrong otp entered", Snackbar.LENGTH_LONG);
                snackbar.show();
                return false;
            }
        }

    //when the back button is pressed then this activity will be destroyed, and its parent activity will be opened which is defined in the manifest file
        public void backButtonPressed (View view){
            finish();
        }
}
