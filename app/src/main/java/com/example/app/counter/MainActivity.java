package com.example.app.counter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //USED VARIABLES
    public static int counterA = 0;
    public static int counterB = 0;
    int points_limit = 5;
    //int time = 100;
    long start_time = 10000;
    boolean timerRunning;
    //boolean checkTimer = true;
    long timeLeft = start_time;
    CountDownTimer mCountDownTimer;
    private Button decrease_a, decrease_b;
    private ImageButton point_a, point_b, startPause, resetTime;
    private TextView countDownView;
    private TextView score_a, score_b, playerName1, playerName2;

    public static final String EXTRA_FIRST = "com.counter.app.EXTRA_FIRST";
    public static final String EXTRA_SECOND = "com.counter.app.EXTRA_SECOND";


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        score_a = (TextView) findViewById(R.id.scoreTeamA);
        score_b = (TextView) findViewById(R.id.scoreTeamB);
        final TextView result = (TextView) findViewById(R.id.tvWinner);
        point_a = (ImageButton) findViewById(R.id.btnTeamA);
        point_b = (ImageButton) findViewById(R.id.btnTeamB);
        decrease_a = (Button) findViewById(R.id.minusTeamA);
        decrease_b = (Button) findViewById(R.id.minusTeamB);
        countDownView = (TextView) findViewById(R.id.countDown);
        startPause = (ImageButton) findViewById(R.id.btnImgStartPause);
        resetTime = (ImageButton) findViewById(R.id.btnImgReset);
        playerName1 = (TextView) findViewById(R.id.playerName1);
        playerName2 = (TextView) findViewById(R.id.playerName2);

        //Toast toast = Toast.makeText(getApplicationContext(),
        //     "This is a message displayed in a Toast",
        //  Toast.LENGTH_SHORT);

        //TIMER
        //START- PAUSE BUTTON
        startPause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (timerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });
        //RESET BUTTON
        resetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
                startCounterA();
                startCounterB();
            }
        });

        updateCountDownText();

        //Intents
        //final Intent winner = new Intent(this, winner.class);

        //CHECK IF TIME STARTS


        //TEAM A POINTS COUNTER
        point_a.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                startTimer();
                MediaPlayer score = MediaPlayer.create(MainActivity.this, R.raw.score2);
                score.start();
                //INCREASE A
                counterA++;
                score_a.setText(Integer.toString(counterA));

                //THE A WINNING
                if (counterA > points_limit - 1) {
                    score_a.setText("Winner");
                    openActivityWinner();
                }
            }
        });

        //DECREASE A
        decrease_a.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                startTimer();
                MediaPlayer minus = MediaPlayer.create(MainActivity.this, R.raw.minus);
                minus.start();
                counterA--;
                score_a.setText(Integer.toString(counterA));
            }
        });


        //TEAM B POINTS COUNTER
        point_b.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                startTimer();
                MediaPlayer score = MediaPlayer.create(MainActivity.this, R.raw.score2);
                score.start();
                //INCREASE B
                counterB++;
                score_b.setText(Integer.toString(counterB));

                //THE B WINNING
                if (counterB > points_limit - 1) {
                    score_b.setText("Winner");
                    openActivityWinner();
                }
            }
        });


        //DECREASE B
        decrease_b.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                startTimer();
                MediaPlayer minus = MediaPlayer.create(MainActivity.this, R.raw.minus);
                minus.start();
                counterB--;
                score_b.setText(Integer.toString(counterB));
            }
        });

    }

    //TIMER FUNCTIONS
    public void startTimer() {
        //Toast toast0 = Toast.makeText(getApplicationContext(),
        //     "Time started",
        //  Toast.LENGTH_SHORT);
        //toast0.show();
        mCountDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateCountDownText();

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                timerRunning = false;
                //startPause.setText("Start");
                startPause.setImageResource(R.drawable.play_white);
                startPause.setVisibility(View.INVISIBLE);
                resetTime.setVisibility(View.VISIBLE);
                //Toast.makeText(MainActivity.this, "Time finished, NO WINNER !", Toast.LENGTH_SHORT).show();
                //decrease_a.setVisibility(View.INVISIBLE);
                //decrease_b.setVisibility(View.INVISIBLE);
                decrease_a.setOnClickListener(null);
                decrease_b.setOnClickListener(null);
                point_a.setOnClickListener(null);
                point_b.setOnClickListener(null);

            }
        }.start();
        timerRunning = true;
        //startPause.setText("Pause");
        startPause.setImageResource(R.drawable.pause_white);
        resetTime.setVisibility(View.INVISIBLE);

    }

    public void pauseTimer() {
        Toast toast0 = Toast.makeText(getApplicationContext(),
                "Time paused",
                Toast.LENGTH_SHORT);
        toast0.show();
        mCountDownTimer.cancel();
        timerRunning = false;
        //startPause.setText("Start");
        startPause.setImageResource(R.drawable.play_white);
        resetTime.setVisibility(View.VISIBLE);

    }

    public void resetTimer() {
        timeLeft = start_time;
        updateCountDownText();
        resetTime.setVisibility(View.INVISIBLE);
        startPause.setVisibility(View.VISIBLE);
        counterA = 0;
        counterB = 0;
        score_a.setText("0");
        score_b.setText("0");
        Toast toast = Toast.makeText(getApplicationContext(),
                "Time & scores reset",
                Toast.LENGTH_SHORT);
        toast.show();
        recreate();


    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeft / 1000) / 60;
        int seconds = (int) (timeLeft / 1000) % 60;
        String timeLedtFormated = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        countDownView.setText(timeLedtFormated);
    }

    private void openActivityWinner() {
        TextView player1 = (TextView) findViewById(R.id.playerName1);
        TextView player2 = (TextView) findViewById(R.id.playerName2);
        String first = player1.getText().toString();
        String second = player2.getText().toString();

        Intent winner = new Intent(this, winner.class);
        winner.putExtra(EXTRA_FIRST, first);
        winner.putExtra(EXTRA_SECOND, second);
        startActivity(winner);
        //finish();
        recreate();

    }

    public void startCounterA() {
        //TEAM A POINTS COUNTER
        point_a.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                startTimer();
                MediaPlayer score = MediaPlayer.create(MainActivity.this, R.raw.score2);
                score.start();
                //INCREASE A
                counterA++;
                score_a.setText(Integer.toString(counterA));

                //THE A WINNING
                if (counterA > points_limit - 1) {
                    score_a.setText("Winner");
                    openActivityWinner();
                }
            }
        });

        //DECREASE A
        decrease_a.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                startTimer();
                MediaPlayer minus = MediaPlayer.create(MainActivity.this, R.raw.minus);
                minus.start();
                counterA--;
                score_a.setText(Integer.toString(counterA));
            }
        });

    }

    public void startCounterB() {
        //TEAM B POINTS COUNTER
        point_b.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                startTimer();
                MediaPlayer score = MediaPlayer.create(MainActivity.this, R.raw.score2);
                score.start();
                //INCREASE B
                counterB++;
                score_b.setText(Integer.toString(counterB));

                //THE B WINNING
                if (counterB > points_limit - 1) {
                    score_b.setText("Winner");
                    openActivityWinner();
                }
            }
        });


        //DECREASE B
        decrease_b.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                startTimer();
                MediaPlayer minus = MediaPlayer.create(MainActivity.this, R.raw.minus);
                minus.start();
                counterB--;
                score_b.setText(Integer.toString(counterB));
            }
        });
    }

}
