package TanksGame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

public class TankBullet extends tanksGame{
  
   private Image BulletImage; 

   private int ImageX, ImageY;

   private int ImageWidth, ImageHeight; 

   private int SpeedX, SpeedY;
    
    TankBullet(Image bulletImage, int speedX,int speedY,int imageX, int imageY) {
        BulletImage = bulletImage;

        ImageWidth = BulletImage.getWidth(null);
        ImageHeight = BulletImage.getHeight(null);

        SpeedX = speedX;
        SpeedY = speedY;

        ImageX = imageX;
        ImageY = imageY;
        
    }

    public boolean BulletCollision(int width,int height,int x,int y) {

       if ((ImageHeight + ImageY > y) && (ImageY < height + y)) {
          if((ImageWidth + ImageX > x) && (ImageX < width + x)){
                ImageX =  2948;
                ImageY = 2308; 
                return true;
            }
         }
        return false;

         
    }

    public boolean BulletMovement() {
        
        ImageX= ImageX + SpeedX;
        ImageY= ImageY + SpeedY;

        return (-ImageHeight  >ImageY || ImageHeight + 1154 < ImageY) || (-ImageWidth  > ImageX || ImageWidth + 1474 < ImageX);
    }

    public void DrawBullet(Graphics g, ImageObserver ImgObs) {
       
        g.drawImage(BulletImage, ImageX, ImageY, ImgObs);
   }

}
