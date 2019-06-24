package com.example.assignmentretry5.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.assignmentretry5.adapters.HomeAdapter;
import com.example.assignmentretry5.R;
import com.example.assignmentretry5.fragments.AddStudentFragment;
import com.example.assignmentretry5.fragments.StudentListFragment;
import com.example.assignmentretry5.interfaces.Contract;
import com.example.assignmentretry5.models.Student;
import com.example.assignmentretry5.services.AsyncTaskHelper;
import com.example.assignmentretry5.services.IntentServiceHelper;
import com.example.assignmentretry5.services.ServiceHelper;
import com.example.assignmentretry5.utils.Constants;
import com.example.assignmentretry5.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class StudentListActivity extends AppCompatActivity implements StudentListFragment.StudentListFragmentListener, AddStudentFragment.AddStudentFragmentListener, Contract {

    private BroadcastReceiver mReceiver;
    private IntentFilter mIntent = new IntentFilter();
    private List<Fragment> mFragmentsList=new ArrayList<>();
    private ViewPager mViewPager;
    private ArrayList<String> mTabNames=new ArrayList<>();
    private TabLayout mTabLayout;
    private int mTabPosition;
    Constants constants = new Constants();
    Util util = new Util();
    AsyncTaskHelper asyncTaskHelper;
    private Student studentObject;
    private String optionSelected = constants.DIALOG_MESSAGE_ASYNCTASK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        mTabLayout = findViewById(R.id.tab_layout);
        mTabNames.add(getString(R.string.tab_layout_list));
        mTabNames.add(getString(R.string.tab_layout_add));

        mViewPager = findViewById(R.id.view_pager);
        addPagesToFragmentList();

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //getting position of tab
                mTabPosition = tab.getPosition();
                if (mTabPosition == constants.FRAGMENT_TWO_INTIAL) {
                    AddStudentFragment fragmentDetail =(AddStudentFragment) mFragmentsList.get(constants.FRAGMENT_TWO_INTIAL);
                    fragmentDetail.clearEditText();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        mViewPager.setAdapter(new HomeAdapter(getSupportFragmentManager(),mFragmentsList,mTabNames));
        mViewPager.setOffscreenPageLimit(constants.SET_OFF_SCREEN_PAGE_LIMIT);
        mViewPager.setCurrentItem(constants.FRAGMENT_ONE_INTIAL);
        mViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mTabLayout.setupWithViewPager(mViewPager);

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                StudentListFragment studentListFragment = (StudentListFragment) mFragmentsList.get(constants.FRAGMENT_ONE_INTIAL);
                AddStudentFragment addStudentFragment = (AddStudentFragment) mFragmentsList.get(constants.FRAGMENT_TWO_INTIAL);

                String result = intent.getStringExtra(constants.BROADCAST_STRING_KEY);

                if(result.equals(constants.ADD_SUCCESS)){
                    tabSwitch(constants.FRAGMENT_ONE_INTIAL);
                    studentListFragment.addDataToArrayList(studentObject);
                }
                else if(result.equals(constants.ADD_FAIL)){
                    tabSwitch(constants.FRAGMENT_TWO_INTIAL);
                    addStudentFragment.setEditText(studentObject);
                }
                else if(result.equals(constants.EDIT_SUCCESS)){
                    tabSwitch(constants.FRAGMENT_ONE_INTIAL);
                    studentListFragment.editDataOfArrayList(studentObject);
                }
                else if(result.equals(constants.EDIT_FAIL)){
                    tabSwitch(constants.FRAGMENT_TWO_INTIAL);
                    addStudentFragment.setEditText(studentObject);
                }
                else if(result.equals(constants.DELETE_SUCCESS)){
                    studentListFragment.deleteDataFromArrayList();
                }
                else if(result.equals(constants.ROLL_NUMBER_EXIST)){
                    util.displayToast(StudentListActivity.this,getString(R.string.toast_roll_exists));
                    addStudentFragment.setEditText(studentObject);
                }
            }
        };
    }

    /**
     * @param position is the position of fragment to which user wants to navigate
     */

    public void tabSwitch(int position){
        mViewPager.setCurrentItem(position);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mIntent = new IntentFilter(constants.SERVICE_BROADCAST_KEY);
        registerReceiver(mReceiver,mIntent);
    }

    @Override
    protected void onPause() {
        if(mIntent != null) {
            unregisterReceiver(mReceiver);
            mIntent = null;
        }
        super.onPause();
    }

    //adding different fragments to an arraylist
    public void addPagesToFragmentList(){
        StudentListFragment fragment = new StudentListFragment();
        fragment.instantiateListener(this);
        mFragmentsList.add(fragment);
        AddStudentFragment fragment2 = new AddStudentFragment();
        fragment2.instantiateAddListener(this);
        mFragmentsList.add(fragment2);
    }

    @Override
    public void studentListFragment(Student student,String actionOfButton) {
        if(actionOfButton.equals(constants.KEY_ADD)) {
            tabSwitch(constants.FRAGMENT_TWO_INTIAL);
            AddStudentFragment fragment = (AddStudentFragment) mFragmentsList.get(constants.FRAGMENT_TWO_INTIAL);
            fragment.clearEditText();
        }
        else if(actionOfButton.equals(constants.KEY_UPDATE)){
            tabSwitch(constants.FRAGMENT_TWO_INTIAL);
            AddStudentFragment fragment = (AddStudentFragment) mFragmentsList.get(constants.FRAGMENT_TWO_INTIAL);
            fragment.editEditText(student);
        }
        else if(actionOfButton.equals(constants.KEY_DELETE)){
            if(optionSelected.equals(constants.DIALOG_MESSAGE_ASYNCTASK)) {
                asyncTaskHelper = new AsyncTaskHelper(this, constants.KEY_DELETE);
                asyncTaskHelper.execute(student);
            }
            else if(optionSelected.equals(constants.DIALOG_MESSAGE_SERVICE)){
                Intent intent = new Intent(StudentListActivity.this, ServiceHelper.class);
                intent.putExtra(constants.SERVICE_STUDENT_KEY,student);
                intent.putExtra(constants.SERVICE_VALUE_BUTTON,constants.KEY_DELETE);
                startService(intent);
            }
            else if(optionSelected.equals(constants.DIALOG_MESSAGE_INTENTSERVICE)){
                Intent intent = new Intent(StudentListActivity.this, IntentServiceHelper.class);
                intent.putExtra(constants.INTENT_STUDENT_KEY,student);
                intent.putExtra(constants.INTENT_VALUE_BUTTON,constants.KEY_DELETE);
                startService(intent);
            }
        }
        else if(actionOfButton.equals(constants.ASYNC_KEY_GETALLDATA)){
            asyncTaskHelper = new AsyncTaskHelper(this,constants.ASYNC_KEY_GETALLDATA);
            asyncTaskHelper.execute(new Student("","",""));
        }
    }

    @Override
    public void addStudentFragment(Student student,String valueOfButton,int dialogPosition) {
        if(dialogPosition == constants.DIALOG_ONE) {
            optionSelected = constants.DIALOG_MESSAGE_ASYNCTASK;
            AsyncTaskHelper asyncTaskHelper = new AsyncTaskHelper(this, valueOfButton);
            asyncTaskHelper.execute(student);
        }
        else if(dialogPosition == constants.DIALOG_TWO){
            studentObject = student;
            optionSelected = constants.DIALOG_MESSAGE_SERVICE;
            Intent intent = new Intent(StudentListActivity.this, ServiceHelper.class);
            intent.putExtra(constants.SERVICE_STUDENT_KEY,student);
            intent.putExtra(constants.SERVICE_VALUE_BUTTON,valueOfButton);
            startService(intent);
        }
        else if(dialogPosition == constants.DIALOG_THREE){
            studentObject = student;
            optionSelected = constants.DIALOG_MESSAGE_INTENTSERVICE;
            Intent intent = new Intent(StudentListActivity.this, IntentServiceHelper.class);
            intent.putExtra(constants.INTENT_STUDENT_KEY,student);
            intent.putExtra(constants.INTENT_VALUE_BUTTON,valueOfButton);
            startService(intent);
        }
    }

    @Override
    public void asyncTaskStatus(Student mStudent,String mResult) {
        StudentListFragment studentListFragment = (StudentListFragment) mFragmentsList.get(constants.FRAGMENT_ONE_INTIAL);
        AddStudentFragment addStudentFragment = (AddStudentFragment) mFragmentsList.get(constants.FRAGMENT_TWO_INTIAL);

        if(mResult.equals(constants.ADD_SUCCESS)){
            tabSwitch(constants.FRAGMENT_ONE_INTIAL);
            studentListFragment.addDataToArrayList(mStudent);
        }
        else if(mResult.equals(constants.EDIT_SUCCESS)){
            tabSwitch(constants.FRAGMENT_ONE_INTIAL);
            studentListFragment.editDataOfArrayList(mStudent);
        }
        else if(mResult.equals(constants.DELETE_SUCCESS)){
            studentListFragment.deleteDataFromArrayList();
        }
        else if(mResult.equals(constants.ADD_FAIL)){
            util.displayToast(this,getString(R.string.toast_async_add_fail));
            addStudentFragment.setEditText(mStudent);
        }
        else if(mResult.equals(constants.EDIT_FAIL)){
            util.displayToast(this,getString(R.string.toast_async_edit_fail));
            addStudentFragment.editEditText(mStudent);
        }
        else if(mResult.equals(constants.DELETE_FAIL)){
            util.displayToast(this,getString(R.string.toast_async_delete_fail));
        }
        else if(mResult.equals(constants.ROLL_NUMBER_EXIST)){
            util.displayToast(this,getString(R.string.toast_roll_exists));
            addStudentFragment.setEditText(mStudent);
        }
    }

    @Override
    public void showAllData(ArrayList<Student> studentArrayList){
        StudentListFragment studentListFragment = (StudentListFragment) mFragmentsList.get(constants.FRAGMENT_ONE_INTIAL);
        studentListFragment.getAllData(studentArrayList);
    }
}
