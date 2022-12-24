package com.company;

import com.sun.opengl.util.FPSAnimator;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Main extends JFrame {
    static  JFrame Home , Body_of_Game;
    static HomeGLEventListener home = new HomeGLEventListener();
    static BodyGLEventListener body;

    static {
        try {
            body = new BodyGLEventListener();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static GLCanvas glcanvas_of_Home,glcanvas_of_Body_of_Game ;
    static  JButton BT1 ;
    static  AudioStream as;
    static FPSAnimator animtor1,animtor2  ;

    public static void main(String[] args) throws IOException {
        CreateJframe();
        CreateGLCanvas();
        CreateButton();
        InputStream Get_M_of_Home = new FileInputStream("C:\\my_project\\artical_WibSite\\Tank-game\\Music\\WorldofTanks.wav");
        as = new AudioStream(Get_M_of_Home);
        //AudioPlayer.player.start(as);

        HandlerButton();
        animtor1.start();
        animtor2.start();
    }

    private static void HandlerButton() {
        BT1.addActionListener(e ->{
             Home.setVisible(false);
             Body_of_Game.setVisible(true);
             AudioPlayer.player.stop(as);
        });
    }

    private static void CreateButton() {
        BT1 = new JButton("start");
        BT1.setSize(50,20);
       // BT1.setLocation(200,100);
        Home.add(BT1,BorderLayout.SOUTH);

    }

    private static void CreateGLCanvas() {
        // ------------- Create a glcanvas_of_Home ------------------//
        glcanvas_of_Home = new GLCanvas();
        glcanvas_of_Home.addGLEventListener(home);
        home.setGLCanvas(glcanvas_of_Home);
        Home.add(glcanvas_of_Home, BorderLayout.CENTER);
        animtor1=null;
        animtor1 = new FPSAnimator(glcanvas_of_Home,60);

        // ------------- Create a glcanvas_of_Body_of_Game ------------------//
        glcanvas_of_Body_of_Game = new GLCanvas();
        glcanvas_of_Body_of_Game.addGLEventListener(body);
        glcanvas_of_Body_of_Game.addKeyListener(body);
        body.setGLCanvas(glcanvas_of_Body_of_Game);
        Body_of_Game.add(glcanvas_of_Body_of_Game,BorderLayout.CENTER);
        animtor2=null;
        animtor2 = new FPSAnimator(glcanvas_of_Body_of_Game,60);
    }

    private static void CreateJframe() {
        //--------------------- Create  Home Frame ------------------------//
        Home = new JFrame("Tanks-game_home");
        Home.setSize(500,500);
        Home.setVisible(false);
        centerWindow(Home);
        Home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Home.setLayout(null);

        //--------------------- Create  Body_of_Game Frame ------------------------//
        Body_of_Game = new JFrame("Tanks-game");
        Body_of_Game.setSize(900,800);
        Body_of_Game.setVisible(true);
        centerWindow(Body_of_Game);
        Body_of_Game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    static  public void centerWindow(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize  = frame.getSize();

        if (frameSize.width  > screenSize.width ) frameSize.width  = screenSize.width;
        if (frameSize.height > screenSize.height) frameSize.height = screenSize.height;

        frame.setLocation (
                (screenSize.width  - frameSize.width ) >> 1,
                (screenSize.height - frameSize.height) >> 1
        );
    }
}