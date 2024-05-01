package com.example.lms;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApplyforleaveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApplyforleaveFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ApplyforleaveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ApplyforleaveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ApplyforleaveFragment newInstance(String param1, String param2) {
        ApplyforleaveFragment fragment = new ApplyforleaveFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    EditText etaltitle,etalperpose,etalemail,etalnumber,etaladdress,etalnodays,date,date2;
    Spinner spinnertl,spinnerdp;
    String DeID=null ,LeID=null;
    Button btnal;
    DatabaseHelper db;
    DatePickerDialog datePickerDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void fill_spinner(){

        Cursor c =db.fetch_all("tbl_department");

        ArrayList<String> cat= new ArrayList<>();

        while(c.moveToNext()){
            cat.add(c.getString(1));
        }

        ArrayAdapter adapter=new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,cat);
        spinnerdp.setAdapter(adapter);
    }

    public void fill_spinner2(){

        Cursor c2 =db.fetch_all("tbl_Leavecategory");

        ArrayList<String> cat2= new ArrayList<>();

        while(c2.moveToNext()){
            cat2.add(c2.getString(1));
        }

        ArrayAdapter adapter=new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,cat2);
        spinnertl.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_applyforleave, container, false);
        btnal = v.findViewById(R.id.btnal);
        etaltitle = v.findViewById(R.id.etaltitle);
        spinnertl = v.findViewById(R.id.spinneraltl);
        spinnerdp = v.findViewById(R.id.spinneraldp);
        etalperpose = v.findViewById(R.id.etalperpose);
        etalemail = v.findViewById(R.id.etalemail);
        etalnumber = v.findViewById(R.id.etalnumber);
        etaladdress = v.findViewById(R.id.etaladdress);
        etalnodays= v.findViewById(R.id.etalnodays);
        db = new DatabaseHelper(v.getContext());
        date = (EditText) v.findViewById(R.id.datestart);
        date2 = (EditText) v.findViewById(R.id.dateend);
        fill_spinner();
        fill_spinner2();

        spinnerdp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String category = parent.getItemAtPosition(position).toString();
                DeID =  db.getDeIDByName(category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnertl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String category2 = parent.getItemAtPosition(position).toString();
                LeID =  db.getLeIDByName(category2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(
                                    DatePicker view, int year,
                                    int monthOfYear, int dayOfMonth)
                            { date.setText(dayOfMonth +
                                    "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(
                                    DatePicker view, int year,
                                    int monthOfYear, int dayOfMonth)
                            { date2.setText(dayOfMonth +
                                    "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btnal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = etaltitle.getText().toString();
                String purpose = etalperpose.getText().toString();
                String email = etalemail.getText().toString();
                String number = etalnumber.getText().toString();
                String address = etaladdress.getText().toString();
                String nodays = etalnodays.getText().toString();
                String dates = date.getText().toString();
                String datee = date2.getText().toString();

                boolean isValid = validate(title, purpose, email,number,address,nodays,dates,datee);
                if (isValid) {

                    ContentValues cv = new ContentValues();
                    cv.put("le_title", String.valueOf(etaltitle.getText()));
                    cv.put("le_lcid", String.valueOf(LeID));
                    cv.put("le_did", String.valueOf(DeID));
                    cv.put("le_purpose", String.valueOf(etalperpose.getText()));
                    cv.put("le_email", String.valueOf(etalemail.getText()));
                    cv.put("le_number", String.valueOf(etalnumber.getText()));
                    cv.put("le_address", String.valueOf(etaladdress.getText()));
                    cv.put("le_nodays", String.valueOf(etalnodays.getText()));
                    cv.put("le_startle", String.valueOf(date.getText()));
                    cv.put("le_endle", String.valueOf(date2.getText()));

                    boolean isSaved = db.insert_data("tbl_leave", cv);

                    if (isSaved) {
                        Intent i = new Intent(getContext(), MainActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getContext(), "Can not save", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            private boolean validate( String title, String purpose, String number, String email, String address, String nodays,
                                      String dates,String datee){
                if (title.isEmpty()){
                    etaltitle.setError("Please give a proper Title");
                    etaltitle.requestFocus();
                    return false;
                }
                if (purpose.isEmpty()){
                    etalperpose.setError("Please give a proper purpose");
                    etalperpose.requestFocus();
                    return false;
                }
                if (email.isEmpty()){
                    etalemail.setError("Please give a proper email");
                    etalemail.requestFocus();
                    return false;
                }
                if (number.isEmpty()){
                    etalnumber.setError("Please give a proper number");
                    etalnumber.requestFocus();
                    return false;
                }
                if (address.isEmpty()){
                    etaladdress.setError("Please give a proper address");
                    etaladdress.requestFocus();
                    return false;
                }
                if (nodays.isEmpty()){
                    etalnodays.setError("Please give a proper No Of Days");
                    etalnodays.requestFocus();
                    return false;
                }
                if (dates.isEmpty()){
                    date.setError("Please give a proper Start Date");
                    date.requestFocus();
                    return false;
                }
                if (datee.isEmpty()){
                    date2.setError("Please give a proper End Date");
                    date2.requestFocus();
                    return false;
                }
                return true;
            }

        });

        return v;
    }
}