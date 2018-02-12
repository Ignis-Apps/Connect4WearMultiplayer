package com.ignis_apps.connect4wear.Game.Scenes;

import android.graphics.RectF;
import android.view.MotionEvent;

import com.ignis_apps.connect4wear.Game.OpernGL.Objects.OpenGLViewCordHelper;
import com.ignis_apps.connect4wear.Game.OpernGL.Objects.Rectangle;
import com.ignis_apps.connect4wear.Game.OpernGL.OpenGLPanelCallback;
import com.ignis_apps.connect4wear.Game.OpernGL.TextureManager;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Andreas on 11.02.2018.
 */

public class Menu {

    public final int MENUE_MAIN = 0;
    public final int MENUE_MULTIPLAYER = 1;
    public final int MENUE_SINGLEPLAYER = 2;
    public final int MENUE_MULTIPLAYER_ONLINE = 3;
    int currentMenue = 0;

    OpenGLPanelCallback openGLPanelCallback;

    private MenuMain mMain;
    private MenuMultiplayer mMultiplayer;
    private MenuMultiplayerOnline mMultiplayerOnline;

    private MenuInterfaceHandler menuInterfaceHandler;

    private Rectangle menu_title;

    public Menu(OpenGLPanelCallback openGLPanelCallback){
        this.openGLPanelCallback = openGLPanelCallback;
        setup();
    }

    public void render(GL10 gl){

        switch (currentMenue){

            case MENUE_MAIN:
                mMain.render(gl);
                break;

            case MENUE_MULTIPLAYER:
                mMultiplayer.render(gl);
                break;

            case MENUE_SINGLEPLAYER:

                break;

            case MENUE_MULTIPLAYER_ONLINE:
                mMultiplayerOnline.render(gl);
                break;

        }

        menu_title.render(gl);
    }

    private void setup(){

        menuInterfaceHandler = new MenuInterfaceHandler(this);

        mMain = new MenuMain(menuInterfaceHandler);
        mMultiplayer = new MenuMultiplayer(menuInterfaceHandler);
        mMultiplayerOnline = new MenuMultiplayerOnline(menuInterfaceHandler);

        menu_title = new Rectangle(244,50,false,false);
        menu_title.setTranslationX(OpenGLViewCordHelper.getVirtualX(78));
        menu_title.setTranslationY(OpenGLViewCordHelper.getVirtualY(320));

    }

    public void postSetup(TextureManager tManager){
        mMain.postSetup(tManager);
        mMultiplayer.postSetup(tManager);
        mMultiplayerOnline.postSetup(tManager);

        menu_title.setTexturePointer(tManager.getGPURefIDByTextureObjectName("menu_title"));
        menu_title.setTexture(0);
    }

    public void setMenu(int menu){
        this.currentMenue = menu;
    }

    public void handleInput(MotionEvent e){

        switch (currentMenue){

            case MENUE_MAIN:
                mMain.handleClick(e);
                break;

            case MENUE_MULTIPLAYER:
                mMultiplayer.handleClick(e);
                break;

            case MENUE_SINGLEPLAYER:

                break;

            case MENUE_MULTIPLAYER_ONLINE:
                mMultiplayerOnline.handleClick(e);
                break;

        }

        if(e.getAction()==MotionEvent.ACTION_UP||e.getAction()==MotionEvent.ACTION_DOWN){
            openGLPanelCallback.requestNewFrame();
        }

    }

}

class MenuMain{

    private Rectangle singlepalyer_btn, multiplayer_btn, rate_app_btn;
    private RectF singleplayer_btn_hitbox, multiplayer_btn_hitbox, rate_app_btn_hitbox;
    private MenueInterface menueInterface;

    MenuMain(MenueInterface menueInterface){

        this.menueInterface = menueInterface;

        singlepalyer_btn = new Rectangle(128,128,true,false);
        singlepalyer_btn.setTranslationX(OpenGLViewCordHelper.getVirtualX(133));
        singlepalyer_btn.setTranslationY(OpenGLViewCordHelper.getVirtualY(180));

        multiplayer_btn = new Rectangle(128,128,true,false);
        multiplayer_btn.setTranslationX(OpenGLViewCordHelper.getVirtualX(266));
        multiplayer_btn.setTranslationY(OpenGLViewCordHelper.getVirtualY(180));

        rate_app_btn = new Rectangle(80,80,true,false);
        rate_app_btn.setTranslationX(OpenGLViewCordHelper.getVirtualX(200));
        rate_app_btn.setTranslationY(OpenGLViewCordHelper.getVirtualY(80));

        singleplayer_btn_hitbox = OpenGLViewCordHelper.getRealHitbox(singlepalyer_btn,true,true);
        multiplayer_btn_hitbox  = OpenGLViewCordHelper.getRealHitbox(multiplayer_btn,true,true);
        rate_app_btn_hitbox     = OpenGLViewCordHelper.getRealHitbox(rate_app_btn,true,true);

    }

    void postSetup(TextureManager tManager){

        singlepalyer_btn.setTexturePointer(tManager.getGPURefIDByTextureObjectName("ic_singleplayer"));
        multiplayer_btn.setTexturePointer(tManager.getGPURefIDByTextureObjectName("ic_multiplayer"));
        rate_app_btn.setTexturePointer(tManager.getGPURefIDByTextureObjectName("ic_rate"));

        singlepalyer_btn.setTexture(0);
        multiplayer_btn.setTexture(0);
        rate_app_btn.setTexture(0);

    }

    void render(GL10 gl){
        singlepalyer_btn.render(gl);
        multiplayer_btn.render(gl);
        rate_app_btn.render(gl);

    }

    void handleClick(MotionEvent e){
        RectF click = new RectF(e.getX()-1f,e.getY()-1f,e.getX()+1f,e.getY()+1f);
        switch (e.getAction()){

            case MotionEvent.ACTION_DOWN:

                if(RectF.intersects(click,singleplayer_btn_hitbox)){
                    singlepalyer_btn.setScaleX(0.8f);
                    singlepalyer_btn.setScaleY(0.8f);
                    return;

                }else if(RectF.intersects(click, multiplayer_btn_hitbox)){
                    multiplayer_btn.setScaleX(0.8f);
                    multiplayer_btn.setScaleY(0.8f);
                    return;
                } else if(RectF.intersects(click, rate_app_btn_hitbox)){
                    rate_app_btn.setScaleX(0.8f);
                    rate_app_btn.setScaleY(0.8f);
                    return;
                }

                break;

            case MotionEvent.ACTION_UP:

                singlepalyer_btn.setScaleX(1f);
                singlepalyer_btn.setScaleY(1f);

                multiplayer_btn.setScaleX(1f);
                multiplayer_btn.setScaleY(1f);

                rate_app_btn.setScaleX(1f);
                rate_app_btn.setScaleY(1f);

                if(RectF.intersects(click,singleplayer_btn_hitbox)){
                    menueInterface.onSingleplayerButtonClicked();

                }else if(RectF.intersects(click, multiplayer_btn_hitbox)){
                    menueInterface.onMultipalyerButtonClicked();
                }else if(RectF.intersects(click, rate_app_btn_hitbox)){
                    menueInterface.onRateApp();
                    return;
                }
                break;

        }
    }
}
class MenuMultiplayer{

    private Rectangle local_btn, online_btn,back_btn;
    private RectF local_btn_hitbox, online_btn_hitbox,back_btn_hitbox;

    private MenueInterface menueInterface;

    MenuMultiplayer(MenueInterface menueInterface){

        this.menueInterface = menueInterface;

        local_btn = new Rectangle(128,128,true,false);
        local_btn.setTranslationX(OpenGLViewCordHelper.getVirtualX(133));
        local_btn.setTranslationY(OpenGLViewCordHelper.getVirtualY(180));

        online_btn = new Rectangle(128,128,true,false);
        online_btn.setTranslationX(OpenGLViewCordHelper.getVirtualX(266));
        online_btn.setTranslationY(OpenGLViewCordHelper.getVirtualY(180));

        back_btn = new Rectangle(80,80,true,false);
        back_btn.setTranslationX(OpenGLViewCordHelper.getVirtualX(200));
        back_btn.setTranslationY(OpenGLViewCordHelper.getVirtualY(80));

        local_btn_hitbox    = OpenGLViewCordHelper.getRealHitbox(local_btn,true,true);
        online_btn_hitbox   = OpenGLViewCordHelper.getRealHitbox(online_btn,true,true);
        back_btn_hitbox     = OpenGLViewCordHelper.getRealHitbox(back_btn,true,true);

    }

    void postSetup(TextureManager tManager){

        local_btn.setTexturePointer(tManager.getGPURefIDByTextureObjectName("ic_multiplayer_local"));
        online_btn.setTexturePointer(tManager.getGPURefIDByTextureObjectName("ic_multiplayer_online"));
        back_btn.setTexturePointer(tManager.getGPURefIDByTextureObjectName("ic_back"));


        local_btn.setTexture(0);
        online_btn.setTexture(0);
        back_btn.setTexture(0);

    }

    void render(GL10 gl){
        local_btn.render(gl);
        online_btn.render(gl);
        back_btn.render(gl);

    }

    void handleClick(MotionEvent e){
        RectF click = new RectF(e.getX()-1f,e.getY()-1f,e.getX()+1f,e.getY()+1f);
        switch (e.getAction()){

            case MotionEvent.ACTION_DOWN:

                if(RectF.intersects(click, local_btn_hitbox)){
                    local_btn.setScaleX(0.8f);
                    local_btn.setScaleY(0.8f);
                    return;

                }else if(RectF.intersects(click, online_btn_hitbox)){
                    online_btn.setScaleX(0.8f);
                    online_btn.setScaleY(0.8f);
                    return;
                }else if(RectF.intersects(click, back_btn_hitbox)){
                    back_btn.setScaleX(0.8f);
                    back_btn.setScaleY(0.8f);
                    return;
                }

                break;

            case MotionEvent.ACTION_UP:

                local_btn.setScaleX(1f);
                local_btn.setScaleY(1f);

                online_btn.setScaleX(1f);
                online_btn.setScaleY(1f);

                back_btn.setScaleX(1f);
                back_btn.setScaleY(1f);

                if(RectF.intersects(click, local_btn_hitbox)){
                    menueInterface.onMultiplayerLocalSelected();
                    return;

                }else if(RectF.intersects(click, online_btn_hitbox)){
                    menueInterface.onMultipalyerOnlineSelected();
                    return;
                }else if(RectF.intersects(click, back_btn_hitbox)){
                    menueInterface.onShowMainMenu();
                    return;
                }

                break;

        }
    }

}
class MenuMultiplayerOnline{

    private Rectangle new_game_btn, current_games_btn, back_btn;
    private RectF new_game_btn_hitbox, current_game_btn_hitbox,back_btn_hitbox;

    private MenueInterface menueInterface;

    MenuMultiplayerOnline(MenueInterface menueInterface){

        this.menueInterface = menueInterface;

        new_game_btn = new Rectangle(128,128,true,false);
        new_game_btn.setTranslationX(OpenGLViewCordHelper.getVirtualX(133));
        new_game_btn.setTranslationY(OpenGLViewCordHelper.getVirtualY(180));

        current_games_btn = new Rectangle(128,128,true,false);
        current_games_btn.setTranslationX(OpenGLViewCordHelper.getVirtualX(266));
        current_games_btn.setTranslationY(OpenGLViewCordHelper.getVirtualY(180));

        back_btn = new Rectangle(80,80,true,false);
        back_btn.setTranslationX(OpenGLViewCordHelper.getVirtualX(200));
        back_btn.setTranslationY(OpenGLViewCordHelper.getVirtualY(80));

        new_game_btn_hitbox = OpenGLViewCordHelper.getRealHitbox(new_game_btn,true,true);
        current_game_btn_hitbox = OpenGLViewCordHelper.getRealHitbox(current_games_btn,true,true);
        back_btn_hitbox     = OpenGLViewCordHelper.getRealHitbox(back_btn,true,true);

    }

    void postSetup(TextureManager tManager){

        new_game_btn.setTexturePointer(tManager.getGPURefIDByTextureObjectName("multiplayer_new_game"));
        current_games_btn.setTexturePointer(tManager.getGPURefIDByTextureObjectName("multiplayer_current_games"));
        back_btn.setTexturePointer(tManager.getGPURefIDByTextureObjectName("ic_back"));

        new_game_btn.setTexture(0);
        current_games_btn.setTexture(0);
        back_btn.setTexture(0);

    }

    void render(GL10 gl){
        new_game_btn.render(gl);
        current_games_btn.render(gl);
        back_btn.render(gl);

    }

    void handleClick(MotionEvent e){
        RectF click = new RectF(e.getX()-1f,e.getY()-1f,e.getX()+1f,e.getY()+1f);
        switch (e.getAction()){

            case MotionEvent.ACTION_DOWN:

                if(RectF.intersects(click, new_game_btn_hitbox)){
                    new_game_btn.setScaleX(0.8f);
                    new_game_btn.setScaleY(0.8f);
                    return;

                }else if(RectF.intersects(click, current_game_btn_hitbox)){
                    current_games_btn.setScaleX(0.8f);
                    current_games_btn.setScaleY(0.8f);
                    return;
                }else if(RectF.intersects(click, back_btn_hitbox)){
                    back_btn.setScaleX(0.8f);
                    back_btn.setScaleY(0.8f);
                    return;
                }

                break;

            case MotionEvent.ACTION_UP:

                new_game_btn.setScaleX(1f);
                new_game_btn.setScaleY(1f);

                current_games_btn.setScaleX(1f);
                current_games_btn.setScaleY(1f);

                back_btn.setScaleX(1f);
                back_btn.setScaleY(1f);

                if(RectF.intersects(click, new_game_btn_hitbox)){
                    menueInterface.onStartNewOnlineMultiplayer();
                    return;

                }else if(RectF.intersects(click, current_game_btn_hitbox)){
                    menueInterface.onShowCurrentMatches();
                    return;
                }else if(RectF.intersects(click, back_btn_hitbox)){
                    menueInterface.onMultipalyerButtonClicked();
                    return;
                }

                break;

        }
    }


}

class MenuInterfaceHandler implements MenueInterface{

    private Menu menu;

    MenuInterfaceHandler(Menu menu){
        this.menu = menu;
    }

    MenueInterface getMenuInterface(){
        return this;
    }

    @Override
    public void onSingleplayerButtonClicked() {
        menu.openGLPanelCallback.getRenderer().startGame(Game.HUMAN_VS_AI);
    }

    @Override
    public void onMultipalyerButtonClicked() {
        menu.currentMenue = menu.MENUE_MULTIPLAYER;
    }

    @Override
    public void onAILevelSelected(int AILevel) {

    }

    @Override
    public void onMultiplayerLocalSelected() {
        menu.openGLPanelCallback.getRenderer().startGame(Game.HUMAN_VS_HUMAN_LOCAL);
    }

    @Override
    public void onMultipalyerOnlineSelected() {
        menu.currentMenue = menu.MENUE_MULTIPLAYER_ONLINE;
    }

    @Override
    public void onStartNewOnlineMultiplayer() {

    }

    @Override
    public void onShowCurrentMatches() {

    }

    @Override
    public void onShowMainMenu() {
        menu.currentMenue = menu.MENUE_MAIN;
    }

    @Override
    public void onRateApp() {

    }
}

interface MenueInterface{

    void onSingleplayerButtonClicked();
    void onMultipalyerButtonClicked();
    void onAILevelSelected(int AILevel);
    void onMultiplayerLocalSelected();
    void onMultipalyerOnlineSelected();
    void onStartNewOnlineMultiplayer();
    void onShowCurrentMatches();
    void onShowMainMenu();
    void onRateApp();

}