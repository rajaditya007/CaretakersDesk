package com.aditya1.caretakersdesk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StudentRegistrationActivity extends AppCompatActivity {
    private TextView regname, regemail, regpassword, regphoneno;
    private Button registerbutton;
    private FirebaseAuth mAuth;
    private DatabaseReference userdatabaseRef;
    private ProgressDialog loader;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        regname = findViewById(R.id.regname);
        regpassword = findViewById(R.id.regpassword);
        regemail = findViewById(R.id.regemail);
        regphoneno = findViewById(R.id.regphoneno);
        registerbutton = findViewById(R.id.Registerbutton);
        loader = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = regemail.getText().toString().trim();
                final String password = regpassword.getText().toString().trim();
                final String fname = regname.getText().toString().trim();
                final String phoneno = regphoneno.getText().toString().trim();

                if (TextUtils.isEmpty(fname)) {
                    regname.setError("Name is Required");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    regemail.setError("Email is Required");
                    return;
                }
                int ind= email.indexOf('@');
                String rem=email.substring(ind);


                if (rem.equals("@gmail.com")){

                }
                else
                {
                    regemail.setError("Invalid email");
                    return;
                }



                if (TextUtils.isEmpty(password)) {
                    regpassword.setError("Password is Required");
                return;
                }
                if(TextUtils.isEmpty(phoneno)){
                    regphoneno.setError("Phone Number is Required");
                return;
                }
                if(TextUtils.getTrimmedLength(phoneno)!=10){
                    regphoneno.setError("Invalid phone number");
                return;
                }
                else{
                    loader.setMessage("Registration in Progress...");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                String error=task.getException().toString();
                                Toast.makeText(StudentRegistrationActivity.this, "Error Occured:" +error,Toast.LENGTH_SHORT).show();
                            }
                            else{
                                String currentUserId = mAuth.getCurrentUser().getUid();
                                userdatabaseRef= FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId).child("Credentials");

                                //Save user id to shared preference
                                Context context = StudentRegistrationActivity.this;
                                SharedPreferences sharedPref = context.getSharedPreferences(
                                        "My Pref File", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("USER_ID", currentUserId);
                                editor.apply();


                                HashMap<String,Object> userinfo = new HashMap<>();
                                userinfo.put("email",email);
                                userinfo.put("name",fname);
                                userinfo.put("password",password);
                                userinfo.put("phone-Number",phoneno);
                                userinfo.put("Type","Student");

                                userdatabaseRef.updateChildren(userinfo).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {

                                        if(task.isSuccessful()){
                                            sendemail();

                                            Toast.makeText(StudentRegistrationActivity.this,"Details Sent Successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent= new Intent(StudentRegistrationActivity.this,LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                            loader.dismiss();
                                        }
                                        else{
                                            Toast.makeText(StudentRegistrationActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                          finish();
                                          loader.dismiss();
                                        }
                                    }

                                    private void sendemail() {
                                        user = FirebaseAuth.getInstance().getCurrentUser();
                                        if(user != null){
                                            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(StudentRegistrationActivity.this,"check email for verification",Toast.LENGTH_LONG).show();

                                                    }
                                                }
                                            });
                                        }
                                    }
                                });


                            }
                        }
                    });

                }

            }
        });




    }
    }
