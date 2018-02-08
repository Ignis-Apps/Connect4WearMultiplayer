package com.ignis_apps.connect4wear;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ignis_apps.connect4wear.Game.Game;
import com.ignis_apps.connect4wear.Game.Hud;

/**
 * Created by Andreas on 07.02.2018.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread thread;
    private Game game;
    private Hud hud;

    private float scaleX = 1f;
    private float scaleY = 1f;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new GameThread(getHolder(), this);
        game = new Game(context);
        hud = new Hud(context);
        setFocusable(true);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        event.setLocation(event.getX()/scaleX,event.getY()/scaleY);
        game.handleInput(event);
        return true;
    }

    public void update(){
        game.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {

            canvas.save();
            canvas.scale(scaleX,scaleY);

            canvas.drawColor(Color.rgb(38,50,56));
            game.render(canvas);
            hud.render(canvas);

            System.out.println(scaleX + " HEIGHT : " + scaleY);

            canvas.restore();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        scaleX = getScreenWidth()/400f;
        scaleY = getScreenHeight()/400f;

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }

    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
