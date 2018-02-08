package com.ignis_apps.connect4wear.Game;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.Nullable;

import com.ignis_apps.connect4wear.R;

import java.util.ArrayList;

/**
 * Created by Andreas on 07.02.2018.
 */

public class Board extends GameObject{

    public ArrayList<Stone> stones;

    public Board(Context c, int x, int y,int width,int height){

        setPosition(x,y);
        setDimensions(width, height);
        setImage(BitmapFactory.decodeResource(c.getResources(), R.drawable.board));

        stones = new ArrayList<>();

    }

    public void addStone(Stone s){

        System.out.println("ADDING STONE");

        while (canDrop(s)){
            System.out.println("CHECKING STONE");
            System.out.println("st. colum = " + s.getColum() + "st.row = " + s.getRow());
            s.setBoardPosition(s.getRow()+1,s.getColum());
        }

        stones.add(s);

    }

    public boolean canDrop(Stone s){

        if(s.getRow()>=BoardPositionHandler.board_rows-1){
            return false;
        }

        if(getStone(s.getColum(),s.getRow()+1)==null){
            return true;
        }

        return false;

    }

    @Nullable
    private Stone getStone(int colum, int row){

        for(Stone st:stones){
            if(st.getColum()==colum&&st.getRow()==row){
                System.out.println("st. colum = " + st.getColum() + "st.row = " + st.getRow() + " row = " + row + "  colums = " + colum);
                return st;
            }
        }
        return null;

    }

    public void update(){

    }

    public int[][] getBoardAsArray(){

        int out[][] = new int[BoardPositionHandler.board_colums][BoardPositionHandler.board_rows];

        for(Stone s: stones){
            int p = (s.isRed()) ? 1 : 2;
            out[s.getColum()][s.getRow()] = p;
        }

        return out;
    }

    public void render(Canvas canvas){
        canvas.drawBitmap(getImage(),getX(),getY(),null);

        for(Stone st:stones){
            st.render(canvas);
        }

    }

}
