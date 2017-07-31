package com.stevygee.ld39;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Night {
	public static final float LENGTH = 5;

	public static boolean hasEnded;
	static int powerFailures;

	static float time;

	static Music music;
	static boolean musicPlaying = false;

	private static Texture bgTex;

	public static void init() {
		reset();

		bgTex = new Texture("InselNacht.png");
		music = Gdx.audio.newMusic(Gdx.files.internal("DanceLounge.mp3"));
		music.setLooping(true);
	}

	public static void reset() {
		hasEnded = false;
		powerFailures = 0;
		time = 0;
	}

	public static void update(float delta) {
		if( !musicPlaying ) {
			music.play();
			musicPlaying = true;
		}

		time += delta;
		if( time >= LENGTH ) {
			time = 0;

			end();

			music.pause();
			musicPlaying = false;
		} else {
			// Draining batteries
			if( PowerManager.isPowerOn ) {
				PowerManager.update(delta);
			}
		}

		//Gdx.app.log("Night", "Time: " + time);
	}

	public static void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		batch.begin();
		batch.draw(bgTex, 0, 0, PartyIsland.NATIVE_WIDTH, PartyIsland.NATIVE_HEIGHT, 0, 0, PartyIsland.NATIVE_WIDTH, PartyIsland.NATIVE_HEIGHT, false, false);
		batch.end();
	}

	private static void end() {
		hasEnded = true;
	}

	public static void dispose() {
		//music.dispose();
	}
}
