package com.kms.defsan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Eye {
    private boolean directionSet = false, toLeft = false, toDown = false;
    private final Rectangle rectangleEye;
    private Rectangle rectangleBullet;
    private List<Rectangle> bulletList = new ArrayList();

    public Eye(Rectangle rectangleEye) {
        this.rectangleEye = rectangleEye;
    }

    public List<Rectangle> getBulletList() {
        return bulletList;
    }

    public void setBulletList(List<Rectangle> bulletList) {
        this.bulletList = bulletList;
    }

    public Rectangle getBullet() {
        return rectangleBullet;
    }

    public void moveBullet(float x, float y, float time, float period) {
        for (Rectangle bullet : bulletList) {
            if (!directionSet) {
                if (x < bullet.x) toLeft = true;
                if (x > bullet.x) toLeft = false;
                if (y < bullet.y) toDown = true;
                if (y > bullet.y) toDown = false;
                directionSet = true;
            }
            if (toLeft) bullet.x -= 50 * Gdx.graphics.getDeltaTime();
            if (!toLeft) bullet.x += 50 * Gdx.graphics.getDeltaTime();
            if (toDown) bullet.y -= 50 * Gdx.graphics.getDeltaTime();
            if (!toDown) bullet.y += 50 * Gdx.graphics.getDeltaTime();
        }
    }

    public void addBullet() {
        bulletList.add(new Rectangle(rectangleEye.x, rectangleEye.y, 16, 16));
    }

    public void removeBullets() {
        bulletList.removeAll(bulletList);
    }
}
