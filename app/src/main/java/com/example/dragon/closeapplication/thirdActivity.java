package com.example.dragon.closeapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class thirdActivity extends AppCompatActivity {

    public static final String TEXT_ACTIVITY_THIRD = "textTrzeciejAktywnośic";
    public final static String WYNIK="wynik";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
    }

    public void runSecondActivityMessage(View v)
    {
        EditText et =(EditText) findViewById(R.id.etThirdMessage1);
        Intent zamiar=new Intent(this, SecondActivity.class);
        zamiar.putExtra(WYNIK,et.getText().toString());
        setResult(RESULT_OK,zamiar);
        finish();
    }



    public void returnToSecond(View v)
    {
        EditText et2=(EditText) findViewById(R.id.etThirdMessage1);
        Intent zamiar=new Intent();
        zamiar.putExtra(WYNIK, et2.getText().toString());
        setResult(RESULT_CANCELED,zamiar);
        finish();
    }
}
