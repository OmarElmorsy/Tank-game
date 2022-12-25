package com.company;

public class ShotMove {
    float widthShot = (float) 0.5;
    float heightShot = 2;
    float xShot = 0, yShot = 0;
    int direction = 0 , indexTexture = 0 ;


    ShotMove (float x, float y, int direction) {
        this.direction = direction;
        this.xShot = (float) (x);
        this.yShot = (float) (y);
        indexTexture = direction + 4;

        if(direction == 2 || direction == 3) {
            float temp = widthShot;
            widthShot = heightShot;
            heightShot = temp;
        }
    }
}
