package com.kms.defsan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Eye {
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

    public void moveBullet() {
        for (Rectangle bullet : bulletList) {
            bullet.y += 20 * Gdx.graphics.getDeltaTime();
        }
    }

    public void addBullet() {
        bulletList.add(new Rectangle(rectangleEye.x, rectangleEye.y, 16, 16));
    }
}
