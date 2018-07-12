/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jarsenal;


import java.util.Random;

/**
 *
 * @author maksim.bobylev
 */
public class DiamondSquareMap {
    final static int NOTFREE = 0;
    final static int SQUARE = 1;
    final static int DIAMOND = 2;
    static float[][] map;
    static Random rnd;
    private static int size;
    //private static int seed;
    private static int squareNoise;
    private static int diamondNoise;
    
    public DiamondSquareMap(int size){
        DiamondSquareMap.size = size;
    }
    
    public void setSeed(int seed){
        rnd = new Random(seed);
    }
    
    public int[][] generateMap(){
        squareNoise = 5;
        diamondNoise = 5;
        //bi = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

        map = new float[size][size];
        int step = size;

//        map[0][0] = rnd.nextInt(255);
//        map[0][step/2] = rnd.nextInt(255);
//        map[step/2][0] = rnd.nextInt(255);
//        map[step/2][step/2] = rnd.nextInt(255);
        for (int y = 0; y <= size - size / 4; y = y + size / 4) {
            for (int x = 0; x <= size - size / 4; x = x + size / 4) {
                map[y][x] = rnd.nextInt(2);
            }
        }

        //printMap(map);

        for (int i = 2; i <= Math.sqrt(size); i++) {
            step = step / 2;
            if (step < 1) {
                break;
            }

            //System.out.println("Step = " + step);
            for (int y = 0; y <= size - step; y = y + step) {
                for (int x = 0; x <= size - step; x = x + step) {
                    squareProceed(x, y, step);
                }
            }
            for (int y = 0; y <= size - step; y = y + step) {
                for (int x = 0; x <= size - step; x = x + step) {
                    diamondProceed(x, y, step);
                }
            }
            //drawMap(map);
        }

        //normalizeMap();
        //drawMap(map);
        return normalizeMap();
        //return map;

    }
    
    public static void printMap(float[][] map) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (x < map[y].length - 1) {
                    System.out.print("" + (int) map[y][x] + " ");
                } else {
                    System.out.println((int) map[y][x]);
                }
            }
        }
    }

    public static int checkLayer(int x, int y, int step) {
        int yCheck = (y + step) % (step * 2);
        int xCheck = (x + step) % (step * 2);
        //System.out.println("x = " + x + " xCheck = " + xCheck);
        //System.out.println("y = " + y + " yCheck = " + yCheck);
        //System.out.println(4 % 8);

        if (yCheck == 0) {
            if (xCheck == 0) {
                return SQUARE;
            } else {
                return DIAMOND;
            }
        } else if (xCheck == 0 /*&& xCheck != 1/**/) {
            return DIAMOND; // old GORISONTAL
        } else {
            return NOTFREE;
        }
    }

    private static void squareProceed(int x, int y, int step) {
        if ( checkLayer(x, y, step) == SQUARE){
                float average = (map[prevDot(y, step)][prevDot(x, step)]
                        + map[prevDot(y, step)][nextDot(x, step)]
                        + map[nextDot(y, step)][nextDot(x, step)]
                        + map[nextDot(y, step)][prevDot(x, step)]) / 4;
                map[y][x] = (float) (average + squareNoise * step * Math.pow(-1, rnd.nextInt(2) + 1));
        }
    }

    private static void diamondProceed(int x, int y, int step) {
        if ( checkLayer(x, y, step) == DIAMOND){
                float average = (map[y][prevDot(x, step)]
                        + map[y][nextDot(x, step)]
                        + map[prevDot(y, step)][x]
                        + map[nextDot(y, step)][x]) / 4;
                map[y][x] = (float) (average + diamondNoise * step * Math.pow(-1, rnd.nextInt(2) + 1));
        }
    }

    private static int nextDot(int y, int step) {
        if (y >= size - step) {
            return 0;
        } else {
            return y + step;
        }
    }

    private static int prevDot(int y, int step) {
        if (y < step) {
            return size - step;
        } else {
            return y - step;
        }
    }

    private static int[][] normalizeMap() {
        int[][] normMap = new int[size][size];
        
        float min = 0;
        float max = 0;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (min > map[x][y]) {
                    min = map[x][y];
                }
                if (max < map[x][y]) {
                    max = map[x][y];
                }
            }
        }

        //System.out.println("MAX = " + max);
        //System.out.println("MIN = " + min);
        float add = Math.abs(min);
        max = max + Math.abs(min);
        min = 0;

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                map[x][y] += add;
                normMap[x][y] = (int) Math.round(8 * Math.pow(map[x][y],2.1) / Math.pow(max,2.1));
                if ( normMap[x][y] == 8 ) {
                    normMap[x][y]--;
                }
            }
        }
        
        return normMap;
    }


}
