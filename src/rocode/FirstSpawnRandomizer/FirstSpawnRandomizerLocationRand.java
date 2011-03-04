package rocode.FirstSpawnRandomizer;

import org.bukkit.World;

public class FirstSpawnRandomizerLocationRand implements Runnable{
	private final FirstSpawnRandomizer plugin;
	private final World world;

	FirstSpawnRandomizerLocationRand(FirstSpawnRandomizer instance,World w) {
		this.plugin = instance;
		this.world=w;
	}

	public void run() {
		FirstSpawnRandomizer.setMainLoc(this.plugin.getRandomLocation(world.getSpawnLocation(),10000));
	}
}
