package com.example.dragon.closeapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    private static Button button_close;
    private static Button button_SecondActivity;
    private static Button button_Login;
    private static TextView mCzasDzialaniaEtykieta;
    private GestureDetectorCompat gestureDetector;
    public static final String NAPIS = "napis_na_etykiecie";

    /*@Override
    protected void onSaveInstanceState(Bundle outState) {
        TextView tv=(TextView) findViewById(R.id.editTextName);
        //getText() zwraca CharSequence a nie String
        outState.putString(NAPIS,tv.getText().toString());
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        TextView tv=(TextView) findViewById(R.id.editTextName);
        tv.setText(savedInstanceState.getString(NAPIS));
    }*/


    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Intent intent = new Intent("com.example.dragon.closeapplication.SecondActivity");
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }  // GestureDetector Method

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);  // trzeba zaimpirtować import android.support.v7.widget.Toolbar
        setSupportActionBar(toolbar);
        onButtonClickListener();  // zamykanie aplikacji z alert box-em
        OnClickButtonListener(); // przechodzenie do innej aktywniści 2
        OnClickButtonListenerfourthActyvites(); //  przechdzenie do aktywności 4
        onButtonLoginLisiner();  // przesyłanie tekstu do drugiej aktywności przez zmienna NAPIS
        uruchomZadanieAcynchroniczne();
        /*if (savedInstanceState!=null)
        {
            TextView tv=(TextView) findViewById(R.id.editTextName);
            tv.setText(savedInstanceState.getString(NAPIS));
        }*/


        this.gestureDetector = new GestureDetectorCompat(this, this);  // Obsługa gestów
        gestureDetector.setOnDoubleTapListener(this);

    }

    private void onButtonLoginLisiner() {
        button_Login = (Button) findViewById(R.id.buttonLogin);
        button_Login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.example.dragon.closeapplication.SecondActivity");
                        EditText eTextLogin = (EditText) findViewById(R.id.editTextName);
                        String sNameLogin = eTextLogin.getText().toString();
                        intent.putExtra(NAPIS, sNameLogin);
                        startActivity(intent);
                    }
                }
        );
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void onButtonClickListener() {
        button_close = (Button) findViewById(R.id.btnClose);
        button_close.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);
                        a_builder.setMessage("Do you want to Close this App !!!")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("Alert !!!");
                        alert.show();
                    }
                }
        );
    }

    public void OnClickButtonListener() {
        button_SecondActivity = (Button) findViewById(R.id.btn_secondActivity);
        button_SecondActivity.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.example.dragon.closeapplication.SecondActivity");
                        startActivity(intent);
                    }
                }
        );

    }

    public void OnClickButtonListenerfourthActyvites() {
        button_SecondActivity = (Button) findViewById(R.id.btn_fourthActivity);
        button_SecondActivity.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.example.dragon.closeapplication.fourthActivity");
                        startActivity(intent);
                    }
                }
        );

    }

    private void uruchomZadanieAcynchroniczne() {
        ZadanieAsynchroniczne zadanie=new ZadanieAsynchroniczne();
        zadanie.execute(new Integer[] {10});
        Log.d("async_task", "zadanie uruchomione");
    }


    class ZadanieAsynchroniczne extends
// typ parametrów, postępu, wyniku
            AsyncTask<Integer, Integer, Integer> {
        // obowiązkowa, wywoływana w osobnym wątku
        @Override
        protected Integer doInBackground(Integer... params) {
            for (int i = 0; i < params[0].intValue(); i++) {
                try {
//udaję, że pracuję ;-)
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(new Integer[]{i + 1});
            }
//wynikiem jest całkowity czas
            return params[0].intValue();
        }

        // opcjonalna, wywoływana w wątku GUI
// aktualizuje informacje o postępie
        @Override
        protected void onProgressUpdate(Integer... values) {
            mCzasDzialaniaEtykieta = (TextView)findViewById(R.id.czas_dzialania_etykieta);
            Log.d("async_task",
                    "aktualizacja postępu: " + values[0].intValue());
            mCzasDzialaniaEtykieta.setText(
                    Integer.toString(values[0].intValue()));
            super.onProgressUpdate(values);
        }

        // opcjonalna, wywoływana w wątku GUI
// odpowiedzialna za publikację wyników
        @Override
        protected void onPostExecute(Integer result) {
            Log.d("async_task", "wynik: " + result.intValue());
            TextView wynikEtykieta = (TextView) findViewById(R.id.wynik_etykieta);
            wynikEtykieta.setText("wynik: " + result.intValue());
            super.onPostExecute(result);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
