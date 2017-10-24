package com.thanosfisherman.freqgen.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    final ContinuousBuzzer buzzer = new ContinuousBuzzer();
    private int freq = 440;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        buzzer.setVolume(100);
        buzzer.setPausePeriodSeconds(1);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.btn_play);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                buzzer.setToneFreqInHz(freq);
                freq += 50;
            }

        });
        buzzer.play();
    }
}
