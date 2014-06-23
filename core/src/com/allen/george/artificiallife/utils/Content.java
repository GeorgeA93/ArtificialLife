package com.allen.george.artificiallife.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by George on 22/06/2014.
 */
public class Content {

    //TILES
    private static Texture tileSheet;
    private static final int cols = 16;
    private static final int rows = 12;
    public static TextureRegion grassTile;
    public static TextureRegion grassTileDeatail1;
    public static TextureRegion grassTileDeatail2;
    public static TextureRegion grassTileDeatail3;
    public static TextureRegion waterTopLeft;
    public static TextureRegion waterTopRight;
    public static TextureRegion waterBottomLeft;
    public static TextureRegion waterBottomRight;
    public static TextureRegion waterTop;
    public static TextureRegion waterLeft;
    public static TextureRegion waterBottom;
    public static TextureRegion waterRight;
    public static TextureRegion waterMiddle;
    public static TextureRegion bigRock1;
    public static TextureRegion bigRock2;
    public static TextureRegion bigRock3;
    public static TextureRegion bigRock4;
    public static TextureRegion longRockBase;
    public static TextureRegion longRockFore;



    public static void load(){
        loadTileSheet();

    }

    private static void loadTileSheet(){
        tileSheet = new Texture(Gdx.files.internal("tiles/terrain.png"));
        int xSlices = tileSheet.getWidth() / 32;
        int ySlices = tileSheet.getHeight() / 32;
        TextureRegion[][] res = new TextureRegion[xSlices][ySlices];
        for (int x = 0; x < xSlices; x++) {
            for (int y = 0; y < ySlices; y++) {
                res[x][y] = new TextureRegion(tileSheet, x * 32, y * 32, 32, 32);
                res[x][y].flip(false, false);
            }
        }
        waterTopLeft = res[27][2];
        waterTopRight = res[29][2];
        waterBottomLeft = res[27][4];
        waterBottomRight = res[29][4];
        waterTop = res[28][2];
        waterLeft = res[27][3];
        waterBottom = res[28][4];
        waterRight = res[29][3];
        waterMiddle = res[28][3];

        grassTile = res[1][9];
        grassTileDeatail1 = res[0][11];
        grassTileDeatail2 = res[1][11];
        grassTileDeatail3 = res[2][11];

        bigRock1 = res[24][19];
        bigRock2 = res[24][20];
        bigRock3 = res[25][19];
        bigRock4 = res[25][20];
    }
}
