package com.example.lms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Dashboard_admin extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);
    }

    public void addm(View view) {
        Intent i = new Intent(Dashboard_admin.this, Addmanager.class);
        startActivity(i);
    }

    public void adde(View view) {
        Intent i = new Intent(Dashboard_admin.this, Addemployee.class);
        startActivity(i);
    }
    public void btnl(View view) {
        Intent i = new Intent(Dashboard_admin.this, MainActivity.class);
        startActivity(i);
    }

}