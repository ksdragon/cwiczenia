package com.example.dragon.closeapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.dragon.closeapplication.MainActivity.NAPIS;

public class SecondActivity extends Activity {

    public static final String EXTRA_MESSAGE="textMessage";
    public static final String TEXT_ACTIVITY_SECOND = "textDrugiejAktywnośic";
    public static final int KOD = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        receiveMessage();

       // if (savedInstanceState!=null)
      /*  {
            TextView tv=(TextView) findViewById(R.id.textViewMessage);
            tv.setText(savedInstanceState.getString(EXTRA_MESSAGE));
        }*/  // przywracanie stanu savedInstanceState
    }
    public void runThirdActivityMessage(View v)
    {
        EditText et =(EditText) findViewById(R.id.etSecond_Message1);
        Intent zamiar=new Intent(this, thirdActivity.class);
        zamiar.putExtra(TEXT_ACTIVITY_SECOND,et.getText().toString());
        startActivityForResult(zamiar,KOD);
        //startActivity(zamiar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView tv=(TextView) findViewById(R.id.textViewMessage);
        tv.setText(data.getStringExtra(thirdActivity.WYNIK)+" żądanie: "
                +requestCode+" zakończenie: "+resultCode);
    }



    private void receiveMessage (){

        Intent intent = getIntent();
        String message = intent.getStringExtra(NAPIS);
        TextView textView = (TextView) findViewById(R.id.textViewMessage);
        textView.setText(message);
        /*textView.setTextSize(40);
        textView.setText(message);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_second);
        layout.addView(textView);
*/
    }

/*
     @Override
    protected void onSaveInstanceState(Bundle outState) {
        TextView tv=(TextView) findViewById(R.id.textViewMessage);
        //getText() zwraca CharSequence a nie String
        outState.putString(EXTRA_MESSAGE,tv.getText().toString());
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        TextView tv=(TextView) findViewById(R.id.textViewMessage);
        tv.setText(savedInstanceState.getString(EXTRA_MESSAGE));
    }

*/ // przywracanie stanu savedInstanceState

}
