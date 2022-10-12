package com.kms.defsan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Eye {
    private boolean directionSet = false, toLeft = false, toDown = false;
    private final Rectangle rectangleEye;
    private List<Bullet> bulletList = new ArrayList();

    public Eye(Rectangle rectangleEye) {
        this.rectangleEye = rectangleEye;
    }

    public List<Bullet> getBulletList() {
        return bulletList;
    }

    public void setBulletList(List<Bullet> bulletList) {
        this.bulletList = bulletList;
    }

    public void moveBullet(float x, float y, int count) {
        for (Bullet bullet : bulletList) {
            bullet.moveBullet(count);
        }
    }

    public void addBullet(float x, float y) {
        bulletList.add(new Bullet(new Rectangle(rectangleEye.x, rectangleEye.y, 16,16), x, y));
    }

    public void removeBullets() {
        bulletList.removeAll(bulletList);
    }
}
