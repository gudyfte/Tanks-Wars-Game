package TanksGame;

import TanksGame.GameKeyControls.KeyboardControl;
import TanksGame.GameKeyControls.KeyboardEvents;

import static java.applet.Applet.newAudioClip;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JFrame;
import java.awt.event.WindowEvent;
import java.util.Observable;

public class tanksGame extends JApplet implements Runnable {

    static HashMap<Integer,String> Controls = new HashMap<>();

    static boolean IsGameFinish= false;

    private int BorderX = 1474, BorderY = 1154; 
  
    ImageObserver imgObs;

    private BufferedImage image1, image2;

    private Thread thread;

    private KeyboardEvents event;

    private KeyboardControl KeyCon;

    private MapBackground background;

    static Tank LeftTank, RightTank;

    static Walls walls;

    static Tank[] foe = new Tank[10];
    
    static ArrayList<TankBullet> LeftTankBullets, RightTankBullets;
 
    private AudioClip GameMusic;

    private Image backgroundImage;

    private BufferedImage LeftTankImages;
    private BufferedImage RightTankImages;

    private Image BulletImage;
    
    static AudioClip ExplosionSound, BulletSound;

    static Image WallImage1,WallImage2;

    static Image[] ExplosionImages;

    private int LeftWindowX;
    private int LeftWindowY;

    private int RightWindowX;
    private int RightWindowY;

    private int ScreenWidth=845;
    private int ScreenHeight=611;

    private int LeftTankHealth;
    private int RightTankHealth;

    public static void main(String[] args) {

     openWindow();

    }

    public void CompileProgram() {

        UploadResources();

        background=new MapBackground(backgroundImage);
      
        LeftTankBullets = new ArrayList<>();
        RightTankBullets = new ArrayList<>();

        int LeftTankX=BorderX/3+270;
        int LeftTankY=BorderY / 3-350;

        int RightTankX=2*BorderX/3-190;
        int RightTankY=2*BorderY/3+270;

        int RightTankFoeNum=1;
        int LeftTankFoeNum=2;

        LeftTank = new Tank(LeftTankImages, BulletImage,LeftTankBullets, RightTankBullets, "LeftTank",RightTankFoeNum,LeftTankX,LeftTankY);
        RightTank = new Tank(RightTankImages, BulletImage,RightTankBullets, LeftTankBullets,"RightTank",LeftTankFoeNum,RightTankX,RightTankY);

        foe[1] = RightTank;
        foe[2] = LeftTank;

        walls=new Walls();

        setBackground(Color.WHITE);

        this.setFocusable(true);
 
        event = new KeyboardEvents();

        event.addObserver(LeftTank);
        event.addObserver(RightTank);

        addKeyListener(new KeyboardControl(event));

        getControls();
    }

    public void UploadResources(){

        backgroundImage=GetResource("Pictures/background_tile.png");

        WallImage1 = GetResource("Pictures/wall.png");
        WallImage2=GetResource("Pictures/wall_indestructible.png");

       try{
	LeftTankImages = GetBufferedImageResource("Pictures/Tank_blue_basic_strip60.png");
        RightTankImages = GetBufferedImageResource("Pictures/Tank_blue_basic_strip60.png");
       }catch(Exception e){}

        BulletImage = GetResource("Pictures/bullet.png");

        Image explosionImages[]= {GetResource("Pictures/explosion1_1.png"),
            GetResource("Pictures/explosion1_2.png"),
            GetResource("Pictures/explosion1_3.png"),
            GetResource("Pictures/explosion1_4.png"),
            GetResource("Pictures/explosion1_5.png"),
            GetResource("Pictures/explosion1_6.png")};

         ExplosionImages=explosionImages;
       
        ExplosionSound = GetAudioResource("Pictures/ExplosionSound.wav");
        BulletSound = GetAudioResource("Pictures/BulletSound.wav");
        GameMusic=GetAudioResource("Pictures/turret.wav");

   }

   public void getControls(){

       Controls.put(KeyEvent.VK_A, "LeftTank-left");
        Controls.put(KeyEvent.VK_W, "LeftTank-up");
        Controls.put(KeyEvent.VK_S, "LeftTank-down");
        Controls.put(KeyEvent.VK_D, "LeftTank-right");
        Controls.put(KeyEvent.VK_SPACE, "LeftTank-shoot");
        Controls.put(KeyEvent.VK_LEFT, "RightTank-left");
        Controls.put(KeyEvent.VK_UP, "RightTank-up");
        Controls.put(KeyEvent.VK_DOWN, "RightTank-down");
        Controls.put(KeyEvent.VK_RIGHT, "RightTank-right");
        Controls.put(KeyEvent.VK_BACK_SPACE, "RightTank-shoot");
   }

    private AudioClip GetAudioResource(String FileName) {
        
        return newAudioClip(tanksGame.class.getResource(FileName));
    }

    public void GameMusicStart() {
        GameMusic.loop();
       
        thread=new Thread(this);
        
        thread.start();
    }

    @Override
    public void run() {
        
        if(thread==Thread.currentThread()){
         do{
            repaint();
            try {
                thread.sleep(20);
            } catch (InterruptedException e) {
                
               System.out.println(e);
            }
         }while(thread==Thread.currentThread());
       }
    }

    public void paint(Graphics g) {

        int BorderWidth=1474;
        int BorderHeight=1154;

        Graphics2D page = BuildGraphicPage(BorderWidth, BorderHeight);
        Graphics2D outPage = BuildOuterGraphicPage(BorderWidth, BorderHeight);

        RenewPlayGame(page,imgObs,BorderWidth, BorderHeight);

        page.dispose();

        if(IsGameFinish){
          g.drawImage(image1,0,0,imgObs);
        }
        else{

           GetLeftTankViewWindow();
           GetRightTankViewWindow();    
     
             BufferedImage Image1=image1.getSubimage(LeftWindowX, LeftWindowY, (int)(ScreenWidth/2 -2) , ScreenHeight -31);
            outPage.drawImage(Image1, 0, 0, imgObs);

            Image1=image1.getSubimage(RightWindowX, RightWindowY, (int)(ScreenWidth/2 -2) , ScreenHeight -31);
            outPage.drawImage(Image1, (int)(ScreenWidth/2 -2), 0, imgObs);

            int LineX1= 2+ getSize().width/2;
            int LineY1=0;
            int LineX2=2 + getSize().width/2;
            int LineY2=getSize().height;

            outPage.drawLine(LineX1, LineY1, LineX2, LineY2);

            for(int i=0;i<LeftTank.livesNum;i++){
              if(i==0){
               outPage.setColor(Color.LIGHT_GRAY);
               outPage.fillOval(110,555,15,15);
              }else if(i==1){
                outPage.setColor(Color.LIGHT_GRAY);
               outPage.fillOval(140,555,15,15);
              }
            }

            for(int i=0;i<RightTank.livesNum;i++){
              if(i==0){
               outPage.setColor(Color.LIGHT_GRAY);
               outPage.fillOval(570,555,15,15);
              }else if(i==1){
                outPage.setColor(Color.LIGHT_GRAY);
               outPage.fillOval(600,555,15,15);
              }
            }

            LeftTankHealth=10-LeftTank.LifeValue;

            if(LeftTankHealth==0){
              outPage.setColor(Color.GREEN);
              outPage.fillRect(1,555,100,20);
            }else if(LeftTankHealth==1){
              outPage.setColor(Color.GREEN);
              outPage.fillRect(1,555,90,20);
            }else if(LeftTankHealth==2){
               outPage.setColor(Color.GREEN);
              outPage.fillRect(1,555,80,20);
            }else if(LeftTankHealth==3){
              outPage.setColor(Color.GREEN);
              outPage.fillRect(1,555,70,20);
            }else if(LeftTankHealth==4){
              outPage.setColor(Color.GREEN);
              outPage.fillRect(1,555,60,20);
            }else if(LeftTankHealth==5){
              outPage.setColor(Color.GREEN);
              outPage.fillRect(1,555,50,20);
            }else if(LeftTankHealth==6){
              outPage.setColor(Color.yellow);
              outPage.fillRect(1,555,40,20);
            }else if(LeftTankHealth==7){
              outPage.setColor(Color.yellow);
              outPage.fillRect(1,555,30,20);
            }else if(LeftTankHealth==8){
              outPage.setColor(Color.red);
              outPage.fillRect(1,555,20,20);
            }else if(LeftTankHealth==9){
              outPage.setColor(Color.red);
              outPage.fillRect(1,555,10,20);
            }else if(LeftTankHealth==10){
              outPage.setColor(Color.red);
              outPage.fillRect(0,0,0,0);
            }

            RightTankHealth=10-RightTank.LifeValue;

            if(RightTankHealth==0){
              outPage.setColor(Color.GREEN);
              outPage.fillRect(455,555,100,20);
            }else if(RightTankHealth==1){
              outPage.setColor(Color.GREEN);
              outPage.fillRect(455,555,90,20);
            }else if(RightTankHealth==2){
               outPage.setColor(Color.GREEN);
              outPage.fillRect(455,555,80,20);
            }else if(RightTankHealth==3){
              outPage.setColor(Color.GREEN);
              outPage.fillRect(455,555,70,20);
            }else if(RightTankHealth==4){
               outPage.setColor(Color.GREEN);
              outPage.fillRect(455,555,60,20);
            }else if(RightTankHealth==5){
               outPage.setColor(Color.GREEN);
              outPage.fillRect(455,555,50,20);
            }else if(RightTankHealth==6){
               outPage.setColor(Color.yellow);
              outPage.fillRect(455,555,40,20);
            }else if(RightTankHealth==7){
               outPage.setColor(Color.yellow);
              outPage.fillRect(455,555,30,20);
            }else if(RightTankHealth==8){
               outPage.setColor(Color.red);
              outPage.fillRect(455,555,20,20);
            }else if(RightTankHealth==9){
               outPage.setColor(Color.red);
              outPage.fillRect(455,555,10,20);
            }else if(RightTankHealth==10){
               outPage.setColor(Color.red);
              outPage.fillRect(0,0,0,0);
            }

            outPage.setFont(new Font("sans-serif", Font.BOLD, 20));

            outPage.setColor(Color.WHITE);

            outPage.drawString(LeftTank.scores + "", getSize().width / 4 + 45, 573);

            outPage.setColor(Color.WHITE);

            outPage.drawString(RightTank.scores + "", getSize().width - 150, 573);

            Image img1=image1.getScaledInstance(getSize().width / 5, getSize().height / 5, 1);

            int imgX=getSize().width / 2 - getSize().width / 5/ 2;
            int imgY=3 * getSize().height / 4 - 100;

            outPage.drawImage(img1, imgX+10, imgY, imgObs);

            outPage.dispose();

            g.drawImage(image2, 0, 0, imgObs);

        }

    }

    public void GetLeftTankViewWindow(){
      LeftWindowX = 30 + LeftTank.TankX - (int)(ScreenWidth / 4 -1);
            if (LeftWindowX < 0) {
                LeftWindowX=0;
                
            } 
            else if (BorderX < (int)(ScreenWidth/2 -2) + LeftWindowX) {
               LeftWindowX = BorderX - (int)(ScreenWidth/2 -2) ;
            }

            LeftWindowY = 100 + LeftTank.TankY - (int)(ScreenHeight/2 -15) ;
            if (LeftWindowY < 0) {
                LeftWindowY=0;
            } 
            else if (BorderY < ScreenHeight - 31 + LeftWindowY) {
               LeftWindowY= BorderY - (ScreenHeight -31);
            }
    }

    public void GetRightTankViewWindow(){

      RightWindowX = 30 + RightTank.TankX  - (int)(ScreenWidth / 4 - 1);
            if (RightWindowX<0) {
                RightWindowX=0;
            } else if (BorderX < (int)(ScreenWidth/2 -2) + RightWindowX) {
                RightWindowX=BorderX -(int)(ScreenWidth/2 -2);
            }
            RightWindowY = 100 + RightTank.TankY - (int)(ScreenHeight/2 -15);
            if (RightWindowY<0) {
                RightWindowY= 0;
            } else if (BorderY < ScreenHeight -31 + RightWindowY) {
                RightWindowY=BorderY- (ScreenHeight -31);
            }
   }

    public Graphics2D BuildOuterGraphicPage(int width, int height) {
        Graphics2D page = null;
        if (image2 == null || image2.getHeight() != height || image2.getWidth() != width) {
            
           image2 = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
           
        }
        page = image2.createGraphics();
        page.setBackground(getBackground());
       
        page.clearRect(0, 0, width, height);
        return page;
    }

    public Graphics2D BuildGraphicPage(int width, int height) { 
        Graphics2D page = null;
        if (image1 == null || image1.getHeight() != height || image1.getWidth() != width) {
           
            image1 = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        }
        page = image1.createGraphics();
        page.setBackground(getBackground());

        page.clearRect(0, 0, width, height);
        return page;
    }

    public void RenewPlayGame(Graphics2D gd,ImageObserver ImgObs,int width, int height) {
      
        if(IsGameFinish){

            FinishGame(gd,ImgObs);
        }
        else{

            background.DrawBackground(gd, ImgObs);

            walls.drawWalls(gd, ImgObs);
            
            LeftTank.TankMovement();
            LeftTank.DrawTank(gd, ImgObs,BorderX/3+270,BorderY / 3-350);

            RightTank.TankMovement();
            RightTank.DrawTank(gd, ImgObs,2*BorderX/3-190,2*BorderY/3+270);
            
             int j=0;
            while(j < LeftTankBullets.size()) {
                if (LeftTankBullets.get(j).BulletMovement()) {
                    LeftTankBullets.remove(j);
                } else {
                    
                    LeftTankBullets.get(j).DrawBullet(gd,ImgObs);
                }
             j++;
            }
  
            int i=0;
            while(i< RightTankBullets.size()) {
                if (RightTankBullets.get(i).BulletMovement()) {
                     RightTankBullets.remove(i);
                    
                } else {
                    
                    RightTankBullets.get(i).DrawBullet(gd,ImgObs);
                }
               i++;
            }

        } 

    }


    public void FinishGame(Graphics2D gd,ImageObserver ImgObs){
      GameMusic.stop();

            background.DrawBackground(gd, ImgObs);
                     
            Font font = new Font("Garamond", Font.BOLD, 48);
            gd.setFont(font);

            String ScoreResult="";

            if (LeftTank.scores > RightTank.scores) {
                ScoreResult = "Left Tank wins game! \n\n Score is: " + LeftTank.scores;
            } 
            else{
                ScoreResult = "Right Tank wins game! \n\nScore is: " + RightTank.scores;
            }

            Rectangle2D rd = font.getStringBounds(ScoreResult, gd.getFontRenderContext());

            gd.setPaint(Color.WHITE);

            int StringX=(int) ((getWidth() - rd.getWidth()) / 2);
            int StringY=(int)((getHeight() - rd.getHeight()) / 2 + (-rd.getY()));

            gd.drawString(ScoreResult, StringX, StringY);
    }

    public Image GetResource(String ResourceName) {
       
        Image image;

        image = getToolkit().getImage(tanksGame.class.getResource(ResourceName));

        try {
            MediaTracker mt = new MediaTracker(this);
            mt.addImage(image, 0);
            mt.waitForID(0);
        } catch (Exception e) {
            
           System.out.println(e);
        }

        return image;
    }

    public BufferedImage GetBufferedImageResource(String ImageName) throws IOException {
       
        BufferedImage img = ImageIO.read(tanksGame.class.getResource(ImageName));
        try {
            MediaTracker mt = new MediaTracker(this);
            mt.addImage(img, 0);
            mt.waitForID(0);
        } catch (Exception e) {
            System.out.println(e);
        }
        return img;
    }

    public static void openWindow(){
     tanksGame tankgame = new tanksGame();
     tankgame.CompileProgram();
     JFrame frame = new JFrame("Tank Wars");
        
     frame.getContentPane().add("Center", tankgame);
     frame.pack();
     Dimension dimension=new Dimension(845,611);
     frame.setSize(dimension);
     frame.setVisible(true);
     frame.setResizable(false);
     tankgame.GameMusicStart();
    	
     frame.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e)
        {
                    System.exit(0);
        }
    });
    
   }
}
