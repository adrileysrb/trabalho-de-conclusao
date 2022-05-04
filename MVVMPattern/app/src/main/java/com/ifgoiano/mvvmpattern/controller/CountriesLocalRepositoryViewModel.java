package com.ifgoiano.mvvmpattern.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.common.collect.Lists;
import com.ifgoiano.mvvmpattern.R;
import com.ifgoiano.mvvmpattern.controller.api.CountriesAPIService;
import com.ifgoiano.mvvmpattern.model.Country;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CountriesLocalRepositoryViewModel extends ViewModel {
    private SqlDatabase sqlDatabase;
    private final MutableLiveData<List<String>> countries = new MutableLiveData<>();
    private final MutableLiveData<Boolean> countryError = new MutableLiveData<>();

    public CountriesLocalRepositoryViewModel() {
    }

    public void setContext(Context context){
        sqlDatabase = new SqlDatabase(context);
    }

    public MutableLiveData<List<String>> getCountries(ListView listView, TextView textView, Context context) {
        countries.postValue(sqlDatabase.getAllCountries(listView, textView, context));
        return countries;
    }

    public void deleteCountry(String id){
        sqlDatabase.deleteCountry(id);
    }

    public int getToltalRegistersCount(){
        return sqlDatabase.getToltalRegistersCount();
    }

    public Country getCountry(int id){
        return sqlDatabase.getCountry(id);
    }

    public int updateCountry(Country country){
        return sqlDatabase.updateCountry(country);
    }

    public void addCountry(Country country){
        sqlDatabase.addCountry(country);
    }

    public class SqlDatabase extends SQLiteOpenHelper {

        private static final int    DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "country_db";
        private static final String TABLE_NAME="countries";
        private static final String ID="id";
        private static final String name="name";

        public SqlDatabase(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String table_query="CREATE TABLE if not EXISTS "+TABLE_NAME+
                    "("+
                    ID+" INTEGER PRIMARY KEY,"+
                    name+" TEXT "+
                    ")";
            sqLiteDatabase.execSQL(table_query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        }

        public void addCountry(Country Country){
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(name, Country.getCountryName());
            sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
            sqLiteDatabase.close();
        }

        public Country getCountry(int id){
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query(
                    TABLE_NAME,
                    new String[]{ ID, name },
                    ID+" = ?",
                    new String[]{ String.valueOf(id)},
                    null,
                    null,
                    null
            );

            if(cursor!=null){
                cursor.moveToFirst();
            }
            Country Country = new Country();
            Country.setCountryName(cursor.getString(1));
            Country.setId(cursor.getString(0));
            sqLiteDatabase.close();
            return Country;
        }

        public List<String> getAllCountries(ListView listView, TextView datalist_count, Context context){
            List<String> countries = new ArrayList<>();
            String query = "SELECT * FROM " + TABLE_NAME;
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if(cursor.moveToFirst()){
                do{
                    Country country = new Country();
                    country.setCountryName(cursor.getString(1));
                    country.setId(cursor.getString(0));
                    countries.add(country.getCountryName());
                }
                while(cursor.moveToNext());
            }
            sqLiteDatabase.close(); //context, R.layout.row_layout, R.id.listText, countries
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.row_layout, R.id.listText, countries);
            listView.setAdapter(adapter);
            datalist_count.setText("ALL DATA COUNT : " + countries.size());
            return countries;
        }

        public int updateCountry(Country Country){
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(name, Country.getCountryName());
            return sqLiteDatabase.update(TABLE_NAME, contentValues, ID+"=?", new String[]{String.valueOf(Country.getId())});
        }

        public void deleteCountry(String id){
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            sqLiteDatabase.delete(TABLE_NAME, ID+"=?", new String[]{(id)});
            sqLiteDatabase.close();
        }

        public int getToltalRegistersCount(){
            String query = "SELECT * FROM " + TABLE_NAME;
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            return cursor.getCount();
        }
    }
}
