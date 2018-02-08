package com.ignis_apps.connect4wear.Game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Andreas on 07.02.2018.
 */

public class SimpleRectangle extends GameObject {

    public SimpleRectangle(int x,int y,int width,int height){
        setPosition(x,y);
        setDimensions(width,height);
        Paint p = new Paint();
        p.setColor(Color.RED);
        setPaint(p);
    }

    public void update(){

    }

    public void render(Canvas canvas){

        //System.out.println("RENDER RECTANGLE");
        canvas.drawRect(getHitbox(),getPaint());
    }

}
