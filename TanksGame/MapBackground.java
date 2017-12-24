package TanksGame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

public class MapBackground extends tanksGame{

    private Image BackgroundImage;

    private int GroundWidth;
    private int GroundHeight;

    MapBackground(Image groundImage){
      BackgroundImage=groundImage;

      GroundWidth=BackgroundImage.getWidth(null);
      GroundHeight=BackgroundImage.getHeight(null);
    }

    public void DrawBackground(Graphics2D gd, ImageObserver ImgObs) {

       for(int i=0;i<=GroundWidth;i++){
         for(int j=0;j<=GroundHeight;j++){
           gd.drawImage(BackgroundImage,i*GroundWidth,j*GroundHeight,GroundWidth,GroundHeight,ImgObs);
          }
       }
        
    }

}
