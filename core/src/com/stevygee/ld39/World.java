package com.stevygee.ld39;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class World {
	static final int LOT_SOLAR = 1;
	static final int LOT_BATTERY = 2;
	static final int LOT_HOTEL = 3;

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

	public static void init() {
		powerLots = new int[8][1];
		hotelLots = new int[10][5];

		hotelPosition = new Vector2(4,4);
		powerPosition = new Vector2(4,6);

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

	public static void add(int type, int[][] array) {
		boolean added = false;

		for(int i = 0; i < array[0].length; i++) {
			for(int j = 0; j < array.length; j++) {
				if( array[j][i] == 0 ) {
					array[j][i] = type;
					added = true;
					break;
				}
			}
			if( added ) {
				break;
			}
		}
	}

	public static void addSolar(int no) {
		for(int i = 0; i < no; i++) {
			add(LOT_SOLAR, powerLots);
			PowerManager.addSolarPanel();
			noSolarPanels++;
		}
	}

	public static void addBattery(int no) {
		for(int i = 0; i < no; i++) {
			add(LOT_BATTERY, powerLots);
			PowerManager.addBattery();
			noBatteries++;
		}
	}

	public static void addHotel(int no) {
		for(int i = 0; i < no; i++) {
			add(LOT_HOTEL, hotelLots);
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
			for(int j = 0; j < powerLots[i].length; j++) {
				x = (powerPosition.x + i) * 32f;
				y = (powerPosition.y + j) * 32f;

				if( powerLots[i][j] == LOT_BATTERY ) {
					batch.begin();
					batch.draw(texBattery, x, y);
					batch.end();
				} else if( powerLots[i][j] == LOT_SOLAR ) {
					batch.begin();
					batch.draw(texSolar, x, y);
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
