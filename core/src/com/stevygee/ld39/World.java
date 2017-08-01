package com.stevygee.ld39;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class World {
	static final int LOT_SOLAR = 1;
	static final int LOT_BATTERY = 2;
	static final int LOT_HOTEL = 3;

	static final int HOTEL_PRICE = 10000;
	static final int SOLAR_PRICE = 2000;
	static final int BATTERY_PRICE = 5000;

	static int[][] powerLots;
	static int[][] hotelLots;
	static Vector2 hotelPosition;
	static Vector2 powerPosition;

	static int noBatteries;
	static int noSolarPanels;
	static int noHotels;

	static Texture texBattery;
	static Texture texBatteryDay;
	static Texture texBatteryNight;

	static Texture texSolar;
	static Texture texSolarDay;
	static Texture texSolarNight;

	static Texture texHotel;
	static Texture texHotelDay;
	static Texture texHotelNight;
	static Texture texHotelNightNoPower;

	static Sound sndBuild;

	public static void init() {
		sndBuild = Gdx.audio.newSound(Gdx.files.internal("build.ogg"));

		powerLots = new int[16][1];
		hotelLots = new int[10][5];

		hotelPosition = new Vector2(4,3.25f);
		powerPosition = new Vector2(4,5.8f);

		texBatteryDay = new Texture("BatteryTag.png");
		texBatteryNight = new Texture("BatteryNacht.png");

		texSolarDay = new Texture("SolarTag.png");
		texSolarNight = new Texture("SolarNacht.png");

		texHotelDay = new Texture("HausTag.png");
		texHotelNightNoPower = new Texture("HausNacht.png");
		texHotelNight = new Texture("HausNachtLicht.png");
	}

	public static void update() {

	}

	public static boolean add(int type, int[][] array, int noOccupied) {
		boolean added = false;

		if( noOccupied >= array.length * array[0].length ) {
			return false;
		}

		while(!added) {
			int r = (int)Math.floor(Math.random() * array.length);

			for(int j = 0; j < array[r].length; j++) {
				if( array[r][j] == 0 ) {
					array[r][j] = type;
					added = true;
					break;
				}
			}
		}

		if( added ) {
			sndBuild.play();
		}

		return added;
	}

	public static void addSolar(int no, float priceModifier) {
		if( !GameLogic.spendMoney(SOLAR_PRICE * priceModifier) ) {
			return;
		}

		for(int i = 0; i < no; i++) {
			add(LOT_SOLAR, powerLots, noBatteries + noSolarPanels);
			PowerManager.addSolarPanel();
			noSolarPanels++;
		}
	}

	public static void addBattery(int no, float priceModifier) {
		if( !GameLogic.spendMoney(BATTERY_PRICE * priceModifier) ) {
			return;
		}

		for(int i = 0; i < no; i++) {
			add(LOT_BATTERY, powerLots, noBatteries + noSolarPanels);
			PowerManager.addBattery();
			noBatteries++;
		}
	}

	public static void addHotel(int no, float priceModifier) {
		if( !GameLogic.spendMoney(HOTEL_PRICE * priceModifier) ) {
			return;
		}

		for(int i = 0; i < no; i++) {
			add(LOT_HOTEL, hotelLots, noHotels);
			GameLogic.buildings++;
			PowerManager.addEnergyUser();
			noHotels++;
		}
	}

	public static void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		float x, y;

		if( GameLogic.isDay ) {
			texBattery = texBatteryDay;
			texSolar = texSolarDay;
			texHotel = texHotelDay;
		} else {
			texBattery = texBatteryNight;
			texSolar = texSolarNight;

			if( PowerManager.isPowerOn ) {
				texHotel = texHotelNight;
			} else {
				texHotel = texHotelNightNoPower;
			}
		}

		// Render power layer
		for(int i = 0; i < powerLots.length; i++) {
			for (int j = 0; j < powerLots[i].length; j++) {
				x = (powerPosition.x * 32f) + (i * 16f);
				y = (powerPosition.y * 32f) + (j * 16f);

				if (powerLots[i][j] == LOT_BATTERY) {
					batch.begin();
					batch.draw(texBattery, x, y, 16f, 16f, 0, 0, 32, 32, false, false);
					batch.end();
				} else if (powerLots[i][j] == LOT_SOLAR) {
					batch.begin();
					batch.draw(texSolar, x, y, 16f, 16f, 0, 0, 32, 32, false, false);
					batch.end();
				}
			}
		}

		// Render hotel layer
		for(int i = 0; i < hotelLots.length; i++) {
			for(int j = 0; j < hotelLots[i].length; j++) {
				x = (hotelPosition.x + i) * 32f;
				y = (hotelPosition.y + j) * 32f;

				if( hotelLots[i][j] == LOT_HOTEL ) {
					batch.begin();
					batch.draw(texHotel, x, y);
					batch.end();
				}
			}
		}
	}
}
