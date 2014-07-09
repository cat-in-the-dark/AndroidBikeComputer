package org.catinthedark.smartbike.app.calculator;

/**
 * Created by kirill on 06.07.14.
 */
public class SpeedCalculator {
    private static final float KMH_IN_MPS = 3.6f;
    private static final float MPH_IN_MPS = 2.2369f;

    public static float calculateSpeed(long lastTimestamp, long currentTimestamp, int wheelSize, boolean mph) {
        if (currentTimestamp < lastTimestamp) {
            throw new IllegalArgumentException("Current timestamp can't be less then last timestamp");
        }
        float speedInMps = 0.0f;
        if (lastTimestamp != 0) {
            speedInMps = (float) wheelSize / ((float) (currentTimestamp - lastTimestamp));
        }
        if (mph) {
            return speedInMps * MPH_IN_MPS;
            //TODO: determine correct value of miles per hour
        } else {
            return speedInMps * KMH_IN_MPS;
        }
    }
}
