package com.kms.defsan;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    private Rectangle rectangle;
    private Texture texture;

    private TextureRegion[][] sprites;
    private boolean inMotion;
    private double currentFrame;
    private int currentDirection;

    private final int SPRITE_ROW = 9;
    private final int SPRITE_COLUMN = 4;
    private final int SPRITE_WIDTH = 16;
    private final int SPRITE_HEIGHT = 16;
    private int xDelta;
    private int yDelta;

    public Player(Rectangle playerRectangle, Texture playerTexture) {
        this.rectangle = playerRectangle;
        this.texture = playerTexture;
        this.sprites = new TextureRegion[SPRITE_COLUMN][SPRITE_ROW];
        for (int i = 0; i < SPRITE_ROW; i++) {
            for (int j = 0; j < SPRITE_COLUMN; j++) {
                sprites[j][i] = new TextureRegion(playerTexture, i * SPRITE_WIDTH, j * SPRITE_HEIGHT, SPRITE_WIDTH, SPRITE_HEIGHT);
            }
        }
    }

    public void updateDirection() {
        currentFrame = ((currentFrame + 0.1) % 8);
        inMotion = true;
        if (xDelta == 0 && yDelta == 0) {
            inMotion = false;
        } else if (xDelta == -1) {
            currentDirection = 2;
        } else if (xDelta == 1) {
            currentDirection = 3;
        } else if (yDelta == -1) {
            currentDirection = 0;
        } else if (yDelta == 1) {
            currentDirection = 1;
        }
    }

    public Rectangle getPlayerRectangle() {
        return rectangle;
    }

    public Texture getTexture() {
        return texture;
    }

    public boolean isInMotion() {
        return inMotion;
    }

    public TextureRegion[][] getPlayerSprites() {
        return sprites;
    }

    public int getCurrentDirection() {
        return currentDirection;
    }

    public double getCurrentFrame() {
        return currentFrame;
    }

    public void setXDelta(int xDelta) {
        this.xDelta = xDelta;
    }

    public void setYDelta(int yDelta) {
        this.yDelta = yDelta;
    }

}
