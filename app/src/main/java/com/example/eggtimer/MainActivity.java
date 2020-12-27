package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SeekBar mTimerSeekBar;
    private TextView mTimerTextView;
    private int mTime;
    private boolean mTimerIsActive = false;
    private Button mButton;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimerSeekBar = findViewById(R.id.timer_seekBar);

        mTimerTextView = findViewById(R.id.timer_textView);

        mButton = findViewById(R.id.controller_button);

        mTimerSeekBar.setMax(600);

        mTimerSeekBar.setProgress(30);

        mTime = 30;

        mTimerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (fromUser) {
                    mTime = progress;
                    setTimerValue(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    public void controlTimer(View view) {

        if (!mTimerIsActive) {

            mButton.setText("Stop");
            mTimerSeekBar.setEnabled(false);
            mTimerIsActive = true;

            countDownTimer = new CountDownTimer(mTime * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    mTime = mTime - 1;

                    setTimerValue(mTime);

                    mTimerSeekBar.setProgress(mTime);

                }

                @Override
                public void onFinish() {
//                mTimerSeekBar.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.blips);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();

        } else {
            Toast.makeText(MainActivity.this, "Already active, resseting...", Toast.LENGTH_SHORT).show();
            resetTimer();
        }

    }

    private void resetTimer() {

        mTime = 30;
        mTimerSeekBar.setEnabled(true);
        mTimerIsActive = false;
        mTimerSeekBar.setProgress(30);
        mButton.setText("Go");
        countDownTimer.cancel();
        mTimerTextView.setText("0:30");

    }

    private void setTimerValue(int time) {
        int minutes = time / 60;
        int seconds = time - minutes * 60;

        if (seconds < 0)
            seconds = 0;

        String secondString = Integer.toString(seconds);

        if (secondString.length() == 1) {
            secondString = "0" + secondString;
        }


        mTimerTextView.setText(minutes + ":" + secondString);
    }
}