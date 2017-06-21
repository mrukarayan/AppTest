package com.syntax.ubertest;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.skyfishjy.library.RippleBackground;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TimerTask timerTask;
    private RippleBackground rippleBackground;
    private ImageView imageView;
    private Button btnStart;
    private boolean isDoubleClickedOnCircle = false;
    private MediaPlayer mp;
    private boolean isTimerRunning = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rippleBackground = (RippleBackground) findViewById(R.id.content);
        imageView = (ImageView) findViewById(R.id.profile_image);
        btnStart = (Button) findViewById(R.id.btnStart);

        imageView.setOnClickListener(this);
        btnStart.setOnClickListener(this);

    }

    private void startTimer(){
        if(!isTimerRunning){
            Timer timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    isTimerRunning = true;
                    playBeepSound(R.raw.beep);
                }
            };
            timer.schedule(timerTask, 0, 1000);
        }
    }

    private void playBeepSound(int sound) {
        mp = MediaPlayer.create(getBaseContext(), sound);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }

        });
        mp.setLooping(false);
        mp.setVolume(1.0f, 1.0f);
        mp.start();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnStart) {
            rippleBackground.startRippleAnimation();
            startTimer();
        }

        else if(id == R.id.profile_image){
            if(isDoubleClickedOnCircle){
                rippleBackground.stopRippleAnimation();
                timerTask.cancel();
                isTimerRunning = false;

            }
            else {
                isDoubleClickedOnCircle = true;
                rippleBackground.startRippleAnimation();
                startTimer();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        isDoubleClickedOnCircle = false;
                    }
                }, 2000);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        timerTask.cancel();
    }
}
