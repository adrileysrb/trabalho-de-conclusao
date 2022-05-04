package com.ifgoiano.mvcpattern.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ifgoiano.mvcpattern.model.Country;

import java.util.ArrayList;
import java.util.List;

//Aqui é a classe do SQL, não tem muito o que dizer
public class CountriesLocalRepositoryController extends SQLiteOpenHelper {

    private static final int    DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "country_db";
    private static final String TABLE_NAME="countries";
    private static final String ID="id";
    private static final String name="name";

    public CountriesLocalRepositoryController(Context context){
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

    public List<Country> getAllCountries(){
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
