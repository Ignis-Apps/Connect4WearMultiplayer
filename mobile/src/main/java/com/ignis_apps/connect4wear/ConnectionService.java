package com.ignis_apps.connect4wear;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;
import com.google.firebase.crash.FirebaseCrash;


public class ConnectionService extends WearableListenerService implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, RoomUpdateListener, RealTimeMessageReceivedListener {
    public static final String LOG_TAG = "js-labs-log";
    private static final String LAUNCH_MOBILE_APP = "launch_app";
    public static final String CALLBACK_WEAR = "connected";
    public static final String PANIC = "panic";
    public static final String JOIN = "join";
    public static final String CREATE = "create";

    public GoogleSignInClient googleSignInClient;
    public GoogleApiClient googleApi;
    public Node node;

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        toastLog(LOG_TAG, "Received: " + messageEvent.getPath());

        if(messageEvent.getPath().equals(LAUNCH_MOBILE_APP)){



            googleApi = new GoogleApiClient.Builder(this)
                    .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                    .addApiIfAvailable(Wearable.API)
                    .addConnectionCallbacks(this)
                    .build();

            googleApi.connect();
        }else if(messageEvent.getPath().equals(CREATE)){

            Bundle amc = RoomConfig.createAutoMatchCriteria(1, 1, 0);

            // build the room config:
            RoomConfig.Builder roomConfigBuilder = makeBasicRoomConfigBuilder();
            roomConfigBuilder.setAutoMatchCriteria(amc);
            RoomConfig roomConfig = roomConfigBuilder.build();

            // create room:
            Games.RealTimeMultiplayer.create(googleApi, roomConfig);

        }else if(messageEvent.getPath().equals(JOIN)){


        }
    }

    private void callbackWearable() {

        if (node != null && googleApi != null && googleApi.isConnected()) {
            Wearable.MessageApi.sendMessage(
                    googleApi, node.getId(), CALLBACK_WEAR, null).setResultCallback(

                    new ResultCallback<MessageApi.SendMessageResult>() {
                        @Override
                        public void onResult(MessageApi.SendMessageResult sendMessageResult) {

                            if (!sendMessageResult.getStatus().isSuccess()) {
                                toastLog(LOG_TAG, "Failed to send message with status code: "
                                        + sendMessageResult.getStatus().getStatusCode());
                            }else {
                                toastLog(LOG_TAG, "Connected to Wearable!");
                            }
                        }
                    }
            );
        }else{
            toastLog(LOG_TAG, "Couldn't callback Wearable!");
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        toastLog(LOG_TAG, "Connected to Google API!");
        resolveNode();
    }

    @Override
    public void onConnectionSuspended(int i) {
        FirebaseCrash.report(new Exception("Google API Connection suspended: " + i));
        toastLog(LOG_TAG, "Google API Connection suspended: " + i);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        FirebaseCrash.report(new Exception(connectionResult.getErrorCode() + ": " + connectionResult.getErrorMessage()));
        toastLog(LOG_TAG, connectionResult.getErrorCode() + ": " + connectionResult.getErrorMessage());
    }

    private void resolveNode() {

        Wearable.NodeApi.getConnectedNodes(googleApi).setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
            @Override
            public void onResult(NodeApi.GetConnectedNodesResult nodes) {
                if(nodes.getNodes() == null){
                    toastLog(LOG_TAG, "Couldn't resolve Node!");
                }else {
                    toastLog(LOG_TAG, "Resolved Node!");
                    node = nodes.getNodes().get(0);
                    callbackWearable();
                }
            }
        });
    }

    private void toastLog(String logTag, String log){
        Toast.makeText(this, log, Toast.LENGTH_SHORT).show();
        Log.d(logTag, log);
    }

    private RoomConfig.Builder makeBasicRoomConfigBuilder() {
        return RoomConfig.builder(this)
                .setMessageReceivedListener(this);
    }

    @Override
    public void onRoomCreated(int i, Room room) {
        toastLog(LOG_TAG, "Room created!");
    }

    @Override
    public void onJoinedRoom(int i, Room room) {
        toastLog(LOG_TAG, "Room joined: " + room.getParticipant(room.getParticipantIds().get(1)).getDisplayName());
    }

    @Override
    public void onLeftRoom(int i, String s) {
        toastLog(LOG_TAG, "Room left: " + s);
    }

    @Override
    public void onRoomConnected(int i, Room room) {
        toastLog(LOG_TAG, "Room connected!");
    }

    @Override
    public void onRealTimeMessageReceived(RealTimeMessage realTimeMessage) {

    }


    private boolean isSignedIn() {
        return GoogleSignIn.getLastSignedInAccount(this) != null;
    }

    private void signInSilently() {
        Log.d(LOG_TAG, "signInSilently()");

        /*googleSignInClient.silentSignIn().addOnCompleteListener(this,
                new OnCompleteListener<GoogleSignInAccount>() {
                    @Override
                    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                        if (task.isSuccessful()) {
                            Log.d(LOG_TAG, "signInSilently(): success");

                        } else {
                            Log.d(LOG_TAG, "signInSilently(): failure", task.getException());

                        }
                    }
                });*/
    }
}
