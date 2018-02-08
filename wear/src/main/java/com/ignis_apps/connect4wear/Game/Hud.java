package com.ignis_apps.connect4wear.Game;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.ignis_apps.connect4wear.R;

/**
 * Created by Andreas on 07.02.2018.
 */

public class Hud {

    SimpleRectangle lArrow,rArrow;

    public Hud(Context c){

        lArrow = new SimpleRectangle(25,185,25,30);
        lArrow.setImage(BitmapFactory.decodeResource(c.getResources(), R.drawable.arrow_left));

        rArrow = new SimpleRectangle(350,185,25,30);
        rArrow.setImage(BitmapFactory.decodeResource(c.getResources(), R.drawable.arrow_right));

    }

    public void update(){

    }

    public void render(Canvas canvas){

        canvas.drawBitmap(lArrow.getImage(),lArrow.getX(),lArrow.getY(),null);
        canvas.drawBitmap(rArrow.getImage(),rArrow.getX(),rArrow.getY(),null);
    }

}
