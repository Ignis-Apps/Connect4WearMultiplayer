package com.ignis_apps.connect4wear.Game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * Created by Andreas on 07.02.2018.
 */

public class Game {

    private Board board;
    private Context context;
    private Stone stone;
    private boolean gameHasEnded = false;

    public Game(Context c){
        this.context = c;
        setup();
    }

    public void setup(){
        board = new Board(context,90,114,220,173);
        stone = new Stone(context,-1,0);
    }

    public void update(){
        board.update();
    }

    public void render(Canvas canvas){

        board.render(canvas);

        if(!gameHasEnded){
            stone.render(canvas);
        }

    }

    public void handleInput(MotionEvent event){

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:

                if(!gameHasEnded){

                    // Handle stone movement
                    if(event.getX() > board.getHitbox().right){
                        stone.setBoardPosition(stone.getRow(),stone.getColum()+1);
                    }else if(event.getX() < board.getHitbox().left){
                        stone.setBoardPosition(stone.getRow(),stone.getColum()-1);
                    }

                    // check if stone shoud be dropped
                    if(event.getX() < board.getHitbox().right && event.getX() > board.getHitbox().left && event.getY() > board.getHitbox().bottom ){

                        if(board.canDrop(stone)){

                            Stone nStone = new Stone(stone.getRow(),stone.getColum());
                            nStone.setIsRed(stone.isRed());
                            board.addStone(nStone);
                            stone.setIsRed(!stone.isRed());
                        }
                        // check if this move leads to a win
                        int win = WinAlgorithm.checkWin(board.getBoardAsArray());
                        if (win==1) {
                            Toast.makeText(context,"Spieler ROT hat gewonnen",Toast.LENGTH_SHORT).show();
                            gameHasEnded = true;
                        }else if (win==2){
                            Toast.makeText(context,"Spieler GELB hat gewonnen",Toast.LENGTH_SHORT).show();
                            gameHasEnded = true;
                        }

                        if(board.stones.size()==BoardPositionHandler.board_rows*BoardPositionHandler.board_colums){
                            Toast.makeText(context,"KEIN Spieler hat gewonnen",Toast.LENGTH_SHORT).show();
                            gameHasEnded = true;
                        }

                    }
                }

                // Check if top was clicked (restart)
                if(event.getX() < board.getHitbox().right && event.getX() > board.getHitbox().left && event.getY() < board.getHitbox().top){

                    if(gameHasEnded){
                        board.stones.clear();
                        gameHasEnded = false;
                    }

                }

                break;

            case MotionEvent.ACTION_MOVE:

                break;

            case MotionEvent.ACTION_UP:

                break;

        }

    }

}
