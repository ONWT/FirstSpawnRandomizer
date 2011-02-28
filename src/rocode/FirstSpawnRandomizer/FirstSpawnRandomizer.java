package rocode.FirstSpawnRandomizer;

import java.io.File;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
/**
 * FirstSpawnRandomizer for Bukkit
 *
 * @author rocode
 */
public class FirstSpawnRandomizer extends JavaPlugin {
	private final FirstSpawnRandomizerPlayerListener playerListener = new FirstSpawnRandomizerPlayerListener(this);

    private Random rand = new Random(System.nanoTime());

    public void onEnable() {
    	PluginManager pm = getServer().getPluginManager();
    	pm.registerEvent(Type.PLAYER_LOGIN, playerListener, Priority.Low, this);
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }
    public void onDisable() {
        System.out.println("FirstSpawnRandomizer Disabled!");
    }
	protected void teleport(Player player) {
		World world = player.getWorld();
		player.teleportTo(getRandomLocation(world));
	}
	private Location getRandomLocation(World world) {
		int radius = 10000;
		int x = rand.nextInt(radius * 2) - radius;
		int z = rand.nextInt(radius * 2) - radius;
		int y = world.getHighestBlockYAt(x, z);
		System.out.println("Finding random location...");
		return new Location(world, x + 0.5, y + 2, z + 0.5);
	}
	public boolean isFirstLogin (Player player)
	{
		boolean exists = (new File(getServer().getWorlds().get(0).getName() + "/players/" + player.getName() + ".dat")).exists();
		if (!exists)
		{
			System.out.println("First login detected.");
			return true;
		}
		System.out.println("Not first login.");
		return false;
	}

}

