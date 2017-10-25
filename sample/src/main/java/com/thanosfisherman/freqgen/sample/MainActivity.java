package com.thanosfisherman.freqgen.sample;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.thanosfisherman.freqgen.sample.buzzer.ContinuousBuzzer;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener
{
    private Handler mHandler = new Handler();
    private boolean isButtonPressed;
    final ContinuousBuzzer contBuzzer = new ContinuousBuzzer();
    int vol = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        contBuzzer.setPausePeriodSeconds(1);
        contBuzzer.setVolume(vol);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        final int volStart = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) / 2;
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volStart, AudioManager.FLAG_SHOW_UI);
        Button button = (Button) findViewById(R.id.btn_play);
        button.setOnTouchListener(this);

        mHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if (isButtonPressed)
                {
                    vol -= 2;
                    contBuzzer.setVolume(vol);
                }
                else
                {
                    vol += 2;
                    contBuzzer.setVolume(vol);
                }

                if (vol > 100)
                    vol = 100;
                else if (vol < 0)
                    vol = 0;

                Log.i("MAIN", "BUZZER VOL " + contBuzzer.getVolume());
                mHandler.postDelayed(this, 1000);
            }
        }, 1000);
        contBuzzer.play();
        //contBuzzer.play();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                System.out.println(" pressed ");
                v.setPressed(true);
                isButtonPressed = true;
                return true;
            case MotionEvent.ACTION_UP:
                v.setPressed(false);
                System.out.println(" released ");
                isButtonPressed = false;
                return true;
        }
        return false;
    }
}
