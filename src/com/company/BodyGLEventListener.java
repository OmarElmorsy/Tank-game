package com.company;

import com.TextureReader;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class BodyGLEventListener implements GLEventListener , KeyListener {

    private GLCanvas gcl;
    private final int canvasWidth = 100, canvasHeight = 100;
    private final int wallWidth = 5, wallHeight = 5;
    private final  int tankWidth = 5, tankHeight = 5;
    float poxTanks = 10, poyTanks = 10;
    int directionTank = 1;
    int removeWallX, removeWallY , level = 0;
    ArrayList<Tanks> tanks = new ArrayList<>();
    String textureName[]= {"Stone.png", "Tank5T.png", "Tank5R.png", "Tank5L.png", "Tank5B.png", "Shot2T.png", "Shot2R.png",
                            "Shot2L.png", "Shot2B.png", "Enemy1T.png", "Enemy1R.png", "Enemy1L.png", "Enemy1B.png" };
    TextureReader.Texture texture[] = new TextureReader.Texture[textureName.length] ;
    int textureIndex[] = new int[textureName.length];
    WallMaps walls = new WallMaps();
    ArrayList <ShotMove> shotData = new ArrayList<>();

    public void setGLCanvas(GLCanvas gcl , int Level){
        this.gcl=gcl;
        this.level = level ;
    }
    @Override
    public void init(GLAutoDrawable drawable) {
        tanks.add(new Tanks(35, 80 , 4 ));
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
        drawMap(gl);
        creatTanks(gl, true);
        creatTanks(gl, false );
        for (int i = 0; i < shotData.size() ; i ++){
            drawTexture(gl,shotData.get(i).indexTexture, shotData.get(i).xShot, shotData.get(i).yShot, shotData.get(i).widthShot, shotData.get(i).heightShot);
            if (shotData.get(i).direction ==1){
                shotData.get(i).yShot +=.4;
                if(!canMove(false, shotData.get(i).xShot, shotData.get(i).yShot, "Top")){
                    shotData.remove(i);
                    walls.hardMap[19- removeWallY][removeWallX] = 0;
                }
            }else if (shotData.get(i).direction ==3){
                shotData.get(i).xShot -=.4;
                if(!canMove(false, shotData.get(i).xShot, shotData.get(i).yShot, "Left")){
                    shotData.remove(i);
                    walls.hardMap[removeWallY][removeWallX] = 0;
                }
            }else if (shotData.get(i).direction ==4){
                shotData.get(i).yShot -=.4;
                if(!canMove(false, shotData.get(i).xShot, shotData.get(i).yShot, "Down")){
                    shotData.remove(i);
                    walls.hardMap[removeWallY][removeWallX] = 0;
                }
            }else if (shotData.get(i).direction == 2){
                shotData.get(i).xShot +=.4;
                if(!canMove(false, shotData.get(i).xShot, shotData.get(i).yShot, "Right")){
                    shotData.remove(i);
                    walls.hardMap[removeWallY][removeWallX] = 0;
                }
            }
        }

    }

    private void creatTanks(GL gl, boolean isEnemy) {
      if(isEnemy){

          for (int i = 0; i < tanks.size(); i++) {
              drawTexture(gl, tanks.get(i).indexTexture , tanks.get(i).x, tanks.get(i).y,tankWidth, tankHeight);
              if(canMove(true, tanks.get(i).x, tanks.get(i).y, "Top")){
                  tanks.get(i).generateRandomMove("Top");
              }else  if (canMove(true, tanks.get(i).x, tanks.get(i).y, "Left")){
                        tanks.get(i).generateRandomMove("Left");
              }else  if (canMove(true, tanks.get(i).x, tanks.get(i).y, "Down")){
                       tanks.get(i).generateRandomMove("Down");
              }else  if (canMove(true, tanks.get(i).x, tanks.get(i).y, "Right")){
                     System.out.println("can move");
                  tanks.get(i).generateRandomMove("Right");
              }else {
                  tanks.get(i).generateRandomMove("Cant_Move");
              }


          }
      }else {
          drawTexture(gl, directionTank, poxTanks, poyTanks,tankWidth, tankHeight);
      }

    }

    private void drawMap(GL gl) {
        if (level==1){
            for (int i = 0; i < walls.easyMap.length; i++ ){
                for (int j = 0; j< walls.easyMap[i].length; j++){
                    if (walls.hardMap[walls.easyMap.length-i-1][j]==1){
                        drawTexture(gl,0, (float)(j*wallWidth),(float)(i*wallHeight), (float)wallWidth, (float)wallHeight);
                    }
                }
            }
        }else if(level==2){
            for (int i = 0; i < walls.mediumMap.length; i++ ){
                for (int j = 0; j< walls.mediumMap[i].length; j++){
                    if (walls.hardMap[walls.mediumMap.length-i-1][j]==1){
                        drawTexture(gl,0, (float)(j*wallWidth),(float)(i*wallHeight), (float)wallWidth, (float)wallHeight);
                    }
                }
            }
        }else if (level==3){
            for (int i = 0; i < walls.hardMap.length; i++ ){
                for (int j = 0; j< walls.hardMap[i].length; j++){
                    if (walls.hardMap[walls.hardMap.length-i-1][j]==1){
                        drawTexture(gl,0, (float)(j*wallWidth),(float)(i*wallHeight), (float)wallWidth, (float)wallHeight);
                    }
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
        double max_X = 95, max_Y = 95, min_X = 0, min_Y = 0;
        if (e.getKeyCode()==KeyEvent.VK_UP){
            if (poyTanks+scale*2<max_Y&&canMove(true, poxTanks, poyTanks,"Top")) {
                poyTanks += scale * 4;
            }
            directionTank = 1 ;
        }
        if (e.getKeyCode()==KeyEvent.VK_DOWN){
            if (poyTanks-scale*2>min_Y&&canMove(true, poxTanks, poyTanks,"Down")) {
                poyTanks -= scale * 4;
            }
            directionTank = 4 ;
        }
        if (e.getKeyCode()==KeyEvent.VK_LEFT){
            if (poxTanks-scale*2>min_X&&canMove(true, poxTanks, poyTanks,"Left")) {
                poxTanks -= scale * 4;
            }
            directionTank = 3 ;
        }
        if (e.getKeyCode()==KeyEvent.VK_RIGHT){
            if (poxTanks+scale*2<max_X&&canMove(true, poxTanks, poyTanks,"Right")) {
                poxTanks += scale * 4;
            }
            directionTank = 2 ;
        }
        if (e.getKeyCode()==KeyEvent.VK_SPACE){
            float x = (directionTank == 1 || directionTank == 4) ? poxTanks + tankWidth/2 : (directionTank == 2) ? poxTanks+tankWidth : poxTanks;
            float y = (directionTank == 2 || directionTank == 3) ? poyTanks + tankHeight/2 : (directionTank == 1) ? poyTanks+tankHeight : poyTanks;
            shotData.add(new ShotMove(x, y,directionTank));
            soundBegin();
        }
    
    }

    private boolean canMove(boolean isTank,float x, float y,String direction) {
        int row = 0, colum = 0;
        float widthShape = isTank? tankWidth : 1 ;
        float heightShape = isTank? tankHeight : 1 ;

        if (x > 95 || x < 5 || y > 95 || y < 5){
            //System.out.println("correct work ");
            return false;
        }
        if (direction=="Top"){
            row = (int)(Math.floor((y+ heightShape +0.4)/5));
            colum = (int)(Math.floor((x)/5));
            if (isTank ? walls.hardMap[row +(19-row*2)][colum]==0 && walls.hardMap[row+(19-row*2)][colum+1]==0 :walls.hardMap[row +(19-row*2)][colum]==0 ) return true;
        }else if(direction=="Right"){
                row = (int)(Math.floor((y+ heightShape)/5));
                colum= (int)(Math.floor((x+ widthShape + 0.4)/5));
                row = row +(19-row*2);
                System.out.println( "row : " + "  col : " + colum);
                if (isTank ? walls.hardMap[row][colum]==0 && walls.hardMap[row+1][colum]==0 : walls.hardMap[row][colum]==0) return true;
        }else if (direction=="Down"){
                row = (int)(Math.floor((y-.4)/5));
                colum= (int)(Math.floor((x)/5));
                row = row +(19-row*2);
                if (isTank ? walls.hardMap[row][colum]==0 && walls.hardMap[row][colum+1]==0 : walls.hardMap[row][colum]==0) return true;
        }else if (direction=="Left"){
                row =(int)(Math.floor((y)/5));
                colum= (int)(Math.floor((x-0.4)/5));
                row = row +(19-row*2);
                if (isTank ? walls.hardMap[row][colum]==0 && walls.hardMap[row-1][colum]==0 : walls.hardMap[row][colum]==0 ) return true;
        }

            removeWallY = row;
            removeWallX = colum;
            return false;
    }

    private void soundBegin() {
         InputStream Shot_sound;
         AudioStream Shot_start = null;
            try {
                Shot_sound = new FileInputStream("C:\\my_project\\artical_WibSite\\Tank-game\\Music\\shotsound.wav");
                Shot_start = new AudioStream(Shot_sound);
            } catch (IOException e) {
                e.printStackTrace();
            }
        AudioPlayer.player.start(Shot_start);
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
