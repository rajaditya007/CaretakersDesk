package com.aditya1.caretakersdesk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

public class ComplainActivity extends AppCompatActivity{
    EditText roomblock,description;
    Button complain_button;
        private FirebaseAuth mAuth;
    private DatabaseReference userdatabaseRef;
    private ProgressDialog loader;
    Spinner spinner, spinner2;
  //  Context context = ComplainActivity.this;
   // SharedPreferences sharedPref = context.getSharedPreferences(
           // "My Pref File", Context.MODE_PRIVATE);
    String USER_ID;
        @SuppressLint("MissingInflatedId")
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);
            loader = new ProgressDialog(this);
            mAuth = FirebaseAuth.getInstance();
            spinner = findViewById(R.id.spinner);
            spinner2= findViewById(R.id.spinner2);
            complain_button=findViewById(R.id.complain_submit);
            roomblock=findViewById(R.id.room_block);
           description=findViewById(R.id.Description);
       //     USER_ID=sharedPref.getString("USER_ID","null");


            List<String>  Hostel= new ArrayList<>();
            Hostel.add(0, "Select Hostel");
            Hostel.add("Vindhyachal");
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Hostel);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (parent.getItemAtPosition(position).equals("Select Hostel")){
                    }else {
                        String item = parent.getItemAtPosition(position).toString();
                        Toast.makeText(parent.getContext(),"Selected: " +item, Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            List<String>  department= new ArrayList<>();
            department.add(0, "Select Department");
            department.add("Electric");
            department.add("Furniture");
            department.add("Internet");
            department.add("Others");

            ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, department);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(arrayAdapter2);

            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (parent.getItemAtPosition(position).equals("Select Department")){
                    }else {
                        String item = parent.getItemAtPosition(position).toString();
                        Toast.makeText(parent.getContext(),"Selected: " +item, Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            complain_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String hostel = (String) spinner.getSelectedItem().toString().trim();
                    final String room = roomblock.getText().toString().trim();
                    final String problemtype = (String) spinner2.getSelectedItem().toString().trim();
                    final String problemdescription = description.getText().toString().trim();
                    final String fromTimeZone = "GMT+5:30";
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    Date date = new Date();
                    format.setTimeZone(TimeZone.getTimeZone(fromTimeZone));
                    String Date = format.format(date);
                    if (TextUtils.isEmpty(hostel)) {
                        TextView spinnertextview = (TextView) spinner.getSelectedView();

                        spinnertextview.setError("Hostel is Required");
                        return;
                    }
                    if (TextUtils.isEmpty(room)) {
                        roomblock.setError("Room and Block is Required");
                        return;
                    }
                    if (TextUtils.isEmpty(problemtype)) {
                        TextView spinnertextview2 = (TextView) spinner2.getSelectedView();
                        spinnertextview2.setError("Problem Department is Required");
                        return;
                    }
                    if (TextUtils.isEmpty(problemdescription)) {
                        description.setError("Description is Required");
                        return;
                    } else {
                        loader.setMessage("Complain Registering...");
                        loader.setCanceledOnTouchOutside(false);
                        String currentUserId = mAuth.getCurrentUser().getUid();loader.show();



                        DatabaseReference userdatabaseRef = userdatabaseRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId).child("Complaints");
                        DatabaseReference _compalaintRef = userdatabaseRef.child(Date);


                        HashMap<String, Object> complaininfo = new HashMap<>();
                        complaininfo.put("Hostel", hostel);
                        complaininfo.put("Department", problemtype);
                        complaininfo.put("Room & Block", room);
                        complaininfo.put("Description_problem", problemdescription);
                        complaininfo.put("Time",Date);
                        complaininfo.put("Status","Pending");

                        _compalaintRef.updateChildren(complaininfo).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {

                                if (task.isSuccessful()) {
                                    Toast.makeText(ComplainActivity.this, "Details Sent Successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ComplainActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    finish();
                                    loader.dismiss();
                                }
                            }
                        });

                        Intent intent= new Intent(ComplainActivity.this,dashboard_user.class);
                        startActivity(intent);
                        finish();
                        loader.dismiss();

                    }

                }
                        });

                    }
                }
