package com.ifgoiano.mvvmpattern.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ifgoiano.mvvmpattern.R;
import com.ifgoiano.mvvmpattern.controller.CountriesAPIViewModel;

import java.util.ArrayList;
import java.util.List;

public class APIActivity extends AppCompatActivity {

    private List<String> listValues = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView list;
    private CountriesAPIViewModel viewModel;
    private Button retryButton;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("TIMESYSTEM", "TIME: "+System.currentTimeMillis());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apiactivity);

        setTitle("MVVM Activity");

        viewModel = new ViewModelProvider(this).get(CountriesAPIViewModel.class);


        list = findViewById(R.id.list);
        retryButton = findViewById(R.id.retryButton);
        progress = findViewById(R.id.progress);
        adapter = new ArrayAdapter<>(this, R.layout.row_layout, R.id.listText, listValues);

        list.setAdapter(adapter);
        list.setOnItemClickListener((parent, view, position, id) -> Toast.makeText(APIActivity.this, "You clicked " + listValues.get(position), Toast.LENGTH_SHORT).show());

        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.getCountries().observe(this, countries -> {
            if(countries != null) {
                listValues.clear();
                listValues.addAll(countries);
                list.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
                Log.i("TIMESYSTEM", "TIME: "+System.currentTimeMillis());
            } else {
                list.setVisibility(View.GONE);
            }
        });

        viewModel.getCountryError().observe(this, error -> {
            progress.setVisibility(View.GONE);
            if(error) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                retryButton.setVisibility(View.VISIBLE);
            } else {
                retryButton.setVisibility(View.GONE);
            }
        });
    }

    public void onRetry(View view) {
        viewModel.onRefresh();
        list.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
    }

}