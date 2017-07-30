package com.stevygee.ld39;

import com.badlogic.gdx.Gdx;

public class GameLogic {

	static boolean isDay = true;
	static int currentDay = 1;

	// TODO: People per day array (FILO)
	// TODO: People leaving

	static int power = 10;
	static int people = 10;
	static int buildings = 2;

	public static void init() {
		Day.init();
		Night.init();
		PowerManager.init(3, 1, 2);
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

			int oldPeople = people;
			people += (people * 0.1) + (buildings * 4);
			int deltaPeople = people - oldPeople;
			Gdx.app.log("GameLogic", "Good morning! " + deltaPeople + " people come to the island");
			Gdx.app.log("GameLogic", people + " people are on the island.");

			Day.init();
		}

		// Updates during Day / Night
		if( isDay) {
			Day.update(delta);
		} else {
			Night.update(delta);
		}
	}

	public static void render() {
		if( isDay ) {
			Day.render();
		} else {
			Night.render();
		}
	}

	public static void dispose() {

	}
}
