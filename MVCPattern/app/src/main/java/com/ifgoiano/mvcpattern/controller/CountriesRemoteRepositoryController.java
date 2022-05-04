package com.ifgoiano.mvcpattern.controller;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.ifgoiano.mvcpattern.R;
import com.ifgoiano.mvcpattern.view.FirebaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Aqui faz conexão com o Firebase, a ideia no geral é a mesma do SQL
// a unica diferença que esse é um banco de dados não relacional,
// é muito semelhante ao banco que você utilizou no projeto da disciplina de Android
public class CountriesRemoteRepositoryController {
    FirebaseFirestore db;
    private String COLLECTION_NAME = "countries";
    private Context context;

    public CountriesRemoteRepositoryController(Context context){
        db = FirebaseFirestore.getInstance();
        this.context = context;
    }

    public void addCountry(String name) {
        Map<String, Object> colecao = new HashMap<>();
        colecao.put("name", name);

        db.collection(COLLECTION_NAME)
                .add(colecao)
                .addOnSuccessListener(documentReference -> Toast.makeText(context, "Cadastrado!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAGCadastro", "Erro ao cadastrar", e);
                    }
                });
    }

    public void readCountry(String name){
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(name);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    ((FirebaseActivity)context).getCountry(document.getString("name"));
                } else {
                    Log.d("TAG", "Documento não encontrado");
                }
            } else {
                Log.d("TAG", "Falhou em ", task.getException());
            }
        });
    }

    public void getAllCountry(ListView listView, TextView datalist_count){

        CollectionReference docRef = db.collection(COLLECTION_NAME);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<String> countries = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        countries.add(doc.getString("name"));
                    }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.row_layout, R.id.listText, countries);
                listView.setAdapter(adapter);
                datalist_count.setText("ALL DATA COUNT : " + countries.size());
            } else {
                Log.d("TAG", "Falhou em ", task.getException());
            }
        });
    }

    public void updateCountry(int position, String name){
        CollectionReference docRef = db.collection(COLLECTION_NAME);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<String> countries = new ArrayList<>();
                for (QueryDocumentSnapshot doc : task.getResult()) {
                countries.add(doc.getId());
                }

                db.collection(COLLECTION_NAME).document(countries.get(position)).update("name", name).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Atualizado!", Toast.LENGTH_SHORT).show();
                        ((FirebaseActivity) context).refreshData();
                    }
                }).addOnFailureListener(e -> Toast.makeText(context, "Falhou ao atualizar!", Toast.LENGTH_SHORT).show());

            } else {
                Log.d("TAG", "Falhou em ", task.getException());
            }
        });
    }

    public void deleteCountry(int id){

        CollectionReference docRef = db.collection(COLLECTION_NAME);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<String> countries = new ArrayList<>();
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    countries.add(doc.getId());
                }
                db.collection(COLLECTION_NAME).document(countries.get(id))
                        .delete()
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(context, "Deletado!", Toast.LENGTH_SHORT).show();
                            ((FirebaseActivity) context).refreshData();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(context, "Erro ao deletar!", Toast.LENGTH_SHORT).show();
                        });
            } else {
                Log.d("TAG", "Falhou em ", task.getException());
            }
        });
    }
}

