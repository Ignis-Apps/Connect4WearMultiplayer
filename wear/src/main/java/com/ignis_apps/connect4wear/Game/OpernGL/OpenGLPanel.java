package com.ignis_apps.connect4wear.Game.OpernGL;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by Andreas on 23.12.2017.
 */

public class OpenGLPanel extends GLSurfaceView implements OpenGLPanelCallback {

    private OpenGLRenderer mRenderer;

    public OpenGLPanel(Context context) {
        super(context);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mRenderer.handleInput(event);
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
}
