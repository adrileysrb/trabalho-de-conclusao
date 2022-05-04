package com.ifgoiano.mvcpattern.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ifgoiano.mvcpattern.R;

public class HomeActivity extends AppCompatActivity {
    private Button btnApi, btnSql, btnFirebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnApi = findViewById(R.id.button_api);
        btnSql = findViewById(R.id.button_sql);
        btnFirebase = findViewById(R.id.button_firebase);

        btnApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMVCActivity();
            }
        });

        btnSql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMainActivity();
            }
        });

        btnFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFirebaseActivity();
            }
        });
    }

    public void startMVCActivity(){
        startActivity(new Intent(this, APIActivity.class));
    }

    public void startMainActivity(){
        startActivity(new Intent(this, SQLActivity.class));
    }

    public void startFirebaseActivity(){
        startActivity(new Intent(this, FirebaseActivity.class));
    }
}