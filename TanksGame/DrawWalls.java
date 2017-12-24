package TanksGame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;


public class DrawWalls extends tanksGame{

    private int WallX, WallY;

    private int WallWidth1= WallImage1.getWidth(null);
    private int WallHeight1= WallImage1.getHeight(null); 
    
    private int time=0;

    private int WallWidth2=WallImage2.getWidth(null);
    private int WallHeight2=WallImage2.getWidth(null);

    private boolean ifWallExist=false;

    DrawWalls(int wallX, int wallY) {
        WallX = wallX;
        WallY = wallY;
    }

    
    public void RenewRegularWall() {

        if (!ifWallExist) {
           int i=0;
            while(i < RightTankBullets.size()) {
                if (RightTankBullets.get(i).BulletCollision(WallWidth1-21, WallHeight1, 21 + WallX, WallY)) {
                    BulletSound.play();
                    ifWallExist = true;
                    
                }
               i++;
            }

           int j=0;
            while(j < LeftTankBullets.size()) {
                if(LeftTankBullets.get(j).BulletCollision(WallWidth1-21,WallHeight1,21+WallX,WallY)){
                    BulletSound.play();
                    ifWallExist = true;
                    
                }
               j++;
            }
            
        } 
        else {
            time+=1;
            if ((!foe[1].TankCollision(WallWidth1,WallHeight1,WallX, WallY)) && (!foe[2].TankCollision(WallWidth1,WallHeight1,WallX, WallY)) && (time>599)) {
                time=0;
                ifWallExist = false;
                
            }
        }


    }

    public void RenewIndestructibleWall(){
     if (!ifWallExist) {
           int i=0;
            while(i < RightTankBullets.size()) {
                if (RightTankBullets.get(i).BulletCollision(WallWidth2 - 21, WallHeight2, 21+WallX, WallY)) {
                    BulletSound.play();
                    ifWallExist = false;
                    
                }
               i++;
            }
           int j=0;
            while(j < LeftTankBullets.size()) {
                if (LeftTankBullets.get(j).BulletCollision(WallWidth2-21, WallHeight2, 21+WallX, WallY)) {
                     BulletSound.play();
                    ifWallExist = false;
                    
                }
                j++;
            }
            
        } else {
            time+=1;
            if ((!foe[1].TankCollision(WallWidth2,WallHeight2,WallX, WallY)) && (!foe[2].TankCollision(WallWidth2,WallHeight2,WallX, WallY)) && (time>599)) {
                time=0;
              ifWallExist = false;
               
            }
        }
    }

    
    public void DrawRegularWall(Graphics g, ImageObserver ImgObs) {
        if (!ifWallExist) {
            g.drawImage(WallImage1, WallX, WallY, ImgObs);
        }
    }

    public void DrawIndestructibleWall(Graphics g, ImageObserver ImgObs) {
        if (!ifWallExist) {
            g.drawImage(WallImage2, WallX, WallY, ImgObs);
        }
    }
    
    public boolean RegularWallCollision(int width, int height, int x, int y) {

       if(!ifWallExist){
        if((WallHeight1 + WallY > y) && (WallY < height + y)){
          if((WallWidth1 + WallX > x) && (WallX < width + x)){
            return true;
          }
        }
        return false;
       }
       return false;
    }

    public boolean IndestructibleWallCollision(int width, int height, int x, int y) {

    if(!ifWallExist){
     if((WallHeight2 + WallY > y) && (WallY < height + y)){
      if((WallWidth2 + WallX > x) && (WallX < width + x)){
        return true;
      }
     }
     return false;
    }
    return false;
  }   

}
