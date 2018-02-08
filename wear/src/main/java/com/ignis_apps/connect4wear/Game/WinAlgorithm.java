package com.ignis_apps.connect4wear.Game;

/**
 * Created by Andreas on 07.02.2018.
 */

public class WinAlgorithm {

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

}
