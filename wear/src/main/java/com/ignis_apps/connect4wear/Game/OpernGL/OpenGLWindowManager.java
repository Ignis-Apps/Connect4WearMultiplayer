package com.ignis_apps.connect4wear.Game.OpernGL;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Andreas on 23.12.2017.
 */

public class OpenGLWindowManager {

    private float startTime = 0f;
    private float sStartTime = 0f;
    private int framecounter=0;

    public int SCREEN_HEIGHT,SCREEN_WIDTH;

    public OpenGLWindowManager(){

    }

    public void initialiseWindow(GL10 gl){
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glDisable(GL10.GL_DITHER);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        gl.glClearColor(0.125f,0.196f,0.219f,0f);
        gl.glClearDepthf(1f);
    }

    public void changeWindow(GL10 gl, int width, int height){
        gl.glViewport(0,0,width,height);
        SCREEN_HEIGHT = height;
        SCREEN_WIDTH = width;

        gl.glOrthof (-200f,200f,200f,-200f,-1f,1f);

    }

    public void updateWindow(GL10 gl){
        float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
        startTime = System.nanoTime();

        float sDeltaTime = (System.nanoTime() - sStartTime) / 1000000000.0f;

        if(sDeltaTime>1.0f){
            sStartTime = System.nanoTime();
            System.out.println("FPS : " + framecounter);
            framecounter=0;
        }

        framecounter++;

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();


    }

}
