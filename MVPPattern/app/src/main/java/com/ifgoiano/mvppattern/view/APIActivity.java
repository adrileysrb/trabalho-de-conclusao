package com.ifgoiano.mvppattern.view;

import androidx.appcompat.app.AppCompatActivity;

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

import com.ifgoiano.mvppattern.R;
import com.ifgoiano.mvppattern.presenter.CountriesAPIPresenter;
import com.ifgoiano.mvppattern.presenter.api.CountriesApi;

import java.util.ArrayList;
import java.util.List;

public class APIActivity extends AppCompatActivity implements CountriesAPIPresenter.View{

    private List<String> listValues = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView list;
    private CountriesAPIPresenter presenter;
    private Button retryButton;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("TIMESYSTEM", "TIME: "+System.currentTimeMillis());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apiactivity);
        setTitle("MVP Activity - API");


        presenter = new CountriesAPIPresenter(this);

        list = findViewById(R.id.list);
        retryButton = findViewById(R.id.retryButton);
        progress = findViewById(R.id.progress);
        adapter = new ArrayAdapter<>(this, R.layout.row_layout, R.id.listText, listValues);

        list.setAdapter(adapter);
        list.setOnItemClickListener((parent, view, position, id) -> Toast.makeText(APIActivity.this, "You clicked " + listValues.get(position), Toast.LENGTH_SHORT).show());
    }

    @Override
    public void setValues(List<String> countries) {
        listValues.clear();
        listValues.addAll(countries);
        retryButton.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        list.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
        Log.i("TIMESYSTEM", "TIME: "+System.currentTimeMillis());
    }

    @Override
    public void onError() {
        Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        progress.setVisibility(View.GONE);
        list.setVisibility(View.GONE);
        retryButton.setVisibility(View.VISIBLE);
    }

    public void onRetry(View view) {
        presenter.onRefresh();
        list.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
    }

}
