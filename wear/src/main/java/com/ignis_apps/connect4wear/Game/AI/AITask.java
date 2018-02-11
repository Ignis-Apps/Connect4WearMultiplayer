package com.ignis_apps.connect4wear.Game.AI;

import android.os.AsyncTask;

/**
 * Created by Andreas on 10.02.2018.
 */

public class AITask extends AsyncTask<String,Void,Integer> {

    private AI ai;
    private boolean isRunning = false;
    private AIInterface aiInterface;

    public AITask(AI ai,AIInterface aiInterface){
        this.ai=ai;
        this.aiInterface = aiInterface;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        isRunning = true;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        isRunning = false;
        ai.makeTurn(integer);
        aiInterface.AIFinishedProcessing(integer);

    }

    @Override
    protected Integer doInBackground(String... strings) {
        int row = ai.getNextTurn();
        return row;
    }
}
