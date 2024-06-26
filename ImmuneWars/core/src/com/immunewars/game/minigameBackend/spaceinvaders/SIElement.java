package com.immunewars.game.minigameBackend.spaceinvaders;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.immunewars.game.minigameBackend.MinigamePresets;

public abstract class SIElement extends Actor {
	protected boolean isEnemy = false;
	protected Texture texture;
	
	protected void moveBounded(float x, float y) {
		float newXMin = this.getX() + x;
		float newXMax = newXMin + this.getWidth();
		
		float newYMin = this.getY() + y;
		float newYMax = newYMin + this.getHeight();
		
		if (newXMin < 180) {
			newXMax -= newXMin;
			newXMin = 180;
		}
		
		if (newXMax > MinigamePresets.SpaceInvaders.xUpperBound) {
			newXMin = MinigamePresets.SpaceInvaders.xUpperBound - getWidth();
			newXMax = MinigamePresets.SpaceInvaders.xUpperBound;
		}
		
		if (newYMin < 0) {
			newYMax -= newYMin;
			newYMin = 0;
		}
		
		if (newYMax > MinigamePresets.SpaceInvaders.yUpperBound) {
			newYMin = MinigamePresets.SpaceInvaders.yUpperBound - getHeight();
			newYMax = MinigamePresets.SpaceInvaders.yUpperBound;
		}
		
		this.setBounds(newXMin, newYMin, this.getWidth(), this.getHeight());
	}
	
	protected void forceMove(float x, float y) {
		this.moveBy(x, y);
	}
	
	protected boolean isOutOfBounds() {
		return getX() > MinigamePresets.SpaceInvaders.xUpperBound
				|| getY() > MinigamePresets.SpaceInvaders.yUpperBound
				|| getX() + getWidth() < 0
				|| getY() + getHeight() < 0;
	}
	
	public boolean collidesWith(SIElement element) {
		return getX() < element.getX() + element.getWidth() &&
				getY() < element.getY() + element.getHeight() &&
				getX() + getWidth() > element.getX() &&
				getY() + getHeight() > element.getY();
				
	}
	
	public void draw (Batch batch) {
		batch.draw(texture, getX(), getY(), getWidth(), getHeight());
	}
	
	public void draw (Batch batch, float parentAlpha) {
		batch.draw(texture, getX(), getY(), getWidth(), getHeight());
	}
	
}
