package com.immunewars.game.screens;

import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Texture;
import com.immunewars.game.ImmuneWars;
import com.immunewars.game.minigameBackend.MinigamePresets;
import com.immunewars.game.minigameBackend.SpeedTyping.Box;
import com.immunewars.game.GameConfig;
import com.immunewars.game.screens.*;

public class SpeedTypingScreen implements Screen
{
    Boolean isGameTerminated = false;
    int winTreshold = 1000;
    int winnerType;
    ImmuneWars game;
    int cameraX = GameConfig.resolutionX; 
	int cameraY = GameConfig.resolutionY;
	OrthographicCamera camera;
	Viewport viewport;
	Stage stage;
	SpriteBatch batch;
    final int SPACE_BETWEEN_BOXES = 10;
    ArrayList<Box> boxes = new ArrayList<Box>();
    TextField textField; 
    String word;
    Label label;
    Group boxGroup;
    Group letters;
    String wordList[] = MinigamePresets.SpeedTyping.wordList;
    String wordDefinitionList[] = MinigamePresets.SpeedTyping.wordDefinitions;
    int score = 0;
    Label scoreLabel;
    Boolean bodyWin;

    private float timeCount;
    private int gameTimer;
    private Label timerLabel;

    private Label meaningLabel;
    TheMapScreen mainMapScreen;

    public SpeedTypingScreen(ImmuneWars game, TheMapScreen mainMapScreen)
    {
        this.mainMapScreen = mainMapScreen;
        this.game = game;
        gameTimer = 60;

        Skin skin = new Skin(Gdx.files.internal("uiskin.json")); // sets the font and all; across all letter-related-elements

        boxGroup = new Group();
        letters = new Group();

        textField = new TextField("", skin);
        textField.setVisible(false);

        label = new Label("", skin);
        label.setPosition(600, 700);

        timerLabel = new Label((" TIME REMAINING: "+ String.valueOf(gameTimer)), skin);
        timerLabel.setPosition(0, cameraY - 50);

        meaningLabel = new Label("mehmetcan", skin);
        meaningLabel.setPosition(0, 100);
        
        
        camera = new OrthographicCamera();
		camera.setToOrtho(false, cameraX, cameraY);
		
        viewport = new StretchViewport(cameraX, cameraY, camera);
		viewport.apply();
		
		scoreLabel = new Label("Score: ", skin);
        scoreLabel.setPosition(0, cameraY - 20);
        
        stage = new Stage();
        stage.setViewport(viewport);
        Gdx.input.setInputProcessor(stage);
        
        stage.addActor(textField);
        stage.addActor(label);
        stage.addActor(boxGroup);
        stage.addActor(letters);
        stage.addActor(scoreLabel);
        stage.setKeyboardFocus(textField);
        stage.addActor(timerLabel);
        stage.addActor(meaningLabel);

        Random rand = new Random();
        int a = rand.nextInt(wordList.length);
        newWord(wordList[a]);
        meaningLabel.setText( "Meaning: " + wordDefinitionList[a]); //TODO
    }

    @Override
    public void render(float delta) 
    {
        this.update(delta);
        if( this.isGameTerminated()){
            TheMapScreen.score += score;
            game.setScreen(mainMapScreen);
            //send the data idk
        }
       
        System.out.println(textField.getText());
        scoreLabel.setText("Score: " + score);
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        for (int i = 0; i < textField.getText().length(); i++)
        {
            if (i >= boxes.size())
            {
                break;
            }
            boxes.get(i).updateLetter(textField.getText());
        }
        for (int i = textField.getText().length(); i < word.length(); i++)
        {
            boxes.get(i).resetLetter();
        }

        if (checkWord())
        {
            score++;

            textField.setText("");
            
            for(int i = 0; i < boxes.size(); i++)
            {
                boxes.get(i).delete();
            }
            Random rand = new Random();
            int a = rand.nextInt(wordList.length);
            newWord(wordList[a]);
            meaningLabel.setText( "Meaning: " + wordDefinitionList[a]); 
        }

        if (isGameTerminated())
        {
            game.setScreen(new TheMapScreen(game));
        }

        stage.act();
        stage.draw();
    }

    public boolean checkWord()
    {
        for (Box box : boxes)
        {
            if (!box.check())
            {
                return false;
            }
        }
        return true;
    }

    
    public void newWord(String  newWord)
    {
        word = newWord;
        label.setText(word);
        int indent = (GameConfig.resolutionX -(word.length() * Box.BOX_WIDTH + (word.length() - 1) * SPACE_BETWEEN_BOXES)) / 2;
        boxes = new ArrayList<Box>();
        for (int i = 0; i <  word.length(); i++)
        {
            Box box = new Box(indent + i * (Box.BOX_WIDTH + SPACE_BETWEEN_BOXES) , letters, word, i);
            boxes.add(box);
            boxGroup.addActor(box);
        }
        textField.setMaxLength(word.length());
    }

    public void update(float dt){
        timeCount += dt;
        if(timeCount >= 1){ // 1 second
            gameTimer--;
            timerLabel.setText(" TIME REMAINING: " + String.format("%02d", gameTimer));
            timeCount = 0;
        }
    }

    public boolean isGameTerminated()
    {
        if((gameTimer <= 0) || (score >= winTreshold))
        {
            if(score >= winTreshold)
            {
                bodyWin = true;

                return true;
            }
            else
            {
                bodyWin = false;
                return true;
            }
        }
        else
        {
            return false;
        }
    }

   public boolean getBodyWin()
    {
        return bodyWin;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {}

    @Override
    public void pause() {
      
    }

    @Override
    public void resume() {
     
    }

    @Override
    public void hide() {
      
    }

    @Override
    public void dispose() {
       
    }

}