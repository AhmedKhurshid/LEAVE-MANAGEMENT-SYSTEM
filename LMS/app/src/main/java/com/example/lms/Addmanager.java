package com.example.lms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class Addmanager extends AppCompatActivity {

    EditText etamname,etamcnic,etamaddress,etamsalary,etamleave,etamemail,etampass;
    Spinner spinneram;
    String DeID=null;
    ImageView ivm;
    int GALLERY_REQUEST_CODE = 1122;
    Button btnam;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmanager);

        btnam = findViewById(R.id.btne);
        ivm = findViewById(R.id.ivm);
        etamname = findViewById(R.id.eteid);
        etamcnic = findViewById(R.id.etename);
        etamaddress = findViewById(R.id.etejd);
        etamsalary = findViewById(R.id.etemn);
        etamleave = findViewById(R.id.etamleave);
        etamemail= findViewById(R.id.etamemail);
        etampass = findViewById(R.id.etampass);
        etampass = findViewById(R.id.etampass);
        spinneram = findViewById(R.id.spinneram);
        db = new DatabaseHelper(Addmanager.this);
        fill_spinner();
        db.insert_initials();

        ivm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(Addmanager.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},GALLERY_REQUEST_CODE);
            }
        });

        spinneram.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String category = parent.getItemAtPosition(position).toString();
                DeID =  db.getDeIDByName(category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = etamname.getText().toString();
                String cnic = etamcnic.getText().toString();
                String address = etamaddress.getText().toString();
                String salary = etamsalary.getText().toString();
                String leave = etamleave.getText().toString();
                String email = etamemail.getText().toString();
                String pass = etampass.getText().toString();
                byte[] img= convertImageToByte(ivm);

                boolean isValid = validate(name, cnic, address,salary,leave,email,pass);
                if (isValid) {

                    ContentValues cv = new ContentValues();
                    cv.put("m_name", String.valueOf(etamname.getText()));
                    cv.put("m_img",img);
                    cv.put("m_cnic", String.valueOf(etamcnic.getText()));
                    cv.put("m_address", String.valueOf(etamaddress.getText()));
                    cv.put("m_salary", String.valueOf(etamsalary.getText()));
                    cv.put("m_leave", String.valueOf(etamleave.getText()));
                    cv.put("m_email", String.valueOf(etamemail.getText()));
                    cv.put("m_password", String.valueOf(etampass.getText()));
                    cv.put("dm_did", DeID);

                    boolean isSaved = db.insert_data("tbl_manager", cv);

                    if (isSaved) {
                        Intent i = new Intent(Addmanager.this, Dashboard_admin.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(Addmanager.this, "Can not save", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            private boolean validate( String name, String cnic, String address, String salary,String leave, String email, String pass){
                if (name.isEmpty()){
                    etamname.setError("Please give a proper name");
                    etamname.requestFocus();
                    return false;
                }
                if (cnic.isEmpty()){
                    etamcnic.setError("Please give a proper cnic Number");
                    etamcnic.requestFocus();
                    return false;
                }
                if (address.isEmpty()){
                    etamaddress.setError("Please give a proper Address");
                    etamaddress.requestFocus();
                    return false;
                }
                if (salary.isEmpty()){
                    etamsalary.setError("Please give a proper Salary");
                    etamsalary.requestFocus();
                    return false;
                }
                if (leave.isEmpty()){
                    etamleave.setError("Please give a proper leave");
                    etamleave.requestFocus();
                    return false;
                }
                if (email.isEmpty()){
                    etamemail.setError("Please give a proper email");
                    etamemail.requestFocus();
                    return false;
                }
                if (pass.isEmpty()){
                    etampass.setError("Please give a proper email");
                    etampass.requestFocus();
                    return false;
                }
                return true;
            }
        });
    }

    public void fill_spinner(){

        Cursor c =db.fetch_all("tbl_department");

        ArrayList<String> cat= new ArrayList<>();

        while(c.moveToNext()){
            cat.add(c.getString(1));
        }

        ArrayAdapter adapter=new ArrayAdapter(Addmanager.this,R.layout.support_simple_spinner_dropdown_item,cat);
        spinneram.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (GALLERY_REQUEST_CODE == requestCode) {
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i,GALLERY_REQUEST_CODE);
            }
            else  {
                Toast.makeText(Addmanager.this, "You don't have access permission.", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                ivm.setImageBitmap(bitmap);

            }catch (FileNotFoundException ex){
                ex.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private byte[] convertImageToByte(ImageView ivm) {
        Bitmap bitmap= ((BitmapDrawable)ivm.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray= stream.toByteArray();
        return byteArray;
    }
}