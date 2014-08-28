package org.catinthedark.smartbike.app.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import org.catinthedark.smartbike.app.R;
import org.catinthedark.smartbike.app.calculator.SpeedCalculator;

public class SettingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final EditText wheelSizeEditText = (EditText) findViewById(R.id.wheelSizeEditText);
        final Switch speedUnitsSwitch = (Switch) findViewById(R.id.speedUnitsSwitch);

        SharedPreferences preferences = getSharedPreferences(MainActivity.SHARED_PREFS_NAME, 0);
        if (preferences.contains(MainActivity.WHEEL_SIZE_PREFS_KEY)) {
            CharSequence sequence = String.valueOf(preferences.getInt(MainActivity.WHEEL_SIZE_PREFS_KEY, 0));
            wheelSizeEditText.setText(sequence);
        }
        if (preferences.contains(MainActivity.SPEED_IN_MPH_PREFS_KEY)) {
            speedUnitsSwitch.setChecked(preferences.getBoolean(MainActivity.SPEED_IN_MPH_PREFS_KEY, false));
        }

        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int wheelSize = Integer.valueOf(wheelSizeEditText.getText().toString());
                boolean speenInmph = speedUnitsSwitch.isChecked();

                SharedPreferences preferences = getSharedPreferences(MainActivity.SHARED_PREFS_NAME, 0);
                SharedPreferences.Editor preferencesEditor = preferences.edit();
                preferencesEditor.putInt(MainActivity.WHEEL_SIZE_PREFS_KEY, wheelSize);
                preferencesEditor.putBoolean(MainActivity.SPEED_IN_MPH_PREFS_KEY, speenInmph);
                preferencesEditor.apply();
                finish();
            }
        });
    }
}
