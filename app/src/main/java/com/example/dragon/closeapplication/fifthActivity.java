package com.example.dragon.closeapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class fifthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);
    }

    protected void pobierz(View v) {
        EditText adresEdycja = (EditText)
                findViewById(R.id.adres_edycja);
        String adres = adresEdycja.getText().toString();
        PobierzTask zadanie = new PobierzTask();
        zadanie.execute(adres);
    }

    private class PobierzTask extends
            AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... parametry) {
            Log.d("PT", "uruchomione");
            HttpURLConnection polaczenie = null;
            StringBuilder budowniczyNapisu = new StringBuilder();
            try {
                URL url = new URL(parametry[0]);
                Log.d("PT", "URL " + url.toString());
                polaczenie = (HttpURLConnection) url.openConnection();
                polaczenie.setRequestMethod("GET");
                Log.d("PT", "polaczenie: " + polaczenie.toString());
                Log.d("PT", "rozmiar: " +
                        polaczenie.getContentLength());
                Log.d("PT", "typ: " + polaczenie.getContentType());
                BufferedReader bufor = new BufferedReader(
                        new InputStreamReader(polaczenie.getInputStream()));
                String linia;
                while ((linia = bufor.readLine()) != null) {
                    Log.d("PT", "kolejna linia " + linia);
                    budowniczyNapisu.append(linia);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (polaczenie != null) {
                    polaczenie.disconnect();
                    Log.d("PT", "rozłączone");
                }
            }
            return budowniczyNapisu.toString();

        }

        @Override
        protected void onPostExecute(String wynik) {
            Log.d("PIT", "zakonczone");
            super.onPostExecute(wynik);
            TextView pobraneEdycja = (TextView)
                    findViewById(R.id.pobrane_edycja);
            pobraneEdycja.setMovementMethod(new ScrollingMovementMethod());
            pobraneEdycja.setText(wynik);
        }
    }
}


