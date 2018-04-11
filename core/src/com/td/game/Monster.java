package com.td.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Monster {
    private Map map; //ссылка на карту
    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;//вектор скорости
    private float speed;//скорость
    private int group;
    private boolean checkCrossroad;//флаг для проверки. говпорит повернул ли монстр или нет(чтобы он не крутился на одном месте

    public Monster(Map map, int group, float x, float y) {
        this.map = map;//запоминает карту
        this.texture = new Texture("monster.png");
        this.position = new Vector2(x, y);//встает в какую то позицию
        this.speed = 100.0f;
        this.velocity = new Vector2(0, 0);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - texture.getWidth() / 2, position.y - texture.getHeight() / 2);
    }

    public void update(float dt) {
        position.mulAdd(velocity, dt);

        int cx = (int) (position.x / 80);//смотрим в какой клетке мы находимся
        int cy = (int) (position.y / 80);

        int dx = (int) Math.abs(cx * 80 + 40 - position.x); // смотрим на сколько далеко мы находимся от центра клетки
        int dy = (int) Math.abs(cy * 80 + 40 - position.y);

        if (map.checkCrossRoad(cx, cy) != null) {//если мы дошли до развилки то смотрим
            if (dx < 10 && dy < 10 && !checkCrossroad) {//дошли ли мы до цетра клетки, если да, то будем поварачивать, НО только если мы е поворачивали уже
                //спрашиваем у карты а куда нам двигаться. если развилки 2, то поворачиваем случайно.копируем себе и идем со скоростью
                velocity = map.checkCrossRoad(cx, cy).get(MathUtils.random(0, map.checkCrossRoad(cx, cy).size() - 1)).cpy().scl(speed);
                checkCrossroad = true;//говорим что повернули и пока не дойдем до обычной земли. поворачивать не будем
            }
        } else {
            checkCrossroad = false;//если мы идем по обычной дороге, то говорим что мы еше ни куда не поворачиваем
        }
    }
}
