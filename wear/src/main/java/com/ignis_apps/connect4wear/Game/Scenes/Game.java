package com.ignis_apps.connect4wear.Game.Scenes;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.widget.Toast;

import com.ignis_apps.connect4wear.Game.AI.AI;
import com.ignis_apps.connect4wear.Game.AI.AIInterface;
import com.ignis_apps.connect4wear.Game.AI.AITask;
import com.ignis_apps.connect4wear.Game.AI.Utils;
import com.ignis_apps.connect4wear.Game.GameObjects.Board;
import com.ignis_apps.connect4wear.Game.GameObjects.BoardConfig;
import com.ignis_apps.connect4wear.Game.GameObjects.Hud;
import com.ignis_apps.connect4wear.Game.GameObjects.Stone;
import com.ignis_apps.connect4wear.Game.OpernGL.OpenGLPanelCallback;
import com.ignis_apps.connect4wear.Game.OpernGL.TextureManager;

import javax.microedition.khronos.opengles.GL10;


/**
 * Created by Andreas on 07.02.2018.
 */

public class Game implements AIInterface {

    public static final int HUMAN_VS_AI = 1;
    public static final int HUMAN_VS_HUMAN_LOCAL = 2;
    public static final int HUMAN_VS_HUMAN_ONLINE = 3;
    private int GAME_TYPE;

    private OpenGLPanelCallback openGLPanelCallback;

    private Context context;
    private Board board;
    private Stone stone;
    private Hud hud;
    private boolean gameHasEnded = false;
    private boolean lockInput = false;
    private AI ai;
    private GameInterface gameInterface;


    public Game(Context c, OpenGLPanelCallback callback){
        this.context = c;
        this.openGLPanelCallback = callback;
        setup();
    }

    public Game(Context c, OpenGLPanelCallback callback, GameInterface gi){
        this(c,callback);
        this.gameInterface = gi;
    }

    private void setup(){
        board = new Board(60,310,280,220);
        stone = new Stone(3);
        ai    = new AI(board,stone);
        hud   = new Hud();
    }

    public void postSetup(TextureManager tManager){
        board.postSetup(tManager);
        stone.postSetup(tManager);
        hud.postSetup(tManager);
    }

    public void startGame(int GAME_TYPE){

        this.GAME_TYPE = GAME_TYPE;
        board.stones.clear();
        hud.hideReplayButton();
        stone.setBoardPosition(-1,3);
        gameHasEnded = false;

    }

    public void render(GL10 gl){

        board.render(gl);
        if(!gameHasEnded){
            stone.render(gl);
        }
        hud.render(gl);

    }

    public void setLockInput(boolean b){
        lockInput = b;
    }

    public void handleInput(MotionEvent event){

        switch (event.getAction()){

            case MotionEvent.ACTION_UP:

                if(!gameHasEnded&&!lockInput){

                    // Handle stone movement
                    if(event.getX() > 300){
                        if(stone.isAValidPosition(stone.getRow(),stone.getColum()+1)){
                            stone.setBoardPosition(stone.getRow(),stone.getColum()+1);
                            if (gameInterface!=null)
                                gameInterface.onPlayerChangedStonePosition(stone.getColum());
                        }

                    }else if(event.getX() < 100){

                        if(stone.isAValidPosition(stone.getRow(),stone.getColum()-1)){
                            stone.setBoardPosition(stone.getRow(),stone.getColum()-1);
                            if (gameInterface!=null)
                                gameInterface.onPlayerChangedStonePosition(stone.getColum());
                        }

                    }

                    // check if stone shoud be dropped
                    if(event.getX() < 300&& event.getX() > 100 && event.getY() > 300 ){

                        if(board.canDrop(stone)){
                            Stone nStone = new Stone(stone.getRow(),stone.getColum());
                            nStone.setIsRed(stone.isRed());
                            board.addStone(nStone);
                            stone.setIsRed(!stone.isRed());

                            if (gameInterface!=null)
                                gameInterface.onPlayerMadeHisMove(board.getBoardAsArray());

                        }else {
                            return;
                        }
                        checkForWin();

                        if(GAME_TYPE == HUMAN_VS_AI&&!gameHasEnded){
                            doAIStuff();
                        }

                    }
                }

                // Check if top was clicked (restart)
                if(event.getX() < 300 && event.getX() > 100 && event.getY() < 300 ){

                    if(gameHasEnded){
                        board.stones.clear();
                        gameHasEnded = false;
                        hud.hideReplayButton();
                    }

                }

                openGLPanelCallback.requestNewFrame();

                break;

            case MotionEvent.ACTION_MOVE:

                break;

            case MotionEvent.ACTION_DOWN:

                break;

        }

    }

    private void checkForWin(){

        // check if this move leads to a win
        int win = Utils.checkWin(board.getBoardAsArray());
        if (win==1) {

            Activity main = (Activity) context;
            main.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context,"Spieler ROT hat gewonnen",Toast.LENGTH_SHORT).show();
                }
            });

            gameHasEnded = true;
        }else if (win==2){

            Activity main = (Activity) context;
            main.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context,"Spieler GELB hat gewonnen",Toast.LENGTH_SHORT).show();
                }
            });
            gameHasEnded = true;
        }

        if(board.stones.size()== BoardConfig.board_rows* BoardConfig.board_colums){

            Activity main = (Activity) context;
            main.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context,"KEIN Spieler hat gewonnen",Toast.LENGTH_SHORT).show();
                }
            });

            gameHasEnded = true;
        }


        if(gameHasEnded){
            hud.showReplayButton();
            if(gameInterface!=null)
            gameInterface.onGameCompleted(win);

        }

    }

    public Board getBoard(){
        return board;
    }

    private void doAIStuff(){
        lockInput = true;
        AITask aiTask = new AITask(ai,this);
        aiTask.execute();
    }

    @Override
    public void AIFinishedProcessing(int colum_to_play) {

        checkForWin();
        openGLPanelCallback.requestNewFrame();
        lockInput = false;

    }
}
