package com.stevygee.ld39;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Night {
	public static final float LENGTH = 6;

	public static boolean hasEnded;
	static int powerFailures;

	static float time;

	static Music music;
	static boolean musicPlaying = false;

	static Sound sndPowerLoss;
	static boolean sndPowerLossPlaying = false;
	static float sndPowerLossTimer = 0f;

	private static Texture bgTex;

	public static void init() {
		reset();

		bgTex = new Texture("InselNacht.png");

		music = Gdx.audio.newMusic(Gdx.files.internal("DanceLounge.mp3"));
		music.setLooping(true);

		sndPowerLoss = Gdx.audio.newSound(Gdx.files.internal("powerloss.ogg"));
	}

	public static void reset() {
		hasEnded = false;
		powerFailures = 0;
		time = 0;
		musicPlaying = false;
		sndPowerLossPlaying = false;
		sndPowerLossTimer = 0;
	}

	public static void update(float delta) {
		if( !musicPlaying ) {
			music.play();
			musicPlaying = true;
		}

		if( sndPowerLossPlaying ) {
			sndPowerLossTimer += delta;
			//Gdx.app.log("Night", sndPowerLossTimer  + " snd");
		}

		time += delta;
		//Gdx.app.log("Night", "time: " + time);
		if( time >= LENGTH ) {
			time = 0;

			if( PowerManager.isPowerOn ) {
				end();
				//Gdx.app.log("Night", "end with power");
			//} else {
				} else if(sndPowerLossTimer >= 5.5f) {
				end();

				sndPowerLoss.stop();
				//Gdx.app.log("Night", "end no power");
			}

			music.pause();
			musicPlaying = false;
		} else {
			// Draining batteries
			if( PowerManager.isPowerOn ) {
				PowerManager.update(delta);
			} else {
				// Power loss
				if( !sndPowerLossPlaying ) {
					sndPowerLoss.play();
					sndPowerLossPlaying = true;
				}
				music.pause();
				musicPlaying = false;
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
