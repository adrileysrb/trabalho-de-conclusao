package com.ifgoiano.mvvmpattern.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ifgoiano.mvvmpattern.R;
import com.ifgoiano.mvvmpattern.controller.CountriesLocalRepositoryViewModel;
import com.ifgoiano.mvvmpattern.controller.CountriesRemoteRepositoryViewModel;
import com.ifgoiano.mvvmpattern.model.Country;

import java.util.ArrayList;
import java.util.List;

public class LocalRepositoryActivity extends AppCompatActivity {

    CountriesLocalRepositoryViewModel viewModel;
    TextView datalist;
    TextView datalist_count;
    private List<String> listValues = new ArrayList<>();
    private List<String> listValuesIDs = new ArrayList<>();
    private ListView list;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_repository);

        setTitle("MVVM Activity - LOCAL");

        list = findViewById(R.id.list_sql);
        list.setOnItemClickListener((parent, view, position, id) -> Toast.makeText(LocalRepositoryActivity.this, "ID: "+listValuesIDs.get(position)+ " - "+listValues.get(position), Toast.LENGTH_SHORT).show());


        viewModel = new ViewModelProvider(this).get(CountriesLocalRepositoryViewModel.class);
        viewModel.setContext(LocalRepositoryActivity.this);
        Button delete=findViewById(R.id.delete_data);
        Button insert=findViewById(R.id.insert_data);
        Button update=findViewById(R.id.update_data);
        Button read=findViewById(R.id.refresh_data);
        datalist_count=findViewById(R.id.data_list_count);

        read.setOnClickListener(v -> refreshData());

        insert.setOnClickListener(v -> ShowInputDialog());

        update.setOnClickListener(v -> showUpdateIdDialog());

        delete.setOnClickListener(v -> showDeleteDialog());

    }

    private void refreshData() {
        datalist_count.setText("ALL DATA COUNT : " + viewModel.getToltalRegistersCount());

        viewModel.getCountries(list, datalist_count, LocalRepositoryActivity.this).observe(this, countries -> {
            listValues = new ArrayList<>();
            for(String studentModel : countries){
                listValues.add(studentModel);
                //listValuesIDs.add(studentModel.getId());
            }
            adapter = new ArrayAdapter<>(this, R.layout.row_layout, R.id.listText, listValues);
            list.setAdapter(adapter);
        });

    }

    private void showDeleteDialog() {
        AlertDialog.Builder al=new AlertDialog.Builder(LocalRepositoryActivity.this);
        View view=getLayoutInflater().inflate(R.layout.delete_dialog,null);
        al.setView(view);
        final EditText id_input=view.findViewById(R.id.id_input);
        Button delete_btn=view.findViewById(R.id.delete_btn);
        final AlertDialog alertDialog=al.show();

        delete_btn.setOnClickListener(v -> {
            viewModel.deleteCountry(id_input.getText().toString());
            alertDialog.dismiss();
            refreshData();

        });


    }

    private void showUpdateIdDialog() {
        AlertDialog.Builder al=new AlertDialog.Builder(LocalRepositoryActivity.this);
        View view=getLayoutInflater().inflate(R.layout.update_id_dialog,null);
        al.setView(view);
        final EditText id_input=view.findViewById(R.id.id_input);
        Button fetch_btn=view.findViewById(R.id.update_id_btn);
        final AlertDialog alertDialog=al.show();
        fetch_btn.setOnClickListener(v -> {
            showDataDialog(id_input.getText().toString());
            alertDialog.dismiss();
            refreshData();
        });

    }

    private void showDataDialog(final String id) {
        Country studentModel= viewModel.getCountry(Integer.parseInt(id));
        AlertDialog.Builder al=new AlertDialog.Builder(LocalRepositoryActivity.this);
        View view=getLayoutInflater().inflate(R.layout.update_dialog,null);
        final EditText name=view.findViewById(R.id.name);
        Button update_btn=view.findViewById(R.id.update_btn);
        al.setView(view);

        name.setText(studentModel.getCountryName());

        final AlertDialog alertDialog=al.show();
        update_btn.setOnClickListener(v -> {
            Country studentModel1 =new Country();
            studentModel1.setCountryName(name.getText().toString());
            studentModel1.setId(id);
            viewModel.updateCountry(studentModel1);
            alertDialog.dismiss();
            refreshData();
        });
    }

    private void ShowInputDialog() {
        AlertDialog.Builder al=new AlertDialog.Builder(LocalRepositoryActivity.this);
        View view=getLayoutInflater().inflate(R.layout.insert_dialog,null);
        final EditText name=view.findViewById(R.id.name);
        Button insertBtn=view.findViewById(R.id.insert_btn);
        al.setView(view);

        final AlertDialog alertDialog=al.show();

        insertBtn.setOnClickListener(v -> {
            Country studentModel=new Country();
            studentModel.setCountryName(name.getText().toString());
            viewModel.addCountry(studentModel);
            alertDialog.dismiss();
            refreshData();
        });
    }
}