package com.ignis_apps.connect4wear.Game.OpernGL.Objects;

import android.graphics.RectF;

/**
 * Created by Andreas on 10.02.2018.
 */

public class OpenGLViewCordHelper {

    public static float vDisplayHeight = 400f;
    public static float vDisplayWidth = 400f;

    public static float getVirtualX(float X){
        X-=vDisplayWidth/2f;
        return ( X/(vDisplayWidth/2f));
    }

    public static float getVirtualY(float Y){
        Y-=vDisplayHeight/2f;
        return (Y/(vDisplayHeight/2f));
    }

    public static float getRealX(float virtualX){
        virtualX*=(vDisplayHeight/2f);
        virtualX+=vDisplayHeight/2f;
        return virtualX;
    }

    public static float getRealY(float virtualY){
        virtualY*=(vDisplayWidth/2f);
        virtualY+=vDisplayWidth/2f;
        return virtualY;
    }

    public static RectF getRealHitbox(Rectangle src, boolean yOnTop,boolean originOnCenter){

        float translationX = OpenGLViewCordHelper.getRealX(src.getTranslationX());
        float translationY = OpenGLViewCordHelper.getRealY(src.getTranslationY());
        float width        = src.getWidth();
        float height       = src.getHeight();

        if(yOnTop){
            translationY = 400f-translationY;
        }

        if(originOnCenter){
            return new RectF(translationX-(width/2f),translationY-(height/2f),translationX+(width/2f),translationY+(height/2f));
        }

        return new RectF(translationX,translationY,translationX+width,translationY+height);

    }

}
