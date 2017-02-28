package com.example.dragon.closeapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class fourthActivity extends AppCompatActivity {
    private ArrayList<Integer> mDane = new ArrayList<Integer>();
    private ListView mLista;
    private ArrayAdapter<Integer> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        listViewRandomNumber();
    }

    // generowanie liczb z odświeżaniem i obsługą zdarzeń ListView
    private void listViewRandomNumber() {
        Random generator = new Random();
        for (int i = 0; i < 10; i++)
            mDane.add(generator.nextInt(100));
        mLista = (ListView) findViewById(R.id.lista);

        /*Konstruktor adaptera przyjmuje następujące parametry: kontekst (tu referencję
        do aktywności), identyfikator układu wiersza, identyfikator elementu wiersza,
        w którym należy umieścić daną z tablicy oraz samo źródło danych.*/

        mAdapter = new ArrayAdapter<Integer>(this,
                R.layout.wiersz_listy, //wygląd wiersza
                R.id.wartosc_etykieta, //elem. do którego zapisać wartość
                mDane); //źródło danych
        mLista.setAdapter(mAdapter); //...

        // obsługa zadrzenia kliknięcia w ListView
        mLista.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(
                    AdapterView<?> arg0, View arg1,
                    int arg2, long arg3) {
                Toast.makeText(fourthActivity.this, "Kliknieto: " +
                        mDane.get(arg2),Toast.LENGTH_SHORT).show();
            }
        });

    }
    // obsługa klawisza odśwież z wykorzystaniem adaptera ArrayAdapter
    public void odswiezDane(View v)
    {
        mDane.clear();
        Random generator = new Random();
        for (int i = 0; i < 10; i++)
            mDane.add(generator.nextInt(100));
        mAdapter.notifyDataSetChanged();
    }
}
