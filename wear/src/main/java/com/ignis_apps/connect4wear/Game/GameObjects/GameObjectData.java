package com.ignis_apps.connect4wear.Game.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Andreas on 07.02.2018.
 */

public class GameObjectData {

    private int width;
    private int height;
    private int x;
    private int y;
    private Paint paint;
    private Bitmap image;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public void setDimensions(int width,int height){
        setHeight(height);
        setWidth(width);
    }

    public void setPosition(int x,int y){
        setX(x);
        setY(y);
    }

    public Rect getHitbox(){
        return new Rect(x,y,x+width,y+height);
    }

}
