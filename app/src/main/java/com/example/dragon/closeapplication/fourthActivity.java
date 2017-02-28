package com.example.dragon.closeapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    }

    private void listViewRandomNumber() {
        Random generator = new Random();
        for (int i = 0; i < 10; i++)
            mDane.add(generator.nextInt(100));
        mLista = (ListView) findViewById(R.id.lista);
        mAdapter = new ArrayAdapter<Integer>(this,
                R.layout.activity_third, //wygląd wiersza
                R.id.lista, //elem. do którego zapisać wartość
                mDane); //źródło danych
        mLista.setAdapter(mAdapter); //...
    }
}
