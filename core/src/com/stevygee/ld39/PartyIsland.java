package com.stevygee.ld39;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PartyIsland extends ApplicationAdapter {
	private static float t = 0;
	private final float dt = 1/100f; // 100 updates/second
	private float frameTime = 0f;
	private float accumulator = 0f;

	private float secondAccumulator = 0f;
	private static int updates = 0;
	public static float updatesPerSecond = 0;

	SpriteBatch batch;
	Texture img;

	// TODO: People per day array (FILO)
	// TODO: People leaving

	int power = 10;
	int people = 10;
	int buildings = 2;

	float dayLength = 2;
	float time = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	public void update(float delta) {
		time += delta;
		time = Math.min(time, dayLength);
		if( time == dayLength ) {
			time = 0;

			int oldPeople = people;
			people += (people * 0.1) + (buildings * 4);
			int deltaPeople = people - oldPeople;
			Gdx.app.log("Test", "Day over! " + deltaPeople + " people come to the island");
			Gdx.app.log("Test", people + " people are on the island.");
		}
	}

	@Override
	public void render () {
		// "Free the physics" game loop
		// see: http://gafferongames.com/game-physics/fix-your-timestep/
		// TODO: Implement "the final touch"
		// TODO: Implement integrator

		frameTime = Gdx.graphics.getRawDeltaTime();
		frameTime = Math.min(0.25f, frameTime); // note: max frame time to avoid spiral of death

		accumulator += frameTime;
		secondAccumulator += frameTime;

		while( accumulator >= dt ) {
			// update current game state
			update(dt);

			accumulator -= dt;
			t += dt;
			updates++;
		}

		// Count updates per second
		if( secondAccumulator >= 1f ) {
			secondAccumulator = 0f;
			updatesPerSecond = updates;
			updates = 0;
		}

		// really render
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
