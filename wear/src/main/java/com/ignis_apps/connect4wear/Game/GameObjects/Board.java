package com.ignis_apps.connect4wear.Game.GameObjects;

import android.support.annotation.Nullable;

import com.ignis_apps.connect4wear.Game.AI.Move;
import com.ignis_apps.connect4wear.Game.AI.Utils;
import com.ignis_apps.connect4wear.Game.OpernGL.Objects.OpenGLViewCordHelper;
import com.ignis_apps.connect4wear.Game.OpernGL.Objects.Rectangle;
import com.ignis_apps.connect4wear.Game.OpernGL.TextureManager;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Andreas on 07.02.2018.
 */

public class Board extends GameObjectData {

    public ArrayList<Stone> stones;
    private Rectangle obj;
    //private int lastMove = 0;
    private Move lastMove;
                            // 60 , 310
    public Board(int x, int y,int width,int height){
        obj = new Rectangle(width,height,false,false);
        obj.setTranslationX(OpenGLViewCordHelper.getVirtualX(x));
        obj.setTranslationY(OpenGLViewCordHelper.getVirtualY(y));
        stones = new ArrayList<>();
    }

    public void postSetup(TextureManager tManager){
        obj.setTexturePointer(tManager.getGPURefIDByTextureObjectName("board"));
        obj.setTexture(0);
    }

    public Board(ArrayList<Stone> stones){
        setStones(stones);
    }

    public void addStone(Stone s){
        while (canDrop(s)){
            s.setBoardPosition(s.getRow()+1,s.getColum());
        }
        if(s.getRow()>=0){
            lastMove = new Move(s.getRow(),s.getColum());
            stones.add(s);
        }
    }

    public boolean canDrop(Stone s){
        if(s.getRow()>= BoardConfig.board_rows-1){
            return false;
        }
        return getStone(s.getColum(), s.getRow() + 1) == null;
    }

    public boolean isFull(){
        return stones.size()== BoardConfig.board_rows* BoardConfig.board_colums;
    }

    public void reset(){

    }

    @Nullable
    private Stone getStone(int colum, int row){
        for(Stone st:stones){
            if(st.getColum()==colum&&st.getRow()==row){
                return st;
            }
        }
        return null;
    }

    public void update(){

    }

    public void render(GL10 gl){

        obj.render(gl);

        try{
            for(Stone s:stones){
                s.render(gl);
            }
        }catch (ConcurrentModificationException e){
            e.printStackTrace();
        }


    }


    public ArrayList<Board> getChilds(int player){
        ArrayList<Board> boards = new ArrayList<>();
        for(int i = 0; i<= BoardConfig.board_rows; i++){
            Stone s = new Stone(-1,i);
            s.setIsRed(player==1);
            if(canDrop(s)){
                Board child = new Board(cloneStoneArrayList(this.stones));
                child.addStone(s);
                boards.add(child);
            }
        }
        return boards;
    }

    public int evaluateBoard(int player){

        int twoInARowY,twoInARowR,threeInARowY,threeInARowR,scoreRed,scoreYellow,win;
        int[][]board = getBoardAsArray();
        win = Utils.checkWin(board);

            twoInARowR = Utils.checkTwoInARow(board,1);
            twoInARowY = Utils.checkTwoInARow(board,2);
            threeInARowR =  Utils.checkThreeInARow(board,1);
            threeInARowY = Utils.checkThreeInARow(board,2);
            scoreRed = twoInARowR + (10*threeInARowR);
            scoreYellow = twoInARowY + (10*threeInARowY);

        if(win == 2){
            scoreYellow += 100;
        }else if(win==1){
            scoreRed +=100;
        }
            if(player==1)
                return scoreRed - scoreYellow;
            else
                return scoreYellow - scoreRed;


    }

    public int[][] getBoardAsArray(){
        int out[][] = new int[BoardConfig.board_colums][BoardConfig.board_rows];

        System.out.println("STONE SIZE" + stones.size());

        for(Stone s: stones){
            int p = (s.isRed()) ? 1 : 2;
            System.out.println(s.getColum()+" " + s.getRow());

            out[s.getColum()][s.getRow()] = p;
        }
        return out;
    }

    public void setBoard(int[][] board){
        stones.clear();
        int cIndex = 0;
        for(int[] colum:board){
            int rIndex = 0;
            for(int row:colum){
                if(row ==1){
                    Stone s = new Stone(rIndex,cIndex);
                    s.setIsRed(true);
                    stones.add(s);
                }else if(row == 2){
                    Stone s = new Stone(rIndex,cIndex);
                    s.setIsRed(false);
                    stones.add(s);
                }
                rIndex++;
            }
            cIndex++;
        }
    }




    public void setStones(ArrayList<Stone> stones){
        this.stones = stones;
    }

    public static ArrayList<Stone> cloneStoneArrayList(ArrayList<Stone> src) {
        return (ArrayList<Stone>) src.clone();
    }

    public Move getLastMove(){
        return lastMove;
    }

}
