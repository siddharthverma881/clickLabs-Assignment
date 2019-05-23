package com.example.android.assignment4.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.android.assignment4.R;
import com.example.android.assignment4.Utilities.Constants;
import com.example.android.assignment4.fragments.AddStudentFragment;
import com.example.android.assignment4.fragments.StudentListFragment;

import java.util.ArrayList;
import java.util.List;



public class StudentListActivity extends AppCompatActivity implements StudentListFragment.StudentListFragmentListener, AddStudentFragment.OnAddFragmentListener {
    /**
     * @param mPageAdapter is the object of PageAdapter
     * @param mFragmentsList for adding the various fragments in the pageadapter.
     * @param mViewPager is the object of ViewPager
     * @param mTabName for giving the names to the tab titles and adding into an arraylist
     * @param constant is the object of class constant declared in util package for fetching constant values
     */

    private PagerAdapter mPageAdapter;
    private List<Fragment> mFragmentsList=new ArrayList<>();
    private ViewPager mViewPager;
    private ArrayList<String> mTabNames=new ArrayList<>();
    private TabLayout tabLayout;
    private int tabPosition;
    Constants constant=new Constants();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        tabLayout = findViewById(R.id.tab_layout);
        mTabNames.add(getString(R.string.tab_layout_list));
        mTabNames.add(getString(R.string.tab_layout_add));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //getting position of tab
                tabPosition = tab.getPosition();
                if (tabPosition == constant.FRAGMENT_TWO_INTIAL) {
                    AddStudentFragment fragmentDetail =(AddStudentFragment) mFragmentsList.get(constant.FRAGMENT_TWO_INTIAL);
                    fragmentDetail.clearEditText();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager = findViewById(R.id.view_pager);
        addPagesToFragmentList();

        mPageAdapter = new com.example.android.assignment4.adapters.PageAdapter(getSupportFragmentManager(),mFragmentsList,mTabNames);
        mViewPager.setAdapter(mPageAdapter);
        mViewPager.setOffscreenPageLimit(constant.SET_OFF_SCREEN_PAGE_LIMIT);
        mViewPager.setCurrentItem(constant.FRAGMENT_ONE_INTIAL);
        mViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        tabLayout.setupWithViewPager(mViewPager);
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

    //this is the method of interface studentListFragmentListener declared in studentListFragment
    @Override
    public void studentFragment(Bundle bundle) {
        mViewPager.setCurrentItem(constant.FRAGMENT_TWO_INTIAL);
        if(bundle.getString(constant.KEY).equals(getString(R.string.value_adding)) ) {
            AddStudentFragment fragment = (AddStudentFragment) mFragmentsList.get(constant.FRAGMENT_TWO_INTIAL);
            fragment.clearEditText();
        }
        else if(bundle.getString(constant.KEY).equals(getString(R.string.value_editing)) ){
            AddStudentFragment fragment = (AddStudentFragment) mFragmentsList.get(constant.FRAGMENT_TWO_INTIAL);
            fragment.setEditText(bundle);
        }

    }


//this is the method of interface onAddFragmentListener declared in class AddStudentActivity
    @Override
    public void addFragmentListener(Bundle bundle) {
        StudentListFragment studentFragment = (StudentListFragment)mFragmentsList.get(0);
        mViewPager.setCurrentItem(constant.FRAGMENT_ONE_INTIAL);
        studentFragment.dataTransfer(bundle);
    }
}
