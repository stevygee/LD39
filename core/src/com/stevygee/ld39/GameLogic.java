package com.stevygee.ld39;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameLogic {
	static boolean isDay = true;
	static int currentDay = 1;

	// TODO: People per day array (FILO)

	static int guests = 10;
	static int buildings = 2;

	public static void init() {
		Day.init();
		Night.init();
		World.init();
		World.addBattery(3);
		World.addSolar(1);
		PowerManager.init(World.noBatteries, World.noSolarPanels, buildings);
	}

	public static void update(float delta) {
		// Day / Night Cycle
		if( isDay && Day.hasEnded ) {
			// Day just ended
			isDay = !isDay;

			// Tonight's the night...
			Gdx.app.log("GameLogic", "--- NIGHT ---");
			Night.init();
		} else if( !isDay && Night.hasEnded ) {
			// Night just ended
			isDay = !isDay;
			PowerManager.isPowerOn = true;

			Gdx.app.log("Night", "has ended, power failures: " + Night.powerFailures);

			// A new day
			Gdx.app.log("GameLogic", "--- DAY ---");

			int arriving = (int)Math.floor( (guests * 0.1f) + (buildings * 4f) );
			int leaving = (int)Math.floor( guests * 0.3f * (float)Night.powerFailures );
			guests += arriving - leaving;
			Gdx.app.log("GameLogic", "Good morning! " + arriving + " guests come to the island, " + leaving + " left.");
			Gdx.app.log("GameLogic", guests + " guests are on the island.");

			Day.init();
		}

		// Updates during Day / Night
		if( isDay) {
			Day.update(delta);
		} else {
			Night.update(delta);
		}
	}

	public static void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		if( isDay ) {
			Day.render(batch, shapeRenderer);
		} else {
			Night.render(batch, shapeRenderer);
		}

		World.render(batch, shapeRenderer);
	}

	public static void dispose() {

	}
}
