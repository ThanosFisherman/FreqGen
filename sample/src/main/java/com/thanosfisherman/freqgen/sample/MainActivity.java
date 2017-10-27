package com.thanosfisherman.freqgen.sample;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener
{
    @NonNull private Handler mHandler = new Handler();
    AudioManager audioManager;
    SoundPool soundPool;
    private boolean isButtonPressed;
    int volDec = 0;
    float volF = 0;
    int soundId, streamId;
    private final Runnable mButtonPressedRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            if (isButtonPressed)
            {
                volF = decreaseVol();
                // audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volDec--, AudioManager.FLAG_SHOW_UI);
                soundPool.setVolume(streamId, volF, volF);
            }
            else
            {
                volF = increaseVol();
                soundPool.setVolume(streamId, volF, volF);
                //audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volDec++, AudioManager.FLAG_SHOW_UI);
            }


            mHandler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        createSoundPool();
        loadSounds();
        setVolume();
        Button button = findViewById(R.id.btn_play);
        button.setOnTouchListener(this);

        mHandler.postDelayed(mButtonPressedRunnable, 1000);
    }

    @Override
    public boolean onTouch(@NonNull View v, @NonNull MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                Toast.makeText(this, "New Threshold " + ConverterUtil.linearToDecibel(volF), Toast.LENGTH_SHORT).show();
                v.setPressed(true);
                isButtonPressed = true;

                return true;
            case MotionEvent.ACTION_UP:
                System.out.println(" released ");
                v.setPressed(false);
                Toast.makeText(this, "New Threshold " + ConverterUtil.linearToDecibel(volF), Toast.LENGTH_SHORT).show();
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
        soundPool.release();

    }

    private void createSoundPool()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            final AudioAttributes.Builder audioAttrsBuilder = new AudioAttributes.Builder();
            audioAttrsBuilder.setUsage(AudioAttributes.USAGE_MEDIA);
            audioAttrsBuilder.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC);
            soundPool = new SoundPool.Builder().setAudioAttributes(audioAttrsBuilder.build()).setMaxStreams(1).build();
        }
        else
        {
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }
    }

    private void loadSounds()
    {
        soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) ->
                                            {
                                                if (sampleId == soundId)
                                                    streamId = soundPool.play(sampleId, 0, 0, 0, -1, 1);

                                            });
        soundId = soundPool.load(getApplicationContext(), R.raw.freq4000, 1);
    }

    private float increaseVol()
    {
        volDec++;
        if (volDec > 100)
            volDec = 100;
        Log.i("MAIN", "VOLUME " + (float) volDec / 100.0f);
        return (float) volDec / 100.0f;
    }

    private float decreaseVol()
    {
        volDec--;
        if (volDec < 0)
            volDec = 0;
        Log.i("MAIN", "VOLUME " + (float) volDec / 100.0f);
        return (float) volDec / 100.0f;
    }

    @SuppressWarnings("ConstantConditions")
    private void setVolume()
    {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 1, AudioManager.FLAG_SHOW_UI);

    }
}
