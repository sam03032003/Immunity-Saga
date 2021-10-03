package com.example.immunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    EditText signin_editTextEmail, signin_editTextPassword;
    TextView signUp;
    Button login_btn;
    RadioGroup radioGroup;
    RadioButton radioButton;


    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        signin_editTextEmail = findViewById(R.id.signin_editTextEmail);
        signin_editTextPassword = findViewById(R.id.signin_editTextPassword);

        signUp = findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, MainActivity.class);
                startActivity(intent);
            }
        });

        auth = FirebaseAuth.getInstance();

        login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmail = signin_editTextEmail.getText().toString();
                String userPassword = signin_editTextPassword.getText().toString();



                if (TextUtils.isEmpty(userEmail)){
                    Toast.makeText(SignIn.this, "Enter your Email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(userPassword)){
                    Toast.makeText(SignIn.this, "Enter your Password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int radioID = radioGroup.getCheckedRadioButtonId();
                radioGroup = findViewById(R.id.radioGroup);
                radioButton = findViewById(radioID);

//                auth.signInAnonymously(userEmail,userPassword, radioButton)
//                        .addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                
//                            }
//                        });

                auth.signInWithEmailAndPassword(userEmail,userPassword)
                        .addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                if(task.isSuccessful()){
                                    Toast.makeText(SignIn.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignIn.this, Home.class));
                                }else{
                                    Toast.makeText(SignIn.this, "Login Failed "+ task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

    }
}