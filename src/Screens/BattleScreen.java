package Screens;

import Engine.*;
import Game.ScreenCoordinator;
import GameObject.Rectangle;
import SpriteFont.SpriteFont;
import Utils.Sound;
import Maps.TestMap;
import Game.GameState;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BattleScreen extends Screen {
    protected SpriteFont winMessage;
    protected SpriteFont playerHP;
    protected SpriteFont enemyHP;
    protected SpriteFont instructions;
    protected KeyLocker keyLocker = new KeyLocker();
    protected PlayLevelScreen playLevelScreen;
    protected Rectangle rectangle;
    protected GraphicsHandler graphicsHandler;
    protected ScreenCoordinator screenCoordinator;

    protected int coleHealth;
    protected int bobcatHealth;

    protected List<SpriteFont> attackOptions;
    protected int selectedAttack;
    protected SpriteFont attackMessage; // Add this line

    public BattleScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
        initialize();
    }

    @Override
    public void initialize() {
        winMessage = new SpriteFont("FIGHT!", 300, 200, "Trebuchet MS", 60, Color.black);
        playerHP = new SpriteFont("BOBCAT | 100 HP", 15, 15, "Trebuchet MS", 22, Color.black);
        enemyHP = new SpriteFont("ENEMY | 150 HP", 625, 15, "Trebuchet MS", 22, Color.black);
        rectangle = new Rectangle();
        coleHealth = 150;
        bobcatHealth = 100;
        keyLocker.lockKey(Key.SPACE);
        keyLocker.lockKey(Key.Y);
        
        // Initialize attack options
        attackOptions = new ArrayList<>();
        attackOptions.add(new SpriteFont("HEAVY", 50, 300, "Trebuchet MS", 22, Color.black));
        attackOptions.add(new SpriteFont("LIGHT", 50, 350, "Trebuchet MS", 22, Color.black));
        attackOptions.add(new SpriteFont("DEFEND", 50, 400, "Trebuchet MS", 22, Color.black));
        selectedAttack = 0;
        
        attackMessage = new SpriteFont("", 300, 250, "Trebuchet MS", 30, Color.black); // Add this line
    }

    @Override
    public void update() {
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }
    
        if (Keyboard.isKeyUp(Key.Y)) {
            keyLocker.unlockKey(Key.Y);
        }
    
        if (Keyboard.isKeyDown(Key.SPACE) && !keyLocker.isKeyLocked(Key.SPACE)) {
            playLevelScreen.resetLevel();
        } else if (Keyboard.isKeyDown(Key.ESC) && !keyLocker.isKeyLocked(Key.SPACE)) {
            playLevelScreen.goBackToMenu();
        } else if (Keyboard.isKeyDown(Key.Y) && !keyLocker.isKeyLocked(Key.Y)) {
            // Handle the selected attack here
            String attackName = attackOptions.get(selectedAttack).getText();
            attackMessage.setText("Pikachu used " + attackName + "!!!");
        } else if (Keyboard.isKeyDown(Key.U) && !keyLocker.isKeyLocked(Key.U)) {
            String attackName = attackOptions.get(selectedAttack).getText();
            attackMessage.setText("Bobcat used " + attackName + "!!!");
        }
    
        // Handle attack option selection (up and down arrow keys)
        if (Keyboard.isKeyDown(Key.DOWN) && selectedAttack < attackOptions.size() - 1) {
            selectedAttack++;
        } else if (Keyboard.isKeyDown(Key.UP) && selectedAttack > 0) {
            selectedAttack--;
        }
    }
    

    public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawFilledRectangle(0, 420, 450, 160, Color.black);
        graphicsHandler.drawFilledRectangle(500, 420, 400, 160, Color.black);
        winMessage.draw(graphicsHandler);
        playerHP.draw(graphicsHandler);
        enemyHP.draw(graphicsHandler);
        
        // Draw attack options with a different color for the selected option
        for (SpriteFont attackOption : attackOptions) {
            int currentIndex = attackOptions.indexOf(attackOption);
            if (currentIndex == selectedAttack) {
                attackOption.setColor(Color.red);
            } else {
                attackOption.setColor(Color.black);
            }
            attackOption.draw(graphicsHandler);
        }
        
        // Draw the attack message
        attackMessage.draw(graphicsHandler);
    }
}