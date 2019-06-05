package com.example.assignment5.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.assignment5.R;
import com.example.assignment5.adapters.HomePageAdapter;
import com.example.assignment5.fragments.AddStudentFragment;
import com.example.assignment5.fragments.StudentListFragment;
import com.example.assignment5.utilities.Constants;

import java.util.ArrayList;
import java.util.List;

public class StudentListActivity extends AppCompatActivity implements StudentListFragment.StudentListFragmentListener, AddStudentFragment.OnAddFragmentListener {

    private PagerAdapter mPageAdapter;
    private List<Fragment> mFragmentsList=new ArrayList<>();
    private ViewPager mViewPager;
    private ArrayList<String> mTabNames=new ArrayList<>();
    private TabLayout mTabLayout;
    private int mTabPosition;
    Constants constant=new Constants();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        mTabLayout = findViewById(R.id.tab_layout);
        mTabNames.add(getString(R.string.tab_layout_list));
        mTabNames.add(getString(R.string.tab_layout_add));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //getting position of tab
                mTabPosition = tab.getPosition();
                if (mTabPosition == constant.FRAGMENT_TWO_INTIAL) {
                    AddStudentFragment fragmentDetail =(AddStudentFragment) mFragmentsList.get(constant.FRAGMENT_TWO_INTIAL);
                    fragmentDetail.clearEditText();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        mViewPager = findViewById(R.id.view_pager);
        addPagesToFragmentList();

        mPageAdapter = new HomePageAdapter(getSupportFragmentManager(),mFragmentsList,mTabNames);
        mViewPager.setAdapter(mPageAdapter);
        mViewPager.setOffscreenPageLimit(constant.SET_OFF_SCREEN_PAGE_LIMIT);
        mViewPager.setCurrentItem(constant.FRAGMENT_ONE_INTIAL);
        mViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mTabLayout.setupWithViewPager(mViewPager);
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

    //this is the method of interface StudentListFragmentListener declared in class StudentListFragment
    @Override
    public void studentFragment(Bundle bundle) {
        mViewPager.setCurrentItem(constant.FRAGMENT_TWO_INTIAL);
        if(bundle.getString(constant.KEY).equals(constant.VALUE_ADDING) ) {
            AddStudentFragment fragment = (AddStudentFragment) mFragmentsList.get(constant.FRAGMENT_TWO_INTIAL);
            fragment.clearEditText();
        }
        else if(bundle.getString(constant.KEY).equals(constant.VALUE_EDITING) ){
            AddStudentFragment fragment = (AddStudentFragment) mFragmentsList.get(constant.FRAGMENT_TWO_INTIAL);
            fragment.setEditText(bundle);
        }
    }


    //this is the method of interface onAddFragmentListener declared in class AddStudentActivity
    @Override
    public void addFragmentListener(Bundle bundle) {
        StudentListFragment studentFragment = (StudentListFragment)mFragmentsList.get(constant.FRAGMENT_ONE_INTIAL);
        mViewPager.setCurrentItem(constant.FRAGMENT_ONE_INTIAL);
        studentFragment.dataTransfer(bundle);
    }
}
