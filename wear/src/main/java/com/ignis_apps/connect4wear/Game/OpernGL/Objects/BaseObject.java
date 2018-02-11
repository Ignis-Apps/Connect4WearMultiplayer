package com.ignis_apps.connect4wear.Game.OpernGL.Objects;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Andreas on 23.12.2017.
 */

public class BaseObject extends BaseObjectProperties {

    // Contains vertices of object
    private ByteBuffer verBuf;
    private FloatBuffer vertexBuffer;

    // Contains color values for vertices
    private ByteBuffer colBuf;
    private FloatBuffer colorBuffer;

    // Contains the drawing order
    private ByteBuffer indBuf;
    private ShortBuffer indexBuffer;
    private int indexLength;

    // Contains the uv coordinates
    private ByteBuffer textBuf;
    private FloatBuffer textureBuffer;

    // Contains length of the vertices eg. 2D-Point length = 2, 3D Point length = 3 ...
    private int vert_length;

    // Contains the id's of the textures which are loaded in the GPU
    private int texturePointer[];
    private int currentTexture;

    private boolean shoudDrawColors = false;
    private boolean shoudDrawTexture = false;
    private boolean shoudUseBlending = false;


    public BaseObject(){

    }

    public BaseObject(float[]verts,short[]index,int vert_length){

        setVertices(verts,vert_length);
        setIndecies(index);

    }

    public void setVertices(float[] verts,int vert_length){

        verBuf = ByteBuffer.allocateDirect(verts.length*4);
        verBuf.order(ByteOrder.nativeOrder());

        vertexBuffer = verBuf.asFloatBuffer();
        vertexBuffer.put(verts);
        vertexBuffer.position(0);
        this.vert_length = vert_length;

    }

    public void setColor(float[] color){

        colBuf = ByteBuffer.allocateDirect(color.length*4);
        colBuf.order(ByteOrder.nativeOrder());

        colorBuffer = colBuf.asFloatBuffer();
        colorBuffer.put(color);
        colorBuffer.position(0);
        shoudDrawColors = true;

    }

    public void setIndecies(short[] index){

        indBuf = ByteBuffer.allocateDirect(index.length*2);
        indBuf.order(ByteOrder.nativeOrder());

        indexBuffer = indBuf.asShortBuffer();
        indexBuffer.put(index);
        indexBuffer.position(0);
        indexLength = index.length;

    }

    public void setTextureCords(float[] textureCords){

        textBuf = ByteBuffer.allocateDirect(textureCords.length*4);
        textBuf.order(ByteOrder.nativeOrder());

        textureBuffer = textBuf.asFloatBuffer();
        textureBuffer.put(textureCords);
        textureBuffer.position(0);
        shoudDrawTexture = true;

    }

    public void setTexturePointers(int[] textPointer){
        texturePointer = textPointer;
    }

    public void setTexturePointer(int textPointer){
        texturePointer = new int[]{textPointer};
    }

    public void setTexture(int texture){
        currentTexture = texture;
        shoudDrawTexture = true;
        shoudUseBlending = true;
    }

    public void render(GL10 gl){

        gl.glFrontFace(GL10.GL_CW);

        if(shoudDrawTexture){
            gl.glBindTexture(GL10.GL_TEXTURE_2D, texturePointer[currentTexture]);
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
        }

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        if(shoudDrawColors){
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
            gl.glColorPointer(4, GL10.GL_FLOAT,0, colorBuffer);
        }


        gl.glEnable(GL10.GL_CULL_FACE);

        if(shoudUseBlending){
            gl.glEnable(GL10.GL_BLEND);
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        }

        gl.glVertexPointer(vert_length, GL10.GL_FLOAT,0, vertexBuffer);

        gl.glPushMatrix();
        gl.glLoadIdentity();



        if(shoudUseRotate){
            gl.glRotatef(rotationX,1f,0f,0f);
            gl.glRotatef(rotationY,0f,1f,0f);
            gl.glRotatef(rotationZ,0f,0f,1f);
        }

        if(shoudUseTransform)
            gl.glTranslatef(translationX,translationY,0f);

        if(shoudUseScale)
            gl.glScalef(scaleX,scaleY,scaleZ);




        gl.glDrawElements(GL10.GL_TRIANGLES, indexLength, GL10.GL_UNSIGNED_SHORT, indexBuffer);

        gl.glPopMatrix();

        if(shoudDrawTexture){
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        }

        if(shoudDrawColors){
            gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        }

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glDisable(GL10.GL_CULL_FACE);

        if(shoudUseBlending)
        gl.glDisable(GL10.GL_BLEND);

    }


    public final float default_colors[]= {

            1f,1f,1f,1f,  // top Left
            1f,1f,1f,1f,  // bot Left
            1f,1f,1f,1f,  // bot right
            1f,1f,1f,1f,  // top right
            1f,1f,1f,1f,  // top left
            1f,1f,1f,1f   // bot right
    };

    public final float identity_vertices[] = {
            -1f,1f,
            -1f,-1f,
            1f,1f,
            1f,-1f};

    public final float default_vertices[] = {

            60f,400f,    //bot left 0
            60f,80f,      //top left 1
            260f,400f,  //bot right 2
            260f,80f};   //top right 3

    public final float default_texture[] = {

            0f,1f,   //bot left
            0f,0f,   //top left
            1f,1f,   //bot right
            1f,0f};  //top right


    public final short[] default_index = {
            2,1,3,
            1,2,0
    };

    public void showNextTexture(){

        if(currentTexture<texturePointer.length-1)
            currentTexture++;
        else
            currentTexture=0;


    }

    public String toString(){
        String output = "";
        output+= "TranslationX : " + translationX + "\n";
        output+= "TranslationY : " + translationY + "\n";
        return output;
    }



}
