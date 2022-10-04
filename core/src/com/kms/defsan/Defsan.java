package com.kms.defsan;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ScreenUtils;

import java.lang.System;
import java.util.ArrayList;
import java.util.List;

public class Defsan extends ApplicationAdapter {

    private final int t = 16;
    private int level = 0;
    private int expectedNextLevel = 1;
    private boolean doesHoldKey;
    private int count = 0;
    private float time = 0f;
    private float period = 0.2f;

    private Texture neotpustit, playerImage, sheet, bulletImage;
    private TextureRegion[] tiles;
    public Rectangle player;
    private List<Eye> eyes = new ArrayList<>();
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private int[][] map;

    @Override
    public void create() {

        //textures
        neotpustit = new Texture(Gdx.files.internal("rEzYKUHmMWg.jpg"));
        sheet = new Texture(Gdx.files.internal("sheet.png"));
        tiles = new TextureRegion[10];
        bulletImage = new Texture(Gdx.files.internal("bullet.png"));
        for (int i = 0; i < 5; i++) {
            tiles[i] = new TextureRegion(sheet, i * 16, 0, 16, 16);
        }
        /*
        [TILES]
        0 WALL
        1 FLOOR
        2 DOOR
        3 EYE ENEMY
        4 KEY
        */

        //levels
        map = new int[4][256];
        JsonReader json = new JsonReader();
        JsonValue base = json.parse(Gdx.files.internal("levels.json"));
        JsonValue level_1 = base.get("level_1");
        JsonValue level_2 = base.get("level_2");
        map[0] = level_1.asIntArray();
        map[1] = level_2.asIntArray();

        //player
        player = new Rectangle();
        player.x = 64;
        player.y = -64;
        player.height = t;
        player.width = t;
        playerImage = new Texture(Gdx.files.internal("player.png"));

        //camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 200, 150);

        //batch
        batch = new SpriteBatch();

        System.out.println(level_1);
    }

    @Override
    public void render() {

        ScreenUtils.clear(0, 0.2f, 0.2f, 1);
        camera.position.set(player.getX() + 8, player.getY() + 8, 0);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(neotpustit, -666, -666, 160, 120);
        for (int i = 0; i < map[level].length; i++) {
            if (map[level][i] == 0) {
                batch.draw(tiles[0], t * (i % 16), t * -(i / 16));
            }
            if (map[level][i] == 1) {
                batch.draw(tiles[1], t * (i % 16), t * -(i / 16));
            }
            if (map[level][i] == 2) {
                batch.draw(tiles[2], t * (i % 16), t * -(i / 16));
            }
            if (map[level][i] == 3) {
                batch.draw(tiles[3], t * (i % 16), t * -(i / 16));
                eyes.add(new Eye(new Rectangle(t * (i % 16), t * -(i / 16), t, t)));
            }
            if (map[level][i] == 4) {
                batch.draw(tiles[4], t * (i % 16), t * -(i / 16));
            }

        }

        // what the fuck
        time += Gdx.graphics.getDeltaTime();

        if (count < 3) {
            if (time > period) {
                for (Eye eye : eyes) {
                    eye.addBullet();
                }
            }
        }

        if (time > period) {
            time -= period;
            count++;
            if (count > 6) count = 0;
        }

        for (Eye eye : eyes) {
            eye.moveBullet(player.x, player.y, time, period);
            for (Rectangle rectangle : eye.getBulletList()) {
                batch.draw(bulletImage, rectangle.x, rectangle.y);
            }
        }

        if (level == expectedNextLevel) { // things to do when switching level
            for (Eye eye : eyes) {
                eye.removeBullets();
            }
            eyes.removeAll(eyes);
            expectedNextLevel++;
        }

        batch.draw(playerImage, player.x, player.y);

        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.W)) player.y += 100 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.S)) player.y -= 100 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.A)) player.x -= 100 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.D)) player.x += 100 * Gdx.graphics.getDeltaTime();

        if (isCollision()) {
            if (Gdx.input.isKeyPressed(Input.Keys.W)) player.y -= 100 * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyPressed(Input.Keys.S)) player.y += 100 * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyPressed(Input.Keys.A)) player.x += 100 * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyPressed(Input.Keys.D)) player.x -= 100 * Gdx.graphics.getDeltaTime();
        }
    }

    @Override
    public void dispose() {
        playerImage.dispose();
        batch.dispose();
    }

    private boolean checkCollision(float r1x, float r1y, float r1w, float r1h, float r2x, float r2y) {
        return r1x + r1w >= r2x &&             // r1 right edge past r2 left
                r1x <= r2x + (float) 16 &&      // r1 left edge past r2 right
                r1y + r1h >= r2y &&             // r1 top edge past r2 bottom
                r1y <= r2y + (float) 16;        // r1 bottom edge past r2 top
    }

    private boolean isCollision() {
        for (int i = 0; i < map[level].length; i++) {
            if (map[level][i] != 1 && map[level][i] != 3) { // COLLIDES NOT WITH FLOOR OR EYE
                if (checkCollision(player.x, player.y, player.width, player.height / 2, t * (i % 16), t * -(i / 16))) {
                    if (map[level][i] == 2 && doesHoldKey) // IF COLLIDES WITH DOOR, THEN CHANGE THE LEVEL (IF HOLDS KEY)
                        level++;
                    if (map[level][i] == 4) { // IF COLLIDES WITH KEY, PICK UP KEY
                        doesHoldKey = true;
                        map[level][i] = 1;
                    }
                    return true;
                }
            }
        }
        return false;
    }

}
