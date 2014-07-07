package org.catinthedark.smartbike.app.activity;

import android.content.*;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import org.catinthedark.smartbike.app.R;
import org.catinthedark.smartbike.app.receiver.MediaButtonReceiver;


public class MainActivity extends ActionBarActivity {

    public static final String WHEEL_SIZE_PREFS_KEY = "wheel_size";
    public static final String SHARED_PREFS_NAME = "SmartBikeSettings";
    public static final String CYCLE_RECEIVER_ACTION = "speed_received";

    private MediaButtonReceiver mediaButtonReceiver;
    private int wheelSize;
    private TextView currentSpeedTextView;

    private BroadcastReceiver wheelCycleReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            currentSpeedTextView.setText(updateSpeed());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentSpeedTextView = (TextView) findViewById(R.id.currentSpeedTextView);

        IntentFilter mediaButtonfilter = new IntentFilter();
        mediaButtonfilter.addAction("android.intent.action.ACTION_MEDIA_BUTTON");
        mediaButtonfilter.setPriority(1000);

        mediaButtonReceiver = new MediaButtonReceiver();
        registerReceiver(mediaButtonReceiver, mediaButtonfilter);
//        ((AudioManager)getSystemService(AUDIO_SERVICE)).registerMediaButtonEventReceiver(
//                new ComponentName(
//                        getPackageName(),
//                        MediaButtonReceiver.class.getName()));

        IntentFilter cycleFilter = new IntentFilter();
        cycleFilter.addAction(CYCLE_RECEIVER_ACTION);
        registerReceiver(wheelCycleReceiver, cycleFilter);

        SharedPreferences preferences = getSharedPreferences(SHARED_PREFS_NAME, 0);
        wheelSize = preferences.getInt(WHEEL_SIZE_PREFS_KEY, 0);
        if (wheelSize == 0) {
            openSettings();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent keyevent) {
        if ((keyCode == KeyEvent.KEYCODE_MEDIA_PLAY
                || keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE
                || keyCode == KeyEvent.KEYCODE_HEADSETHOOK)
                && keyevent.getAction() == KeyEvent.ACTION_DOWN) {
            sendBroadcast(new Intent(CYCLE_RECEIVER_ACTION));
            return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(wheelCycleReceiver, new IntentFilter(CYCLE_RECEIVER_ACTION));
        registerReceiver(mediaButtonReceiver, new IntentFilter("android.action.ACTION_MEDIA_BUTTON"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            openSettings();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        if (mediaButtonReceiver != null) {
            unregisterReceiver(mediaButtonReceiver);
        }
        if (wheelCycleReceiver != null) {
            unregisterReceiver(wheelCycleReceiver);
        }
        startActivity(intent);
    }

    private long timestamp = 0;
    private String updateSpeed() {
        long oldTimestamp = timestamp;
        timestamp = System.currentTimeMillis();
        long cycleInterval = timestamp - oldTimestamp;  // in ms

        if (oldTimestamp != 0) {
            float speedInMs = (float) wheelSize / (float) cycleInterval;
            float speedInKmh = speedInMs * 3.6f;
            return String.format("%.1f", speedInKmh);
        } else {
            return "0.0";
        }
    }
}
