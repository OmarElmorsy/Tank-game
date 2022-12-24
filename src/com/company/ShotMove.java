package com.company;

public class ShotMove {
    float x = 0 ;
    float y = 0 ;
    int direction = 0 ;
    int indexTexture =0 ;
    float width = 0 ;
    float height = 0 ;
    ShotMove (float x, float y, int direction) {
        this.direction = direction;
        this.x = (float) (x+2);
        this.y = (float) (y+5);
        indexTexture =direction+4;
        if (direction==1||direction==4){
            width=(float) 0.5;
            height=2;
        }else {
            width=2;
            height=(float) 0.5;
        }

    }
}
