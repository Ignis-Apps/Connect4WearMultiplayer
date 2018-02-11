package com.ignis_apps.connect4wear.Game.GameObjects;

import com.ignis_apps.connect4wear.Game.GameObjects.GameObjectData;

/**
 * Created by Andreas on 07.02.2018.
 */

public class GameObject extends GameObjectData {

    public GameObject(int x, int y, int width, int height){
        setPosition(x,y);
        setDimensions(width,height);
    }


}
