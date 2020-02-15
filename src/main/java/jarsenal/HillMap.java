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
public class HillMap {
    private static int[][] map;
    private static int size;
    private static Random rnd;
    private static int value;
    private static double F = 1.61803;
    
    public HillMap(){
        
    }
    
    public HillMap(int size){
        map = new int[size][size];
        HillMap.size = size;
    }
    
    public void setSeed(int seed){
        rnd = new Random(seed);
    }
    
     public void setSize(int size){
        HillMap.size = size;
    }
    
    public int[][] generateMap() {
        int[][] zeroLayer = generateZeroLayer();
        //int[][] Layer = copyArray(zeroLayer);
        
        //System.arraycopy(zeroLayer, 0, Layer, 0, zeroLayer.length);
        //Layer = ;
                
        //arrayPlus(map, getEdge(zeroLayer));
        arrayPlus(map, zeroLayer);
        //arrayPlus(map, getEdge(zeroLayer));
        int[][] firstLayer = cutLayer(zeroLayer, getEdge(zeroLayer));
        //arrayPlus(map, getEdge(zeroLayer));
        arrayPlus(map, firstLayer);
                
        int[][] secondLayer = cutLayer(firstLayer, getEdge(firstLayer));
        arrayPlus(map, secondLayer);
        
        int[][] mountineLayer = cutLayer(secondLayer, getEdge(secondLayer));
        arrayPlus(map, mountineLayer);
        
        int[][] ocean = extrudeLayer(zeroLayer, getEdge(zeroLayer));
        arrayPlus(map, ocean);
        
        int[][] middleOcean = extrudeLayer(ocean, getEdge(ocean));
        arrayPlus(map, middleOcean);
        
        int[][] deepOcean = extrudeLayer(ocean, getEdge(middleOcean));
        arrayPlus(map, deepOcean);
        
        return map;
    }

    private static int[][] generateZeroLayer() {
        int[][] layer = new int[size][size];
        int diameter = (int) (size / (F * 2) );
        
        int x;
        int y;
        
        int iteration = 1;
        while (diameter > 1) {
            System.out.println("Radius = " + diameter);
            iteration =+ (int) (iteration * F) * 3;          
            for (int i = 0; i < iteration; i++) {
                x = rnd.nextInt(size);
                y = rnd.nextInt(size);
                if (layer[x][y] == 1 || i < 3) {
                    value = 1;
                } else {
                    value = 0;
                }
                //System.out.println("x = " + x + "; y = " + y + "; Radius = " + rad + " " + value);
                drawCircle(layer, x, y, diameter);

            }
            diameter = (int) (diameter / F);
        }
        
        clean(layer);
        cleanDiagonal(layer);
        
        return layer;
    }
    
    private int[][] cutLayer(int[][] layer, int[][] edge){
        
        int[][] returnLayer = copyArray(layer);
        
        for (int x = 0; x < edge.length; x++){
            for (int y = 0; y < edge[x].length; y++){
                if (edge[x][y] != 0){
                    value = 0;
                    drawCircle(returnLayer, x, y, rnd.nextInt(4)+3);
                }
            }
        }
        clean(returnLayer);
        cleanDiagonal(returnLayer);
        return returnLayer;
    }
    
    private static int[][] extrudeLayer(int[][] layer, int[][] edge){
        
        for (int x = 0; x < edge.length; x++){
            for (int y = 0; y < edge[x].length; y++){
                if (edge[x][y] != 0){
                    value = 1;
                    drawCircle(layer, x, y, rnd.nextInt(4)+4);
                }
            }
        }
        clean(layer);
        cleanDiagonal(layer);
        return layer;
    }
    
    private static int[][] getEdge(int[][] layer){
        int[][] edge = new int[size][size];
        
        for (int x = 0; x < layer.length; x++){
            for (int y = 0; y < layer[x].length; y++){
                if (layer[x][y] == 1 && countAround(layer, x, y, layer[x][y]) != 8){
                    edge[x][y] = 98;
                }
            }
        }
         
        return edge;
    }
    
    private static int[][] getCircle(int diameter){
        //int diameter = rad * 2;
        int rad = diameter / 2;
        int pk = diameter % 2;
        int mk = 1 - pk;
        //System.out.println("pk = " + pk + "\nmk = " + mk);
        int[][] circle = new int[diameter][diameter];
        int x = 0;
        int y = rad-mk;
        int d = 3 - 2*rad;
        int center = rad;
        if (diameter != 3){ // чтобы круг с диаметром 3 был не квадратом а крестом.
            while (x < y) {
                //System.out.println("" + x + "." + y);
                circle[center+x+pk][center+y] = 1;
                circle[center+y][center+x+pk] = 1;

                circle[center-x-mk-pk][center+y] = 1;
                circle[center-y-mk][center+x+pk] = 1;

                circle[center+x+pk][center-y-mk] = 1;
                circle[center+y][center-x-mk-pk] = 1;

                circle[center-x-mk-pk][center-y-mk] = 1;
                circle[center-y-mk][center-x-mk-pk] = 1;

                if (d < 0) {
                    d= d + 4*x + 6;
                } else {
                    d= d + 4*(x-y) + 10;
                    --y;
                }
                ++x;

            }
        }
        if (x == y && pk == 0) {
            circle[center+x][center+y] = 1;
            circle[center-x-mk][center+y] = 1;
            circle[center+x][center-y-mk] = 1;
            circle[center-x-mk][center-y-mk] = 1;
        } else {
            circle[center][0] = 1;
            circle[0][center] = 1;
            circle[center][circle.length-1] = 1;
            circle[circle.length-1][center] = 1;
        }
            
        for ( x = 1 ; x < circle.length-1 ; x++){
            boolean paint = false;
            for ( y = 0 ; y < circle[x].length ; y++){
                if ( y != 0 || y != circle.length-1){
                    if (circle[x][y] == 1 && !paint && y < circle.length /2){
                        paint = true;
                    } else {
                        if (circle[x][y] == 1 && paint && y > circle.length /2){
                            paint = false;
                        }
                    }
                    if (paint){
                        circle[x][y] = 1;
                    }
                }
            }
        }
        return circle;
    }
    
    private static void drawCircle(int[][] layer, int cx, int cy, int diameter){
        int[][] circle = getCircle(diameter);
        int rx;
        int ry;
        
        if (diameter % 2 == 0){
            // rnd.nextInt(2) - дает дрожание центра круга с четным диаметром
            rx = cx - (diameter / 2) + rnd.nextInt(2);
            ry = cy - (diameter / 2) + rnd.nextInt(2);
        } else {
            rx = cx - (diameter / 2);
            ry = cy - (diameter / 2);
        }
        
        //int rx = cx - (diameter / 2);
        //int ry = cy - (diameter / 2);        
        
        for (int x = 0; x < circle.length; x++) {
            for (int y = 0; y < circle[x].length; y++) {
                if (circle[x][y] == 1) {
                    int dx = rx+x;
                    int dy = ry+y;
                    
                    if (dx < 0){
                        dx = size + dx;
                    }
                    if (dy < 0){
                        dy = size + dy;
                    }
                    if ( dx >= size){
                        dx = dx - size;
                    }
                    if ( dy >= size){
                        dy = dy - size;
                    }
                    layer[dx][dy] = value;
                }
            }
        }
    }
    
    // Удаление "заусенцев" карты
    private static void clean(int[][] layer){
        for (int x = 0; x < layer.length; x++){
            for (int y = 0; y < layer[x].length; y++){
                if ( countAround(layer, x, y, layer[x][y]) == 3){    
                    layer[x][y] = Math.abs(layer[x][y] - 1);
                }
            }
        }
    }
    
    // удаление мест где пиксели расположены в шахмотном порядке
    private static void cleanDiagonal(int[][] layer){
        for (int x = 0; x < layer.length; x++){
            for (int y = 0; y < layer[x].length; y++){
                if ( diagonalPixel(layer, x, y, layer[x][y])){
                    //System.out.println("found diagonal " + rnd.nextInt(4));
                    switch (rnd.nextInt(4)){
                        case 0: {
                            layer[x][y] = Math.abs(layer[x][y] - 1);
                            break;
                        }
                        case 1: {
                            layer[x][next(y)] = Math.abs(layer[x][next(y)] - 1);
                            break;
                        }
                        case 2: {
                            layer[next(x)][next(y)] = Math.abs(layer[next(x)][next(y)] - 1);
                            break;
                        }
                        case 3: {
                            layer[next(x)][y] = Math.abs(layer[next(x)][y] - 1);
                            break;
                        }
                    }
                }
            }
            
        }
    }
    
    private static boolean diagonalPixel(int[][] layer, int x, int y, int value){
        return layer[next(x)][y] != value && layer[x][next(y)] != value && layer[next(x)][next(y)] == value;
        
    }
    
    // посчитать количество пикселей рядом заданной величины
    private static int countAround(int[][] layer, int x, int y, int value){
        int count = 0;
        if ( layer[prev(x)][prev(y)] == value) count++;
        if ( layer[x][prev(y)] == value) count++;
        if ( layer[next(x)][prev(y)] == value) count++;
        if ( layer[next(x)][y] == value) count++;
        if ( layer[next(x)][next(y)] == value) count++;
        if ( layer[x][next(y)] == value) count++;
        if ( layer[prev(x)][next(y)] == value) count++;
        if ( layer[prev(x)][y] == value) count++;
        return count;
    }
    
    private static int next(int x){
        if ( x == size - 1){
            return 0;
        } else {
            return x + 1;
        }
    }
    
    private static int prev(int x){
        if ( x == 0){
            return size - 1;
        } else {
            return x - 1;
        }
    }
    
    private static void arrayPlus( int[][] first, int[][] second){
        for (int i = 0; i < first.length; i++){
            for ( int k = 0; k < first[i].length; k++){
                first[i][k] = first[i][k] + second[i][k];
            }
        }
//        return first;
    }

    private static int[][] copyArray(int[][] array){
        
        int[][] dstArray = new int[array.length][array.length];
        
        for (int x = 0; x < array.length; x++){
            for (int y = 0; y < array[x].length; y++){
                dstArray[x][y] = array[x][y];
            }
        }
        
        return dstArray;
    }
    
}
