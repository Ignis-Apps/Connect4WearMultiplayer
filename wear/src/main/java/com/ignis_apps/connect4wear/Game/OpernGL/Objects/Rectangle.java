package com.ignis_apps.connect4wear.Game.OpernGL.Objects;

/**
 * Created by Andreas on 23.12.2017.
 */

public class Rectangle extends BaseObject {

    public Rectangle(float width, float height, boolean originAtCenter,boolean useIdentityVerts) {

        setWidth(width);
        setHeight(height);

        if(useIdentityVerts){

            setVertices(identity_vertices,2);
            setIndecies(default_index);
            setScaleX(width/2f);
            setScaleY(height/2f);

        }else {

            float[] verticies;

            if (originAtCenter) {

                float halfHeight = height / 2f;
                float halfWidth = width / 2f;

                verticies = new float[]{
                        -halfHeight, halfWidth,
                        -halfHeight, -halfWidth,
                        halfWidth, halfHeight,
                        halfWidth, -halfHeight
                };
            } else {
                verticies = new float[]{
                        0f, height,
                        0f, 0f,
                        width, height,
                        width, 0f
                };
            }

            setVertices(verticies,2);
            setIndecies(default_index);
        }

        setTextureCords(default_texture);
        setColor(default_colors);

    }



}
