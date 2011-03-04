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
	private static Location mainLoc;

    private Random rand = new Random(System.nanoTime());

    public void onEnable() {
    	setMainLoc(new Location(this.getServer().getWorlds().get(0), 0, 0, 0));
    	PluginManager pm = getServer().getPluginManager();
    	pm.registerEvent(Type.PLAYER_JOIN, playerListener, Priority.Low, this);
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
        for(World world:this.getServer().getWorlds())
        {
        	getServer().getScheduler().scheduleAsyncRepeatingTask(this,new FirstSpawnRandomizerLocationRand(this,world),1L, 200L);
        }
    }
    public void onDisable() {
        System.out.println("FirstSpawnRandomizer Disabled!");
    }
	protected void teleport(Player player) {
		Location location = getRandomLocation(getMainLoc(),40);
		player.teleportTo(location);
	}
	/**
	 * old version
	 * @param world
	 * @param radius
	 * @return
	 */
	private Location getRandomLocation(World world,int radius) {
		int x = rand.nextInt(radius * 2) - radius;
		int z = rand.nextInt(radius * 2) - radius;
		world.getChunkAt(world.getBlockAt(x,0,z));
		int y = world.getHighestBlockYAt(x, z);
		return new Location(world, x + 0.5, y + 3, z + 0.5);
	}
	/**
	 * New version takes the world from the location given
	 * @param loc
	 * @param radius
	 * @return
	 */
	public Location getRandomLocation(Location loc,int radius) {
		int x = rand.nextInt(radius * 2) - radius;
		int z = rand.nextInt(radius * 2) - radius;
		World world=loc.getWorld();
		world.getChunkAt(world.getBlockAt(x,0,z));
		int y = world.getHighestBlockYAt(x, z);
		return new Location(world, x + loc.getBlockX(), y + loc.getBlockY(), z + loc.getBlockZ());
	}
	public boolean isFirstLogin (Player player)
	{
		boolean exists = (new File(getServer().getWorlds().get(0).getName() + "/players/" + player.getName() + ".dat")).exists();
		if (!exists)
		{
			System.out.println("First login detected.");
			return true;
		}
		return false;
	}
	/**
	 * @param mainLoc the mainLoc to set
	 */
	public static void setMainLoc(Location mainLoc) {
		FirstSpawnRandomizer.mainLoc = mainLoc;
	}
	/**
	 * @return the mainLoc
	 */
	public static Location getMainLoc() {
		return mainLoc;
	}

}

