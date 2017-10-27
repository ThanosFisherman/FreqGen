package com.thanosfisherman.freqgen.sample;

/**
 * Created by thanos on 10/26/17.
 */

public final class ConverterUtil
{

    public static float linearToDecibel(float linear)
    {
       /* if (linear == 0)
            return -144.0f;
        else
            return (float) (20.0f * Math.log10(linear));*/

        return linear == 0 ? -144.0f : (float) (20.0f * Math.log10(linear));
    }

    public static float decibelToLinear(float dB)
    {
        return (float) Math.pow(10.0f, dB / 20.0f);
    }
}
