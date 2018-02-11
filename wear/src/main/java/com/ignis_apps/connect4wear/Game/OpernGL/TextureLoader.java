package com.ignis_apps.connect4wear.Game.OpernGL;

import com.ignis_apps.connect4wear.R;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Andreas on 23.12.2017.
 */

public class TextureLoader {

    private TextureManager tManager;

    public TextureLoader(TextureManager textureManager){
        tManager = textureManager;
    }

    public void loadTextures(GL10 gl){

        tManager.makeTextureObjctF("board", R.drawable.board,gl);
        tManager.makeTextureObjctF("stone_red",R.drawable.player_red,gl);
        tManager.makeTextureObjctF("stone_yellow",R.drawable.player_yellow,gl);
        tManager.makeTextureObjctF("arrow_left",R.drawable.arrow_left,gl);
        tManager.makeTextureObjctF("arrow_right",R.drawable.arrow_right,gl);
        tManager.makeTextureObjctF("ic_singleplayer",R.drawable.ic_singleplayer,gl);
        tManager.makeTextureObjctF("ic_multiplayer",R.drawable.ic_multiplayer,gl);
        tManager.makeTextureObjctF("ic_multiplayer_local",R.drawable.ic_multiplayer_local_3,gl);
        tManager.makeTextureObjctF("ic_multiplayer_online",R.drawable.ic_multiplayer_online_3,gl);
        tManager.makeTextureObjctF("ic_rate",R.drawable.ic_rate,gl);
        tManager.makeTextureObjctF("ic_back",R.drawable.ic_back,gl);
        tManager.makeTextureObjctF("ic_replay",R.drawable.ic_replay,gl);

    }

}
