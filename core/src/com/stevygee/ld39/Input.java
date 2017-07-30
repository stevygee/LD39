package com.stevygee.ld39;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;

public class Input implements InputProcessor {
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if( GameLogic.isDay ) {
			if( keycode == Keys.ENTER ) {
				Day.end();
				return true;
			}

			if( keycode == Keys.NUM_1 ) {
				World.addBattery(1);
			}

			if( keycode == Keys.NUM_2 ) {
				World.addSolar(1);
			}

			if( keycode == Keys.NUM_3 ) {
				World.addHotel(1);
			}
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
