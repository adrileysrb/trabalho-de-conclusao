package com.ifgoiano.mvvmpattern.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ifgoiano.mvvmpattern.R;

public class HomeActivity extends AppCompatActivity {
    private Button btnApi, btnSql, btnFirebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnApi = findViewById(R.id.button_api);
        btnSql = findViewById(R.id.button_sql);
        btnFirebase = findViewById(R.id.button_firebase);

        btnApi.setOnClickListener(view -> startMVCActivity());

        btnSql.setOnClickListener(view -> startMainActivity());

        btnFirebase.setOnClickListener(view -> startFirebaseActivity());
    }

    public void startMVCActivity(){
        startActivity(new Intent(this, APIActivity.class));
    }

    public void startMainActivity(){
        startActivity(new Intent(this, LocalRepositoryActivity.class));
    }

    public void startFirebaseActivity(){
        startActivity(new Intent(this, RemoteRepositoryActivity.class));
    }

}