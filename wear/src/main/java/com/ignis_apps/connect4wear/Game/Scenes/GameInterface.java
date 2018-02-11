package com.ignis_apps.connect4wear.Game.Scenes;

/**
 * Created by Andreas on 10.02.2018.
 */

public interface GameInterface {

    void onPlayerMadeHisMove(int[][] board);
    void onPlayerChangedStonePosition(int position);
    void onGameCompleted(int player_who_won);


}
