package com.aditya1.caretakersdesk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.number.IntegerWidth;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class admindashboardActivity extends AppCompatActivity {

    RecyclerView recyclerview;
    DatabaseReference database;
    adapter myadapter;
    ArrayList<complaint> list;
    FirebaseAuth mAuth;
    ImageView imageview, imageview2, imageview3;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user);
        mAuth = FirebaseAuth.getInstance();
        String currentUserId = mAuth.getCurrentUser().getUid();
        imageview =findViewById(R.id.home);
        imageview2 =findViewById(R.id.new_complain);
        imageview3 =findViewById(R.id.account);
        recyclerview=findViewById(R.id.recycler);
        database= FirebaseDatabase.getInstance().getReference("users").child(currentUserId).child("Complaints");
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));


        list= new ArrayList<>();
        myadapter=new adapter(this,list);
        recyclerview.setAdapter(myadapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    complaint complaint = dataSnapshot.getValue(complaint.class);
                    list.add(complaint);
                }

                myadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







/***
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(admindashboardActivity.this,"Home",Toast.LENGTH_SHORT).show();
            }
        });

        imageview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(admindashboardActivity.this,ComplainActivity.class);
                startActivity(intent);
            }

        });

        imageview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(admindashboardActivity.this,accountdetails.class);
                startActivity(intent);
            }

        });***/


    }
}