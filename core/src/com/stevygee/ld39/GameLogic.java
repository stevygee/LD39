package com.stevygee.ld39;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.IntArray;

public class GameLogic {
	static boolean isDay = true;
	static int currentDay = 1;

	static int goalDay = 30;
	static int goalGuests = 500;
	static boolean gameOver = false;

	static int guests = 0;
	static IntArray guestsBuffer;
	static int buildings = 2;
	static int funds = 10000;

	static Sound sndHorn;

	public static void init() {
		Day.init();
		Night.init();
		World.init();
		PowerManager.init();

		World.addHotel(buildings, 0, false);
		World.addBattery(1, 0, false);
		World.addSolar(2, 0, false);

		int[] weekDays = {0,0,0,0,0,0,0};
		guestsBuffer = new IntArray(weekDays);

		sndHorn = Gdx.audio.newSound(Gdx.files.internal("horn.ogg"));
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
			if( currentDay == 1 ) {
				UI.status = "Welcome to your new island! Try to accommodate " + goalGuests + " guests by day " + goalDay + "!";
			} else {

				sndHorn.play();

				UI.status = "Good morning! " + arriving + " guests arrived, " + leaving + " left.";
			}
			Gdx.app.log("GameLogic", UI.status);
			Gdx.app.log("GameLogic", guests + " guests are on the island.");

			if( currentDay >= goalDay ) {
				gameOver = true;
			}

			Day.reset();
			currentDay++;
		}

		if( gameOver ) {
			if( guests >= goalGuests ) {
				UI.status = "You won!";
			} else {
				UI.status = "Not enough guests, try again!";
			}
			//Gdx.app.log("GameLogic", UI.status);
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
