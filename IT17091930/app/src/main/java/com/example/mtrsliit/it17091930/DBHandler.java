package com.example.mtrsliit.it17091930;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {


    public DBHandler(Context context) {
        super(context, "database.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table FilmDetail(_ID INTEGER PRIMARY KEY AUTOINCREMENT,filmName text, filmDirectorID int, filmCategory text)");
        db.execSQL("create table DirectorDetails(_ID INTEGER PRIMARY KEY AUTOINCREMENT,DirectorName text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addfilm(String name, int derectorid, String filmcategory) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FilmName", name);
        contentValues.put("DirectorID", derectorid);
        contentValues.put("FilmCategory", filmcategory);

        long res = db.insert("FilmDetail", null, contentValues);

        if (res == -1) {
            return false;
        } else {
            return true;
        }

    }

    public boolean addDirector(String name) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("DirectorName", name);

        long res = db.insert("DirectorDetails", null, contentValues);

        if (res == -1) {
            return false;
        } else {
            return true;
        }

    }


    public ArrayList<director> loadDirector(){

        ArrayList<director> models= new ArrayList<>();
        SQLiteDatabase dbres = this.getReadableDatabase();
        Cursor results = dbres.rawQuery("select * from DirectorDetails",null);
        results.moveToFirst();

        while (results.isAfterLast()==false){

            director smodel= new director();

            smodel.setName(results.getString(1));

            models.add(smodel);
            results.moveToNext();

        }

        return models;
    }







    public ArrayList<Film> searchFilm(String directorname){

        ArrayList<film> models= new ArrayList<>();
        SQLiteDatabase resdb = this.getReadableDatabase();
        Cursor results = resdb.rawQuery("select * from FilmDetail b,DirectorDetails a where a._ID=b.directorID and a.directorName='"+directorname+"'",null);
        results.moveToFirst();

        while (results.isAfterLast()==false){

            film smodel= new film();
            smodel.setId(results.getInt(0));
            smodel.setname(results.getString(1));
            smodel.setDirectorID(results.getInt(2));
            smodel.setFilmCategory(results.getString(3));

            models.add(smodel);
            results.moveToNext();

        }

        return models;
    }

}
    
}

