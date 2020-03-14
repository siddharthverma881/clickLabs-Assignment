package com.example.android.assignment2;

import android.content.Intent;
import android.os.Handler;

import com.example.siddharthlibrary.MainActivity;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    private EditText emailText;
    private EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    //when the eye image is pressed, the below function will show the password
        ImageView image = (ImageView) findViewById(R.id.Login_iv_eye);
        image .setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        passwordText.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        passwordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });


    //for setting the text in the textview where text is defined in the strings.xml file
        TextView loginTextView = (TextView) findViewById(R.id.Login_tv_login);
        loginTextView.setText(R.string.loginText);


     //for setting the hint("Email") in the editview field
        emailText = (EditText) findViewById(R.id.Login_et_email);
        emailText.setHint(getString(R.string.emailHint));


     //for setting the hint("Password") which is defined in the strings.xml file
        passwordText = (EditText) findViewById(R.id.Login_et_password);
        passwordText.setHint(getString(R.string.passwordHint));


     //for setting the text("Log In") which is defined in the strings.xml file
        Button button = (Button) findViewById(R.id.Login_btn_login);
        button.setText(getString(R.string.buttonText));


        //for setting the text which is defined in the strings.xml file
        TextView notRegisteredTextView = (TextView) findViewById(R.id.Login_tv_notregistered);
        notRegisteredTextView.setText(getString(R.string.noAccount));


        //for setting the text("Register") which is defined in the strings.xml file
        TextView register = (TextView) findViewById(R.id.Login_tv_register);
        register.setText(getString(R.string.register));


     //if password edit text is empty and back button is pressed on the keyboard, then it will jump on to the email edit text by the function defined below
        passwordText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (passwordText.getText().toString().length() == 0)
                {
                    emailText.requestFocus();
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


    //the below functions are defined so that when user presses the back button twice within a time limit of 2 seconds then the app will shut down.
    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        if (mHandler != null) { mHandler.removeCallbacks(mRunnable); }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        mHandler.postDelayed(mRunnable, 2000);
    }


    //if the user needs to sign up then on clicking the register button this function will open up the register activity
    public void register(View view){
        Intent i = new Intent(Login.this, Signup.class);
        startActivity(i);
    }

    //this is the regex validation for the validation of email id entered by the user
    private static final Pattern EMAIL =
            Pattern.compile("^" +
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{5,}" +               //at least 4 characters
                    "$");

    //this is the regex validation for the validation of password entered by the user
    private static final Pattern PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{5,}" +               //at least 4 characters
                    "$");

    //this function checks whether email is valid or not
    private boolean validEmail(View view){
        String email = emailText.getEditableText().toString().trim();
        if(email.isEmpty()){
            Snackbar snackbar = Snackbar.make(view, "Email can not be empty" , Snackbar.LENGTH_SHORT);
            snackbar.show();
            return false;
        }
        //here EMAIL is the pattern that we created for the regex validation for email id
        else if(!EMAIL.matcher(email).matches()){
            Snackbar snackbar = Snackbar.make(view, "Wrong email entered" , Snackbar.LENGTH_SHORT);
            snackbar.show();
            return false;
        }
        return true;
    }


    //this function checks whether password is valid or not
    private boolean validPassword(View view){
        String password = passwordText.getEditableText().toString().trim();
        if(password.isEmpty()){
            Snackbar snackbar = Snackbar.make(view, "Password can not be empty" , Snackbar.LENGTH_SHORT);
            snackbar.show();
            return false;
        }
        //here PATTERN is the pattern that we created for the regex validation of password
        else if(!PATTERN.matcher(password).matches()){
            if(password.length()<5) {
                Snackbar snackbar = Snackbar.make(view, "Password too weak", Snackbar.LENGTH_SHORT);
                snackbar.show();
                return false;
            }
            else{
                Snackbar snackbar = Snackbar.make(view,
                        "Password should contain atleast:-1 uppercase,1 lowercase,1 special character,1 numeric",
                        Snackbar.LENGTH_SHORT);
                snackbar.show();
                return false;
            }
        }
        return true;
    }


    //this function is called when the user clicks on the login in button
    //it checks whether the email id and password match the pattern we have described or not
    public void confirmInput(View view){
        if(!(validEmail(view) && validPassword(view)))
            return;
        else{
            Snackbar snackbar = Snackbar.make(view, "Login successful" , Snackbar.LENGTH_SHORT);
            snackbar.show();
            startActivity(new Intent(Login.this, MainActivity.class));
        }
    }
}