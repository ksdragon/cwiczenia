package com.example.dragon.closeapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HP ProDesk on 2017-04-06.
 */

public class PomocnikBD extends SQLiteOpenHelper{
    private Context mKontekst;
    //przydatne stałe
    public final static int WERSJA_BAZY = 1;
    public final static String NAZWA_BAZY = "baza_wartosci";
    public final static String NAZWA_TABELI = "wartosci";
    public final static String ID = "_id";
    public final static String WARTOSC = "wartosc";
    public final static String ETYKIETA = "PomocnikBD";
    public final static String TW_BAZY = "CREATE TABLE " +
            NAZWA_TABELI+"("+ID+" integer primary key autoincrement, "+
            WARTOSC + " text not null);";
    private static final String KAS_BAZY = "DROP TABLE IF EXISTS "
            + NAZWA_TABELI;
    public PomocnikBD(Context context) {
        super(context, NAZWA_BAZY, null, WERSJA_BAZY);
        mKontekst = context;
    }
    //tworzenie bazy danych
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TW_BAZY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(KAS_BAZY);
        onCreate(db);
    }
}

