package com.immunewars.game.screens;

import java.net.http.WebSocket.Listener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.text.StyledEditorKit.ForegroundAction;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Timer;
import com.immunewars.game.ImmuneWars;
import com.immunewars.game.minigameBackend.MainMap.Edge.Edge;
import com.immunewars.game.minigameBackend.MainMap.Edge.EdgeData;
import com.immunewars.game.minigameBackend.MainMap.Node.Node;
import com.immunewars.game.minigameBackend.MainMap.Node.NodeData;

public class TheMapScreen implements Screen 
{
    private ImmuneWars game;
    private ShapeRenderer shapeRenderer;
    private Stage stage;
    private Image background;
    private Random random = new Random();
    private int i = 2;
    private Sprite backgroundSprite;
    private Boolean minigameResult;
    boolean enemyTurn = false;
    public boolean nodePressed = false;
    private SpriteBatch spriteBatch = new SpriteBatch();
    Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
    public static int score = 0;

    
    ArrayList<NodeData> nodes = new ArrayList<NodeData>();
    ArrayList<EdgeData> edges = new ArrayList<EdgeData>();
    ArrayList<NodeData> enemyNodes = new ArrayList<NodeData>();
    NodeData brainNode = new NodeData(0,60 , 350, "Brain", 0, 70 / i,this);
    NodeData mouthNode = new NodeData(2, 170, 450, "Mouth", 0, 35 / i,this);
    NodeData noseNode = new NodeData(3, 130,490 , "Nose", 0, 30 / i,this);
    NodeData heartNode = new NodeData(4, 300, 480, "Heart", 0, 50 / i,this);
    NodeData lungsNode = new NodeData(5, 300, 350, "Lungs", 0, 50 / i,this);
    NodeData stomachNode = new NodeData(6, 550, 440, "Stomach", 0, 50 / i,this);
    NodeData liverNode = new NodeData(7, 500, 350, "Liver", 0, 40 / i,this);
    NodeData kidneysNode = new NodeData(8, 600, 350, "Kidneys", 0, 60 / i,this);
    NodeData intestinesNode = new NodeData(9, 650, 460, "Intestines", 0, 30 / i,this);
    NodeData armNode = new NodeData(10, 500, 530, "Arm", 0, 30 / i,this);
    NodeData legNode = new NodeData(11, 850, 420, "Leg", 0, 30 / i,this);
    NodeData footNode = new NodeData(12, 1200, 400, "Foot", 0, 20 / i,this);
    NodeData handNode = new NodeData(13,600 , 570, "Hand", 0, 30 / i,this);

    EdgeData brainMouthEdge = new EdgeData(brainNode, mouthNode);
    EdgeData brainNoseEdge = new EdgeData(brainNode, noseNode);
    EdgeData brainHeartEdge = new EdgeData(brainNode, heartNode);
    EdgeData heartLungsEdge = new EdgeData(heartNode, lungsNode);
    EdgeData heartStomachEdge = new EdgeData(heartNode, stomachNode);
    EdgeData mouthLungEdge = new EdgeData(mouthNode, lungsNode);
    EdgeData mouthNoseEdge = new EdgeData(mouthNode, noseNode);
    EdgeData stomachIntestinesEdge = new EdgeData(stomachNode, intestinesNode);
    EdgeData stomachLiverEdge = new EdgeData(stomachNode, liverNode);
    EdgeData stomachKidneysEdge = new EdgeData(stomachNode, kidneysNode);
    EdgeData intestinesLiverEdge = new EdgeData(intestinesNode, liverNode);
    EdgeData intestinesKidneysEdge = new EdgeData(intestinesNode, kidneysNode);
    EdgeData noseLungEdge = new EdgeData(noseNode, lungsNode);
    EdgeData mouthStomachEdge = new EdgeData(mouthNode, stomachNode);
    EdgeData armHandEdge = new EdgeData(armNode, handNode);
    EdgeData heartArmEdge = new EdgeData(heartNode, armNode);
    EdgeData kidneyLEdge = new EdgeData(kidneysNode, legNode);
    EdgeData legFootEdge = new EdgeData(legNode, footNode);
    EdgeData intestinesLegEdge = new EdgeData(intestinesNode, legNode);

    

    public TheMapScreen(ImmuneWars game) 
    {
        this.game = game;
        this.minigameResult = minigameResult;
        nodes.add(brainNode);
        nodes.add(mouthNode);
        nodes.add(noseNode);
        nodes.add(heartNode);
        nodes.add(lungsNode);
        nodes.add(stomachNode);
        nodes.add(liverNode);
        nodes.add(kidneysNode);
        nodes.add(intestinesNode);
        nodes.add(armNode);
        nodes.add(legNode);
        nodes.add(footNode);
        nodes.add(handNode);

        edges.add(brainMouthEdge);
        edges.add(brainNoseEdge);
        edges.add(brainHeartEdge);
        edges.add(heartLungsEdge);
        edges.add(heartStomachEdge);
        edges.add(mouthLungEdge);
        edges.add(mouthNoseEdge);
        edges.add(stomachIntestinesEdge);
        edges.add(stomachLiverEdge);
        edges.add(stomachKidneysEdge);
        edges.add(intestinesLiverEdge);
        edges.add(intestinesKidneysEdge);
        edges.add(noseLungEdge);
        edges.add(mouthStomachEdge);
        edges.add(armHandEdge);
        edges.add(heartArmEdge);
        edges.add(kidneyLEdge);
        edges.add(legFootEdge);
        edges.add(intestinesLegEdge);

        brainNode.setNeighbors(new ArrayList<NodeData>(){{add(mouthNode); add(noseNode); add(heartNode);}});
        mouthNode.setNeighbors(new ArrayList<NodeData>(){{add(lungsNode); add(noseNode); add(stomachNode); add(brainNode);}});
        noseNode.setNeighbors(new ArrayList<NodeData>(){{add(lungsNode); add(mouthNode); add(brainNode);}});
        heartNode.setNeighbors(new ArrayList<NodeData>(){{add(lungsNode); add(stomachNode); add(brainNode); add(armNode);}});
        lungsNode.setNeighbors(new ArrayList<NodeData>(){{add(heartNode); add(mouthNode); add(noseNode);}});
        stomachNode.setNeighbors(new ArrayList<NodeData>(){{add(lungsNode); add(heartNode); add(mouthNode);}});
        liverNode.setNeighbors(new ArrayList<NodeData>(){{add(stomachNode); add(intestinesNode);}});
        kidneysNode.setNeighbors(new ArrayList<NodeData>(){{add(stomachNode); add(liverNode); add(intestinesNode); add(legNode);}});
        intestinesNode.setNeighbors(new ArrayList<NodeData>(){{add(stomachNode); add(kidneysNode); add(liverNode); add(legNode);}});
        armNode.setNeighbors(new ArrayList<NodeData>(){{add(heartNode); add(handNode);}});
        legNode.setNeighbors(new ArrayList<NodeData>(){{add(kidneysNode); add(intestinesNode); add(footNode);}});
        footNode.setNeighbors(new ArrayList<NodeData>(){{add(legNode);}});
        handNode.setNeighbors(new ArrayList<NodeData>(){{add(armNode);}});

        stage = new Stage();
        shapeRenderer = new ShapeRenderer();
        //armNode.

        System.out.println("constructed.");

        //giveEnemyTwoNodes();
    }

    public void paintMap()
    {
        Gdx.input.setInputProcessor(stage);
        // paint background
        Texture a = new Texture("mapBackground.png");
        backgroundSprite = new Sprite(new Texture("mapBackground.png"));
        backgroundSprite.setBounds(0, 0, 1280, 800);
        background = new Image(a);
        background.setSize(1280, 800);
        stage.addActor(background);
        
        // paint nodes
        Node nodeActor;
        for (NodeData node : nodes) 
        {   
            nodeActor = new Node(shapeRenderer, node);
            stage.addActor(nodeActor);
        }
        for(EdgeData edge : edges)
        {
            Edge edgeActor = new Edge(shapeRenderer, edge);
            stage.addActor(edgeActor);
        }
        System.out.println("map painted");
    }

    public void renderBackground(){
        backgroundSprite.draw(spriteBatch);
    }

    @Override
    public void render(float delta) {
        spriteBatch.begin();
        paintMap();

        renderBackground();
        spriteBatch.end();
        stage.act();
        stage.draw();
        if(nodePressed){
            this.randomMinigameTrigger(); // player
            nodePressed = false;
        }
        if(score >= 50){
            game.setScreen(new winScreen(game));;
        }
    }

    public void randomMinigameTrigger(){

        int random = (int) (Math.random() * 4);

        if (random == 0)
        {
            TicTacToeScreen ticTacToeScreen = new TicTacToeScreen(game,this);
            game.setScreen(ticTacToeScreen);               
        }
        else if (random == 1)
        {
        SpaceInvadersScreen spaceInvadersScreen = new SpaceInvadersScreen(game,this);
        game.setScreen(spaceInvadersScreen);
            
        }
        else if (random == 2)
    {
        SnakeScreen snakeScreen = new SnakeScreen(game,this);
        game.setScreen(snakeScreen);
            
    }

    else if (random == 3)
    {
        SpeedTypingScreen speedTypingScreen = new SpeedTypingScreen(game,this);
        game.setScreen(speedTypingScreen);
    }
        
}
    /*
    public void enemyChooseAndAttack()
    {
        NodeData targetNode = pickTargetNode();
       
        if (targetNode != null) 
        {
            System.out.println("enemy attacks: " + targetNode.getName());
        }
        
    }


    public void giveEnemyTwoNodes()
    {
        ArrayList<NodeData> avaiableNodes = new ArrayList<NodeData>();
        ArrayList<NodeData> nodes = getNodes();
        
        for (int i = 0; i < nodes.size(); i++) 
        {
            NodeData node = nodes.get(i);
            if (node.getId() != 0 ) {
                avaiableNodes.add(node);
            }
        }

        int randomNode1Index = random.nextInt(avaiableNodes.size());
        int randomNode2Index = random.nextInt(avaiableNodes.size());

        NodeData randomNode1 = avaiableNodes.get(randomNode1Index);
        NodeData randomNode2 = avaiableNodes.get(randomNode2Index);

        randomNode1.setId(1);
        randomNode2.setId(1);

        enemyNodes.add(randomNode1);
        enemyNodes.add(randomNode2);

        System.out.println("enemy nodes are: " + randomNode1.getName() + " and " + randomNode2.getName());
<<<<<<< HEAD
=======
        }


        public NodeData pickTargetNode() {
            ArrayList<NodeData> availableTargets = new ArrayList<>(enemyNodes);
            
        
            // Check if other nodes are available
            if (!availableTargets.isEmpty()) {
                // Randomly select from other nodes
                int randomIndex = random.nextInt(availableTargets.size());
                NodeData targetNode = availableTargets.get(randomIndex);
                System.out.println("Attacking " + targetNode.getName());
                return targetNode;
            } 
            else 
            {
                // Check if brain is available
                for (NodeData node : enemyNodes) {
                    if (node.getName().equals("Brain")) {
                        System.out.println("Attacking Brain");
                        return node;
                    }
                }
        
                // Check if heart is available
                for (NodeData node : enemyNodes) {
                    if (node.getName().equals("Heart")) {
                        System.out.println("Attacking Heart");
                        return node;
                    }
                }
            }
            
            // If no valid target is found, return null
            return null; 
        }

        private void handleMinigameResult(boolean success) {
            if (success) {
                System.out.println("Minigame Won! Enemy progress halted.");
                NodeData targetNode = enemyNodes.get(0); // Get the first (and only) enemy node
                if (targetNode != null) {
                    targetNode.setId(0); // Reset to player-owned
                    enemyNodes.remove(targetNode);
                    // The line below is removed because we don't want to delete the node from nodes list.
                    // nodes.remove(targetNode); 
                }
            } else {
                System.out.println("Minigame Lost! Enemy takes a node.");
                NodeData targetNode = pickTargetNode();
                if (targetNode != null) {
                    targetNode.setId(1); // Mark as enemy-owned
                    enemyNodes.remove(targetNode); 
                    nodes.remove(targetNode); 
                }
            }
            paintMap();
        }

        public void randomMinigameTrigger()
        {
        // triggers a random minigame and switches to that screen

        Double a = Math.random();

        if ((0 < a) && (a < 0.2))
        {
            // pops a screen for the player to play the minigame

            TicTacToeScreen ticTacToeScreen = new TicTacToeScreen(game);
            game.setScreen(ticTacToeScreen);
            minigameResult = ticTacToeScreen.getBodyWin(); //TODO handle minigame result
            if (minigameResult) 
            {
                handleMinigameResult(true);
            } 
            else 
            {
                handleMinigameResult(false);
            }

          
        }
        else if ((0.2 < a) && (a < 0.4))
        {
            SpaceInvadersScreen spaceInvadersScreen = new SpaceInvadersScreen(game);
            game.setScreen(spaceInvadersScreen);
            minigameResult = spaceInvadersScreen.getBodyWin();
            if (minigameResult) 
            {
                handleMinigameResult(true);
            } 
            else 
            {
                handleMinigameResult(false);
            }
            
        }
        else if ((0.4 < a) && (a < 0.6))
        {
            SnakeScreen snakeScreen = new SnakeScreen(game);
            game.setScreen(snakeScreen);
            snakeScreen.getBodyWin(); //TODO handle minigame result
            if (minigameResult) 
            {
                handleMinigameResult(true);
            } 
            else 
            {
                handleMinigameResult(false);
            }
            
        }
        else if ((0.6 < a) && (a < 0.8))
        {
            ImageMatchingScreen imageMatchingScreen = new ImageMatchingScreen(game, 16);
            game.setScreen(imageMatchingScreen);
            imageMatchingScreen.getBodyWin(); //TODO handle minigame result
            if (minigameResult) 
            {
                handleMinigameResult(true);
            } 
            else 
            {
                handleMinigameResult(false);
            }
        }
        else if ((0.8 < a) && (a < 1))
        {
            SpeedTypingScreen speedTypingScreen = new SpeedTypingScreen(game);
            game.setScreen(speedTypingScreen);
            minigameResult = speedTypingScreen.getBodyWin();
            if (minigameResult) 
            {
                handleMinigameResult(true);
            } 
            else 
            {
                handleMinigameResult(false);
            }
        }
        
>>>>>>> 03680fc249af4b8e2fad1104fe65465ddcebd060
    }


    
    private void handleMinigameResult(boolean success) {
        if (success) {
            System.out.println("Minigame Won! Enemy progress halted.");
            NodeData targetNode = enemyNodes.get(0); // Get the first (and only) enemy node
            if (targetNode != null) {
                targetNode.setId(0); // Reset to player-owned
                enemyNodes.remove(targetNode);
                // The line below is removed because we don't want to delete the node from nodes list.
                // nodes.remove(targetNode); 
            }
        } else {
            System.out.println("Minigame Lost! Enemy takes a node.");
            NodeData targetNode = pickTargetNode();
            if (targetNode != null) {
                targetNode.setId(1); // Mark as enemy-owned
                enemyNodes.remove(targetNode); 
                nodes.remove(targetNode); 
            }
        }
        paintMap();
    }



    public void endGame()
    {
        // terminates the game
        System.out.println("Game Over");
       
    }

    public ArrayList<NodeData> getNodes() {
        return nodes;
    }
*/

@Override
public void show() {
}

@Override
public void resize(int width, int height) {
    
}


@Override
public void pause() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'pause'");
}


@Override
public void resume() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'resume'");
}


@Override
public void hide() {
    // hides the map screen

    stage.clear();
}


@Override
public void dispose() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'dispose'");
}
}


