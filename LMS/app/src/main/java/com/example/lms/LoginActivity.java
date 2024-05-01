package com.example.lms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    static final String sharedPref="Login";
    static final String username="etUsername";


    EditText etUsername,etPassword;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db=new DatabaseHelper(this);
        db.insert_initials();
        init();
    }

    public void init (){
        etUsername = findViewById(R.id.etusersip);
        etPassword = findViewById(R.id.etpasssip);
    }

    public void log(View view) {

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        Cursor c = db.dbLog(username,password);
        Cursor c2 = db.dbLogUser(username,password);
        Cursor c3 = db.dbLogUser2(username,password);
        if (validate(username,password)){


            SharedPreferences sharedPreferences=getSharedPreferences(sharedPref,MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString(username,etUsername.getText().toString());
            editor.apply();


        if (c.moveToFirst()){

            Intent i=new Intent(LoginActivity.this,Dashboard_admin.class);
            startActivity(i);
            }
        else  if (c2.moveToFirst()){
            Intent i=new Intent(LoginActivity.this,UserPanel.class);
            startActivity(i);
            }
        else  if (c3.moveToFirst()){
            Intent i=new Intent(LoginActivity.this,UserPanel.class);
            startActivity(i);
        }
        else{
            Toast.makeText(this,"Enter Correct Username & Password", Toast.LENGTH_SHORT).show();
        }
        }
    }
    private boolean validate(String username,String password){
        if (username.isEmpty()){
            etUsername.setError("Please give a Proper Username");
            etUsername.requestFocus();
            return false;
        }

        if (password.isEmpty()){
            etPassword.setError("Please give a Proper Password");
            etPassword.requestFocus();
            return false;
        }

        return true;
    }
}