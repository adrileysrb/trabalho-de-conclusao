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
import com.ifgoiano.mvppattern.presenter.CountriesLocalRepositoryPresenter;
import com.ifgoiano.mvppattern.presenter.CountriesRemoteRepositoryPresenter;

import java.util.ArrayList;
import java.util.List;

public class RemoteRepositoryActivity extends AppCompatActivity implements CountriesRemoteRepositoryPresenter.View {
    CountriesRemoteRepositoryPresenter countriesRemoteRepositoryPresenter;
    TextView datalist_count;
    private List<String> listValues = new ArrayList<>();
    private List<String> listValuesIDs = new ArrayList<>();
    private ListView list;
    private String studentModelString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);
        setTitle("MVP Activity - REMOTE");

        list = findViewById(R.id.list_sql2);
        list.setOnItemClickListener((parent, view, position, id) -> Toast.makeText(RemoteRepositoryActivity.this, "ID: " + listValuesIDs.get(position) + " - " + listValues.get(position), Toast.LENGTH_SHORT).show());


        countriesRemoteRepositoryPresenter = new CountriesRemoteRepositoryPresenter(RemoteRepositoryActivity.this, this);
        Button delete = findViewById(R.id.delete_data2);
        Button insert = findViewById(R.id.insert_data2);
        Button update = findViewById(R.id.update_data2);
        Button read = findViewById(R.id.refresh_data2);
        datalist_count = findViewById(R.id.data_list_count2);

        read.setOnClickListener(v -> refreshData());

        insert.setOnClickListener(v -> ShowInputDialog());

        update.setOnClickListener(v -> showUpdateIdDialog());

        delete.setOnClickListener(v -> showDeleteDialog());
    }

    public void getCountry(String name){
            this.studentModelString = name;
    }


        private void showDeleteDialog() {
            AlertDialog.Builder al=new AlertDialog.Builder(RemoteRepositoryActivity.this);
            View view=getLayoutInflater().inflate(R.layout.delete_dialog,null);
            al.setView(view);
            final EditText id_input=view.findViewById(R.id.id_input);
            Button delete_btn=view.findViewById(R.id.delete_btn);
            final AlertDialog alertDialog=al.show();

            delete_btn.setOnClickListener(v -> {
                countriesRemoteRepositoryPresenter.deleteCountry(Integer.parseInt(id_input.getText().toString()));
                alertDialog.dismiss();
            });

        }

        private void showUpdateIdDialog() {
            AlertDialog.Builder al=new AlertDialog.Builder(RemoteRepositoryActivity.this);
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
            //countriesRemoteRepositoryPresenter.readCountry(id);
            AlertDialog.Builder al=new AlertDialog.Builder(RemoteRepositoryActivity.this);
            View view=getLayoutInflater().inflate(R.layout.update_dialog,null);
            final EditText name=view.findViewById(R.id.name);
            Button update_btn=view.findViewById(R.id.update_btn);
            al.setView(view);
            name.setText(studentModelString);

            final AlertDialog alertDialog=al.show();
            View view2=getLayoutInflater().inflate(R.layout.update_id_dialog,null);
            final EditText id_input=view2.findViewById(R.id.id_input);
            update_btn.setOnClickListener(v -> {
                countriesRemoteRepositoryPresenter.updateCountry(Integer.parseInt(id),name.getText().toString());
                alertDialog.dismiss();
            });
        }

        private void ShowInputDialog() {
            AlertDialog.Builder al=new AlertDialog.Builder(RemoteRepositoryActivity.this);
            View view=getLayoutInflater().inflate(R.layout.insert_dialog,null);
            final EditText name=view.findViewById(R.id.name);
            Button insertBtn=view.findViewById(R.id.insert_btn);
            al.setView(view);

            final AlertDialog alertDialog=al.show();

            insertBtn.setOnClickListener(v -> {
                countriesRemoteRepositoryPresenter.addCountry(name.getText().toString());
                alertDialog.dismiss();
                refreshData();
            });
        }

    @Override
    public void refreshData() {
        listValues = new ArrayList<>();
        countriesRemoteRepositoryPresenter.getAllCountry(list, datalist_count);
    }

    @Override
    public void getAllCountries(ArrayAdapter<String> adapter) {
        list.setAdapter(adapter);
    }

    @Override
    public void getTotalRegistersCount(int size) {
        datalist_count.setText("ALL DATA COUNT : " + size);
    }
}