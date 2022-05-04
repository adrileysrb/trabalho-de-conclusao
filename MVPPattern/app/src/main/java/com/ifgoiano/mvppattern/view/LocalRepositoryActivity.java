package com.ifgoiano.mvppattern.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ifgoiano.mvppattern.R;
import com.ifgoiano.mvppattern.model.Country;
import com.ifgoiano.mvppattern.presenter.CountriesLocalRepositoryPresenter;

import java.util.ArrayList;
import java.util.List;

public class LocalRepositoryActivity extends AppCompatActivity implements CountriesLocalRepositoryPresenter.View {

    CountriesLocalRepositoryPresenter countriesLocalRepositoryPresenter;
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

        setTitle("MVP Activity - LOCAL");

        list = findViewById(R.id.list_sql);
        list.setOnItemClickListener((parent, view, position, id) -> Toast.makeText(LocalRepositoryActivity.this, "ID: "+listValuesIDs.get(position)+ " - "+listValues.get(position), Toast.LENGTH_SHORT).show());


        countriesLocalRepositoryPresenter =new CountriesLocalRepositoryPresenter(LocalRepositoryActivity.this, this);
        Button delete=findViewById(R.id.delete_data);
        Button insert=findViewById(R.id.insert_data);
        Button update=findViewById(R.id.update_data);
        Button read=findViewById(R.id.refresh_data);
        datalist_count=findViewById(R.id.data_list_count);

        read.setOnClickListener(v -> countriesLocalRepositoryPresenter.getAllCountries());

        insert.setOnClickListener(v -> ShowInputDialog());

        update.setOnClickListener(v -> showUpdateIdDialog());

        delete.setOnClickListener(v -> showDeleteDialog());
    }

    @Override
    public void getCountry(Country country) {
        AlertDialog.Builder al=new AlertDialog.Builder(LocalRepositoryActivity.this);
        View view=getLayoutInflater().inflate(R.layout.update_dialog,null);
        final EditText name=view.findViewById(R.id.name);
        Button update_btn=view.findViewById(R.id.update_btn);
        al.setView(view);

        name.setText(country.getCountryName());

        final AlertDialog alertDialog=al.show();
        update_btn.setOnClickListener(v -> {
            Country studentModel1 =new Country();
            studentModel1.setCountryName(name.getText().toString());
            studentModel1.setId(country.getId());
            countriesLocalRepositoryPresenter.updateCountry(studentModel1);
            alertDialog.dismiss();
            countriesLocalRepositoryPresenter.getAllCountries();
        });
    }

    @Override
    public void getAllCountries(List<Country> countries) {
        countriesLocalRepositoryPresenter.getToltalRegistersCount();
        List<Country> studentModelList= countries;
        listValues = new ArrayList<>();
        for(Country studentModel:studentModelList){
            listValues.add(studentModel.getCountryName());
            listValuesIDs.add(studentModel.getId());
        }
        adapter = new ArrayAdapter<>(this, R.layout.row_layout, R.id.listText, listValues);
        list.setAdapter(adapter);
    }


    @Override
    public void getTotalRegistersCount(int size) {
        datalist_count.setText("ALL DATA COUNT : " + size);
    }

    private void showDeleteDialog() {
        AlertDialog.Builder al=new AlertDialog.Builder(LocalRepositoryActivity.this);
        View view=getLayoutInflater().inflate(R.layout.delete_dialog,null);
        al.setView(view);
        final EditText id_input=view.findViewById(R.id.id_input);
        Button delete_btn=view.findViewById(R.id.delete_btn);
        final AlertDialog alertDialog=al.show();

        delete_btn.setOnClickListener(v -> {
            countriesLocalRepositoryPresenter.deleteCountry(id_input.getText().toString());
            alertDialog.dismiss();
            countriesLocalRepositoryPresenter.getAllCountries();

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
            countriesLocalRepositoryPresenter.getAllCountries();
        });

    }

    private void showDataDialog(final String id) {
        countriesLocalRepositoryPresenter.getCountry(Integer.parseInt(id));
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
            countriesLocalRepositoryPresenter.addCountry(studentModel);
            alertDialog.dismiss();
            countriesLocalRepositoryPresenter.getAllCountries();
        });
    }
}