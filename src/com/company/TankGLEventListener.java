package com.company;

import com.TextureReader;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class TankGLEventListener implements GLEventListener, KeyListener {
    // -------------- Data -------------------------//
    double max_X = 1, max_Y = 1, min_X = -1, min_Y = -1;
    double x = 0, y = 0;
    double scale = 0.1;
    GLCanvas gcl;
    double rotate_angle = 0 ;
    double quad_radius = 1;
    protected String assetsFolderName = "Assets";
    String textureName[]= {"Tank2t.png","Tank2r.png","Background.png"};
    TextureReader.Texture texture []=  new TextureReader.Texture[textureName.length];
    int textureIndex[] = new int[textureName.length];
    public  void setGLCanvas (GLCanvas gcl){
        this.gcl=gcl;
    }
    // ---------------------------  GLEventListener implement Method     ----------------------------/
    @Override
    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);  //This Will Clear The Background Color To Black
        gl.glViewport(0, 0, 600, 300);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
       // gl.glOrtho(-.0, 2.0, -2.0, 2.0, -1.0, 1.0);
        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        //number of textures, array to hold the indeces
        gl.glGenTextures(textureName.length, textureIndex, 0);
        for (int i = 0 ; i< textureIndex.length ; i++) {

            try {
                texture[i]= TextureReader.readTexture(assetsFolderName + "//" + textureName[i], true);
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
         DrawBackground(gl);
         DrawSprite(gl,0,rotate_angle);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }
    //  ----------------------------Sumfunction to help in game ---------------------- //
        // Draw Tank
    public void DrawSprite(GL gl ,int index, double rotate_angle) {
        gl.glEnable(GL.GL_BLEND);
        gl.glPushMatrix();
        if (rotate_angle==-90) {
            gl.glBindTexture(GL.GL_TEXTURE_2D, textureIndex[1]);
            gl.glTranslated( x * scale, y * scale, 1);
            gl.glRotated(0,0,0,1);
        }else {
            gl.glBindTexture(GL.GL_TEXTURE_2D, textureIndex[0]);// Turn Blending On
            gl.glTranslated( x * scale, y * scale, 1);
            gl.glRotated(rotate_angle,0,0,1);
        }//gl.glColor3f(0, 0, 0);


        gl.glScaled(scale-.05, scale-.02, 1);
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
      // Draw background
      public void DrawBackground (GL gl){

          gl.glEnable(GL.GL_BLEND);	// Turn Blending On
          gl.glBindTexture(GL.GL_TEXTURE_2D, textureIndex[2]);
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
      // Draw boll or fire
      public void DrawSpriteboll(GL gl, double x, double y, double rotate_angle) {
          gl.glEnable(GL.GL_BLEND);
          gl.glBindTexture(GL.GL_TEXTURE_2D, textureIndex[5]);// Turn Blending On
          gl.glColor3f(1.0f, 1.0f, 1.0f);
          gl.glPushMatrix();
          gl.glTranslated( x * scale, y * scale, 1);
          gl.glRotated(rotate_angle, 0, 0, 1);
          gl.glScaled(scale-.052, scale-.052, 1);
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
//    --------------------------- Handel Key Event ---------------------------------//

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

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
