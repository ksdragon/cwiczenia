package com.example.dragon.closeapplication;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by HP ProDesk on 2017-04-06.
 */
public class WartosciProvider extends ContentProvider {
    private PomocnikBD mPomocnikBD;
    //identyfikator (ang. authority) dostawcy
    private static final String IDENTYFIKATOR =
            "com.example.dragon.closeapplication.WartosciProvider";
    //stała – aby nie trzeba było wpisywać tekstu samodzielnie
    public static final Uri URI_ZAWARTOSCI =
            Uri.parse("content://" + IDENTYFIKATOR + "/" +
                    PomocnikBD.NAZWA_TABELI);
    //stałe pozwalające zidentyfikować rodzaj rozpoznanego URI
    private static final int CALA_TABELA = 1;
    private static final int WYBRANY_WIERSZ = 2;
    //UriMacher z pustym korzeniem drzewa URI (NO_MATCH)
    private static final UriMatcher sDopasowanieUri =
            new UriMatcher(UriMatcher.NO_MATCH);

    static {
//dodanie rozpoznawanych URI
        sDopasowanieUri.addURI(IDENTYFIKATOR,
                PomocnikBD.NAZWA_TABELI, CALA_TABELA);
        sDopasowanieUri.addURI(IDENTYFIKATOR,
                PomocnikBD.NAZWA_TABELI + "/#", WYBRANY_WIERSZ);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
//czy wiersz czy cała tabela i otworzenie bazy
        int typUri = sDopasowanieUri.match(uri);
        SQLiteDatabase baza = mPomocnikBD.getWritableDatabase();
        long idDodanego = 0;
        switch (typUri) {
            case CALA_TABELA:
                idDodanego =
                        baza.insert(PomocnikBD.NAZWA_TABELI, //tabela
                                null, //nullColumnHack
                                values); //wartości
                break;
            default:
                throw new IllegalArgumentException("Nieznane URI: " +
                        uri);
        }
//powiadomienie o zmianie danych (->np. odświeżenie listy)
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(PomocnikBD.NAZWA_TABELI + "/" + idDodanego);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Cursor query(Uri uri, String[] projection,
                        String selection, String[] selectionArgs,
                        String sortOrder) {
        int typUri = sDopasowanieUri.match(uri);
        SQLiteDatabase baza = mPomocnikBD.getWritableDatabase();
        Cursor kursor = null;
        switch (typUri) {
            case CALA_TABELA:
                kursor = baza.query(false, //distinct
                        PomocnikBD.NAZWA_TABELI, //tabela
                        projection, //kolumny
                        selection, //klauzula WHERE np. w=1 lub w=?
                        selectionArgs, //argumenty WHRERE jeżeli wyżej są „?”
                        null, //GROUP BY
                        null, //HAVING
                        sortOrder, //ORDER BY
                        null, //ograniczenie liczby rekordów, null - brak
                        null); //sygnał anulowania
                break;
            case WYBRANY_WIERSZ: //w przypadku jednego wiersza
//modyfikowana jest WHERE
                kursor = baza.query(false, PomocnikBD.NAZWA_TABELI,
                        projection, dodajIdDoSelekcji(selection, uri),
                        selectionArgs, null, null, sortOrder, null,
                        null);
                break;
            default:
                throw new IllegalArgumentException("Nieznane URI: " + uri);
        }
//URI może być monitorowane pod kątem zmiany danych – tu jest
//rejestrowane. Obserwator (którego trzeba zarejestrować
//będzie powiadamiany o zmianie danych)
        kursor.setNotificationUri(getContext().
                getContentResolver(), uri);
        return kursor;
    }

    @Override
    public boolean onCreate() {
        mPomocnikBD = new PomocnikBD(getContext());
        return true;
    }

    //dodaje do klauzuli WHERE identyfikator wiersza odczytany z URI
    private String dodajIdDoSelekcji(String selekcja, Uri uri) {
//jeżeli już jest to dodajemy tylko dodatkowy warunek
        if (selekcja != null && !selekcja.equals(""))
            selekcja = selekcja + " and " + PomocnikBD.ID + "="
                    + uri.getLastPathSegment();
//jeżeli nie ma WHERE tworzymy je od początku
        else
            selekcja = PomocnikBD.ID + "=" + uri.getLastPathSegment();
        return selekcja;
    }

    @Override
    public int delete(Uri uri, String selection,
                      String[] selectionArgs) {
        int typUri = sDopasowanieUri.match(uri);
        SQLiteDatabase baza = mPomocnikBD.getWritableDatabase();
        int liczbaUsunietych = 0;
        switch (typUri) {
            case CALA_TABELA:
                liczbaUsunietych = baza.delete(PomocnikBD.NAZWA_TABELI,
                        selection, //WHERE
                        selectionArgs); //argumenty
                break;
            case WYBRANY_WIERSZ: //modyfikowane jest WHERE
                liczbaUsunietych = baza.delete(PomocnikBD.NAZWA_TABELI,
                        dodajIdDoSelekcji(selection, uri), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Nieznane URI: " +
                        uri);
        }
//powiadomienie o zmianie danych
        getContext().getContentResolver().notifyChange(uri, null);
        return liczbaUsunietych;
    }

    @Override
    public int update(Uri uri, ContentValues values,
                      String selection, String[] selectionArgs) {
        int typUri = sDopasowanieUri.match(uri);
        SQLiteDatabase baza = mPomocnikBD.getWritableDatabase();
        int liczbaZaktualizowanych = 0;
        switch (typUri) {
            case CALA_TABELA:
                liczbaZaktualizowanych =
                        baza.update(PomocnikBD.NAZWA_TABELI,
                                values, selection, selectionArgs);
            //wartości, WHERE, argumenty WHERE
                break;
            case WYBRANY_WIERSZ: //modyfikacja WHERE
                liczbaZaktualizowanych =
                        baza.update(PomocnikBD.NAZWA_TABELI, values,
                                dodajIdDoSelekcji(selection, uri), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Nieznane URI: " + uri);
        } //powiadomienie o zmianie danych
        getContext().getContentResolver().notifyChange(uri, null);
        return liczbaZaktualizowanych;
    }
}
