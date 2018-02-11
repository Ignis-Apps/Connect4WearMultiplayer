package com.ignis_apps.connect4wear.Game.AI;



import com.ignis_apps.connect4wear.Game.GameObjects.Board;
import com.ignis_apps.connect4wear.Game.GameObjects.Stone;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by Andreas on 09.02.2018.
 */

public class AI {

    public static int MAX_DEPTH = 2;
    public static boolean OFFENSIVE = true;

    private Board board;
    private Stone stone;

    public AI(Board board,Stone stone){
        this.board = board;
        this.stone = stone;
    }

    public void makeTurn(int col){

        Stone nStone = new Stone(stone.getRow(),col);
        nStone.setIsRed(stone.isRed());
        board.addStone(nStone);
        stone.setIsRed(!stone.isRed());

    }

    public int getNextTurn(){
        return MiniMax(board,OFFENSIVE).getCol();
    }

    public Move MiniMax(Board board,boolean offensive) {
        if (offensive) {
            return maximalWinMove(board, 0);
        } else {
            return minimalLostMove(board, 0);
        }
    }

    public Move minimalLostMove(Board board,int depth){
        Random r = new Random();
        if(this.board.isFull()||depth == MAX_DEPTH){
            return new Move(board.getLastMove().getRow(),board.getLastMove().getCol(),board.evaluateBoard(1));
        }
        ArrayList<Board> possibleBoards = new ArrayList<>(board.getChilds(1));
        Move minimalMove = new Move(Integer.MAX_VALUE);
        for (Board b : possibleBoards) {
            Move move = maximalWinMove(b, depth + 1);
            if(move.getValue() <= minimalMove.getValue()) {
                if ((move.getValue() == minimalMove.getValue())) {
                    if (r.nextInt(2) == 0) {
                        minimalMove.setRow(b.getLastMove().getRow());
                        minimalMove.setCollum(b.getLastMove().getCol());
                        minimalMove.setValue(move.getValue());
                    }
                }
                else {
                    minimalMove.setRow(b.getLastMove().getRow());
                    minimalMove.setCollum(b.getLastMove().getCol());
                    minimalMove.setValue(move.getValue());
                }
            }
        }
        return minimalMove;

    }

    public Move maximalWinMove(Board board,int depth){
        Random r = new Random();
        if(this.board.isFull()||depth == MAX_DEPTH){
            return new Move(board.getLastMove().getRow(),board.getLastMove().getCol(),board.evaluateBoard(2));
        }
        ArrayList<Board> possibleBoards = new ArrayList<>(board.getChilds(2));
        Move maximalMove = new Move(Integer.MIN_VALUE);
        for (Board child : possibleBoards) {
            Move move = minimalLostMove(child, depth + 1);
            if(move.getValue() >= maximalMove.getValue()) {
                if ((move.getValue() == maximalMove.getValue())) {
                    if (r.nextInt(2) == 0) {
                        maximalMove.setRow(child.getLastMove().getRow());
                        maximalMove.setCollum(child.getLastMove().getCol());
                        maximalMove.setValue(move.getValue());
                    }
                }
                else {
                    maximalMove.setRow(child.getLastMove().getRow());
                    maximalMove.setCollum(child.getLastMove().getCol());
                    maximalMove.setValue(move.getValue());
                }
            }
        }
        return maximalMove;
    }


}
