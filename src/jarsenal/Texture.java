/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jarsenal;

import java.io.File;
import java.util.HashMap;
import javafx.scene.image.Image;

/**
 *
 * @author MaxON
 */
public final class Texture {

//    private static Image deepOcean;
//    private static Image middleOcean;
//    private static Image ocean;
//    //private final shoal;
//    private static Image Plain;
//    private static Image Hill;
//    private static Image Plateau;
//    private static Image Mountain;
    private static String season;

    private static HashMap<String, Image> Tiles;
    
    private Texture() {
    }

    public static void setSummer(){
        season = "summer";
        loadTextures();
    }
    
    private static void loadTextures() {
//        deepOcean = new Image("file:graphics/" + season + "/0000.bmp");
//        middleOcean = new Image("file:graphics/" + season + "/1000.bmp");
//        ocean = new Image("file:graphics/" + season + "/2000.bmp");
//        //shoal;
//        Plain = new Image("file:graphics/" + season + "/3000.bmp");
//        Hill = new Image("file:graphics/" + season + "/4000.bmp");
//        Plateau = new Image("file:graphics/" + season + "/5000.bmp");
//        Mountain = new Image("file:graphics/" + season + "/6000.bmp");
        
        Tiles = new HashMap<>();
        File dir = new File("graphics/" + season + "/landscape");
        for (File file : dir.listFiles()) {
            if ( file.isFile() ){
                System.out.println(file.getName().substring(0, file.getName().indexOf('.')) + " " + file.getPath());
                String key = file.getName().substring(0, file.getName().indexOf('.'));
                Tiles.put( key, new Image("file:" + file.getPath()));
            }
        }
        //System.out.println(Tiles.size());
    }
    
    public static Image getTile(String key){
        return (Image) Tiles.get(key);
    }
    
    public static Image getTile(int key){
        return getTile(String.format("%04d", key));
		//return getTile(String.valueOf(key));
    }

}
