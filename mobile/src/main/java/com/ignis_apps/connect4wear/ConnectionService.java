package com.ignis_apps.connect4wear;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;


public class ConnectionService extends WearableListenerService {
    public static final String LOG_TAG = "ignis_log";
    public static final String PANIC = "panic";
    public static final String JOIN = "join";
    public static final String CREATE = "create";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);

    }

    @Override
    public void onDataChanged(@NonNull DataEventBuffer dataEventBuffer) {
        Toast.makeText(this, "onDataChanged", Toast.LENGTH_SHORT);
        for (DataEvent event : dataEventBuffer) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // DataItem changed
                DataItem item = event.getDataItem();
                Log.d(LOG_TAG, "onDataChanged: TYPE_CHANGED / Path: " + item.getUri().getPath());

                if(item.getUri().getPath().equals("/launch")){
                    Intent i = new Intent(this, MainActivityPhone.class);
                    startActivity(i);
                }
            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                // DataItem deleted
                Log.d(LOG_TAG, "onDataChanged: TYPE_DELETED");
            }
        }
    }
}
