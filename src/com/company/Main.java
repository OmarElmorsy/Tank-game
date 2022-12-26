package com.company;

import com.TextureReader;
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
import java.util.ArrayList;

public class Main extends JFrame {
    static  JFrame Home , Body_of_Game;
    static HomeGLEventListener home = new HomeGLEventListener();
    static BodyGLEventListener body;
    static  JButton hard, easy, medium;
    static {
        try {
            body = new BodyGLEventListener();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    static GLCanvas glcanvas_of_Home,glcanvas_of_Body_of_Game ;
    static AudioStream audioStream;
    static FPSAnimator animtor1,animtor2  ;
    static int removeWallX, removeWallY;
    final int canvasWidth = 100, canvasHeight = 100;
    final int wallWidth = 5, wallHeight = 5;
    final  int tankWidth = 5, tankHeight = 5;
    int numberOfPlayers = 2;
    static int [][] wallMap;
    ArrayList<Tanks> tanks = new ArrayList<>();
    ArrayList<Players> players = new ArrayList<>();
    String textureName[]= {"Stone.png", "Tank5T.png", "Tank5R.png", "Tank5L.png", "Tank5B.png", "Shot2T.png", "Shot2R.png",
                            "Shot2L.png", "Shot2B.png", "Enemy1T.png", "Enemy1R.png", "Enemy1L.png", "Enemy1B.png", "paused.png"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureName.length] ;
    int textureIndex[] = new int[textureName.length];

    ArrayList <ShotMove> shotData = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        CreateJframe();
        CreateButton();
        HandlerButton();
        CreateGLCanvas();
        InputStream Get_M_of_Home = new FileInputStream("C:\\my_project\\artical_WibSite\\Tank-game\\Music\\WorldofTanks.wav");
        audioStream = new AudioStream(Get_M_of_Home);
        AudioPlayer.player.start(audioStream);
        animtor1.start();
        animtor2.start();

    }
    private static void HandlerButton() {
        easy.addActionListener(e->{
            Home.setVisible(false);
            Body_of_Game.setVisible(true);
            AudioPlayer.player.stop(audioStream);
            wallMap = new WallMaps().easyMap;
        });
        medium.addActionListener(e->{
            Home.setVisible(false);
            Body_of_Game.setVisible(true);
            AudioPlayer.player.stop(audioStream);
            wallMap = new WallMaps().mediumMap;
        });
        hard.addActionListener(e->{
            Home.setVisible(false);
            Body_of_Game.setVisible(true);
            AudioPlayer.player.stop(audioStream);
            wallMap = new WallMaps().hardMap;
        });
    }
    private static void CreateButton() {
        // --------- create Button to select leve --------//
         easy  =  new JButton(" Easy ");
         medium  =  new JButton(" Medium ");
         hard  =  new JButton(" Hard ");
         //---------------------------------------------------//
        hard.setSize(20,20);
        easy.setSize(20,20);
        medium.setSize(20,20);
        // create
        JPanel p = new JPanel();
        p.setSize(900,90);
        p.add(easy,BorderLayout.WEST);
        p.add(medium,BorderLayout.CENTER);
        p.add(hard,BorderLayout.EAST);
        Home.add(p,BorderLayout.SOUTH);
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
        Body_of_Game.add(glcanvas_of_Body_of_Game,BorderLayout.CENTER);
        animtor2=null;
        animtor2 = new FPSAnimator(glcanvas_of_Body_of_Game,60);
    }
    private static void CreateJframe() {
        //--------------------- Create  Home Frame ------------------------//
        Home = new JFrame("Tanks-game_home");
        Home.setSize(900,800);
        Home.setVisible(true);
        centerWindow(Home);
        Home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Home.setLayout(null);

        //--------------------- Create  Body_of_Game Frame ------------------------//
        Body_of_Game = new JFrame("Tanks-game");
        Body_of_Game.setSize(900,800);
        Body_of_Game.setVisible(false);
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