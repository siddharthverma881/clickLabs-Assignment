package com.example.assignmentretry5.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends FragmentStatePagerAdapter {


    private List<Fragment> mFragments;
    private List<String> mTabNames;

    public HomeAdapter(FragmentManager fm, List<Fragment> fragments, ArrayList<String> tabNames) {
        super(fm);
        this.mFragments=fragments;
        this.mTabNames=tabNames;
    }

    @Override
    public Fragment getItem(int i) {
        return this.mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabNames.get(position);
    }
}
