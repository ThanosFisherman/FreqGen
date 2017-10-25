package com.thanosfisherman.freqgen.sample;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.karlotoy.perfectune.instance.PerfectTune;
import com.thanosfisherman.freqgen.sample.buzzer.OneTimeBuzzer;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener
{
    private Handler mHandler = new Handler();
    AudioManager audioManager;
    private boolean isButtonPressed;
    //final ContinuousBuzzer buzzer = new ContinuousBuzzer();
    final PerfectTune perfectTune = new PerfectTune();
    final OneTimeBuzzer buzzer = new OneTimeBuzzer(10);
    int vol = 0;
    SoundPool soundPool;
    int sound;
    private final Runnable mButtonPressedRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            if (isButtonPressed)
            {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, vol--, AudioManager.FLAG_SHOW_UI);
            }
            else
            {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, vol++, AudioManager.FLAG_SHOW_UI);
            }

            if (vol > audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC))
                vol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            else if (vol < 0)
                vol = 0;
            //buzzer.setToneFreqInHz(freq+=30);
            mHandler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        sound = soundPool.load(getApplicationContext(), R.raw.freq4000, 1); // in 2nd param u have to pass your desire ringtone
        perfectTune.setTuneFreq(4000);
        buzzer.setDuration(10);
        //buzzer.setPausePeriodSeconds(1);

        buzzer.setVolume(50);
        buzzer.setToneFreqInHz(4000);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_SHOW_UI);

        Button button = (Button) findViewById(R.id.btn_play);
        button.setOnTouchListener(this);

        //mHandler.postDelayed(mButtonPressedRunnable, 1000);
        // buzzer.play();
        //perfectTune.playTune();
        Log.i("MAIN","CONVERTER 0.5 " + ConverterUtil.linearToDecibel(0.5f));
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
                soundPool.play(sound, 1, 1, 1, -1, 1);

                return true;
            case MotionEvent.ACTION_UP:
                v.setPressed(false);
                System.out.println(" released ");
                isButtonPressed = false;
                return true;
        }
        return false;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        buzzer.stop();

    }
}
