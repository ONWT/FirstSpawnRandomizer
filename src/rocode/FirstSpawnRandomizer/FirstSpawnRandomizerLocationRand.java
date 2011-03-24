package rocode.FirstSpawnRandomizer;

import org.bukkit.Location;

public class FirstSpawnRandomizerLocationRand implements Runnable{
	private final FirstSpawnRandomizer plugin;
	private final Location world;

	FirstSpawnRandomizerLocationRand(FirstSpawnRandomizer instance,Location w) {
		this.plugin = instance;
		this.world=w;
	}

	public void run() {
		FirstSpawnRandomizer.setMainLoc(this.plugin.getRandomLocation(world,10000));
	}
}
