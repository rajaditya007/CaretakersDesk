package com.aditya1.caretakersdesk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class accountdetails extends AppCompatActivity {
  Button b1;
    TextView t1,t2,t3;
    FirebaseAuth mAuth;
    DatabaseReference database, database1,database2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountdetails);

        t1= findViewById(R.id.textViewname);
        t2= findViewById(R.id.textViewemail);
        t3= findViewById(R.id.textViewnumber);
        mAuth = FirebaseAuth.getInstance();
        b1=findViewById(R.id.button3);
        String currentUserId = mAuth.getCurrentUser().getUid();
        database1= FirebaseDatabase.getInstance().getReference("users").child(currentUserId).child("Credentials").child("name");
        database= FirebaseDatabase.getInstance().getReference("users").child(currentUserId).child("Credentials").child("email");
        database2= FirebaseDatabase.getInstance().getReference("users").child(currentUserId).child("Credentials").child("phone-Number");

        database1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String data=snapshot.getValue().toString();
                    t1.setText(data);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String data=snapshot.getValue().toString();
                    t2.setText(data);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String data=snapshot.getValue().toString();
                    t3.setText(data);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
b1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(accountdetails.this,"Successfully Logged out", Toast.LENGTH_SHORT).show();
        Intent i= new Intent(accountdetails.this,LoginActivity.class);
        startActivity(i);

    }
});


    }
}