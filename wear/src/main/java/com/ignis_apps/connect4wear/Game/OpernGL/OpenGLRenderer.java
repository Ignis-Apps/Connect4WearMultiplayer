package com.ignis_apps.connect4wear.Game.OpernGL;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.ignis_apps.connect4wear.Game.Scenes.Game;
import com.ignis_apps.connect4wear.Game.Scenes.Menu;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Andreas on 23.12.2017.
 */

public class OpenGLRenderer implements GLSurfaceView.Renderer {

    private OpenGLWindowManager wManeger;
    private TextureManager tManager;
    private TextureLoader tLoader;

    private Game game;
    private Menu menu;

    public final int STATE_GAME = 0;
    public final int STATE_MENU = 1;
    private int CURRENT_STATE = 1;

    private OpenGLPanelCallback openGLPanelCallback;
    private Context context;
    private float scaleX,scaleY;

    public OpenGLRenderer(Context c,OpenGLPanelCallback callback){

        this.openGLPanelCallback = callback;
        wManeger = new OpenGLWindowManager();
        tManager = new TextureManager(c);
        tLoader = new TextureLoader(tManager);

        game = new Game(c,callback);
        menu = new Menu(callback);

    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {

        // Initialise window manager
        wManeger.initialiseWindow(gl);
        // load all Textures into memory
        tLoader.loadTextures(gl);
        game.postSetup(tManager);
        menu.postSetup(tManager);
        // calculate scale factor for the input
        scaleX = getScreenWidth()/400f;
        scaleY = getScreenHeight()/400f;
        System.out.println("create surface");

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        wManeger.changeWindow(gl,width,height);
        System.out.println("change window");
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        wManeger.updateWindow(gl);

        switch (CURRENT_STATE){

            case STATE_GAME:
                game.render(gl);
                break;

            case STATE_MENU:
                menu.render(gl);
                break;

        }

    }

    public void handleInput(MotionEvent e){

       e.setLocation(e.getX()/scaleX,e.getY()/scaleY);

        switch (CURRENT_STATE){

            case STATE_GAME:
                game.handleInput(e);
                break;

            case STATE_MENU:
                menu.handleInput(e);
                break;

        }

    }

    public void startGame(int GAMEMODE){
        game.startGame(GAMEMODE);
        CURRENT_STATE = STATE_GAME;
    }

    // Get Screen Size X
    public static int getScreenWidth()
    {return Resources.getSystem().getDisplayMetrics().widthPixels;}

    // Get Screen Size Y
    public static int getScreenHeight()
    {return Resources.getSystem().getDisplayMetrics().heightPixels;}
}
