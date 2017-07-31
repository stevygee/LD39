package com.stevygee.ld39;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.IntArray;

public class GameLogic {
	static boolean isDay = true;
	static int currentDay = 1;

	static int goalDay = 3;
	static int goalGuests = 10;
	static boolean gameOver = false;

	static int guests = 0;
	static IntArray guestsBuffer;
	static int buildings = 1;
	static int funds = 10000;

	public static void init() {
		Day.init();
		Night.init();
		World.init();
		PowerManager.init();

		World.addHotel(buildings, 0);
		World.addBattery(1, 0);
		World.addSolar(1, 0);

		int[] weekDays = {0,0,0,0,0,0,0};
		guestsBuffer = new IntArray(weekDays);
	}

	public static void update(float delta) {
		// Day / Night Cycle
		if( isDay && Day.hasEnded ) {
			// Day just ended
			isDay = !isDay;

			// Tonight's the night...
			Gdx.app.log("GameLogic", "--- NIGHT ---");
			Night.reset();
		} else if( currentDay == 1 || !isDay && Night.hasEnded ) {
			if( currentDay != 1 ) {
				// Night just ended
				isDay = !isDay;
				PowerManager.isPowerOn = true;

				Gdx.app.log("Night", "has ended, power failures: " + Night.powerFailures);
			}

			// A new day
			Gdx.app.log("GameLogic", "--- DAY " + currentDay + " ---");

			float income = guests * 100;
			gettingMoney(income);
			Gdx.app.log("GameLogic", "Made " + income + "$, Funds: " + funds + "$");

			int angryLeaving = (int)Math.floor( guests * 0.3f * (float)Night.powerFailures );
			int regularLeaving = guestsBuffer.get(currentDay % 7);
			int leaving = regularLeaving + angryLeaving;
			int arriving = (int)Math.floor( Math.abs( (guests * 0.1f) + -(angryLeaving * 0.1f) + (buildings * 4f) ) );
			if( currentDay == 1 ) {
				arriving = 1;
			}
			guests += arriving - leaving;
			guestsBuffer.set(currentDay % 7, arriving);
			/*for(int i = 0; i < guestsBuffer.size; i++) {
				Gdx.app.log("GameLogic", "buffer " + i + ": " + guestsBuffer.get(i));
			}*/
			Gdx.app.log("GameLogic", "Good morning! " + arriving + " guests come to the island, " + leaving + " left.");
			Gdx.app.log("GameLogic", guests + " guests are on the island.");

			if( currentDay >= goalDay ) {
				gameOver = true;
			} else {
				Day.reset();
				currentDay++;
			}
		}

		if( gameOver ) {
			if( guests >= goalGuests ) {
				Gdx.app.log("GameLogic", "You won!");
			} else {
				Gdx.app.log("GameLogic", "Try again!");
			}
			return;
		}

		// Updates during Day / Night
		if( isDay ) {
			Day.update(delta);
		} else {
			Night.update(delta);
		}
	}

	public static void gettingMoney(float amount) {
		funds += amount;
		Gdx.app.log("GameLogic", "Getting " + amount + "$");
		Gdx.app.log("GameLogic", "Funds: " + funds + "$");
	}

	public static boolean spendMoney(float amount) {
		if( funds < amount ) {
			return false;
		} else {
			funds -= amount;
			Gdx.app.log("GameLogic", "Spending " + amount + "$");
			Gdx.app.log("GameLogic", "Funds: " + funds + "$");
			return true;
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
		Day.dispose();
		Night.dispose();
	}
}
