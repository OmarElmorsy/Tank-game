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
    private final  int tankWidth = 5, tankHeight = 7;
    private float poxTanks = 10, poyTanks = 10;
    int directionTank = 1;
    // Assets Texture
    String textureName[]= {"Stone.png", "Tank5T.png", "Tank5R.png", "Tank5L.png", "Tank5B.png", "Shot2T.png", "Shot2R.png", "Shot2L.png", "Shot2B.png"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureName.length] ;
    int textureIndex[] = new int[textureName.length];
    WallMaps walls = new WallMaps();
    ArrayList <ShotMove> shotData = new ArrayList<>();

    public void setGLCanvas(GLCanvas gcl){
        this.gcl=gcl;
    }
    @Override
    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glViewport(0, 0, 100,100);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0.0, (float) canvasWidth, 0.0, (float) canvasHeight, -1.0, 1.0);
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        //number of textures, array to hold the indeces
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
        for (int i = 0; i < walls.hardMap.length; i++ ){
            for (int j = 0; j< walls.hardMap[i].length; j++){
                if (walls.hardMap[walls.hardMap.length - i -1 ][j]==1){
                    drawTexture(gl,0, (float)(j*wallWidth),(float) (i*wallHeight), (float)wallWidth, (float)wallHeight);
                }
            }
        }
        drawTexture(gl, directionTank, poxTanks, poyTanks,tankWidth, tankHeight);
        for (int i = 0; i < shotData.size() ; i ++){
            drawTexture(gl,shotData.get(i).indexTexture, shotData.get(i).x, shotData.get(i).y, shotData.get(i).width, shotData.get(i).height);
            if (shotData.get(i).direction ==1){
                shotData.get(i).y+=.4;
            }else if (shotData.get(i).direction ==3){
                shotData.get(i).x-=.4;
            }else if (shotData.get(i).direction ==4){
                shotData.get(i).y-=.4;
            }else if (shotData.get(i).direction ==2){
                shotData.get(i).x+=.4;
            }
            if(shotData.get(i).y > 95|| shotData.get(i).x>95|| shotData.get(i).y<-95|| shotData.get(i).x<-95)
            {
                shotData.remove(i);
               }
        }
    }

    public void drawTexture(GL gl , int index,float xLeft, float yBottom, float Width,float Height) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textureIndex[index]);	// Turn Blending On
        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(xLeft, yBottom, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(xLeft + Width, yBottom, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(xLeft + Width, yBottom + Height, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
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
            if (canMove("Top")&&poyTanks+scale*2<max_Y) {
                poyTanks += scale * 4;
            }
            directionTank = 1 ;
        }
        if (e.getKeyCode()==KeyEvent.VK_DOWN){

            if (poyTanks-scale*2>min_Y) {
                poyTanks -= scale * 4;
            }
            directionTank = 4 ;
        }
        if (e.getKeyCode()==KeyEvent.VK_LEFT){
            if (poxTanks-scale*2>min_X) {
                poxTanks -= scale * 4;
            }
            directionTank = 3 ;
        }
        if (e.getKeyCode()==KeyEvent.VK_RIGHT){
            if (poxTanks+scale*2<max_X) {
                poxTanks += scale * 4;
            }
            directionTank = 2 ;
        }
        if (e.getKeyCode()==KeyEvent.VK_SPACE){
            shotData.add(new ShotMove(poxTanks, poyTanks,directionTank));
            // --------------- Call Method Sound_start to make sound shot ------------------//
            soundBegin();
        }
    
    }

    private boolean canMove(String direction) {
        int row = 0, colum = 0;
        double c = 0;

        if (direction=="Top"){
            System.out.println("posy" + Math.ceil((poyTanks+.4)/5));
            row = (int)(Math.ceil((poyTanks+.4)/5));
            colum =(int)(Math.ceil((poxTanks)/5));
            c= row+(19-row*2);

            System.out.println(" row =" + row+ "colum="+ colum +"colum+(19-colum*2)"+ c );

            if (walls.hardMap[row+(20-row*2)][colum]==0){
                return true;
            }else return false;
        }else if(direction=="Right"){
            return true;
        }else if (direction=="Down"){
            return true;
        }else if (direction=="Left"){
            return true;
        }else return false;


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
