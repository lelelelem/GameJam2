package com.mygdx.custom;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GJActor extends Actor {

    private Rectangle rectangle;
    private TextureRegion texture;

    public GJActor(TextureRegion image) {
        texture = image;
        rectangle = new Rectangle();
        this.setWidth(texture.getRegionWidth());
        this.setHeight(texture.getRegionHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public void setWidth(float width) {
        super.setWidth(width);
        rectangle.setWidth(width);
    }

    @Override
    public void setHeight(float height) {
        super.setHeight(height);
        rectangle.setHeight(height);
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        rectangle.setX(x);
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        rectangle.setY(y);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        rectangle.setX(x);
        rectangle.setY(y);
    }

    public void setPosition(Vector2 postition) {
        setPosition(postition.x, postition.y);
    }


}
