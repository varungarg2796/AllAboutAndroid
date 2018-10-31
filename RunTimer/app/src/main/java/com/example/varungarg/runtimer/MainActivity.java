package com.example.varungarg.runtimer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView;
    SeekBar timerSeekBar;

    public void buttonClicked (View v){
        Log.i("Button Pressed", "Nice");

        CountDownTimer countDownTimer = new CountDownTimer(timerSeekBar.getProgress()*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateTimer((int) millisUntilFinished /1000);
            }

            @Override
            public void onFinish() {
                Log.i("Finish", "Timer all done");
            }
        }.start();
    }

    public void updateTimer(int secondsLeft){

        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes*60);

        String secondString = Integer.toString(seconds);

        if(seconds <=9){
            secondString = "0" + secondString;
        }

        timerTextView.setText(Integer.toString(minutes)+ " : " + secondString);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        timerSeekBar = findViewById(R.id.timerSeekBar);

        timerTextView = findViewById(R.id.countDownTextView);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
