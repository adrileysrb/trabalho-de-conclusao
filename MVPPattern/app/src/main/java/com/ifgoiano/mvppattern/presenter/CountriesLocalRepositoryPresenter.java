package com.ifgoiano.mvppattern.presenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ifgoiano.mvppattern.model.Country;

import java.util.ArrayList;
import java.util.List;

public class CountriesLocalRepositoryPresenter extends SQLiteOpenHelper {

    private CountriesLocalRepositoryPresenter.View view;

    private static final int    DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "country_db_mvp";
    private static final String TABLE_NAME="countries";
    private static final String ID="id";
    private static final String name="name";

    public CountriesLocalRepositoryPresenter(Context context, CountriesLocalRepositoryPresenter.View view){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.view = view;
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

    public void getCountry(int id){
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
        Country country = new Country();
        country.setCountryName(cursor.getString(1));
        country.setId(cursor.getString(0));
        sqLiteDatabase.close();
        view.getCountry(country);
    }

    public void getAllCountries(){
        List<Country> countries = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                Country country = new Country();
                country.setCountryName(cursor.getString(1));
                country.setId(cursor.getString(0));
                countries.add(country);
            }
            while(cursor.moveToNext());
        }
        sqLiteDatabase.close();
        view.getAllCountries(countries);
    }

    public void updateCountry(Country Country){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(name, Country.getCountryName());
        sqLiteDatabase.update(TABLE_NAME, contentValues, ID+"=?", new String[]{String.valueOf(Country.getId())});
    }

    public void deleteCountry(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, ID+"=?", new String[]{(id)});
        sqLiteDatabase.close();
    }

    public void getToltalRegistersCount(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        view.getTotalRegistersCount(cursor.getCount());
    }

    public interface View {
        void getCountry(Country country);
        void getAllCountries(List<Country> countries);
        void getTotalRegistersCount(int size);
    }

}
/*
* void addCountry(Country country); //Não há retorno, então nao tem implementação na view
        void getCountry(Country country);
        void getAllCountries(List<Country> countries);
        int updateCountry(Country contry);
        void deleteCountry(String id);
        void getTotalRegistersCount(int size);
        void onError();
        * */