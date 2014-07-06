package org.catinthedark.smartbike.app.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import org.catinthedark.smartbike.app.R;
import org.catinthedark.smartbike.app.calculator.WheelCalculator;

public class SettingsActivity extends ActionBarActivity {

    private final String RIM_DIAMETER_KEY = "rim_diameter";
    private final String TIRE_THICKNESS_INDEX_KEY = "tire_thickness";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences preferences = getSharedPreferences(MainActivity.SHARED_PREFS_NAME, 0);

        final NumberPicker diameterNumberPicker = (NumberPicker) findViewById(R.id.diameterNumberPicker);
        diameterNumberPicker.setMaxValue(WheelCalculator.RIM_DIAMETER_MAX);
        diameterNumberPicker.setMinValue(WheelCalculator.RIM_DIAMETER_MIN);
        diameterNumberPicker.setValue(preferences.getInt(RIM_DIAMETER_KEY, WheelCalculator.RIM_DIAMETER_MIN));

        final NumberPicker thicknessNumberPicker = (NumberPicker) findViewById(R.id.thicknessNumberPicker);
        thicknessNumberPicker.setDisplayedValues(WheelCalculator.TIRE_THICKNESS_LIST);
        thicknessNumberPicker.setMaxValue(WheelCalculator.TIRE_THICKNESS_LIST.length - 1);
        thicknessNumberPicker.setMinValue(0);
        thicknessNumberPicker.setValue(preferences.getInt(TIRE_THICKNESS_INDEX_KEY, 0));

        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float tireThickness = Float.valueOf(WheelCalculator.TIRE_THICKNESS_LIST[thicknessNumberPicker.getValue()]);
                int wheelSize = WheelCalculator.calculateWheelLength(diameterNumberPicker.getValue(), tireThickness);

                SharedPreferences preferences = getSharedPreferences(MainActivity.SHARED_PREFS_NAME, 0);
                SharedPreferences.Editor preferencesEditor = preferences.edit();
                preferencesEditor.putInt(MainActivity.WHEEL_SIZE_PREFS_KEY, wheelSize);
                preferencesEditor.putInt(RIM_DIAMETER_KEY, diameterNumberPicker.getValue());
                preferencesEditor.putInt(TIRE_THICKNESS_INDEX_KEY, thicknessNumberPicker.getValue());
                preferencesEditor.apply();

                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
