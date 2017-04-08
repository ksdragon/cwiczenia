package com.example.dragon.closeapplication;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.RequiresApi;

//aktywność dziedziczy po PreferenceActivity
public class UstawieniaActivity extends PreferenceActivity {
    //fragment zagnieżdzony dziedziczy po PreferenceFragment
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public static class UstawieniaFragment extends PreferenceFragment

    {
        @Override
        public void onCreate(Bundle savedInstanceState)
        { super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.ustawienia);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onCreate(Bundle savedInstanceState)
    { super.onCreate(savedInstanceState);
    //programowe umieszczenie fragmentu w aktywności
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new UstawieniaFragment())
                .commit();
    }
}