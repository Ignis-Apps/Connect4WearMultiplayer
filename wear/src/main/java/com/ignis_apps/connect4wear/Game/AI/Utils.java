package com.ignis_apps.connect4wear.Game.AI;

/**
 * Created by Andreas on 07.02.2018.
 */

public class Utils {

    public static int checkWin(int[][] board) {
        final int HEIGHT = board.length;
        final int WIDTH = board[0].length;
        final int EMPTY_SLOT = 0;
        for (int r = 0; r < HEIGHT; r++) {
            for (int c = 0; c < WIDTH; c++) {
                int player = board[r][c];
                if (player == EMPTY_SLOT)
                    continue;

                if (c + 3 < WIDTH &&
                        player == board[r][c+1] &&
                        player == board[r][c+2] &&
                        player == board[r][c+3])
                    return player;
                if (r + 3 < HEIGHT) {
                    if (player == board[r+1][c] &&
                            player == board[r+2][c] &&
                            player == board[r+3][c])
                        return player;
                    if (c + 3 < WIDTH &&
                            player == board[r+1][c+1] &&
                            player == board[r+2][c+2] &&
                            player == board[r+3][c+3])
                        return player;
                    if (c - 3 >= 0 &&
                            player == board[r+1][c-1] &&
                            player == board[r+2][c-2] &&
                            player == board[r+3][c-3])
                        return player;
                }
            }
        }
        return EMPTY_SLOT;
    }

    public static int checkThreeInARow(int [][] board, int stone){

        final int HEIGHT = board.length;
        final int WIDTH = board[0].length;
        final int EMPTY_SLOT = 0;
        int streak = 0;
        for (int r = 0; r < HEIGHT; r++) {
            for (int c = 0; c < WIDTH; c++) {
                int player = board[r][c];
                if (player == EMPTY_SLOT||player!=stone)
                    continue;

                if (c + 2 < WIDTH &&
                        player == board[r][c+1] &&
                        player == board[r][c+2] )
                    streak++;
                if (r + 2 < HEIGHT) {
                    if (player == board[r+1][c] &&
                            player == board[r+2][c] )
                        streak++;
                    if (c + 2 < WIDTH &&
                            player == board[r+1][c+1] &&
                            player == board[r+2][c+2] )
                        streak++;
                    if (c - 2 >= 0 &&
                            player == board[r+1][c-1] &&
                            player == board[r+2][c-2])
                        streak++;
                }
            }
        }
        return streak;

    }

    public static int checkTwoInARow(int[][] board, int stone){

        final int HEIGHT = board.length;
        final int WIDTH = board[0].length;
        final int EMPTY_SLOT = 0;
        int streak = 0;
        for (int r = 0; r < HEIGHT; r++) {
            for (int c = 0; c < WIDTH; c++) {
                int player = board[r][c];
                if (player == EMPTY_SLOT||player!=stone)
                    continue;
                if (c + 1 < WIDTH && player == board[r][c+1] )
                    streak++;
                if (r + 1 < HEIGHT) {if (player == board[r+1][c] )
                        streak++;
                if (c + 1 < WIDTH && player == board[r+1][c+1] )
                        streak++;
                if (c - 1 >= 0 && player == board[r+1][c-1])
                        streak++;
                }
            }
        }
        return streak;
    }


    public static int evaluateBoard(int[][] board,int player){

        int twoInARowY,twoInARowR,threeInARowY,threeInARowR,scoreRed,scoreYellow,win;
        win = Utils.checkWin(board);
        if(win==0){
            twoInARowR = Utils.checkTwoInARow(board,1);
            twoInARowY = Utils.checkTwoInARow(board,2);
            threeInARowR =  Utils.checkThreeInARow(board,1);
            threeInARowY = Utils.checkThreeInARow(board,2);
            scoreRed = twoInARowR + (10*threeInARowR);
            scoreYellow = twoInARowY + (10*threeInARowY);
            if(player==1)
                return scoreRed - scoreYellow;
            else
                return scoreYellow - scoreRed;

        }else if(win==player){
            return 1000;
        }else {
          return -1000;
        }
    }

}
