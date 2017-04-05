package com.example.dragon.closeapplication;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class MojaIntentService extends IntentService {
    //akcje które potrafi wykonać usługa (może być więcej niż jedna)
    private static final String AKCJA_ODLICZANIE =
            "com.example.dragon.closeapplication.action.odliczanie";
    //tekstowe identyfikatory parametrów potrzebnych do
//wykonania akji (może być więcej niż jeden)
    private static final String CZAS_ODLICZANIA =
            "com.example.dragon.closeapplication.extra.czas_odliczania";

    //statyczna metoda pomocnicza uruchamiająca zadanie
    public static void uruchomOdliczanie(Context context, int
            czas) {
        Intent zamiar = new Intent(context,
                MojaIntentService.class);
        zamiar.setAction(AKCJA_ODLICZANIE);
        zamiar.putExtra(CZAS_ODLICZANIA, czas);
        context.startService(zamiar);
    }

    //konstruktor
    public MojaIntentService() {
        super("MojaIntentService");
    }

    //metoda wykonująca zadanie
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("intent_service", "usługa otrzymała zadanie");
        if (intent != null) {
            final String action = intent.getAction();
//sprawdzenie o jaką akcję chodzi
            if (AKCJA_ODLICZANIE.equals(action)) {
                final int param1 =
                        intent.getIntExtra(CZAS_ODLICZANIA, 0);
                wykonajOdliczanie(param1); //wykonanie zadania
            } else {
                Log.e("intent_service", "nieznana akcja");
            }
        }
        Log.d("MojaIntentService", "usługa wykonała zadanie");
    }
    private int mCzas;
    private void wykonajOdliczanie(int czas) {
        Log.d("MojaIntentService", "rozpoczynanie odliczania Logcat");

        mCzas = czas;
        for (int i = 0; i < mCzas; ++i) {
            try {
                Thread.sleep(1000);
                wyslijBroadcast(i+1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("MojaIntentService", "czas: " + (i + 1));
        }
/*        Log.d("MojaIntentService", "rozpoczynanie odliczania ProcessBar");

        for(int i =0; i<mCzas; ++i){
            wyslijBroadcast(i+1);
        }*/
    }

    public final static String POWIADOMIENIE =
            "com.example.dragon.closeapplication.odbiornik";
    public final static String INFO_O_CZASIE = "info o czasie";

    // zapisanie czasu w obiekcie implementującym Parcelable
    private void wyslijBroadcast(int czas) {
        Czas c = new Czas();
        c.czas = czas;
        Intent zamiar = new Intent(POWIADOMIENIE);
        zamiar.putExtra(INFO_O_CZASIE, c);
        // wysłanie rozgłoszenia
        Log.d("MojaIntentService","usługa wysłała komunikat: "+c.czas);
        sendBroadcast(zamiar);
    }
}





