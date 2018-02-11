package com.ignis_apps.connect4wear.Game.GameObjects;

import android.graphics.Point;

import com.ignis_apps.connect4wear.Game.OpernGL.Objects.OpenGLViewCordHelper;
import com.ignis_apps.connect4wear.Game.OpernGL.Objects.Rectangle;
import com.ignis_apps.connect4wear.Game.OpernGL.TextureManager;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Andreas on 07.02.2018.
 */

public class Stone extends GameObjectData implements Cloneable {

    private int row;
    private int colum;
    private boolean isRed = true;
    private Rectangle obj;
    public static int stone_red,stone_yellow;


    public void postSetup(TextureManager textureManager){

        stone_red    = textureManager.getGPURefIDByTextureObjectName("stone_red");
        stone_yellow = textureManager.getGPURefIDByTextureObjectName("stone_yellow");

        obj.setTexturePointers(new int[]{stone_red,stone_yellow});

        if(isRed)
            obj.setTexture(0);
        else
            obj.setTexture(1);

    }

    public Stone(int colum){

        obj = new Rectangle(37f,37f,false,false);
        setBoardPosition(-1, colum);
    }


    public Stone(int row,int colum){

        obj = new Rectangle(37f,37f,false,false);
        obj.setTexturePointers(new int[]{stone_red,stone_yellow});
       setBoardPosition(row, colum);
    }

    public void setBoardPosition(int row, int colum){
        this.colum = colum;
        this.row = row;
        updatePosition();
    }

    public boolean isAValidPosition(int row, int colum){
        return !(colum<0||colum>= BoardConfig.board_colums);
    }

    private void updatePosition(){

        Point pos = BoardConfig.getPosition(row,colum);
        float x = pos.x;
        float y = 400f - pos.y;
        obj.setTranslationX(OpenGLViewCordHelper.getVirtualX(x));
        obj.setTranslationY(OpenGLViewCordHelper.getVirtualY(y));

    }

    public void render(GL10 gl){
        obj.render(gl);
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
        if(stone_red!=0&&stone_yellow!=0&&obj!=null){

            if(b)
                obj.setTexture(0);
            else {
                obj.setTexture(1);
            }

        }
    }

}
