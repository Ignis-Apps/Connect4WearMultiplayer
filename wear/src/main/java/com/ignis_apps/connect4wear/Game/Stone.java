package com.ignis_apps.connect4wear.Game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import com.ignis_apps.connect4wear.R;

/**
 * Created by Andreas on 07.02.2018.
 */

public class Stone extends GameObject {

    private int row;
    private int colum;
    private boolean isRed = true;
    public static Bitmap stone_red,stone_yellow;

    public Stone(Context c, int row,int colum){

        stone_red = BitmapFactory.decodeResource(c.getResources(), R.drawable.player_red);
        stone_yellow = BitmapFactory.decodeResource(c.getResources(), R.drawable.player_yellow);
        setBoardPosition(row, colum);

    }

    public Stone(int row,int colum){
       setBoardPosition(row, colum);
    }

    public void setBoardPosition(int row, int colum){

        if(colum<0||colum>=BoardPositionHandler.board_colums){
            return;
        }

        this.colum = colum;
        this.row = row;
        updatePosition();
    }

    private void updatePosition(){

        Point pos = BoardPositionHandler.getPosition(row,colum);
        setPosition(pos.x,pos.y);

    }

    public void render(Canvas canvas){

        if(isRed)
            canvas.drawBitmap(stone_red,getX(),getY(),null);
        else
            canvas.drawBitmap(stone_yellow,getX(),getY(),null);
    }

    public int getRow() {
        return row;
    }

    public int getColum() {
        return colum;
    }

    public boolean isRed(){
        return isRed;
    }

    public void setIsRed(Boolean b){
        isRed = b;
    }

}
