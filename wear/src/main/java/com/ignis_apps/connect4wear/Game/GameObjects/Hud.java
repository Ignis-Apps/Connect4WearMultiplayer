package com.ignis_apps.connect4wear.Game.GameObjects;


import com.ignis_apps.connect4wear.Game.OpernGL.Objects.OpenGLViewCordHelper;
import com.ignis_apps.connect4wear.Game.OpernGL.Objects.Rectangle;
import com.ignis_apps.connect4wear.Game.OpernGL.TextureManager;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Andreas on 07.02.2018.
 */

public class Hud {

    private Rectangle leftArrow,rightArrow, repalyButton;
    private boolean showRepalyButton = false;

    public Hud(){

        leftArrow = new Rectangle(25,30,false,false);
        leftArrow.setTranslationX(OpenGLViewCordHelper.getVirtualX(25));
        leftArrow.setTranslationY(OpenGLViewCordHelper.getVirtualY(215));

        rightArrow = new Rectangle(25,30,false,false);
        rightArrow.setTranslationX(OpenGLViewCordHelper.getVirtualX(350));
        rightArrow.setTranslationY(OpenGLViewCordHelper.getVirtualY(215));

        repalyButton = new Rectangle(64,64,true,false);
        repalyButton.setTranslationX(OpenGLViewCordHelper.getVirtualX(200));
        repalyButton.setTranslationY(OpenGLViewCordHelper.getVirtualY(356));

    }

    public void postSetup(TextureManager tManager){


        leftArrow.setTexturePointer(tManager.getGPURefIDByTextureObjectName("arrow_left"));
        leftArrow.setTexture(0);

        rightArrow.setTexturePointer(tManager.getGPURefIDByTextureObjectName("arrow_right"));
        rightArrow.setTexture(0);

        repalyButton.setTexturePointer(tManager.getGPURefIDByTextureObjectName("ic_replay"));
        repalyButton.setTexture(0);

    }

    public void render(GL10 gl){
       leftArrow.render(gl);
       rightArrow.render(gl);
       if(showRepalyButton)
       repalyButton.render(gl);

    }

    public void showReplayButton(){
        showRepalyButton = true;
    }

    public void hideReplayButton(){
        showRepalyButton = false;
    }

}