package com.stevygee.ld39;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

public class UI {
	private static BitmapFont guiFont = null;
	private static GlyphLayout layout = new GlyphLayout();

	public static String status;

	public static void init() {
		guiFont = new BitmapFont(Gdx.files.internal("Georgia.fnt"));

		status = "";
	}

	public static void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		String text;
		int w = PartyIsland.NATIVE_WIDTH;
		int h = PartyIsland.NATIVE_HEIGHT;

		shapeRenderer.begin( ShapeRenderer.ShapeType.Filled );

		// Draw energy bars, nom nom
		float capacityWidth = w;
		float energyWidth = PowerManager.energy / PowerManager.energyCapacity * capacityWidth;

		shapeRenderer.setColor( new Color(0.3f, 0.3f, 0.3f, 1f) );
		shapeRenderer.rect( 0, h-10, capacityWidth, 10 );
		shapeRenderer.setColor( Color.GREEN );
		shapeRenderer.rect( 0, h-10, energyWidth, 10 );

		shapeRenderer.end();

		batch.begin();
		if( GameLogic.isDay ) {
			guiFont.setColor( Color.BLACK );
		} else {
			guiFont.setColor( Color.WHITE );
		}

		// Energy label
		text = "Energy";
		layout.setText( guiFont, text );
		guiFont.draw( batch, layout, 10, h-14 );

		// Funds
		text = "$ " + GameLogic.funds;
		layout.setText( guiFont, text );
		guiFont.draw( batch, layout, w-layout.width-10, h-40 );

		// Day
		text = "Day " + (GameLogic.currentDay-1);
		layout.setText( guiFont, text );
		guiFont.draw( batch, layout, w-layout.width-10, h-20 );

		// Guests
		text = GameLogic.guests + " guest";
		text = GameLogic.guests > 1 ? text + "s" : text;
		layout.setText( guiFont, text );
		guiFont.draw( batch, layout, w-layout.width-10, h-60 );

		// Build menu
		if( GameLogic.isDay ) {
			text = "Press ENTER to end turn\n";
			text = text + "1: Build battery ($ " + World.BATTERY_PRICE + ") ";
			text = text + "2: Build solar panel ($ " + World.SOLAR_PRICE + ") ";
			text = text + "3: Build hotel ($ " + World.HOTEL_PRICE + ")";
			layout.setText(guiFont, text, guiFont.getColor(), PartyIsland.NATIVE_WIDTH, Align.center, false);
			guiFont.draw(batch, layout, 0, 30);
		}

		// Status
		if( GameLogic.isDay && (Day.active || GameLogic.gameOver )) {
			text = status;
			layout.setText(guiFont, text);
			guiFont.draw(batch, layout, w * 0.5f - layout.width * 0.5f, h-20);
		}

		batch.end();
	}
}
