package com.thanosfisherman.freqgen.sample;

import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    final ContinuousBuzzer tonePlayer = new ContinuousBuzzer();
    private int freq = 440;
    private Handler mHandler = new Handler();
    int vol = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        tonePlayer.setVolume(vol);
        tonePlayer.setPausePeriodSeconds(2);
        tonePlayer.setToneFreqInHz(freq);
        Button button = (Button) findViewById(R.id.btn_play);
        button.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                //if (event.getAction() == MotionEvent.ACTION_DOWN)

                return false;
            }
        });

        mHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                tonePlayer.setVolume(vol += 5);
                mHandler.postDelayed(this, 1000);
            }
        }, 1000);

        tonePlayer.play();
    }
}
