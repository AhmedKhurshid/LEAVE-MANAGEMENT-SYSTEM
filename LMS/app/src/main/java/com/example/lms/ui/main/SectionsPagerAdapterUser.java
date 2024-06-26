package com.example.lms.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.lms.ApplyforleaveFragment;
import com.example.lms.HolidayCalenderFragment;
import com.example.lms.LeaveSummaryFragment;
import com.example.lms.MyTeamFragment;
import com.example.lms.R;
import com.example.lms.UserProfileFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapterUser extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2 ,R.string.tab_text_7, R.string.tab_text_4,R.string.tab_text_5};
    private final Context mContext;

    public SectionsPagerAdapterUser(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) { Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new ApplyforleaveFragment();
                break;
            case 1:
                fragment = new LeaveSummaryFragment();
                break;
            case 2:
                fragment = new MyTeamFragment();
                break;
            case 3:
                fragment = new UserProfileFragment();
                break;
            case 4:
                fragment = new HolidayCalenderFragment();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }
}