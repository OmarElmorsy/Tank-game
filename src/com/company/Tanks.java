package com.company;

public class Tanks {
    float x = 10,y = 10;
    int direction =1, indexTexture = 0;


    Tanks(float x, float y , int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        indexTexture = 8 + direction;

    }

    public void generateRandomMove(String direction) {
        if (direction=="Top"){
            y+= 0.4;
            indexTexture = 9;
        }else  if (direction == "Right"){
            x+=.4;
            indexTexture = 10;
        }else if (direction == "Left"){
            x-=.4;
            indexTexture = 11 ;
        }else  if (direction == "Down"){
            y-=.4;
            indexTexture = 12;
        }else {

        }
    }
}
