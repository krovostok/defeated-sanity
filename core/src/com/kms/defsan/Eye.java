package com.kms.defsan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class Eye {
    private Rectangle rectangleEye;
    private Rectangle rectangleBullet;

    public Eye(Rectangle rectangleEye) {
        this.rectangleEye = rectangleEye;
        rectangleBullet = new Rectangle(rectangleEye.x, rectangleEye.y, 16, 16);
    }

    public Rectangle getBullet() {
        return rectangleBullet;
    }

    public void moveBullet() {
        rectangleBullet.y += 100 * Gdx.graphics.getDeltaTime();
        System.out.println(rectangleBullet.y + "\n");
    }
}
