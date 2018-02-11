package com.ignis_apps.connect4wear;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;

import com.ignis_apps.connect4wear.Game.OpernGL.OpenGLPanel;

/**
 * Created by Andreas on 08.02.2018.
 */

public class MainActivityWatch extends WearableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OpenGLPanel panel = new OpenGLPanel(this);
        setContentView(panel);

    }

}
