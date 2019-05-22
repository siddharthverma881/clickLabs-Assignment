package com.example.android.assignment4.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PageAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;
    private List<String> tabNames;

    public PageAdapter(FragmentManager fm, List<Fragment> fragments, ArrayList<String> tabNames) {
        super(fm);
        this.fragments=fragments;
        this.tabNames=tabNames;
    }

    @Override
    public Fragment getItem(int i) {
        return this.fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames.get(position);
    }
}
