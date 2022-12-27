package com.company;

public class ShotMove {
    float widthShot = (float) 0.5;
    float heightShot = 2;
    float xShot = 0, yShot = 0;
    int direction = 0 , indexTexture = 0 ;
    boolean isEnemyShot = false ;




    ShotMove (float x, float y, int direction, boolean isEnemyShot) {

        this.direction = direction;
        this.xShot = (float) (x);
        this.yShot = (float) (y);
        indexTexture = direction ;
        this.isEnemyShot = isEnemyShot;
        if(direction == 2 || direction == 3) {
            float temp = widthShot;
            widthShot = heightShot;
            heightShot = temp;
        }
    }

}
