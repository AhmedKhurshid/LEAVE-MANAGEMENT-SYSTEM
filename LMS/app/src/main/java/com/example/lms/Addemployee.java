package com.example.lms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class Addemployee extends AppCompatActivity {


    EditText etaename,etaecnic,etaejdate,etaeaddress,etaesalary,etaeleave,etaeemail,etaepass;
    Spinner spinnerae , spinnerae2;
    String DeID=null,MeID=null;
    ImageView ive;
    int GALLERY_REQUEST_CODE = 1122;
    Button btnae;
    DatabaseHelper db;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addemployee);
        btnae = findViewById(R.id.btnae);
        ive = findViewById(R.id.ive);
        etaename = findViewById(R.id.etaename);
        etaecnic = findViewById(R.id.etaename);
        etaejdate = findViewById(R.id.etaejdate);
        etaeaddress = findViewById(R.id.etaeaddress);
        etaesalary = findViewById(R.id.etaesalary);
        etaeleave = findViewById(R.id.etaeleave);
        etaeemail= findViewById(R.id.etaeemail);
        etaepass = findViewById(R.id.etaepass);
        etaepass = findViewById(R.id.etaepass);
        spinnerae = findViewById(R.id.spinnerae);
        spinnerae2 = findViewById(R.id.spinnerae2);
        db = new DatabaseHelper(Addemployee.this);
        fill_spinner();
        fill_spinner2();
        db.insert_initials();

        ive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(Addemployee.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},GALLERY_REQUEST_CODE);
            }
        });

        spinnerae.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String category = parent.getItemAtPosition(position).toString();
                DeID =  db.getDeIDByName(category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerae2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String category = parent.getItemAtPosition(position).toString();
                MeID =  db.getMeIDByName(category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etaejdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(Addemployee.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(
                                    DatePicker view, int year,
                                    int monthOfYear, int dayOfMonth)
                            { etaejdate.setText(dayOfMonth +
                                    "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });



        btnae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = etaename.getText().toString();
                String cnic = etaecnic.getText().toString();
                String jdate = etaejdate.getText().toString();
                String address = etaeaddress.getText().toString();
                String salary = etaesalary.getText().toString();
                String leave = etaeleave.getText().toString();
                String email = etaeemail.getText().toString();
                String pass = etaepass.getText().toString();
                byte[] img= convertImageToByte(ive);

                boolean isValid = validate(name, cnic, jdate, address,salary,leave,email,pass);
                if (isValid) {

                    ContentValues cv = new ContentValues();
                    cv.put("e_name", String.valueOf(etaename.getText()));
                    cv.put("e_img",img);
                    cv.put("e_cnic", String.valueOf(etaecnic.getText()));
                    cv.put("e_jdate", String.valueOf(etaejdate.getText()));
                    cv.put("e_address", String.valueOf(etaeaddress.getText()));
                    cv.put("e_salary", String.valueOf(etaesalary.getText()));
                    cv.put("e_leave", String.valueOf(etaeleave.getText()));
                    cv.put("e_email", String.valueOf(etaeemail.getText()));
                    cv.put("e_password", String.valueOf(etaepass.getText()));
                    cv.put("de_did", DeID);
                    cv.put("me_mid", MeID);

                    boolean isSaved = db.insert_data("tbl_employee", cv);

                    if (isSaved) {
                        Intent i = new Intent(Addemployee.this, Dashboard_admin.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(Addemployee.this, "Can not save", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            private boolean validate(String name, String cnic,String jdate, String address, String salary, String leave, String email, String pass){
                if (name.isEmpty()){
                    etaename.setError("Please give a proper name");
                    etaename.requestFocus();
                    return false;
                }
                if (cnic.isEmpty()){
                    etaecnic.setError("Please give a proper cnic Number");
                    etaecnic.requestFocus();
                    return false;
                }
                if (jdate.isEmpty()){
                    etaejdate.setError("Please give a proper join date");
                    etaejdate.requestFocus();
                    return false;
                }
                if (address.isEmpty()){
                    etaeaddress.setError("Please give a proper Address");
                    etaeaddress.requestFocus();
                    return false;
                }
                if (salary.isEmpty()){
                    etaesalary.setError("Please give a proper Salary");
                    etaesalary.requestFocus();
                    return false;
                }
                if (leave.isEmpty()){
                    etaeleave.setError("Please give a proper leave");
                    etaeleave.requestFocus();
                    return false;
                }
                if (email.isEmpty()){
                    etaeemail.setError("Please give a proper email");
                    etaeemail.requestFocus();
                    return false;
                }
                if (pass.isEmpty()){
                    etaepass.setError("Please give a proper email");
                    etaepass.requestFocus();
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

        ArrayAdapter adapter=new ArrayAdapter(Addemployee.this,R.layout.support_simple_spinner_dropdown_item,cat);
        spinnerae.setAdapter(adapter);
    }

    public void fill_spinner2(){

        Cursor c2 =db.fetch_all("tbl_manager");

        ArrayList<String> cat= new ArrayList<>();

        while(c2.moveToNext()){
            cat.add(c2.getString(1));
        }

        ArrayAdapter adapter=new ArrayAdapter(Addemployee.this,R.layout.support_simple_spinner_dropdown_item,cat);
        spinnerae2.setAdapter(adapter);
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
                Toast.makeText(Addemployee.this, "You don't have access permission.", Toast.LENGTH_SHORT).show();
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
                ive.setImageBitmap(bitmap);

            }catch (FileNotFoundException ex){
                ex.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private byte[] convertImageToByte(ImageView ive2) {
        Bitmap bitmap= ((BitmapDrawable)ive2.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray= stream.toByteArray();
        return byteArray;
    }
}