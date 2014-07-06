package org.catinthedark.smartbike.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import org.catinthedark.smartbike.app.activity.MainActivity;

public class MediaButtonReceiver extends BroadcastReceiver {
    public MediaButtonReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BR", "Received");
        context.sendBroadcast(new Intent(MainActivity.CYCLE_RECEIVER_ACTION));
    }
}
