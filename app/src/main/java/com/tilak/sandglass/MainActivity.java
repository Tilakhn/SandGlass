package com.tilak.sandglass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private int duration =1500;
    private boolean timerRunning = false;
    private CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView hour= findViewById(R.id.hour);
        final TextView min= findViewById(R.id.min);
        final TextView second= findViewById(R.id.sec);
        final AppCompatButton startBtn = findViewById(R.id.startBtn);
        final AppCompatButton stopBtn = findViewById(R.id.stopBtn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!timerRunning){
                    timerRunning = true;
                    countDownTimer = new CountDownTimer(duration * 1000, 1000) {
                        @Override
                        public void onTick(long l) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    String timer = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(l),
                                            TimeUnit.MILLISECONDS.toMinutes(l)- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(l)),
                                            TimeUnit.MILLISECONDS.toSeconds(l)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)), Locale.getDefault());

                                    final String[] hourMinSec = timer.split(":");

                                    hour.setText(hourMinSec[0]);
                                    min.setText(hourMinSec[1]);
                                    second.setText(hourMinSec[2]);
                                }
                            });
                        }

                        @Override
                        public void onFinish() {
                            duration = 1500;
                            timerRunning = false;
                        }
                    }.start();
                }
                else{
                    Toast.makeText(MainActivity.this, "Timer already running", Toast.LENGTH_SHORT).show();
                }
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(countDownTimer != null){
                    countDownTimer.cancel();
                    countDownTimer = null;
                }
                timerRunning = false;
                duration = 1500;
                hour.setText("00");
                min.setText("00");
                second.setText("00");
            }
        });

    }
}