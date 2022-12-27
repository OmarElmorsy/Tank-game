package com.company;

import com.TextureReader;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class BodyGLEventListener extends Main implements GLEventListener , KeyListener {
    boolean paused = false ;
    boolean isKingDie= false;
    static AudioStream Shot_start = null;
    static AudioStream bodySound = null;
    boolean sound= true;
    JPanel p3 = new JPanel();
    @Override
    public void init(GLAutoDrawable drawable) {
        InputStream bodysound = null;
       try {
           bodysound =new FileInputStream("C:\\my_project\\artical_WibSite\\Tank-game\\Music\\soundbody.wav");
           bodySound = new AudioStream(bodysound);
           AudioPlayer.player.start(bodySound);
       }catch (Exception e){
           System.out.println(e);
       }
       for (int i = 0 ; i<numberOfPlayers ;i++){
            players.add(new Players(10+(i*70),10,1));
        }
        tanks.add(new Tanks(16, 80 , 4 ));
        tanks.add(new Tanks(75.5F, 80 , 4 ));
        GL gl = drawable.getGL();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glViewport(0, 0, 100,100);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(5, (float) canvasWidth - 5, 5, (float) canvasHeight -5, -1.0, 1.0);
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureName.length, textureIndex, 0);
        for (int i = 0 ; i < textureName.length;i++) {
            try {
                texture [i]= TextureReader.readTexture("Assets" + "//" + textureName[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textureIndex[i]);
                new GLU().gluBuild2DMipmaps(GL.GL_TEXTURE_2D, GL.GL_RGBA, texture[i].getWidth(), texture[i].getHeight(), GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, texture[i].getPixels());
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }

    }
    @Override
    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClear (GL.GL_COLOR_BUFFER_BIT);

       if (paused){
           drawTexture(gl,13,0, 0,100, 100);
       }else if (!isKingDie) {
           drawMap(gl);
           createTanks(gl, true);
           createTanks(gl, false );
           for (int i = 0; i < shotData.size() ; i++){
              // System.out.println(shotData.get(i).direction);

               if (shotData.get(i).xShot>=95||shotData.get(i).yShot>=95||shotData.get(i).xShot<=5||shotData.get(i).yShot<=5){
                shotData.remove(i);
            }else {
                drawTexture(gl,shotData.get(i).indexTexture, shotData.get(i).xShot, shotData.get(i).yShot, shotData.get(i).widthShot, shotData.get(i).heightShot);
                playerDie(shotData.get(i).xShot, shotData.get(i).yShot, shotData.get(i).direction,shotData.get(i).isEnemyShot );

                if (shotData.get(i).direction ==1){
                    playerDie(shotData.get(i).xShot, shotData.get(i).yShot, shotData.get(i).direction,shotData.get(i).isEnemyShot );
                    shotData.get(i).yShot +=.4;
                    if(!canMove(false, shotData.get(i).xShot, shotData.get(i).yShot, "Top")){
                        //  System.out.println(19-removeWallY + "   " + removeWallX);
                        if(!shotData.get(i).isEnemyShot){
                            score += 10;
                            System.out.println(score);
                        }
                        shotData.remove(i);
                        wallMap[19-removeWallY][removeWallX] = 0;

                    }

                    if ((removeWallX==9&&removeWallY==18)) {
                       // System.out.println(removeWallX + "    " + removeWallY);
                        isKingDie=true;

                    }
                }else if (shotData.get(i).direction ==3){
                    playerDie(shotData.get(i).xShot, shotData.get(i).yShot, shotData.get(i).direction,shotData.get(i).isEnemyShot);
                    shotData.get(i).xShot -=.4;

                    if(!canMove(false, shotData.get(i).xShot, shotData.get(i).yShot, "Left")){
                        if(!shotData.get(i).isEnemyShot){
                            score += 10;
                            System.out.println(score);
                        }
                        shotData.remove(i);
                        wallMap[removeWallY][removeWallX] = 0;

                    }
                    if ((removeWallX==9&&removeWallY==18)) {
                        //System.out.println(removeWallX + "    " + removeWallY);
                        isKingDie=true;

                    }

                }else if (shotData.get(i).direction ==4){
                    playerDie(shotData.get(i).xShot, shotData.get(i).yShot, shotData.get(i).direction,shotData.get(i).isEnemyShot);
                    shotData.get(i).yShot -=.4;
                    if(!canMove(false, shotData.get(i).xShot, shotData.get(i).yShot, "Down")){
                        if(!shotData.get(i).isEnemyShot){
                            score += 10;
                            System.out.println(score);
                        }
                        shotData.remove(i);
                        wallMap[removeWallY][removeWallX] = 0;

                    }
                    if ((removeWallX==9&&removeWallY==18)) {

                        isKingDie=true;

                    }
                }else if (shotData.get(i).direction == 2){
                    playerDie(shotData.get(i).xShot, shotData.get(i).yShot, shotData.get(i).direction,shotData.get(i).isEnemyShot);
                    shotData.get(i).xShot +=.4;
                    if(!canMove(false, shotData.get(i).xShot, shotData.get(i).yShot, "Right")){
                        if(!shotData.get(i).isEnemyShot){
                            score += 10;
                            System.out.println(score);
                        }
                        shotData.remove(i);
                        wallMap[removeWallY][removeWallX] = 0;

                    }
                    if (removeWallX==9&&removeWallY==18) {
                        isKingDie=true;

                    }
                }
            }


           }
       } else {
           Body_of_Game.add(p3, BorderLayout.NORTH);
           drawTexture(gl,15,0, 0,100, 100);
           AudioPlayer.player.stop(bodySound);
       }

    }
    private void createTanks(GL gl, boolean isEnemy) {
        int temp;
        if(isEnemy){
          for (int i = 0; i < tanks.size(); i++) {
              drawTexture(gl, tanks.get(i).indexTexture , tanks.get(i).x, tanks.get(i).y,tankWidth, tankHeight);
              if( tanks.get(i).x >=90 || tanks.get(i).x <6 || tanks.get(i).y >=90 || tanks.get(i).y <6||
                      !canMove(true, tanks.get(i).x, tanks.get(i).y, "Top")
                      || !canMove(true, tanks.get(i).x, tanks.get(i).y, "Left")
                      || !canMove(true, tanks.get(i).x, tanks.get(i).y, "Down")
                      || !canMove(true, tanks.get(i).x, tanks.get(i).y, "Right")

              ){
                   temp = (tanks.get(i).randomDirection+1)%4;
                   tanks.get(i).setRandomDirection((temp));
              }
              tanks.get(i).generateRandomMove();
              if(tanks.get(i).enemyShot) {
                  float x = (tanks.get(i).randomDirection == 0 || tanks.get(i).randomDirection == 3) ? tanks.get(i).x + tankWidth/2 : (tanks.get(i).randomDirection == 1) ? tanks.get(i).x+tankWidth : tanks.get(i).x;
                  float y = (tanks.get(i).randomDirection == 1 || tanks.get(i).randomDirection == 2) ? tanks.get(i).y + tankHeight/2 : (tanks.get(i).randomDirection == 0) ? tanks.get(i).y+tankHeight : tanks.get(i).y;
                //  System.out.println(tanks.get(i).randomDirection+4);
                  shotData.add(new ShotMove(x, y,tanks.get(i).randomDirection+1 ,true));
              }
          }
      }else {
            for (int i = 0 ; i < players.size() ; i++){
                drawTexture(gl, players.get(i).direction, players.get(i).x, players.get(i).y,tankWidth, tankHeight);
            }

      }

    }
    public void playerDie (float x, float y, int direction ,boolean isEnemyShot){
        if (isEnemyShot){
             if (direction==1 && (x>=players.get(0).x && x<=players.get(0).x+5) && y == players.get(0).y ){
               isKingDie = true;
           }else if (direction==2 && (y>=players.get(0).y && y<=players.get(0).y+5) && x == players.get(0).x){
               isKingDie = true ;
           }else if (direction==3 && (y>=players.get(0).y && y<=players.get(0).y+5) && x == players.get(0).x+5){
               isKingDie = true ;
           }else if (direction==4 && (x>=players.get(0).x && x<=players.get(0).x+5) && y == players.get(0).y+5){
               isKingDie =true;
           }
       }

    }
    private void drawMap(GL gl) {
            for (int i = 0; i < wallMap.length; i++ ){
                for (int j = 0; j< wallMap[i].length; j++){
                    if (wallMap[wallMap.length-i-1][j]==1){
                        drawTexture(gl,0, (float)(j*wallWidth),(float)(i*wallHeight), (float)wallWidth, (float)wallHeight);
                    }else if(wallMap[wallMap.length-i-1][j]==2){
                        drawTexture(gl,14, (float)((j*wallWidth)),(float)((i*wallHeight)), (float)wallWidth, (float)wallHeight);
                    }
                }
            }
    }

    public void drawTexture(GL gl , int index,float xLeft, float yBottom, float Width,float Height) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textureIndex[index]);	// Turn Blending On
        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
                //      10     20
        gl.glVertex3f(xLeft, yBottom, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
                    //     15            65
        gl.glVertex3f(xLeft + Width, yBottom, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
                        // 15                 70
        gl.glVertex3f(xLeft + Width, yBottom + Height, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
                    //  10           70
        gl.glVertex3f(xLeft,yBottom + Height, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();
        gl.glDisable(GL.GL_BLEND);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        double scale = 0.1;
        double max_X = 92, max_Y = 92, min_X = 4, min_Y = 4;
        if (e.getKeyCode()==KeyEvent.VK_UP){
            if (players.get(0).y+scale*2<max_Y && canMove(true, players.get(0).x, players.get(0).y,"Top")) {
                players.get(0).y += scale * 4;
            }
            players.get(0).direction = 1 ;
        }
        if (e.getKeyCode()==KeyEvent.VK_DOWN){
            if (players.get(0).y-scale*2>min_Y&&canMove(true, players.get(0).x, players.get(0).y,"Down")) {
                players.get(0).y -= scale * 4;
            }
            players.get(0).direction = 4 ;
        }
        if (e.getKeyCode()==KeyEvent.VK_LEFT){
            if (players.get(0).x-scale*2>min_X&&canMove(true, players.get(0).x, players.get(0).y,"Left")) {
                players.get(0).x -= scale * 4;
            }
            players.get(0).direction = 3 ;
        }
        if (e.getKeyCode()==KeyEvent.VK_RIGHT){
            if (players.get(0).x+scale*2<max_X&&canMove(true,players.get(0).x, players.get(0).y,"Right")) {
                players.get(0).x += scale * 4;
            }
            players.get(0).direction = 2 ;
        }
        if (e.getKeyCode()==KeyEvent.VK_SPACE){
            float x = (players.get(0).direction == 1 || players.get(0).direction == 4) ? players.get(0).x + tankWidth/2 : (players.get(0).direction == 2) ?  players.get(0).x+tankWidth :  players.get(0).x;
            float y = (players.get(0).direction == 2 || players.get(0).direction == 3) ? players.get(0).y + tankHeight/2 : (players.get(0).direction == 1) ?  players.get(0).y+tankHeight : players.get(0).y;
            shotData.add(new ShotMove(x, y,players.get(0).direction,false));
            soundBegin(false,false);
        }
        if(players.size()==2){
            if (e.getKeyCode()==KeyEvent.VK_W){
                if (players.get(1).y+scale*2<max_Y && canMove(true, players.get(1).x, players.get(1).y,"Top")) {
                    players.get(1).y += scale * 4;
                }
                players.get(1).direction = 1 ;
            }
            if (e.getKeyCode()==KeyEvent.VK_S){
                if (players.get(1).y-scale*2>min_Y&&canMove(true, players.get(1).x, players.get(1).y,"Down")) {
                    players.get(1).y -= scale * 4;
                }
                players.get(1).direction = 4 ;
            }
            if (e.getKeyCode()==KeyEvent.VK_A){
                if (players.get(1).x-scale*2>min_X&&canMove(true, players.get(1).x, players.get(1).y,"Left")) {
                    players.get(1).x -= scale * 4;
                }
                players.get(1).direction = 3 ;
            }
            if (e.getKeyCode()==KeyEvent.VK_D){
                if (players.get(1).x+scale*2<max_X&&canMove(true,players.get(1).x, players.get(1).y,"Right")) {
                    players.get(1).x += scale * 4;
                }
                players.get(1).direction = 2 ;
            }
            if (e.getKeyCode()==KeyEvent.VK_F){
                float x = (players.get(1).direction == 1 || players.get(1).direction == 4) ? players.get(1).x + tankWidth/2 : (players.get(1).direction == 2) ?  players.get(1).x+tankWidth :  players.get(1).x;
                float y = (players.get(1).direction == 2 || players.get(1).direction == 3) ? players.get(1).y + tankHeight/2 : (players.get(1).direction == 1) ?  players.get(1).y+tankHeight : players.get(1).y;

                shotData.add(new ShotMove(x, y,players.get(1).direction,false));
                soundBegin(false,false);
            }
        }


        if(e.getKeyCode()== KeyEvent.VK_P){
            paused = !paused;
            if(paused){
                AudioPlayer.player.stop(bodySound);
                sound = false;
            }else {
                AudioPlayer.player.start(bodySound);
                sound = true;

            }
        }
        if (e.getKeyCode()==KeyEvent.VK_M){
            sound = !sound;
            if(sound && !paused){
                AudioPlayer.player.start(bodySound);
            }else {
                AudioPlayer.player.stop(bodySound);

            }
        }
    }

    private boolean canMove(boolean isTank,float x, float y,String direction) {
        int row = 0, colum = 0;
        float widthShape = isTank? tankWidth : (float) (0.5) ;
        float heightShape = isTank? tankHeight : 2 ;
        if (direction=="Top"){
            row = (int)(Math.floor((y+ heightShape +0.4)/5));
            colum = (int)(Math.floor((x)/5));
            if (isTank ?wallMap[row +(19-row*2)][colum]==0 &&wallMap[row+(19-row*2)][colum+1]==0 : wallMap[row +(19-row*2)][colum]==0 ) return true;
        }else if(direction=="Right"){
                row = (int)(Math.floor((y+ heightShape)/5));
                colum= (int)(Math.floor((x+ widthShape + 0.4)/5));
                row = row +(19-row*2);
//                System.out.println( "row : " + "  col : " + colum);
                if (isTank ?wallMap[row][colum]==0 &&wallMap[row+1][colum]==0 :wallMap[row][colum]==0) return true;
        }else if (direction=="Down"){
                row = (int)(Math.floor((y-.4)/5));
                colum= (int)(Math.floor((x)/5));
                row = row +(19-row*2);
                if (isTank ?wallMap[row][colum]==0 &&wallMap[row][colum+1]==0 :wallMap[row][colum]==0) return true;
        }else if (direction=="Left"){
                row =(int)(Math.floor((y)/5));
                colum= (int)(Math.floor((x-0.4)/5));
                row = row +(19-row*2);
                if (isTank ?wallMap[row][colum]==0 &&wallMap[row-1][colum]==0 :wallMap[row][colum]==0 ) return true;
        }
            removeWallY = row;
            removeWallX = colum;
            return false;
    }

    private void soundBegin(boolean isBody,boolean isEnd) {
         InputStream Shot_sound = null;
         if(isBody){
         }else {
             try {
                 Shot_sound = new FileInputStream("C:\\my_project\\artical_WibSite\\Tank-game\\Music\\shotsound.wav");
                 Shot_start = new AudioStream(Shot_sound);
             } catch (IOException e) {
                 e.printStackTrace();
             }
             AudioPlayer.player.start(Shot_start);
         }


    }

    @Override
    public void reshape(GLAutoDrawable drawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable drawable, boolean b, boolean b1) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public BodyGLEventListener() throws FileNotFoundException {
    }
}
