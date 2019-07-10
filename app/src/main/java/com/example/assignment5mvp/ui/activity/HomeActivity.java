package com.example.assignment5mvp.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.assignment5mvp.R;
import com.example.assignment5mvp.adapter.HomePageAdapter;
import com.example.assignment5mvp.ui.fragment.add.AddStudentFragment;
import com.example.assignment5mvp.ui.fragment.list.StudentListFragment;
import com.example.assignment5mvp.model.Student;
import com.example.assignment5mvp.util.Constants;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements StudentListFragment.StudentFragmentListener, AddStudentFragment.AddStudentFragmentListener,HomeView {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ArrayList<String> mTabNames = new ArrayList<>();
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    Constants constants = new Constants();
    private int mTabPosition;
    private HomePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTabLayout = findViewById(R.id.home_tab_layout);
        mTabNames.add(getString(R.string.tab_layout_element_one));
        mTabNames.add(getString(R.string.tab_layout_element_two));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTabPosition = tab.getPosition();
                if (mTabPosition == constants.FRAGMENT_TWO_INTIAL) {
                    AddStudentFragment fragmentDetail =(AddStudentFragment) mFragmentList.get(constants.FRAGMENT_TWO_INTIAL);
                    fragmentDetail.clearEditText();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        mViewPager = findViewById(R.id.home_view_pager);
        addPagesToFragmentList();

        mViewPager.setAdapter(new HomePageAdapter(getSupportFragmentManager(),mFragmentList,mTabNames));
        mViewPager.setOffscreenPageLimit(constants.SET_OFF_SCREEN_PAGE_LIMIT);
        mViewPager.setCurrentItem(constants.FRAGMENT_ONE_INTIAL);
        mViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mTabLayout.setupWithViewPager(mViewPager);

        mPresenter = new HomePresenterImpl(this);
    }

    public void addPagesToFragmentList(){
        StudentListFragment studentListFragment = new StudentListFragment();
        studentListFragment.instantiateListener(this);
        mFragmentList.add(studentListFragment);
        AddStudentFragment addStudentFragment = new AddStudentFragment();
        addStudentFragment.instantiateListener(this);
        mFragmentList.add(addStudentFragment);
    }

    @Override
    public void studentListFragment(Student student,String action) {
        mPresenter.openAddStudentFragment(student,action);
    }

    @Override
    public void addStudentFragment(Student student,String valueOfButton) {
        mPresenter.openStudentListFragment(student,valueOfButton);
    }

    @Override
    public void onAddStudent() {
        AddStudentFragment addStudentFragment = (AddStudentFragment) mFragmentList.get(constants.FRAGMENT_TWO_INTIAL);
        mViewPager.setCurrentItem(constants.FRAGMENT_TWO_INTIAL);
        addStudentFragment.clearEditText();
    }

    @Override
    public void onEditStudent(Student student) {
        AddStudentFragment addStudentFragment = (AddStudentFragment) mFragmentList.get(constants.FRAGMENT_TWO_INTIAL);
        mViewPager.setCurrentItem(constants.FRAGMENT_TWO_INTIAL);
        addStudentFragment.setEditText(student);
    }

    @Override
    public void onViewStudent(Student student) {
        AddStudentFragment addStudentFragment = (AddStudentFragment) mFragmentList.get(constants.FRAGMENT_TWO_INTIAL);
        mViewPager.setCurrentItem(constants.FRAGMENT_TWO_INTIAL);
        addStudentFragment.freezeEditText(student);
    }

    @Override
    public void addStudentSuccess(Student student) {
        StudentListFragment studentListFragment = (StudentListFragment) mFragmentList.get(constants.FRAGMENT_ONE_INTIAL);
        mViewPager.setCurrentItem(constants.FRAGMENT_ONE_INTIAL);
        studentListFragment.addStudent(student);
    }

    @Override
    public void editStudentSuccess(Student student) {
        StudentListFragment studentListFragment = (StudentListFragment) mFragmentList.get(constants.FRAGMENT_ONE_INTIAL);
        mViewPager.setCurrentItem(constants.FRAGMENT_ONE_INTIAL);
        studentListFragment.editStudent(student);
    }
}
