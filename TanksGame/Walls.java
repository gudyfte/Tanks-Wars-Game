package TanksGame;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Walls extends tanksGame{     

    private ArrayList<DrawWalls> regular_wall = new ArrayList<>();
    private ArrayList<DrawWalls> indestructible_wall=new ArrayList<>();

    Walls() {

       PlaceWallsOnMapBackground();

    }


    public void PlaceWallsOnMapBackground(){
       int BorderX=1476;
       int BorderY=1156;

        int i=0;
        while(i<BorderY/31){

            indestructible_wall.add(new DrawWalls(BorderX/2+704, i * (BorderY/36)));

            indestructible_wall.add(new DrawWalls(BorderX/2-737, i * (BorderY/36)));

          for(int j=-710;j<=700;j+=30){
            indestructible_wall.add(new DrawWalls(BorderX/2+j, i * (BorderY)));
            indestructible_wall.add(new DrawWalls(BorderX/2+j, i * (BorderY)+1125));
          }

          for(int j=-705;j<=-525;j+=30){
            indestructible_wall.add(new DrawWalls(BorderX/2+j, i * (BorderY)+577));
          }

          for(int j=433;j<=673;j+=30){
            indestructible_wall.add(new DrawWalls(BorderX/2+j, i * (BorderY)+672));
          }

          for(int j=646;j<=706;j+=30){
           
           indestructible_wall.add(new DrawWalls(i*(BorderY)-288,BorderX/2-j));
           
          }

          for(int j=-356;j<=-296;j+=30){
             indestructible_wall.add(new DrawWalls(i*(BorderY)-438,BorderX/2-j));
          }

          for(int j=-20;j<=130;j+=30){
            indestructible_wall.add(new DrawWalls(BorderX/2+j, i * (BorderY)+1003));
           
          }

          for(int j=10;j<=130;j+=30){
            
           
            indestructible_wall.add(new DrawWalls(BorderX/2+j, i * (BorderY)+124));
          }

          for(int j=159;j<=579;j+=30){
            indestructible_wall.add(new DrawWalls(BorderX/2+j, i * (BorderY)+248));
          }
         
          for(int j=40;j<=460;j+=30){
           
           regular_wall.add(new DrawWalls(i*(BorderY)-260,BorderX/2-j));
           
          }

          for(int j=370;j<=490;j+=30){
            regular_wall.add(new DrawWalls(i*(BorderY)-290,BorderX/2-j));
            
          }
          
          for(int j=190;j<=340;j+=30){
             indestructible_wall.add(new DrawWalls(i*(BorderY)-290,BorderX/2-j));
          }

          for(int j=40;j<=160;j+=30){
            regular_wall.add(new DrawWalls(i*(BorderY)-290,BorderX/2-j));
          }

          for(int j=160;j<=490;j+=30){
	    regular_wall.add(new DrawWalls(i*(BorderY)-320,BorderX/2-j));
          }

          for(int j=40;j<=70;j+=30){
           regular_wall.add(new DrawWalls(i*(BorderY)-320,BorderX/2-j));
           regular_wall.add(new DrawWalls(i*(BorderY)-350,BorderX/2-j));
          }

          for(int j=160;j<=190;j+=30){
            regular_wall.add(new DrawWalls(i*(BorderY)-350,BorderX/2-j));
          }

          for(int j=70;j<=190;j+=30){
            regular_wall.add(new DrawWalls(i*(BorderY)-380,BorderX/2-j));
          }

          for(int j=-50;j<=40;j+=30){
            indestructible_wall.add(new DrawWalls(i*(BorderY)-380,BorderX/2-j));
          }

          for(int j=-140;j<=-80;j+=30){
             regular_wall.add(new DrawWalls(i*(BorderY)-380,BorderX/2-j));
          }

          for(int j=-110;j<=190;j+=30){
            
            regular_wall.add(new DrawWalls(i*(BorderY)-410,BorderX/2-j));
          }

         for(int j=-7;j<=323;j+=30){
          indestructible_wall.add(new DrawWalls(BorderX/2-j, i * (BorderY)-278));
         }
         i++;
       }

    }

    public void drawWalls(Graphics g, ImageObserver ImgObs) {

        int i=0;
        while(i < regular_wall.size()) {
            regular_wall.get(i).RenewRegularWall();
            regular_wall.get(i).DrawRegularWall(g, ImgObs);
            i++;
        }

        int j=0;
        while(j < indestructible_wall.size()) {
            indestructible_wall.get(j).RenewIndestructibleWall();
            indestructible_wall.get(j).DrawIndestructibleWall(g, ImgObs);
            j++;
        }
    }

    public boolean regularWallCollision(int width, int height, int x, int y) {
        int i=0;
       while(i < regular_wall.size()) {
            if (regular_wall.get(i).RegularWallCollision(width, height, x, y)) {
            
                return true;
            }
          i++;
        }
        return false;
    }

    public boolean indestructibleWallCollision(int width, int height, int x, int y) {
       int i=0;
       while(i < indestructible_wall.size()) {
            if (indestructible_wall.get(i).IndestructibleWallCollision(width, height, x, y)) {
            
                return true;
            }
            i++;
        }
        return false;
   }

}
