package com.kms.defsan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class Bullet {
    private Rectangle rectangleBullet;
    private boolean directionSet, toLeft, toDown;
    private float directionX, directionY;

    public Bullet(Rectangle rectangle, float directionX, float directionY) {
        this.rectangleBullet = rectangle;
        this.directionX = directionX;
        this.directionY = directionY;
            if (directionX < rectangleBullet.x) toLeft = true;
            if (directionX > rectangleBullet.x) toLeft = false;
            if (directionY < rectangleBullet.y) toDown = true;
            if (directionY > rectangleBullet.y) toDown = false;
    }

    public Rectangle getRectangleBullet() {
        return rectangleBullet;
    }

    public void setRectangleBullet(Rectangle rectangleBullet) {
        this.rectangleBullet = rectangleBullet;
    }

    public void moveBullet(int count){

        if (toLeft) rectangleBullet.x -= 50 * Gdx.graphics.getDeltaTime();
        if (!toLeft) rectangleBullet.x += 50 * Gdx.graphics.getDeltaTime();
        if (toDown) rectangleBullet.y -= 50 * Gdx.graphics.getDeltaTime();
        if (!toDown) rectangleBullet.y += 50 * Gdx.graphics.getDeltaTime();

        if (count == 6) directionSet = false;
    }
}
