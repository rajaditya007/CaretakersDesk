package com.aditya1.caretakersdesk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class adminlogin extends AppCompatActivity {


    private Button adminloginbtn;
     private EditText adminid, adminpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);

        adminloginbtn=findViewById(R.id.admin_loginbtn);
        adminid=findViewById(R.id.admin_id);
        adminpassword=findViewById(R.id.admin_password);
        String admin="ADMIN123";
        String password="ADMIN123";


        adminloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(adminlogin.this,admindashboardActivity.class);

                startActivity(intent);

            }
        });
    }
}