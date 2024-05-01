package com.example.lms;

import android.os.Bundle;

import com.example.lms.ui.main.SectionsPagerAdapterManager;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

public class UserPanel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_panel);
        SectionsPagerAdapterManager sectionsPagerAdapter = new SectionsPagerAdapterManager(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        TabLayout.Tab tab = tabs.getTabAt(3);
        if (tab != null) {
            tab.select();
        }

    }
}