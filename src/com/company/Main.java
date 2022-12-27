package com.company;

import com.TextureReader;
import com.sun.opengl.util.FPSAnimator;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Main extends JFrame {
    static  JFrame Home , Body_of_Game, instructions ;
    static HomeGLEventListener home = new HomeGLEventListener();
    static BodyGLEventListener body;
    static  JButton hard, easy, medium , instructionB, back;
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
    static JCheckBox checkBox = new JCheckBox("2player");
    static int numberOfPlayers = 1, score = 0;
    static int [][] wallMap;
    ArrayList<Tanks> tanks = new ArrayList<>();
    ArrayList<Players> players = new ArrayList<>();
    String textureName[]= {"Stone.png", "Tank5T.png", "Tank5R.png", "Tank5L.png", "Tank5B.png", "Shot2T.png", "Shot2R.png",
                            "Shot2L.png", "Shot2B.png", "Enemy1T.png", "Enemy1R.png", "Enemy1L.png", "Enemy1B.png", "paused.png",
                            "King.png", "Defeat.png"};
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
        instructionB.addActionListener(e->{
            instructions.setVisible(true);
        });
        back.addActionListener(e->{
            instructions.setVisible(false);
        });
        checkBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    numberOfPlayers = 2 ;
                } else {
                    numberOfPlayers = 1 ;
                };
            }
        });
    }
    private static void CreateButton() {
        // --------- create Button to select leve --------//
         easy  =  new JButton(" Easy ");
         medium  =  new JButton(" Medium ");
         hard  =  new JButton(" Hard ");
         instructionB= new JButton(" instruction ");
         back = new JButton("Back");
         //---------------------------------------------------//
         hard.setSize(20,20);
         easy.setSize(20,20);
         medium.setSize(20,20);
         instructionB.setSize(20,20);
         back.setSize(20,20);
         // create
        JPanel p = new JPanel();
        p.setSize(900,400);
        p.add(easy,BorderLayout.WEST);
        p.add(medium,BorderLayout.CENTER);
        p.add(hard,BorderLayout.EAST);
        p.add(instructionB,BorderLayout.EAST);
        p.add(checkBox,BorderLayout.EAST);
        JPanel p2 = new JPanel();
        p2.setSize(50,70);
        p2.add(new JTextArea(" Player 1 Move : "));
        p2.add(new JTextArea("UP :  ^   Down : v  Left : <  Right : > "));
        p2.add(new JTextArea(" Player 1 Shot :  Space "));
        p2.add(new JTextArea(" Player 2 Move  : "));
        p2.add(new JTextArea(" UP : W   Down : S  Left : A  Right : D "));
        p2.add(new JTextArea(" Player 2 Shot  : F "));
        p2.add(new JTextArea(" Try To Save The Flag "));
        p2.add(new JTextArea(" "));
        instructions.add(p2);
        instructions.add(back,BorderLayout.SOUTH);
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
        // -------------------------- Create Instructions Frame -----------------------//
        instructions = new JFrame("Instructions");
        instructions.setVisible(false);
        instructions.setSize(320,200);
        centerWindow(instructions);
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