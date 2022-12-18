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

    float x = 50, y = 50;
    double rotate_angle = 0;

    // Assets Texture
    String textureName[]= {"Tampel2.png","Tank5.png","Shot2.png"};
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
        int index_post = 0;
        GL gl = drawable.getGL();
        gl.glClear (GL.GL_COLOR_BUFFER_BIT);
        for (int i = 0 ; i < walls.map.length; i++ ){
            for (int j = 0 ; j< walls.map[i].length;j++){
                if (walls.map[walls.map.length - i -1 ][j]==1){
                    drawTexture(gl,index_post,0, (float)(j*wallWidth),(float) (i*wallHeight), (float)wallWidth, (float)wallHeight);
                }
            }
        }
//        drawTexture(gl,2,0, (float)(7*wallWidth),(float) (7*wallHeight), (float)1, (float)2);
        drawTexture(gl,1, 0, x , y, 5, 5);

        for (int i = 0; i < shotData.size() ; i ++){
            drawTexture(gl,index_post, shotData.get(i).rotate_angle, shotData.get(i).x, shotData.get(i).y, wallWidth, wallHeight);

            if (shotData.get(i).rotate_angle==0){
                shotData.get(i).y+=.2;
            }else if (shotData.get(i).rotate_angle==90){
                shotData.get(i).x-=.2;
            }else if (shotData.get(i).rotate_angle==180){
                shotData.get(i).y-=.2;
            }else if (shotData.get(i).rotate_angle==-90){
                shotData.get(i).x+=.2;
            }else if(shotData.get(i).y > 8|| shotData.get(i).x>8|| shotData.get(i).y<-8|| shotData.get(i).x<-8)
            {
                shotData.remove(i);
               }

        }

    }

    public void drawTexture(GL gl , int index, double rotate_angle,float xLeft, float yBottom, float Width,float Height) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textureIndex[index]);	// Turn Blending On
        gl.glPushMatrix();
//        if(index == 1){
//            gl.glTranslated(x*.1, y*0.01,1);
//            gl.glRotated((float)rotate_angle,0,0,1);
//        }
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
        double max_X = 1, max_Y = 1, min_X = -1, min_Y = -1;
        int quad_radius=1;
        if (e.getKeyCode()==KeyEvent.VK_UP){
            y += scale * 2;
            rotate_angle=0;
        }
        if (e.getKeyCode()==KeyEvent.VK_DOWN){
                y -= scale * 2;
                rotate_angle=180;
        }
        if (e.getKeyCode()==KeyEvent.VK_LEFT){
            x -= scale * 2;
            rotate_angle=90;
        }
        if (e.getKeyCode()==KeyEvent.VK_RIGHT){
                x += scale * 2;
                rotate_angle=-90;

        }
        if (e.getKeyCode()==KeyEvent.VK_SPACE){
            shotData.add(new ShotMove(x,y,rotate_angle));
            // --------------- Call Method Sound_start to make sound shot ------------------//
            soundBegin();

        }
    
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
