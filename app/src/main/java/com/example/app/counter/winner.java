package com.example.app.counter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class winner extends AppCompatActivity {
    TextView result;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);
        Intent winner = getIntent();
        String first = winner.getStringExtra(MainActivity.EXTRA_FIRST);
        String second = winner.getStringExtra(MainActivity.EXTRA_SECOND);

         result = (TextView)findViewById(R.id.tvWinner);

        if(MainActivity.counterA > MainActivity.counterB) {
            result.setText(first + " is the winner");
        }else if(MainActivity.counterA < MainActivity.counterB){
            result.setText(second + " is the winner");
        }

        MainActivity.counterB = 0;
        MainActivity.counterA = 0;
        //result = (TextView)findViewById(R.id.tvWinner);

       // result.setText(getIntent().getStringExtra("NAME"));

    }

}
