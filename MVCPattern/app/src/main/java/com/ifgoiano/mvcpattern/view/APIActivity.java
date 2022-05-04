package com.ifgoiano.mvcpattern.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ifgoiano.mvcpattern.R;
import com.ifgoiano.mvcpattern.controller.CountriesAPIController;

import java.util.ArrayList;
import java.util.List;

public class APIActivity extends AppCompatActivity {

    private List<String> listValues = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView list;
    private CountriesAPIController controller;
    private Button retryButton;
    private ProgressBar progress;


    //Essa é a tela que vai mostrar os dados da API
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("TIMESYSTEM", "TIME: "+System.currentTimeMillis());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvc);
        setTitle("MVC Activity - API");

        controller = new CountriesAPIController(this);

        list = findViewById(R.id.list);
        retryButton = findViewById(R.id.retryButton);
        progress = findViewById(R.id.progress);
        adapter = new ArrayAdapter<>(this, R.layout.row_layout, R.id.listText, listValues);

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(APIActivity.this, "You clicked " + listValues.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setValues(List<String> values) {
        listValues.clear();
        listValues.addAll(values);
        retryButton.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        list.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
        Log.i("TIMESYSTEM", "TIME: "+System.currentTimeMillis());
        }

    public void onRetry(View view) {
        controller.onRefresh();
        list.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
    }

    public void onError() {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        progress.setVisibility(View.GONE);
        list.setVisibility(View.GONE);
        retryButton.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
