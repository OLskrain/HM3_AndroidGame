package com.td.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.ArrayList;

public class Map {
    private static final int MAP_WIDTH = 16;//количество клеток по длине(1 клетка 80*80)
    private static final int MAP_HEIGHT = 9;//количество клеток по высоте


    private ArrayList<Vector2>[][] crossroads;//список развилок
    private Texture textureGrass;
    private Texture textureRoad;
    String[][] masMap;

    public Map() {
        this.masMap = readFileToByteArray(MAP_HEIGHT , MAP_WIDTH);
        textureGrass = new Texture("grass.png");
        textureRoad = new Texture("road.png");

        crossroads = new ArrayList[MAP_WIDTH][MAP_HEIGHT];
        crossroads[MAP_WIDTH - 1][3] = new ArrayList<Vector2>();//у нас есть начальная точка для монстра
        crossroads[MAP_WIDTH - 1][3].add(new Vector2(-1, 0));//и тут можно идти только влево
        for (int i = 0; i < this.masMap.length ; i++) {
            for (int j = 0; j < MAP_WIDTH ; j++) {
                if(masMap[i][j].equals("1UD")){
                    crossroads[12][3] = new ArrayList<Vector2>();
                    crossroads[12][3].add(new Vector2(0, 1));
                    crossroads[12][3].add(new Vector2(0, -1));
                }
                if(masMap[i][j].equals("1L")){
                    crossroads[12][1] = new ArrayList<Vector2>();
                    crossroads[14][7] = new ArrayList<Vector2>();
                    crossroads[8][4] = new ArrayList<Vector2>();
                    crossroads[6][4] = new ArrayList<Vector2>();
                    crossroads[3][2] = new ArrayList<Vector2>();
                    crossroads[12][1].add(new Vector2(-1, 0));
                    crossroads[14][7].add(new Vector2(-1, 0));
                    crossroads[8][4].add(new Vector2(-1, 0));
                    crossroads[6][4].add(new Vector2(-1, 0));
                    crossroads[3][2].add(new Vector2(-1, 0));
                }
                if(masMap[i][j].equals("1R")){
                    crossroads[12][5] = new ArrayList<Vector2>();
                    crossroads[12][5].add(new Vector2(1, 0));
                }
                if(masMap[i][j].equals("1U")){
                    crossroads[14][5] = new ArrayList<Vector2>();
                    crossroads[8][1] = new ArrayList<Vector2>();
                    crossroads[14][5].add(new Vector2(0, 1));
                    crossroads[8][1].add(new Vector2(0, 1));
                }
                if(masMap[i][j].equals("1D")){
                    crossroads[6][7] = new ArrayList<Vector2>();
                    crossroads[3][4] = new ArrayList<Vector2>();
                    crossroads[6][7].add(new Vector2(0, -1));
                    crossroads[3][4].add(new Vector2(0, -1));
                }

            }

        }

    }
//метод , который возврашает список развилок для конкретной клетки, если null, то развилок нет
    public ArrayList<Vector2> checkCrossRoad(int x, int y) {
        return crossroads[x][y];
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < masMap.length; i++) { //проходим по массиву. чтобы отрисовать карту
            for (int j = 0; j < MAP_WIDTH; j++) {
                if (masMap[i][j].equals("0") ) {//если ноль, то рисуем траву
                    batch.draw(textureGrass,  j * 80,  640 - i * 80); //640, потому что координату Y Нужно переворачивать
                }
                if (masMap[i][j].equals("1") || masMap[i][j].equals("1R") || masMap[i][j].equals("1D") || masMap[i][j].equals("1L") || masMap[i][j].equals("1U") || masMap[i][j].equals("1UD")) {//если 1 то дорогу
                    batch.draw(textureRoad,  j * 80,  640 - i * 80);
                }
            }
        }
    }
    public String[][] readFileToByteArray(int MAP_HEIGHT, int MAP_WIDTH) {
        BufferedInputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream("map.dat"));
            out = new ByteArrayOutputStream();

            int x;
            while((x = in.read()) != -1) {
                out.write(x);
            }

            byte[] bytes = out.toByteArray();
            String str = new String (bytes);

            String [] masOne = str.split(",");
            masMap = new String[MAP_HEIGHT][MAP_WIDTH];
            int k = 0;//отдельный счетчик для одномерного массива
            for(int i = 0; i < masMap.length; i++) {
                for(int j = 0; j < masMap[i].length; j++) {
                    masMap[i][j] = masOne[k];
                    k++;
                }
            }
        } catch (IOException var12) {
            var12.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException var11) {
                var11.printStackTrace();
            }

        }return masMap;
    }
}
