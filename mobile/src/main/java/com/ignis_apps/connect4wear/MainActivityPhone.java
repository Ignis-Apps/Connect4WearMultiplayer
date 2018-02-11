package com.ignis_apps.connect4wear;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

public class MainActivityPhone extends AppCompatActivity implements DataClient.OnDataChangedListener, View.OnClickListener {
    public static final String LOG_TAG = "ignis_log";

    private Button testBtn, startGameBtn, takeTurnBtn;

    private DataClient dataClient;

    private GooglePlayGamesManager gpgm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_phone);

        dataClient = Wearable.getDataClient(this);
        dataClient.addListener(this);
        connectedCallback();

        gpgm = new GooglePlayGamesManager(this, this);

        gpgm.signIn();

        testBtn = findViewById(R.id.button2);
        testBtn.setOnClickListener(this);
        startGameBtn = findViewById(R.id.button3);
        startGameBtn.setOnClickListener(this);
        takeTurnBtn = findViewById(R.id.button7);
        takeTurnBtn.setOnClickListener(this);
    }

    private void connectedCallback() {
        PutDataRequest putDataReq = PutDataRequest.create("/connected");
        putDataReq.setUrgent();
        putDataReq.setData(Long.toBinaryString(System.currentTimeMillis()).getBytes());
        Task<DataItem> putDataTask = dataClient.putDataItem(putDataReq);
    }

    private void disconnectedCallback() {
        PutDataRequest putDataReq = PutDataRequest.create("/disconnected");
        putDataReq.setUrgent();
        putDataReq.setData(Long.toBinaryString(System.currentTimeMillis()).getBytes());
        Task<DataItem> putDataTask = dataClient.putDataItem(putDataReq);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disconnectedCallback();
        dataClient.removeListener(this);
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

                Toast.makeText(this, "Path: " + item.getUri().getPath(), Toast.LENGTH_SHORT).show();
            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                // DataItem deleted
            }
        }
    }

    @Override
    public void onClick(View view) {
        if( view == testBtn){
            sendDataItem("/test", Long.toBinaryString(System.currentTimeMillis()).getBytes());
        }else if (view == startGameBtn){
            gpgm.openOpponentSelection();
        } else if (view == takeTurnBtn){
            gpgm.takeTimeTurn();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        gpgm.onActivityResult(requestCode, resultCode, data);
    }
}
