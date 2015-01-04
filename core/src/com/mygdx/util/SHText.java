package com.mygdx.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;



public class SHText extends Label {

    public static enum Size {

        XSMALL(13),
        SMALL(15),
        MEDIUM(17),
        LARGE(20),
        XLARGE(25),
        XXLARGE(40),
        FOR_HEADER(35),
        FOR_HEADER_SMALL(30);

        public int size;

        Size(int size) {
            this.size = size;
        }
        
    }

    public static enum Font {
        BEASTFORMER("fonts/Beastformer.ttf");

        String fontName;

        Font(String fontName) {
            this.fontName = fontName;
        }
    }

    public SHText(CharSequence text, Size fontSize, Font font, Color color) {
        this(text, 0, 0, fontSize, font, color);
    }

    public SHText(CharSequence text, int posX, int posY, Size fontSize, Color color) {
        this(text, posX, posY, fontSize, Font.BEASTFORMER, color);
    }

    public SHText(CharSequence text, int posX, int posY, Size fontSize, Font font, Color color) {
        super(text, new Label.LabelStyle(createBitmapFont(fontSize, font), color));
        this.setX(posX);
        this.setY(posY);
        this.pack();
    }

    public void setStyle(Size fontSize, Font font, Color color){
        this.setStyle(new Label.LabelStyle(createBitmapFont(fontSize, font), color));
    }

    private static BitmapFont createBitmapFont(Size fontSize, Font font) {
        //check if loaded...
        BitmapFont cachedFont = LRUFontCache.get(fontSize.name() + font.name());
        
        if (cachedFont == null) {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(font.fontName));
            cachedFont = generator.generateFont(fontSize.size);
            cachedFont.setScale(1);
            cachedFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
            generator.dispose();
            LRUFontCache.put(fontSize.name() + font.name(), cachedFont);
        }
        
        return cachedFont;
    }

    @Override
    public void setText(CharSequence newText) {
        super.setText(newText);
        this.pack();
    }

}

