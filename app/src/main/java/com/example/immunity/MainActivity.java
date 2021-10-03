package com.example.immunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.TextUtilsCompat;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText signup_editTextPersonName, signup_editTextEmail, signup_editTextPassword, signup_age;
    TextView signIn, signup_identity_text;
    Button signup_btn;
    RadioGroup radioGroup;
    RadioButton doctor, not_a_doctor, radioButton;


    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        auth = FirebaseAuth.getInstance();


        signup_editTextPersonName = findViewById(R.id.signup_editTextPersonName);
        signup_editTextEmail = findViewById(R.id.signup_editTextEmail);
        signup_editTextPassword = findViewById(R.id.signup_editTextPassword);
        signup_identity_text = findViewById(R.id.signup_identity_text);

        signIn = findViewById(R.id.signIn);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignIn.class);
                startActivity(intent);
            }
        });



        signup_age = findViewById(R.id.signup_age);
        radioGroup = findViewById(R.id.radioGroup);
        doctor = findViewById(R.id.doctor);
        not_a_doctor = findViewById(R.id.not_a_doctor);




        signup_btn = findViewById(R.id.signup_btn);
        signup_btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                String userName = signup_editTextPersonName.getText().toString();
                String userEmail = signup_editTextEmail.getText().toString();
                String userPassword = signup_editTextPassword.getText().toString();

                if (TextUtils.isEmpty(userName)){
                    Toast.makeText(MainActivity.this, "Enter your Name!", Toast.LENGTH_SHORT).show();
                    return;
                }if (TextUtils.isEmpty(userEmail)){
                    Toast.makeText(MainActivity.this, "Enter your Email!", Toast.LENGTH_SHORT).show();
                    return;
                }if (TextUtils.isEmpty(userPassword)){
                    Toast.makeText(MainActivity.this, "Enter your Password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(userPassword.length() < 4){
                    Toast.makeText(MainActivity.this, "Password is too short, it should be within 4 to 8 characters", Toast.LENGTH_LONG).show();
                }if(userPassword.length() > 8){
                    Toast.makeText(MainActivity.this, "Password is too long, it should be within 4 to 8 characters", Toast.LENGTH_LONG).show();
                }



                auth.createUserWithEmailAndPassword(userEmail,userPassword)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull  Task<AuthResult> task) {

                                if (task.isSuccessful()){
                                    Toast.makeText(MainActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
//
                                    int radioID = radioGroup.getCheckedRadioButtonId();
                                    radioButton = findViewById(radioID);
                                    System.out.println("Radio-------------------->"+radioButton.toString());

                                    if (radioButton == not_a_doctor){
                                        startActivity(new Intent(MainActivity.this, Home.class));
                                    }else{
                                        startActivity(new Intent(MainActivity.this, Doctor_Activity.class));
                                    }


                                }
                                else {
                                    Toast.makeText(MainActivity.this, "Registration Failed "+ task.getException(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });





                // Cloud Firestore
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();

                // Create a new user with a  name and email
                Map <String, String> user = new HashMap<>();
                user.put("Name", userName);
                user.put("Email", userEmail);
                user.put("Age", signup_age.getText().toString());
                int radioID = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioID);

                System.out.println("Radio-------------------->"+radioButton.toString());
                if (radioButton == not_a_doctor){
                    user.put("Profession", not_a_doctor.getText().toString());
                }else{
                    user.put("Profession", doctor.getText().toString());
                }


                // Add a new document with a generated ID
                firestore.collection("users")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(), "Successfully Added", Toast.LENGTH_SHORT).show();
                            }
                        })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Error adding Data", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });





    }
}