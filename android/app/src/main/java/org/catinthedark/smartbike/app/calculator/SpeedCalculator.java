package org.catinthedark.smartbike.app.calculator;

/**
 * Created by kirill on 06.07.14.
 */
public class SpeedCalculator {
    public static final int RIM_DIAMETER_MIN = 14;  // inches
    public static final int RIM_DIAMETER_MAX = 30;

    public static final String[] TIRE_THICKNESS_LIST = {
            "1.9", "2.0", "2.1", "2.2", "2.3", "3.0", "4.0"
    };

    private static final float mmInInch = 25.6F;

    public static int calculateWheelLength(int diameter, float thick) {
        return (int) (Math.PI * ((diameter + thick * 2) * mmInInch));
    }

    public static float calculateSpeed(long lastTimestamp, long currentTimestamp, int wheelSize, boolean mph) {
        if (currentTimestamp < lastTimestamp) {
            throw new IllegalArgumentException("Current timestamp can't be less then last timestamp");
        }
        float speedInMps = 0.0f;
        if (lastTimestamp != 0) {
            speedInMps = (float) wheelSize / ((float) (currentTimestamp - lastTimestamp));
        }
        if (mph) {
            return speedInMps * 10f;
            //TODO: determine correct value of miles per hour
        } else {
            return speedInMps * 3.6f;
        }
    }
}
