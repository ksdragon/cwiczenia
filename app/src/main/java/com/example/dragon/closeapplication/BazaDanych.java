package com.example.dragon.closeapplication;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class BazaDanych extends Activity implements LoaderManager.LoaderCallbacks <Cursor>{
    //adapter łączy kursor z dostawcy i listę
    private SimpleCursorAdapter mAdapterKursora;
    private ListView mLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baza_danych);
        mLista =(ListView) findViewById(R.id.lista_wartosci);
        wypelnijListę();
        Button  przyciskDodaj = (Button) findViewById(R.id.dodaj_przycisk);
        przyciskDodaj.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dodajWartosc();
            }
        });
    }
    private void wypelnijListę(){
        getLoaderManager().initLoader(0, //identyfikator loadera
                null, //argumenty (Bundle)
                this); //klasa implementująca
        //LoaderCallbacks
            // utworzenie mapowania między kolumnami tabeli a
        // kolumnami wyświetlanej listy
        String[] mapujZ = new String[] { PomocnikBD.WARTOSC };
        int[] mapujDo = new int[] { R.id.wartosc_etykieta };
        // adapter wymaga aby wyniku zapytania znajdowała
        // się kolumna _id (zapytanie na następnym slajdzie)
        mAdapterKursora = new SimpleCursorAdapter(this,
                R.layout.wiersz_listy, null, mapujZ, mapujDo, 0);
        mLista.setAdapter(mAdapterKursora);
    }


    private void dodajWartosc() {
        Log.d("BazaDanych","P: dodajWartosc");
        ContentValues wartosci = new ContentValues();
        EditText wartoscEdycja = (EditText)
                findViewById(R.id.wartosc_edycja);
        wartosci.put(PomocnikBD.WARTOSC,
                wartoscEdycja.getText().toString());
        Uri uriNowego = getContentResolver().insert(
                WartosciProvider.URI_ZAWARTOSCI, wartosci);
        Log.d("BazaDanych","K: dodajWartosc");
    }
    //implementacja loadera
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    // adapter wymaga aby wyniku zapytania znajdowała

    // się kolumna _id
        String[] projekcja = { PomocnikBD.ID, PomocnikBD.WARTOSC };
        CursorLoader loaderKursora = new CursorLoader(this,
                WartosciProvider.URI_ZAWARTOSCI, projekcja, null,
                null, null);
        return loaderKursora;
    }
    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor dane) {
    //ustawienie danych w adapterze
        mAdapterKursora.swapCursor(dane);
    }
    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        mAdapterKursora.swapCursor(null);
    }

}
