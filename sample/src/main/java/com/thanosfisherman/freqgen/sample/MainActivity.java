package com.thanosfisherman.freqgen.sample;

import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.thanosfisherman.freqgen.sample.buzzer.ContinuousBuzzer;
import com.thanosfisherman.freqgen.sample.buzzer.OneTimeBuzzer;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener
{
    private volatile int freq = 440;
    private Handler mHandler = new Handler();
    int vol = 0;
    ContinuousBuzzer contBuzzer = new ContinuousBuzzer();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        Button button = (Button) findViewById(R.id.btn_play);
        button.setOnTouchListener(this);

        //        mHandler.postDelayed(new Runnable()
        //        {
        //            @Override
        //            public void run()
        //            {
        //                mHandler.postDelayed(this, 1000);
        //            }
        //        }, 1000);
        contBuzzer.play();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                System.out.println(" pressed ");
                v.setPressed(true);
                freq += 50;
                contBuzzer.setToneFreqInHz(freq);
                return true;
            case MotionEvent.ACTION_UP:
                v.setPressed(false);
                System.out.println(" released ");
                return true;
        }
        return false;
    }
}
