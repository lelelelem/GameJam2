
package com.mygdx.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;


public class GJAssetmanager extends AssetManager {

    private static GJAssetmanager myAssetManager;

    private GJAssetmanager() {

    }

    public static void init() {
        myAssetManager = new GJAssetmanager();
    }

    public static GJAssetmanager get() {
        return myAssetManager;
    }

    public void loadAnimationPacks(String filepath) {
        if (!myAssetManager.isLoaded(filepath + "/attack.pack")) {
            myAssetManager.load(filepath + "/attack.pack", TextureAtlas.class);
        }
        if (!myAssetManager.isLoaded(filepath + "/hurt.pack")) {
            myAssetManager.load(filepath + "/hurt.pack", TextureAtlas.class);
        }
        if (!myAssetManager.isLoaded(filepath + "/dead.pack")) {
            myAssetManager.load(filepath + "/dead.pack", TextureAtlas.class);
        }
        if (!myAssetManager.isLoaded(filepath + "/preview.pack")) {
            myAssetManager.load(filepath + "/preview.pack", TextureAtlas.class);
        }

    }

    public TextureAtlas getAnimationPacks(String filePath) {
        if (myAssetManager.isLoaded(filePath)) {
            return myAssetManager.get(filePath, TextureAtlas.class);
        }
        return null;
    }

}
