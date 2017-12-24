package TanksGame;

import TanksGame.GameKeyControls.KeyboardEvents;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Tank extends tanksGame implements Observer {

    private BufferedImage TankImage;
    
    private ArrayList<TankBullet> FoeBullets;
    private ArrayList<TankBullet> TankBullets;
    
    private Image BulletImage;
    private int LaunchBulletX, LaunchBulletY;

    private int currentBulletType = 0; 

    private int BulletSpeedX = 0, BulletSpeedY = 0;

    private int TankWidth, TankHeight;

    int TankX, TankY;

    int LifeValue=10;

    int scores = 0;
    
    private int MoveDirectionDegree = 0;

    private int MoveDirection_speed = 0;

    private int FoeNum;

    int livesNum = 2; 

    private int ExplosionSteps = 0;
    
    private int TankSpeedX = 0, TankSpeedY = 0;

    private String control;

    private String TankPlayer;

     Tank(BufferedImage tankImage, Image bulletImage, ArrayList<TankBullet> tankBullets, ArrayList<TankBullet> foeBullets, String tankPlayer,int foeNumber,int tankX,int tankY) {

        TankImage=tankImage;
        
        BulletImage=bulletImage;

        FoeBullets = foeBullets;

        TankBullets = tankBullets;

        TankWidth = TankImage.getWidth()/60;
        TankHeight = TankImage.getHeight();
        
        TankX=tankX;
        TankY=tankY;
      
        TankPlayer=tankPlayer;

        FoeNum=foeNumber;
       
        control = tankPlayer + "-" + "";

    }

    public void DrawTank(Graphics g, ImageObserver ImgObs,int tankX,int tankY) {

        int TankImgX=TankWidth * (MoveDirectionDegree/6);
        int TankImgY=0;
        int TankImgW=TankWidth;
        int TankImgH=TankHeight;

        if (LifeValue >= 1) {
            
            g.drawImage(TankImage.getSubimage(TankImgX, TankImgY, TankImgW, TankImgH), TankX, TankY, ImgObs);
  
        }
        else if (livesNum >= 1 && LifeValue < 1) {
                
               TankExplosion(g,ImgObs,tankX,tankY);

        } else {
            ExplosionSound.play();
            IsGameFinish = true;
        }
    }

    public void TankExplosion(Graphics g,ImageObserver ImgObs,int tankX,int tankY){
      int BorderX = 1474, BorderY = 1154; 

      if(livesNum >= 1 && LifeValue < 1){

        g.drawImage(ExplosionImages[ExplosionSteps++], TankX, TankY, ImgObs);

            if (ExplosionSteps > 5) {

                ExplosionSound.play();

                ExplosionSteps = 0;

                livesNum-=1;

                LifeValue=10;
                
               TankX=tankX;
               TankY=tankY;

              if(foe[FoeNum].LifeValue > 10){
               if(FoeNum==1){
                foe[FoeNum].TankX=2*BorderX/3-190;
                foe[FoeNum].TankY=2*BorderY/3+270;
               }else{
                  foe[FoeNum].TankX=BorderX/3+270;
                  foe[FoeNum].TankY=BorderY / 3-350;
                }
              }

            }
      }
   }

    public boolean TankCollision(int width, int height, int x, int y) {

        if((TankHeight + TankY > (y+6)) && (TankY < (height-16) + (y+6))){
          if((TankWidth + TankX > (x+6)) && (TankX < (width-11) + (x+6))){
            return true;
          }
        }
        return false;
      
    }

    public void TankMovement() {

      if((!(walls.regularWallCollision(TankWidth,TankHeight,TankSpeedX+TankX, TankY))) &&  (!(walls.indestructibleWallCollision(TankWidth,TankHeight,TankSpeedX+TankX, TankY))) && (!(foe[FoeNum].TankCollision(TankWidth,TankHeight,TankSpeedX+TankX, TankY))) && (TankSpeedX + TankX > 0) && (TankSpeedX + TankX < 1404)){

        TankX=TankX+TankSpeedX;
      }

      if((!(walls.regularWallCollision(TankWidth,TankHeight,TankX, TankSpeedY+TankY))) && (!(walls.indestructibleWallCollision(TankWidth,TankHeight,TankX, TankSpeedY+TankY))) && (!(foe[FoeNum].TankCollision(TankWidth,TankHeight,TankX, TankSpeedY+TankY))) && (TankSpeedY + TankY > 0) && (TankY + TankSpeedY < 1074)){

        TankY=TankY+TankSpeedY;
      }

      int i=0;
      while(i < FoeBullets.size()) {
            if (FoeBullets.get(i).BulletCollision(TankWidth-21, TankHeight, 21+TankX, TankY)) {
            
                if (LifeValue > 0) {
                    BulletSound.play();
                    LifeValue =LifeValue - 1 + foe[FoeNum].currentBulletType;
                    foe[FoeNum].scores += 10;
                }
            }
        i++;
      }

      MoveDirectionDegree=MoveDirectionDegree + MoveDirection_speed;

      if(MoveDirectionDegree==360){
        MoveDirectionDegree=0;
      }

      if(MoveDirectionDegree==-6){
       MoveDirectionDegree=354;
      }

    }

    public void UseControls(Object event){

        KeyboardEvents Key_event = (KeyboardEvents) event;

        if (Key_event.KeyEventType < 2) {

            KeyEvent ke = (KeyEvent) Key_event.events;
            
            if (Controls.get(ke.getKeyCode()).equals(control + "shoot")) {

                if (Key_event.KeyEventType == 1) {
                     
                    BulletSpeedX= (int) (Math.cos(Math.toRadians(MoveDirectionDegree)) * 16);
                    BulletSpeedY = (int) (Math.sin(Math.toRadians(MoveDirectionDegree)) * -16);

                    LaunchBulletX = 3 * BulletSpeedX + TankWidth/4 + TankX;
                    LaunchBulletY = 3 * BulletSpeedY + TankHeight / 4 + TankY ;

                    TankBullets.add(new TankBullet(BulletImage, BulletSpeedX, BulletSpeedY,LaunchBulletX,LaunchBulletY));
                }
            }

            if (Controls.get(ke.getKeyCode()).equals(control + "up")) {

                TankSpeedX = Key_event.KeyEventType * (int) (Math.cos(Math.toRadians(MoveDirectionDegree)) * 11);
                TankSpeedY = Key_event.KeyEventType * (int) (Math.sin(Math.toRadians(MoveDirectionDegree)) * -11);
                
            } 

            if (Controls.get(ke.getKeyCode()).equals(control + "down")) {
                 TankSpeedX = Key_event.KeyEventType * (int) ( Math.cos(Math.toRadians(MoveDirectionDegree)) * -12);
                TankSpeedY = Key_event.KeyEventType * (int) (Math.sin(Math.toRadians(MoveDirectionDegree)) * 12);
                
            } 

            if (Controls.get(ke.getKeyCode()).equals(control + "left")) {

                MoveDirection_speed = Key_event.KeyEventType * 6;
            } 

            if (Controls.get(ke.getKeyCode()).equals(control + "right")) {
                MoveDirection_speed = Key_event.KeyEventType * -6;
            } 

        }
    }

    public void update(Observable obs, Object events) {

       UseControls(events);
    }

}
