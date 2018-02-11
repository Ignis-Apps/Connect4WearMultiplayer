package com.ignis_apps.connect4wear;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.TurnBasedMultiplayerClient;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchUpdateCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import static com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch.MATCH_STATUS_ACTIVE;
import static com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch.MATCH_TURN_STATUS_MY_TURN;
import static com.ignis_apps.connect4wear.MainActivityPhone.LOG_TAG;

/**
 * Created by janik on 09.02.2018.
 */

public class GooglePlayGamesManager {
    private static final int RC_SIGN_IN = 23212;
    private static final int RC_SELECT_PLAYERS = 9010;

    private Context context;
    private Activity activity;

    private GoogleSignInClient signInClient;
    private GoogleSignInAccount signedInAccount;

    private TurnBasedMatch testMatch;

    private TextView testTxv;

    public GooglePlayGamesManager(Context context, Activity activity){
        this.context = context;
        this.activity = activity;

        signInClient = GoogleSignIn.getClient(context, GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);

        testTxv = activity.findViewById(R.id.textView);
    }

    public void signIn() {
        signInClient.silentSignIn().addOnCompleteListener(activity,
                new OnCompleteListener<GoogleSignInAccount>() {
                    @Override
                    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                        if (task.isSuccessful()) {
                            // The signed in account is stored in the task's result.
                            signedInAccount = task.getResult();
                            Toast.makeText(context, "Signed in to Google Play Games", Toast.LENGTH_SHORT).show();
                        } else {
                            // Player will need to sign-in explicitly using via UI
                            Intent intent = signInClient.getSignInIntent();
                            activity.startActivityForResult(intent, RC_SIGN_IN);
                        }
                    }
                });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // The signed in account is stored in the result.
                signedInAccount = result.getSignInAccount();
                Toast.makeText(context, "Signed in to Google Play Games", Toast.LENGTH_SHORT).show();
            } else {
                String message = result.getStatus().getStatusMessage();
                if (message == null || message.isEmpty()) {
                    message = "Unknown Error occurred!";
                }
                new AlertDialog.Builder(activity).setMessage(message)
                        .setNeutralButton(android.R.string.ok, null).show();
            }
        }else if (requestCode == RC_SELECT_PLAYERS) {
            if (resultCode != Activity.RESULT_OK) {
                new AlertDialog.Builder(activity).setMessage("Unknown Error occurred!")
                        .setNeutralButton(android.R.string.ok, null).show();
                // Canceled or other unrecoverable error.
                return;
            }
            ArrayList<String> invitees = data.getStringArrayListExtra(Games.EXTRA_PLAYER_IDS);

            // Get automatch criteria
            Bundle autoMatchCriteria = null;
            int minAutoPlayers = data.getIntExtra(Multiplayer.EXTRA_MIN_AUTOMATCH_PLAYERS, 0);
            int maxAutoPlayers = data.getIntExtra(Multiplayer.EXTRA_MAX_AUTOMATCH_PLAYERS, 0);

            TurnBasedMatchConfig.Builder builder = TurnBasedMatchConfig.builder()
                    .addInvitedPlayers(invitees);
            if (minAutoPlayers > 0) {
                builder.setAutoMatchCriteria(
                        RoomConfig.createAutoMatchCriteria(minAutoPlayers, maxAutoPlayers, 0));
            }
            startMatch(builder.build());
        }
    }

    public void openOpponentSelection() {
        boolean allowAutoMatch = true;
        Games.getTurnBasedMultiplayerClient(context, signedInAccount)
                .getSelectOpponentsIntent(1, 1, allowAutoMatch)
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        activity.startActivityForResult(intent, RC_SELECT_PLAYERS);
                    }
                });
    }

    public void quickMatch() {
        final TurnBasedMatchConfig.Builder builder = TurnBasedMatchConfig.builder();
        builder.setAutoMatchCriteria(RoomConfig.createAutoMatchCriteria(1, 1, 0));
        startMatch(builder.build());
    }

    private void startMatch(TurnBasedMatchConfig config){
        Games.getTurnBasedMultiplayerClient(context, signedInAccount).createMatch(config).addOnCompleteListener(new OnCompleteListener<TurnBasedMatch>() {
            @Override
            public void onComplete(@NonNull Task<TurnBasedMatch> task) {
                if(task.isSuccessful()){
                    testMatch = task.getResult();

                    new AlertDialog.Builder(activity).setMessage("Success!!!!!")
                            .setNeutralButton(android.R.string.ok, null).show();

                    if(testMatch.getData() == null){
                        //first turn
                        Games.getTurnBasedMultiplayerClient(context, signedInAccount).takeTurn(testMatch.getMatchId(), "Hallo Welt".getBytes(), null).addOnCompleteListener(new OnCompleteListener<TurnBasedMatch>() {
                            @Override
                            public void onComplete(@NonNull Task<TurnBasedMatch> task) {
                            if(task.isSuccessful()){
                                TurnBasedMatch match = task.getResult();

                                Log.d(LOG_TAG, "onComplete takeFirstTurn");
                                testTxv.setText(new String(match.getData()));
                            } else {
                                showException(task);
                            }
                            }
                        });
                    }


                }else {
                    showException(task);
                }
            }
        });
    }

    public void takeTimeTurn(){
        Games.getTurnBasedMultiplayerClient(context, signedInAccount).takeTurn(testMatch.getMatchId(), Long.toString(System.currentTimeMillis()).getBytes(), null).addOnCompleteListener(new OnCompleteListener<TurnBasedMatch>() {
            @Override
            public void onComplete(@NonNull Task<TurnBasedMatch> task) {
                if(task.isSuccessful()){
                    TurnBasedMatch match = task.getResult();

                    Log.d(LOG_TAG, "onComplete takeTimeTurn");
                    testTxv.setText(new String(match.getData()));
                } else {
                    showException(task);
                }
            }
        });
    }

    /*@Override
    public void onTurnBasedMatchReceived(@NonNull TurnBasedMatch turnBasedMatch) {
        Log.d(LOG_TAG, "onTurnBasedMatchReceived");
        testTxv.setText(new String(turnBasedMatch.getData()));

        if(turnBasedMatch.getStatus() == MATCH_STATUS_ACTIVE){
            if(turnBasedMatch.getTurnStatus() == MATCH_TURN_STATUS_MY_TURN){
                //aktiviere Input und warte auf Daten
            }
        }
    }

    @Override
    public void onTurnBasedMatchRemoved(@NonNull String s) {

    }*/

    private void showException(Task task){
        int status = CommonStatusCodes.DEVELOPER_ERROR;
        Exception exception = task.getException();
        if (exception instanceof ApiException) {
            ApiException apiException = (ApiException) exception;
            status = apiException.getStatusCode();
        }

        new AlertDialog.Builder(activity).setMessage(status + ": " + exception)
                .setNeutralButton(android.R.string.ok, null).show();
    }
}
