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
    private static final int MAP_WIDTH = 16; //количество клеток по ширене
    private static final int MAP_HEIGHT = 9;//количество клеток по длине(1 клетка 80*80)

    private byte[][] data; //массив , где мы будем хранить 1 и 0;
    private ArrayList<Vector2>[][] crossroads;//список развилок
    private Texture textureGrass;
    private Texture textureRoad;

    public Map() {
        textureGrass = new Texture("grass.png");
        textureRoad = new Texture("road.png");
        data = new byte[MAP_WIDTH][MAP_HEIGHT];
        for (int i = 6; i < MAP_WIDTH; i++) {//заполняем массив.говорим где у нас 1(т.е дорога)
            data[i][4] = 1; //остальное просто само станет нулями. СТроит горизонтальную линию
        }
        for (int i = 0; i < MAP_HEIGHT; i++) {//вертикальную линию
            data[6][i] = 1;
        }
        crossroads = new ArrayList[MAP_WIDTH][MAP_HEIGHT];
        crossroads[MAP_WIDTH - 1][4] = new ArrayList<Vector2>();//у нас есть начальная точка для монстра
        crossroads[MAP_WIDTH - 1][4].add(new Vector2(-1, 0));//и тут можно идти только влево

        crossroads[6][4] = new ArrayList<Vector2>(); //развилка в этой точке
        crossroads[6][4].add(new Vector2(0, -1)); //из неё мы можем пойти вниз
        crossroads[6][4].add(new Vector2(0, 1)); //или вниз
    }
//метод , который возврашает список развилок для конкретной клетки, если null, то развилок нет
    public ArrayList<Vector2> checkCrossRoad(int x, int y) {
        return crossroads[x][y];
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < MAP_WIDTH; i++) { //проходим по массиву. чтобы отрисовать карту
            for (int j = 0; j < MAP_HEIGHT; j++) {
                if (data[i][j] == 0) {//если ноль, то рисуем траву
                    batch.draw(textureGrass, i * 80, 640 - j * 80); //640, потому что координату Y Нужно переворачивать
                }
                if (data[i][j] == 1) {//если 1 то дорогу
                    batch.draw(textureRoad, i * 80, 640 - j * 80);
                }
            }
        }
    }
    public static void readFileToByteArray() {
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
        } catch (IOException var12) {
            var12.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException var11) {
                var11.printStackTrace();
            }

        }

    }
}
