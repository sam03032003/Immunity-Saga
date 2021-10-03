package com.example.immunity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    Button symptoms_btn, cautions_btn, health_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        symptoms_btn = findViewById(R.id.symptoms_btn);
        cautions_btn = findViewById(R.id.cautions_btn);
        health_btn = findViewById(R.id.health_btn);

        symptoms_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Testing Symptoms", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Home.this,Symptoms.class));
            }
        });

        cautions_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Cautions tips", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Home.this, Cautions.class));
            }
        });

        health_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Mental Health", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Home.this, Health.class));
            }
        });
    }
}