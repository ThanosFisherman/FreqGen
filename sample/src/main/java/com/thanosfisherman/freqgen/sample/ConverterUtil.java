package com.thanosfisherman.freqgen.sample;

/**
 * Created by thanos on 10/26/17.
 */

public final class ConverterUtil
{

    public static int linearToDecibel(float linear)
    {
        float dB;

        if (linear == 0)
            dB = -144.0f;
        else
            dB = (float) (20.0f * Math.log10(linear));

        return (int) dB;
    }

    public static float decibelToLinear(float dB)
    {
        float linear = (float) Math.pow(10.0f, dB / 20.0f);

        return linear;
    }
}
