package org.catinthedark.smartbike.app.calculator;

/**
 * Created by kirill on 06.07.14.
 */
public class WheelCalculator {
    public static final int RIM_DIAMETER_MIN = 14;  // inches
    public static final int RIM_DIAMETER_MAX = 30;

    public static final String[] TIRE_THICKNESS_LIST = {
            "1.9", "2.0", "2.1", "2.2", "2.3", "3.0", "4.0"
    };

    private static final float mmInInch = 25.6F;

    public static int calculateWheelLength(int diameter, float thick) {
        return (int) (Math.PI * ((diameter + thick * 2) * mmInInch));
    }
}
