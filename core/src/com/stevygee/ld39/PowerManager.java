package com.stevygee.ld39;

import com.badlogic.gdx.Gdx;

public class PowerManager {
	static int batteries;
	static int solarPanels;

	private static float time;
	static float energy;
	static float energyCapacity;
	static int energyUsers;

	public static boolean isPowerOn;

	public static void init(int batteries, int solarPanels, int energyUsers) {
		PowerManager.batteries = batteries;
		PowerManager.solarPanels = solarPanels;
		PowerManager.energyUsers = energyUsers;
		time = 0;
		energyCapacity = batteries;
		isPowerOn = true;
	}

	public static void update(float delta) {
		time += delta;

		if( GameLogic.isDay ) {
			// Charge
			energy += delta * solarPanels * 2f;
			energy = Math.min(energy, energyCapacity);
		} else {
			// Drain
			float requestedEnergy = delta * energyUsers * 0.1f;

			if( energy < requestedEnergy ) {
				// Power failure!
				Gdx.app.log("PowerMan", "Power failure!");
				energy = 0;
				Night.powerFailures++;
				isPowerOn = false;
			} else {
				energy -= requestedEnergy;
			}
		}

		//Gdx.app.log("PowerMan", "Energy: " + energy + " Total capacity: " + energyCapacity);
	}

	public static void addBattery() {
		batteries++;
		energyCapacity = batteries;
		//Gdx.app.log("PowerMan", "Added battery, new capacity: " + energyCapacity);
	}

	public static void addSolarPanel() {
		solarPanels++;
		//Gdx.app.log("PowerMan", "Added solar panel");
	}

	public static void addEnergyUser() {
		energyUsers++;
		//Gdx.app.log("PowerMan", "Added energy user");
	}
}
