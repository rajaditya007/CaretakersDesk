package com.aditya1.caretakersdesk;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class recycler_expand extends AppCompatActivity {
   public Button b1, b2;
    private FirebaseAuth mAuth;
    private DatabaseReference userdatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refractor_expands);
        mAuth = FirebaseAuth.getInstance();
       b1=findViewById(R.id.button);
       b2=findViewById(R.id.button2);


b1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String update = "Working";
        String currentUserId = mAuth.getCurrentUser().getUid();
        DatabaseReference userdatabaseRef = FirebaseDatabase.getInstance().getReference();
        userdatabaseRef.child("users").child(currentUserId).child("Complaints").child("StatusNew").setValue("Working");


    }
});
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String update = "Working";
                String currentUserId = mAuth.getCurrentUser().getUid();
                DatabaseReference userdatabaseRef = FirebaseDatabase.getInstance().getReference();
                userdatabaseRef.child("users").child(currentUserId).child("Complaints").child("StatusNew").setValue("Completed");


    }
});

    }
}