package com.ignis_apps.connect4wear.Game.OpernGL;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.ignis_apps.connect4wear.Game.Scenes.GameInterface;

/**
 * Created by Andreas on 23.12.2017.
 */

public class OpenGLPanel extends GLSurfaceView implements OpenGLPanelCallback {

    private OpenGLRenderer mRenderer;
    public GameInterface gameInterface;
    private Context c;

    public OpenGLPanel(Context context,GameInterface gi) {
        super(context);
        this.c = context;
        this.gameInterface = gi;
        setEGLConfigChooser(8 , 8, 8, 8, 16, 0);
        mRenderer = new OpenGLRenderer(context,this);
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    boolean disableNextMotionEvent = false;
    final Handler handler = new Handler();
    Runnable mLongPressed = new Runnable() {
        public void run() {
            Log.i("", "Long press!");
            vibrate(100);
            disableNextMotionEvent = true;
            mRenderer.showMainMenu();
            requestNewFrame();
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(disableNextMotionEvent){
            disableNextMotionEvent = false;
            return false;
        }

        mRenderer.handleInput(event);
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            handler.postDelayed(mLongPressed, 700);
        }else if(event.getAction()==MotionEvent.ACTION_UP){
            handler.removeCallbacks(mLongPressed);
        }

        return true;
    }

    @Override
    public void requestNewFrame() {
        requestRender();
    }

    @Override
    public OpenGLRenderer getRenderer() {
        return mRenderer;
    }

    @Override
    public void vibrate(int ms) {
        try {
            Vibrator v = (Vibrator) c.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(ms);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    @Override
    public OpenGLPanel getInstance() {
        return this;
    }


}
