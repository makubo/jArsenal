/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jarsenal;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 * @author MaxON
 */
public class FrameCreator {

    private int[][] landscape;
    private GraphicsContext frame;
    private int xPosition;
    private int yPosition;
    private int tileSize;
    private int frameWidth;
    private int frameHeight;

    public FrameCreator(GraphicsContext frame) {
        this.frame = frame;
        frameWidth = (int) this.frame.getCanvas().getWidth();
        frameHeight = (int) this.frame.getCanvas().getHeight();
        setTileSize(24);
        Texture.setSummer();
    }

    private void buildFrame(){
        
        int startXtile = xPosition / tileSize;
        int startYtile = yPosition / tileSize;
        
        int xCoord = 0 - xPosition % tileSize;
        int yCoord = 0 - yPosition % tileSize;
        
        //int frameWidth = (int) frame.getCanvas().getWidth();
        //int frameHeight = (int) frame.getCanvas().getHeight();
        
        int verticalTileCount = frameWidth / tileSize + 1;
        int horisontalTileCount = frameHeight / tileSize + 1;
        
        if ( frameWidth % tileSize != 0 ) {
            verticalTileCount++;
        }
        if ( frameHeight % tileSize != 0 ) {
            horisontalTileCount++;
        }
        
        int tileX;
        int tileY;
        
        int startY = yCoord;
        
        Image tile = null;
        for ( int x = 0; x < verticalTileCount; x++) {
            for ( int y = 0; y < horisontalTileCount; y++){
                //System.out.println("X = " + x + " Y = "+ y);
                if (x + startXtile < landscape.length){
                    tileX = x + startXtile;
                } else {
                    tileX = 0 + ( x + startXtile - landscape.length);
                }
                if (y + startYtile < landscape.length){
                    tileY = y + startYtile;
                } else {
                    tileY = 0 + ( y + startYtile - landscape.length);
                }
                
                if (landscape[tileX][tileY] != 0) {
                    tile = Texture.getTile(landscape[tileX][tileY]);
                } else {
                    tile = Texture.getTile("0000");
                }
//                switch ( landscape[tileX][tileY] ) {
//                    case 0:{
//                        tile = Texture.getDeepOcean();
//                                //new Image("file:graphics/0015.bmp");
//                        break;
//                    }
//                    case 1:{
//                        tile = Texture.getMiddleOcean();
//                                //new Image("file:graphics/0014.bmp");
//                        break;
//                    }
//                    case 2:{
//                        tile = Texture.getOcean();
//                                //new Image("file:graphics/0013.bmp");
//                        break;
//                    }
//                    case 3:{
//                        tile = Texture.getPlain();
//                                //new Image("file:graphics/0001.bmp");
//                        break;
//                    }
//                    case 4:{
//                        tile = Texture.getHill();
//                                //new Image("file:graphics/0028.bmp");
//                        break;
//                    }
//                    case 5:{
//                        tile = Texture.getPlateau();
//                                //new Image("file:graphics/0005.bmp");
//                        break;
//                    }
//                    case 6:{
//                        tile = Texture.getMountain();
//                                //new Image("file:graphics/0020.bmp");
//                        break;
//                    }
//                }
                //if ( tile != null ) {
                frame.drawImage(tile, xCoord, yCoord);
                yCoord = yCoord + 24;
                //}
               
            }
            xCoord = xCoord + 24;
            yCoord = startY;
            //System.out.println("---------------");
            
        }
            
        //Image grass = new Image("file:graphics/0001.bmp");
        //frame.drawImage(grass, -5, -2);
        
        
    }
    
    /**
     * @return the landscape
     */
    public int[][] getLandscape() {
        return landscape;
    }

    /**
     * @param landscape the landscape to set
     */
    public void setLandscape(int[][] landscape) {
        this.landscape = landscape;
    }

    /**
     * @return the xPosition
     */
    public int getxPosition() {
        return xPosition;
    }

    /**
     * @param xPosition the xPosition to set
     */
    public void setxPosition(int xPosition) {
        if ( xPosition > landscape.length * tileSize ) {
            this.xPosition = xPosition - landscape.length * tileSize;
        } else if ( xPosition < 0){
            this.xPosition = xPosition + landscape.length * tileSize;
        } else {
            this.xPosition = xPosition;
        }
        
    }

    /**
     * @return the yPosition
     */
    public int getyPosition() {
        return yPosition;
    }

    /**
     * @param yPosition the yPosition to set
     */
    public void setyPosition(int yPosition) {
        if ( yPosition > landscape.length * tileSize ) {
            this.yPosition = yPosition - landscape.length * tileSize;
        } else if ( yPosition < 0){
            this.yPosition = yPosition + landscape.length * tileSize;
        } else {
            this.yPosition = yPosition;
        }
        
    }

    /**
     * @return the tileSize
     */
    public int getTileSize() {
        return tileSize;
    }

    /**
     * @param tileSize the tileSize to set
     */
    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    /**
     * @return the frame
     */
    public GraphicsContext getFrame() {
        buildFrame();
        return frame;
    }
    

}
