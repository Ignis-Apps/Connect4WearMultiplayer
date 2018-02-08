package com.ignis_apps.connect4wear.Game;

import android.graphics.Point;

/**
 * Created by Andreas on 07.02.2018.
 */

public class BoardPositionHandler {

    public static int padding_left = 7;
  //  public static int padding_right = 7;
    public static int padding_top = 4;
  //  public static int padding_bot = 4;

    public static int gap_size_width = 8;
    public static int gap_size_height = 7;

    public static int stone_size = 27;

    public static int board_x = 90;
    public static int board_y = 114;
    public static int board_width = 220;
    public static int board_height = 114;

    public static int board_colums = 6;
    public static int board_rows = 5;

    /*
        -1 [ ] [ ] [ ] [ ] [ ] [ ]
            0   1   2   3   4   5
        0  [ ] [ ] [ ] [ ] [ ] [ ]
        1  [ ] [ ] [ ] [ ] [ ] [ ]
        2  [ ] [ ] [ ] [ ] [ ] [ ]
        3  [ ] [ ] [ ] [ ] [ ] [ ]
        4  [ ] [ ] [ ] [ ] [ ] [ ]

     */

    public static Point getPosition(int row,int colum){

        int x = board_x + padding_left + (colum * (stone_size+gap_size_width));
        int y = board_y + padding_top + (row * (stone_size+gap_size_height));

        return new Point(x,y);

    }

}
