package com.company;

public class Tanks {
    float x = 10,y = 10;
    int direction =1, indexTexture = 0;
    int timeChange = 0, timeShot = 0,randomDirection = 3;
    boolean enemyShot = false;

    Tanks(float x, float y , int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        indexTexture = 8 + direction;

    }

    public void setRandomDirection(int randomDirection) {
        this.randomDirection = randomDirection;
    }

    public void generateRandomMove() {
        if(timeShot++ == 70) {
            enemyShot = true;
            timeShot = 0;
        }else{
            enemyShot = false;
        }

        if (timeChange++ == 100) {
            randomDirection = (int) (Math.random() * 4);
            timeChange = 0;
        }

        moveDirection(randomDirection, (float) (0.2));
    }
    public void moveDirection (int direction, float speed) {
            if(direction == 0 || direction == 4){
                indexTexture = 9;
                y+= speed;
            }else if (direction == 1){
                indexTexture = 10;
                x+= speed;
            }else if (direction == 3){
                indexTexture = 12;
                y-= speed;
            }else if(direction == 2){
                indexTexture = 11;
                x-= speed;
            }

    }
}
