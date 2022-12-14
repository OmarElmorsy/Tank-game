package com.company;

import com.TextureReader;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.io.IOException;

public class HomeGLEventListener implements GLEventListener {

    String textureName="Home_Page.png" ;
    TextureReader.Texture texture ;
    int textureIndex[] = new int[1];
    private GLCanvas gcl;
    protected String assetsFolderName = "Assets";
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
        // gl.glOrtho(-.0, 2.0, -2.0, 2.0, -1.0, 1.0);
        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        //number of textures, array to hold the indeces
        gl.glGenTextures(1, textureIndex, 0);

        try {
            texture= TextureReader.readTexture(assetsFolderName + "//" + textureName, true);
            gl.glBindTexture(GL.GL_TEXTURE_2D, textureIndex[0]);
            //mipmapsFromPNG(gl, new GLU(), texture[i]);
            new GLU().gluBuild2DMipmaps(
                    GL.GL_TEXTURE_2D,
                    GL.GL_RGBA, // Internal Texel Format,
                    texture.getWidth(), texture.getHeight(),
                    GL.GL_RGBA, // External format from image,
                    GL.GL_UNSIGNED_BYTE,
                    texture.getPixels() // Imagedata
            );
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    @Override
    public void display(GLAutoDrawable drawable) {

        GL gl = drawable.getGL();
        gl.glClear (GL.GL_COLOR_BUFFER_BIT);
        DrawBackground(gl);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable drawable, boolean b, boolean b1) {

    }
    void  DrawBackground (GL gl){

        gl.glEnable(GL.GL_BLEND);	// Turn Blending On
        gl.glBindTexture(GL.GL_TEXTURE_2D, textureIndex[0]);
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
}
