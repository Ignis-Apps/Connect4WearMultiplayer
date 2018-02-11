package com.ignis_apps.connect4wear.Game.OpernGL.Objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Andreas on 23.12.2017.
 */

public class ObjectUtilities {

    public static int loadTexture(int resID, Context c, GL10 gl){

        Bitmap image = BitmapFactory.decodeResource(c.getResources(),resID);

        int[] textures = new int[1];

        gl.glGenTextures(1, textures,0);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, image, 0);

        System.out.println(resID+" is now aviable under :" + textures[0]);

        image.recycle();
        return textures[0];

    }

}
