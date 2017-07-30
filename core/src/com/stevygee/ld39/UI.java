package com.stevygee.ld39;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class UI {

	public static void init() {
	}

	public static void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		int w = PartyIsland.NATIVE_WIDTH;
		int h = PartyIsland.NATIVE_HEIGHT;

		shapeRenderer.begin( ShapeRenderer.ShapeType.Filled );

		// Draw energy bars, nom nom
		float capacityWidth = w;
		float energyWidth = PowerManager.energy / PowerManager.energyCapacity * capacityWidth;

		shapeRenderer.setColor( new Color(0.7f, 0.7f, 0.7f, 1f) );
		shapeRenderer.rect( 0, h-10, capacityWidth, 10 );
		shapeRenderer.setColor( Color.GREEN );
		shapeRenderer.rect( 0, h-10, energyWidth, 10 );

		shapeRenderer.end();
	}
}
