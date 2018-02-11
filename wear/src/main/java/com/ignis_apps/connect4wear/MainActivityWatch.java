package com.ignis_apps.connect4wear;

        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.wearable.activity.WearableActivity;
        import android.util.Log;
        import android.widget.Toast;

        import com.google.android.gms.tasks.Task;
        import com.google.android.gms.wearable.DataClient;
        import com.google.android.gms.wearable.DataEvent;
        import com.google.android.gms.wearable.DataEventBuffer;
        import com.google.android.gms.wearable.DataItem;
        import com.google.android.gms.wearable.PutDataRequest;
        import com.google.android.gms.wearable.Wearable;
        import com.ignis_apps.connect4wear.Game.OpernGL.OpenGLPanel;

public class MainActivityWatch extends WearableActivity implements DataClient.OnDataChangedListener{

    private DataClient dataClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OpenGLPanel panel = new OpenGLPanel(this);
        setContentView(panel);

        dataClient = Wearable.getDataClient(this);
        dataClient.addListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        sendDataItem("/launch", Long.toBinaryString(System.currentTimeMillis()).getBytes());
    }

    private void sendDataItem(String path, byte[] data) {
        PutDataRequest putDataReq = PutDataRequest.create(path);
        putDataReq.setUrgent();
        putDataReq.setData(data);
        Task<DataItem> putDataTask = dataClient.putDataItem(putDataReq);
    }

    @Override
    public void onDataChanged(@NonNull DataEventBuffer dataEventBuffer) {
        for (DataEvent event : dataEventBuffer) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // DataItem changed
                DataItem item = event.getDataItem();

                Toast.makeText(this, item.getUri().getPath(), Toast.LENGTH_SHORT).show();

                Log.d("ignis_log", "TYPE_CHANGED: " + item.getUri().getPath() + " / " + new String(item.getData()));
            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                // DataItem deleted
            }
        }
    }
}
