package com.immunewars.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.immunewars.game.ImmuneWars;

public class LoadingScreen implements Screen{
    SpriteBatch batch;
    Texture backgroundImage;
    LoadingScreen screen;
    /*  
    option;
    0 = Tictactoe
    1 = Space invaders
    2 = spedetype
    3 = snake
    4 = imageMatching
     */
    public LoadingScreen(ImmuneWars game, int option){
        screen = this;
        Stage satge = new Stage();
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
 // Replace with your background image path
        Label tip = new Label("null", skin);
        satge.addActor(tip);
        Button deneme = new Button(skin); 
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        batch = new SpriteBatch();
        backgroundImage = new Texture("logo.png");
        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw background image
        batch.begin();
        batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}
