package com.ignis_apps.connect4wear.Game.OpernGL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Andreas on 23.12.2017.
 */

public class TextureManager {

    private Context c;
    private List<TextureObject> textures = new ArrayList<>();

    public TextureManager(Context c){
        this.c=c;
    }

    public TextureObject makeTextureObject(String texturname, int res, GL10 gl){

        Bitmap image = BitmapFactory.decodeResource(c.getResources(),res);
        TextureObject t = new TextureObject(texturname,image,gl);
        image.recycle();

        textures.add(t);
        return t;

    }

    public void makeTextureObjctF(String name, int res, GL10 gl){
        TextureObject t = makeTextureObject(name, res, gl);

    }

    public TextureObject getTextureObjectByName(String name){

        for (TextureObject t:textures){
            if(t.getName().equals(name)){
                return t;
            }

        }
        System.out.println("ERROR : Coudn't find " + name);
        return null;
    }

    public int getGPURefIDByTextureObjectName(String name){

        TextureObject t = getTextureObjectByName(name);
        return t.getGPURefID();


    }

    public int[] getGPURefIDByTextureObjectNames(String[] names){

        int[] out = new int[names.length];
        for (int i = 0; i<names.length;i++){
            out[i]=getGPURefIDByTextureObjectName(names[i]);
        }
        return out;
    }

    public int[] getAllGPURefIDs(){
        int[] out = new int[textures.size()];
        for (int i = 0; i<textures.size();i++){
            out[i]=textures.get(i).getGPURefID();
        }
        return out;
    }

}

class TextureObject{

    private int gpu_ref_id;
    private String name;

    public TextureObject(String name, Bitmap image, GL10 gl){

        this.name = name;
        int[] textures = new int[1];

        gl.glGenTextures(1, textures,0);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, image, 0);
        gpu_ref_id = textures[0];
        System.out.println("Texture ( " + name + " ) is now available under GPU_TEXTZRE_ID : " + getGPURefID());

    }

    public void changeImageOfTexture(Bitmap image, GL10 gl){

        gl.glBindTexture(GL10.GL_TEXTURE_2D,gpu_ref_id);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        //GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, image, 0);
        GLUtils.texSubImage2D(GL10.GL_TEXTURE_2D,0,0,0,image);

    }

  

    public int getGPURefID(){
        return gpu_ref_id;
    }

    public String getName(){
        return name;
    }

}
