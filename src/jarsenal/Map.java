/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jarsenal;

import java.util.Random;

/**
 *
 * @author MaxON
 */

public class Map {

    private final int[][] landscape;
    private int[][] fakeLandscape;
    private int[][] tileMap;
    private final int size;
    private static Random rnd;

    public Map(int[][] landscape, int size, int seed) {
        this.landscape = landscape;
        this.size = size;
        generateTileMap(seed);
    }

    private void generateTileMap(int seed) {
        tileMap = new int[size][size];
        
        rnd = new Random(seed);
        //int[][] 
        fakeLandscape = copyArray(landscape);

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                switch (landscape[x][y]) {
                    case 0: {
                        fakeLandscape[x][y] = 3;
                        break;
                    }
                    case 1: {
                        fakeLandscape[x][y] = 2;
                        break;
                    }
                    case 2: {
                        fakeLandscape[x][y] = 1;
                        break;
                    }
                    case 3: {
                        fakeLandscape[x][y] = 0;
                        break;
                    }
                }
            }
        }

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                //int value = fakeLandscape[x][y];
                tileMap[x][y] = fakeLandscape[x][y] * 1000;
                if (isLeftTopCorner(x, y)) {
                    tileMap[x][y] += 1;
                } else if (isTopNormal(x, y)) {
                    tileMap[x][y] += 2 + randomBeech(tileMap[x][y]);
                } else if (isRightTopCorner(x, y)) {
                    tileMap[x][y] += 3;
                } else if (isRightNormal(x, y)) {
                    tileMap[x][y] += 4 + randomBeech(tileMap[x][y]);
                } else if (isRightBottomCorner(x, y)) {
                    tileMap[x][y] += 5;
                } else if (isBottomNormal(x, y)) {
                    tileMap[x][y] += 6 + randomBeech(tileMap[x][y]);
                } else if (isLeftBottomCorner(x, y)) {
                    tileMap[x][y] += 7;
                } else if (isLeftNormal(x, y)) {
                    tileMap[x][y] += 8 + randomBeech(tileMap[x][y]);
                } else if (isLeftCape(x, y)) {
                    tileMap[x][y] += 9;
                } else if (isHorisontal(x, y)) {
                    tileMap[x][y] += 10;
                } else if (isRightCape(x, y)) {
                    tileMap[x][y] += 11;
                } else if (isTopCape(x, y)) {
                    tileMap[x][y] += 12;
                } else if (isVertical(x, y)) {
                    tileMap[x][y] += 13;
                } else if (isBottomCape(x, y)) {
                    tileMap[x][y] += 14;
                }
            }
        }
    }

    /**
     * @return the fakeLandscape
     */
    public int[][] getLandscape() {
        return landscape;
    }

    /**
     * @return the tileMap
     */
    public int[][] getTileMap() {
        return tileMap;
    }

    private int randomBeech( int value ) {
        if ( value == 4000 ) {
            return rnd.nextInt(2) * 100;
        }
        return 0;
    }
    
    private int next(int x) {
        if (x == size - 1) {
            return 0;
        } else {
            return x + 1;
        }
    }

    private int prev(int x) {
        if (x == 0) {
            return size - 1;
        } else {
            return x - 1;
        }
    }

    private boolean isLeftTopCorner(int x, int y) {
        if (fakeLandscape[x][y] <= fakeLandscape[x][next(y)]
                && fakeLandscape[x][y] <= fakeLandscape[next(x)][y]
                && fakeLandscape[x][y] > fakeLandscape[x][prev(y)]
                && fakeLandscape[x][y] > fakeLandscape[prev(x)][y]) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isTopNormal(int x, int y) {
        if (fakeLandscape[x][y] <= fakeLandscape[prev(x)][y]
                && fakeLandscape[x][y] <= fakeLandscape[next(x)][y]
                && fakeLandscape[x][y] > fakeLandscape[x][prev(y)]
                && fakeLandscape[x][y] <= fakeLandscape[x][next(y)]) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isRightTopCorner(int x, int y) {
        if (fakeLandscape[x][y] == fakeLandscape[prev(x)][y]
                && fakeLandscape[x][y] == fakeLandscape[x][next(y)]
                && fakeLandscape[x][y] > fakeLandscape[x][prev(y)]
                && fakeLandscape[x][y] > fakeLandscape[next(x)][y]) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isRightNormal(int x, int y) {
        if (fakeLandscape[x][y] <= fakeLandscape[prev(x)][y]
                && fakeLandscape[x][y] > fakeLandscape[next(x)][y]
                && fakeLandscape[x][y] == fakeLandscape[x][prev(y)]
                && fakeLandscape[x][y] == fakeLandscape[x][next(y)]) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isRightBottomCorner(int x, int y) {
        if (fakeLandscape[x][y] == fakeLandscape[prev(x)][y]
                && fakeLandscape[x][y] > fakeLandscape[next(x)][y]
                && fakeLandscape[x][y] == fakeLandscape[x][prev(y)]
                && fakeLandscape[x][y] > fakeLandscape[x][next(y)]) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isBottomNormal(int x, int y) {
        if (fakeLandscape[x][y] == fakeLandscape[prev(x)][y]
                && fakeLandscape[x][y] == fakeLandscape[next(x)][y]
                && fakeLandscape[x][y] == fakeLandscape[x][prev(y)]
                && fakeLandscape[x][y] > fakeLandscape[x][next(y)]) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isLeftBottomCorner(int x, int y) {
        if (fakeLandscape[x][y] > fakeLandscape[prev(x)][y]
                && fakeLandscape[x][y] == fakeLandscape[next(x)][y]
                && fakeLandscape[x][y] == fakeLandscape[x][prev(y)]
                && fakeLandscape[x][y] > fakeLandscape[x][next(y)]) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isLeftNormal(int x, int y) {
        if (fakeLandscape[x][y] > fakeLandscape[prev(x)][y]
                && fakeLandscape[x][y] <= fakeLandscape[next(x)][y]
                && fakeLandscape[x][y] == fakeLandscape[x][prev(y)]
                && fakeLandscape[x][y] == fakeLandscape[x][next(y)]) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isLeftCape(int x, int y) {
        if (fakeLandscape[x][y] > fakeLandscape[prev(x)][y]
                && fakeLandscape[x][y] == fakeLandscape[next(x)][y]
                && fakeLandscape[x][y] > fakeLandscape[x][prev(y)]
                && fakeLandscape[x][y] > fakeLandscape[x][next(y)]) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isHorisontal(int x, int y) {
        if (fakeLandscape[x][y] == fakeLandscape[prev(x)][y]
                && fakeLandscape[x][y] == fakeLandscape[next(x)][y]
                && fakeLandscape[x][y] > fakeLandscape[x][prev(y)]
                && fakeLandscape[x][y] > fakeLandscape[x][next(y)]) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isRightCape(int x, int y) {
        if (fakeLandscape[x][y] == fakeLandscape[prev(x)][y]
                && fakeLandscape[x][y] > fakeLandscape[next(x)][y]
                && fakeLandscape[x][y] > fakeLandscape[x][prev(y)]
                && fakeLandscape[x][y] > fakeLandscape[x][next(y)]) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isTopCape(int x, int y) {
        if (fakeLandscape[x][y] > fakeLandscape[prev(x)][y]
                && fakeLandscape[x][y] > fakeLandscape[next(x)][y]
                && fakeLandscape[x][y] > fakeLandscape[x][prev(y)]
                && fakeLandscape[x][y] == fakeLandscape[x][next(y)]) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isVertical(int x, int y) {
        if (fakeLandscape[x][y] > fakeLandscape[prev(x)][y]
                && fakeLandscape[x][y] > fakeLandscape[next(x)][y]
                && fakeLandscape[x][y] == fakeLandscape[x][prev(y)]
                && fakeLandscape[x][y] == fakeLandscape[x][next(y)]) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isBottomCape(int x, int y) {
        if (fakeLandscape[x][y] > fakeLandscape[prev(x)][y]
                && fakeLandscape[x][y] > fakeLandscape[next(x)][y]
                && fakeLandscape[x][y] == fakeLandscape[x][prev(y)]
                && fakeLandscape[x][y] > fakeLandscape[x][next(y)]) {
            return true;
        } else {
            return false;
        }
    }

    private static int[][] copyArray(int[][] array) {

        int[][] dstArray = new int[array.length][array.length];

        for (int x = 0; x < array.length; x++) {
            System.arraycopy(array[x], 0, dstArray[x], 0, array[x].length);
        }

        return dstArray;
    }
}
