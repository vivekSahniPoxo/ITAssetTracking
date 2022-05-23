package com.example.itassettracking;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PreviousAudit homeFragment = new PreviousAudit();
                return homeFragment;
            case 1:
                UpcomingAudit sportFragment = new UpcomingAudit();
                return sportFragment;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
