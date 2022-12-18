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
    double max_X = 1, max_Y = 1, min_X = -1, min_Y = -1;
    double x = 0, y = 0;
    double scale = 0.1;
    int quad_radius=1;
    private GLCanvas gcl;
    double rotate_angle = 0 ;
    protected String assetsFolderName = "Assets";
    String textureName[]= {"Tampel2.png","Tank5.png","Shot2.png"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureName.length] ;
    int textureIndex[] = new int[textureName.length];
    int index_post = 0 ;
    int [][] array_of_blocks = new int[20][20];

    ArrayList <ShotMove> Shot_Data = new ArrayList<>();
    

    public BodyGLEventListener() throws FileNotFoundException {
    }

    public void setGLCanvas(GLCanvas gcl){
        this.gcl=gcl;
    }



    @Override
    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);  //This Will Clear The Background Color To Black
        gl.glViewport(0, 0, 100,100);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0.0, 100.0, 0.0, 100.0, -1.0, 1.0);
        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        //number of textures, array to hold the indeces
        for (int k = 0 ; k < array_of_blocks.length ; k++ ){
            for (int j = 0 ; j< array_of_blocks.length;j++){
                array_of_blocks[k][j]= (int)(Math.round(Math.random()));
            }
        }
        gl.glGenTextures(textureName.length, textureIndex, 0);
        for (int i = 0 ; i < textureName.length;i++) {
            try {
                texture [i]= TextureReader.readTexture(assetsFolderName + "//" + textureName[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textureIndex[i]);
                //mipmapsFromPNG(gl, new GLU(), texture[i]);
                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels() // Imagedata
                );
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
        for (int i = 0 ; i < array_of_blocks.length ; i++ ){
            for (int j = 0 ; j< array_of_blocks.length;j++){
                if (array_of_blocks[i][j]==1){
                    DrawSprite(gl,index_post,0,(float)(i*5),(float) (j*5));
                }
            }
        }

        for (int i = 0 ; i < Shot_Data.size() ; i ++){
            Shot(gl,Shot_Data.get(i).x,Shot_Data.get(i).y,Shot_Data.get(i).rotate_angle);

            if (Shot_Data.get(i).rotate_angle==0){
                Shot_Data.get(i).y+=.2;
            }else if (Shot_Data.get(i).rotate_angle==90){
                Shot_Data.get(i).x-=.2;
            }else if (Shot_Data.get(i).rotate_angle==180){
                Shot_Data.get(i).y-=.2;
            }else if (Shot_Data.get(i).rotate_angle==-90){
                Shot_Data.get(i).x+=.2;
            }else if(Shot_Data.get(i).y > 8||Shot_Data.get(i).x>8||Shot_Data.get(i).y<-8||Shot_Data.get(i).x<-8)
            {
                Shot_Data.remove(i);
               }

        }

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable drawable, boolean b, boolean b1) {

    }
    public void DrawSprite(GL gl ,int index, double rotate_angle, float xLeft, float yTop) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textureIndex[0]);	// Turn Blending On
        //gl.glColor3f(0, 0, 0);
        gl.glPushMatrix();
        gl.glTranslated( x * scale, y * scale, 1);
        gl.glRotated(rotate_angle,0,0,1);
        //gl.glScaled(scale*2, scale*3.5, 1);
        //System.out.println(x +" " + y);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(xLeft, yTop, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(xLeft+5, yTop, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(xLeft+5, yTop+5, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(xLeft,yTop+5, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }
    public void Shot (GL gl, double x, double y, double rotate_angle) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textureIndex[1]);// Turn Blending On
        //gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glPushMatrix();
        gl.glTranslated( x * scale, y * scale+.02, 1);
        gl.glRotated(rotate_angle, 0, 0, 1);
        gl.glScaled(scale*.1, scale-.05, 1);
        //System.out.println(x +" " + y);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();
        gl.glDisable(GL.GL_BLEND);
    }

    void DrawBackground (GL gl){

        gl.glEnable(GL.GL_BLEND);	// Turn Blending On
        //  gl.glBindTexture(GL.GL_TEXTURE_2D, textureIndex[1]);
        gl.glPushMatrix();
        gl.glColor3f(0f,0.5f,0.5f);
        //gl.glScaled(0.5, 0.1, 1);
        gl.glBegin(GL.GL_QUADS);
        // Front Face

        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1f, -1f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();
        gl.glDisable(GL.GL_BLEND);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode()==KeyEvent.VK_UP){
            if ((y + quad_radius) * scale < max_Y) {
                y += scale * 2;

            }
            rotate_angle=0;
        }
        if (e.getKeyCode()==KeyEvent.VK_DOWN){
            if ((y - quad_radius) * scale > min_Y) {
                y -= scale * 2;

            }
            rotate_angle=180;
        }
        if (e.getKeyCode()==KeyEvent.VK_LEFT){
            if ((x - quad_radius) * scale > min_X) {
                x -= scale * 2;

            }
            rotate_angle=90;
        }
        if (e.getKeyCode()==KeyEvent.VK_RIGHT){
            if ((x + quad_radius) * scale < max_X) {
                x += scale * 2;

            }
            rotate_angle=-90;
        }
        if (e.getKeyCode()==KeyEvent.VK_SPACE){
            Shot_Data.add(new ShotMove(x,y,rotate_angle));
            // --------------- Call Method Sound_start to make sound shot ------------------//
            Sound_start();

        }
    
    }

    private void Sound_start() {
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
    public void keyReleased(KeyEvent e) {

    }
}
