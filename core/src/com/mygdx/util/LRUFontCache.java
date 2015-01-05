package com.mygdx.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;


import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * star-hunter-android
 * Created on  9/24/2014 : 4:41 PM
 */
public class LRUFontCache {

    private static final String TAG = "FontCache";
    private final Map<String, BitmapFont> cacheMap;
    private static int MAX_CACHE_SIZE = 5;
    private static LRUFontCache fontCache;

    public static void initAll() {
        dispose();
         
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/LuckiestGuy.ttf"));
        BitmapFont tmpBitmapFont;
        MAX_CACHE_SIZE = SHText.Size.values().length;
        for (SHText.Size fontSize : SHText.Size.values()) {
            tmpBitmapFont = generator.generateFont(fontSize.size);
            tmpBitmapFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
            tmpBitmapFont.setScale(1);
            put(fontSize.name() + SHText.Font.BEASTFORMER, tmpBitmapFont);
        }
        generator.dispose();
    }

    private static LRUFontCache getCache() {
        if (fontCache == null) {
            fontCache = new LRUFontCache(MAX_CACHE_SIZE);
        }

        return fontCache;
    }

    private LRUFontCache(final int cacheSize) {
        this.cacheMap = new LinkedHashMap<String, BitmapFont>(cacheSize, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, BitmapFont> eldest) {
                return size() > cacheSize;
            }
        };
    }

    public static synchronized void put(String key, BitmapFont elem) {
         
        getCache().cacheMap.put(key, elem);
    }

    public static synchronized BitmapFont get(String key) {
        BitmapFont bitmapfont = getCache().cacheMap.get(key);
        if (bitmapfont == null) {
             
        } else {
             
        }
        return bitmapfont;
    }

    public static void dispose() {
        if (fontCache != null) {
            fontCache.cacheMap.clear();
            fontCache = null;
        }
    }

    public static synchronized int size() {
        return getCache().cacheMap.size();
    }

    public static synchronized Collection<BitmapFont> values() {
        return getCache().cacheMap.values();
    }

    public static synchronized Collection<String> keys() {
        return getCache().cacheMap.keySet();
    }

    public static synchronized BitmapFont atomicGetAndSet(String key, BitmapFont elem) {
        BitmapFont result = get(key);
        put(key, elem);
        return result;
    }
}
