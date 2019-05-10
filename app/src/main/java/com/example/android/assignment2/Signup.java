package com.example.android.assignment2;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Signup extends AppCompatActivity {

    private EditText nameText,genderText,userTypeText,occupationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //for setting the text (defined in the strings.xml file) in the textview whose id is fetched below
        TextView welcomeText = (TextView) findViewById(R.id.Signup_tv_welcome);
        welcomeText.setText(getString(R.string.welcome));

        //for setting the text (defined in the strings.xml file) in the textview whose id is fetched below
        TextView belowWelcomeText = (TextView) findViewById(R.id.Signup_tv_details);
        belowWelcomeText.setText(getString(R.string.belowWelcomeText));

        //for setting the hint (defined in the strings.xml file) in the edittext whose id is fetched below
        nameText = (EditText) findViewById(R.id.Signup_et_name);
        nameText.setHint(getString(R.string.nameHint));

        //for setting the hint (defined in the strings.xml file) in the edittext whose id is fetched below
        genderText = (EditText) findViewById(R.id.Signup_et_gender);
        genderText.setHint(getString(R.string.genderHint));

        //for setting the hint (defined in the strings.xml file) in the edittext whose id is fetched below
        userTypeText = (EditText) findViewById(R.id.Signup_et_userType);
        userTypeText.setHint(getString(R.string.userTypeHint));

        //for setting the hint (defined in the strings.xml file) in the edittext whose id is fetched below
        occupationText = (EditText) findViewById(R.id.Signup_et_occupation);
        occupationText.setHint(getString(R.string.occupationHint));

        //for setting the text (defined in the strings.xml file) in the button whose id is fetched below
        Button continueText = (Button) findViewById(R.id.Signup_btn_continue);
        continueText.setText(getString(R.string.continueText));

        //for setting the text (defined in the strings.xml file) in the textview whose id is fetched below
        TextView differentAccountText = (TextView) findViewById(R.id.Signup_tv_differentAccount);
        differentAccountText.setText(getString(R.string.differentAccount));


        //when the gender field is empty then focus will automatically shift to the name field using this function
//        genderText.addTextChangedListener(new TextWatcher() {
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // TODO Auto-generated method stub
//                if (genderText.getText().toString().length() == 0)
//                {
//                    nameText.requestFocus();
//                }
//            }
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//                // TODO Auto-generated method stub
//            }
//            public void afterTextChanged(Editable s) {
//                // TODO Auto-generated method stub
//            }
//        });
//
//        //when the user type field is empty then focus will automatically shift to the gender field using this function
//        userTypeText.addTextChangedListener(new TextWatcher() {
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // TODO Auto-generated method stub
//                if (userTypeText.getText().toString().length() == 0)     //size as per your requirement
//                {
//                    genderText.requestFocus();
//                }
//            }
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//                // TODO Auto-generated method stub
//
//            }
//            public void afterTextChanged(Editable s) {
//                // TODO Auto-generated method stub
//            }
//        });
//
//
//        //when the occupation field is empty then focus will automatically shift to the user type field using this function
//        occupationText.addTextChangedListener(new TextWatcher() {
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // TODO Auto-generated method stub
//                if (occupationText.getText().toString().length() == 0)     //size as per your requirement
//                {
//                    userTypeText.requestFocus();
//                }
//            }
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//                // TODO Auto-generated method stub
//            }
//            public void afterTextChanged(Editable s) {
//                // TODO Auto-generated method stub
//            }
//        });
    }

    //this function checks that whether the name entered matches the validation or not.
    //if matches then return true else return false
    private boolean validName(View view){
        String name = nameText.getEditableText().toString();
        //the name should not be empty
        if(name.isEmpty()){
            Snackbar snackbar = Snackbar.make(view,"Name field can not be empty",Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }
        //the length of name should be greater than 2
        else if(name.length()<3){
            Snackbar snackbar = Snackbar.make(view,"name too short",Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }
        //name should only contain alphabets whether capital or small letters
        else if(!name.matches( "[a-zA-Z]*" )){
            Snackbar snackbar = Snackbar.make(view,"Wrong input entered in the name field",Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }
        return true;
    }

    //this function checks that whether the gender entered matches the validation or not.
    //if matches then return true else return false
    private boolean validGender(View view){
        String gender = genderText.getEditableText().toString().trim();
        gender.toLowerCase();
        //the gender field should not be empty
        if(gender.isEmpty()){
            Snackbar snackbar = Snackbar.make(view,"Gender field can not be empty", Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }
        //gender can be :- m,M,f,F,male,MALE,female,FEMALE
        else if(!(gender.matches("[mfMF]") | gender.matches("male") | gender.matches("MALE") | gender.matches("female") | gender.matches("FEMALE"))){
            Snackbar snackbar = Snackbar.make(view,"Wrong input entered in gender field",Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }
        return true;
    }

    //this function checks that whether the user type entered matches the validation or not.
    //if matches then return true else return false
    private boolean validUser(View view){
        String userType = userTypeText.getEditableText().toString().trim();
        //user type can not be empty
        if(userType.isEmpty()){
            Snackbar snackbar = Snackbar.make(view,"user type can not be empty",Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }
        //user type can not contain any numerics
        else if(!userType.matches("^[a-zA-Z]")){
            Snackbar snackbar = Snackbar.make(view,"user type can only be alphabet",Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }
        return true;
    }

    //this function checks that whether the occupation entered matches the validation or not.
    //if matches then return true else return false
    private boolean validOccupation(View view){
        String occupation = occupationText.getEditableText().toString().trim();
        //occupation should not be empty
        if(occupation.isEmpty()){
            Snackbar snackbar = Snackbar.make(view,"occupation can not be empty",Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }
        //occupation can not contain numerics
        else if(!occupation.matches("^[a-zA-Z]")){
            Snackbar snackbar = Snackbar.make(view,"occupation can only be alphabet",Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }
        return true;
    }

    //when pressed on continue button, this function will first check whether the details entered match the sequences mentioned above or not
    //if details match then a new activity(otp) will be opened
    public void confirmRegistration(View view){
        if(!(validName(view) && validGender(view) && validUser(view) && validOccupation(view)))
            return;
        else{
            Intent i = new Intent(Signup.this,otp.class);
            startActivityForResult(i,0);
        }

    }

    //when the back button is pressed this function will be called and it will finish the ongoing activity and will open the parent activity that is defined in the manifest file
    public void backButton(View view){
        finish();
    }
}
