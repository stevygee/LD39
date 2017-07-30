package com.stevygee.ld39;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PartyIsland extends ApplicationAdapter {
	public static Input input = new Input();

	private static float t = 0;
	private final float dt = 1/100f; // 100 updates/second
	private float frameTime = 0f;
	private float accumulator = 0f;

	private float secondAccumulator = 0f;
	private static int updates = 0;
	public static float updatesPerSecond = 0;

	@Override
	public void create() {
		Gdx.input.setInputProcessor(input);

		GameLogic.init();
	}

	public void update(float delta) {
		GameLogic.update(delta);
	}

	@Override
	public void render() {
		// "Free the physics" game loop
		// see: http://gafferongames.com/game-physics/fix-your-timestep/
		// TODO: Implement "the final touch"
		// TODO: Implement integrator

		frameTime = Gdx.graphics.getRawDeltaTime();
		frameTime = Math.min(0.25f, frameTime); // Note: Max frame time to avoid spiral of death

		accumulator += frameTime;
		secondAccumulator += frameTime;

		while( accumulator >= dt ) {
			// Update current game state
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

		// Really render
		GameLogic.render();
	}

	@Override
	public void dispose() {
		GameLogic.dispose();
	}
}
