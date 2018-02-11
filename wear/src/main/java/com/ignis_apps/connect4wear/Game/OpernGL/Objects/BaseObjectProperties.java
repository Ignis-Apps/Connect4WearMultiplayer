package com.ignis_apps.connect4wear.Game.OpernGL.Objects;

import android.graphics.RectF;

/**
 * Created by Andreas on 23.12.2017.
 */

public class BaseObjectProperties {

    protected boolean shoudUseTransform = false;
    protected boolean shoudUseScale = false;
    protected boolean shoudUseRotate = false;

    protected float translationX = 0f;
    protected float translationY = 0f;
    protected float rotationX = 0f;
    protected float rotationY = 0f;
    protected float rotationZ = 0f;
    protected float width     = 1f;
    protected float height    = 1f;

    protected float scaleX = 1f;
    protected float scaleY = 1f;
    protected float scaleZ = 1f;

    public float getTranslationX() {
        return translationX;
    }

    public void setTranslationX(float translationX) {
        shoudUseTransform = true;
        this.translationX = translationX;
    }

    public float getTranslationY() {

        return translationY;
    }

    public void setTranslationY(float translationY) {
        shoudUseTransform = true;
        this.translationY = translationY;
    }

    public void translate(float x,float y){
        shoudUseTransform = true;
        translationX+=x;
        translationY+=y;

    }

    public float getRotationZ() {
        return rotationZ;
    }

    public void setRotationZ(float rotationZ) {
        this.rotationZ = rotationZ;
        shoudUseRotate = true;
    }

    public void rotateX(float angle){
        this.rotationX+=angle;
        shoudUseRotate = true;
    }

    public void rotateY(float angle){
        this.rotationY+=angle;
        shoudUseRotate = true;
    }

    public void rotateZ(float angle){
        this.rotationZ+=angle;
        shoudUseRotate = true;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
        shoudUseScale = true;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
        shoudUseScale = true;
    }

    public void setScaleZ(float z){
        this.scaleZ = z;
        shoudUseScale = true;
    }

    public void scale(float x,float y,float z){

        this.scaleX+=x;
        this.scaleY+=y;
        this.scaleZ+=z;
        shoudUseScale = true;

    }

    public void setHeight(float h){
        this.height = h;
    }

    public void setWidth(float w){
        this.width = w;
    }

    public float getHeight(){
        return height;
    }

    public float getWidth(){
        return width;
    }

    public RectF getBaseHitbox(){
        return new RectF(0f,0f,width,height);
    }

}
