package com.aditya1.caretakersdesk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    private TextView email, password;
  /**  private TextView adminloginpage;**/
    private Button loginbut;
    private TextView loginPageQuestion;
    private ProgressDialog loader;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activitylogin_main);

       /** adminloginpage=findViewById(R.id.admin_loginQ);**/
        mAuth=FirebaseAuth.getInstance();
        email=findViewById(R.id.login_id);
        password=findViewById(R.id.password);
        loginbut=findViewById(R.id.loginbutton);
        loader= new ProgressDialog(this);
        user = FirebaseAuth.getInstance().getCurrentUser();
        loginPageQuestion= findViewById(R.id.loginPageQuestion);
        loginPageQuestion.setOnClickListener(new View.OnClickListener()
                                    


        {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

/**
        adminloginpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LoginActivity.this, adminlogin.class);
                startActivity(intent);
            }
        });

**/

        loginbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lemail = email.getText().toString().trim();
                String lpassword = password.getText().toString().trim();

                if (TextUtils.isEmpty(lemail)) {
                    email.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(lpassword)) {
                    password.setError("Password is Required");
                    return;
                } else {

                    loader.setMessage("Login in process...");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mAuth.signInWithEmailAndPassword(lemail, lpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                          if (task.isSuccessful()) {

                              if(user.isEmailVerified()){

                                  Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                  Intent intent = new Intent(LoginActivity.this, dashboard_user.class);
                                  startActivity(intent);

                                  loader.dismiss();
                                  finish();
                              }
                              if(!user.isEmailVerified()){
                                  Toast.makeText(LoginActivity.this, "Please Verify Your Email", Toast.LENGTH_SHORT).show();
                                  Intent intent=new Intent(LoginActivity.this,LoginActivity.class);
                                  loader.dismiss();
                              }


                          }

                          else
                          {
                              String error = task.getException().toString();
                              Log.d("login",error);
                              Toast.makeText(LoginActivity.this,error,Toast.LENGTH_LONG).show();

                              loader.dismiss();


                          }

                            }
                    });
                }

            }
        });






        }

        }


/**
 if(task.isSuccessful()){


 if(!user.isEmailVerified()){
 String error = task.getException().toString();
 Log.d("login",error);
 Toast.makeText(LoginActivity.this,error,Toast.LENGTH_LONG).show();

 loader.dismiss();
 finish();
 }

 }else{

 Intent intent = new Intent(LoginActivity.this,dashboard_user.class);
 startActivity(intent);

 loader.dismiss();
 }
 **/



