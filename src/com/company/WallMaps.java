package com.company;

public class WallMaps {
    int [][] hardMap = {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,1,1,0,0,1,1,1,0,0,1,0,0,1,0,1,0,1,0},
            {0,0,1,0,0,0,1,0,1,0,0,1,1,0,1,0,1,1,0,0},
            {0,0,1,0,0,1,1,1,1,1,0,1,0,1,1,0,1,1,0,0},
            {0,0,1,0,0,1,0,0,0,1,0,1,0,0,1,0,1,0,1,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,1,1,1,0,1,1,1,0,1,1,1,0,1,1,1,0},
            {0,0,0,0,0,0,1,0,1,0,1,0,0,0,1,0,0,0,1,0},
            {0,0,0,0,1,1,1,0,1,0,1,0,1,1,1,0,1,1,1,0},
            {0,0,0,0,1,0,0,0,1,0,1,0,1,0,0,0,0,0,1,0},
            {0,0,0,0,1,1,1,0,1,1,1,0,1,1,1,0,1,1,1,0},
            {0,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0},
            {0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,0},
            {0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0},
            {0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0},
            {0,0,0,0,1,0,1,1,1,1,1,1,1,1,0,1,0,0,0,0},
            {0,0,0,0,1,0,1,0,0,0,0,0,0,1,0,1,0,0,0,0},
            {0,0,0,0,1,0,1,0,1,1,1,1,0,1,0,1,0,0,0,0},
            {0,0,0,0,1,0,1,0,1,0,0,1,0,1,0,1,0,0,0,0},
            {0,0,0,0,1,0,1,0,1,0,0,1,0,1,0,1,0,0,0,0},
    };
    int [][] mediumMap = {
            {0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,0,0,1,0,1,0,1,0,0,1,0,1,0,1,0,0,1,0},
            {0,1,0,0,1,0,1,0,1,1,1,1,0,1,0,1,0,0,1,0},
            {0,1,0,0,1,0,1,0,1,0,0,1,0,1,0,1,0,0,1,0},
            {0,1,0,0,1,0,1,0,0,0,0,0,0,1,0,1,0,0,1,0},
            {0,1,0,0,1,0,1,1,1,1,1,1,1,1,0,1,0,0,1,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,1,1,0,1,0,1,0,0,0,0,1,0,1,0,1,1,0,0},
            {0,0,1,1,0,1,0,0,0,1,1,0,0,0,1,0,1,1,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,0,0,1,0,1,0,0,0,0,0,0,1,0,1,0,0,1,0},
            {0,1,0,0,1,0,1,0,0,0,0,0,0,1,0,1,0,0,1,0},
            {0,1,0,0,1,0,1,0,0,0,0,0,0,1,0,1,0,0,1,0},
            {0,1,0,0,1,0,1,1,1,1,1,1,1,1,0,1,0,0,1,0},
            {0,1,0,0,1,0,1,0,0,0,0,0,0,1,0,1,0,0,1,0},
            {0,1,0,0,1,0,1,0,1,1,1,1,0,1,0,1,0,0,1,0},
            {0,1,0,0,1,0,1,0,1,0,0,1,0,1,0,1,0,0,1,0},
            {0,0,0,0,0,0,0,0,1,2,0,1,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    };
    int [][] easyMap = {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0},
            {0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0},
            {0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0},
            {0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0},
            {0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0},
            {0,0,1,0,0,1,0,0,1,1,1,1,0,0,1,0,0,1,0,0},
            {0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0},
            {0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0},
            {0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0},
            {0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,2,0,1,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    };
}
