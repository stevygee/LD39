package com.stevygee.ld39;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class UI {
	public static ShapeRenderer shapeRenderer;

	public static void init() {
		shapeRenderer = new ShapeRenderer(200);
	}

	public static void render() {
		int screenWidth = Gdx.app.getGraphics().getWidth();
		int screenHeight = Gdx.app.getGraphics().getHeight();

		shapeRenderer.begin( ShapeRenderer.ShapeType.Filled );

		// Draw energy bars, nom nom
		float capacityWidth = screenWidth;
		float energyWidth = PowerManager.energy / PowerManager.energyCapacity * capacityWidth;

		shapeRenderer.setColor( new Color(0.7f, 0.7f, 0.7f, 1f) );
		shapeRenderer.rect( 0, screenHeight-10, capacityWidth, 10 );
		shapeRenderer.setColor( Color.GREEN );
		shapeRenderer.rect( 0, screenHeight-10, energyWidth, 10 );

		shapeRenderer.end();

	}
}
