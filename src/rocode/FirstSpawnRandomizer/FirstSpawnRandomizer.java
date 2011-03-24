package rocode.FirstSpawnRandomizer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
	private static final String filePath = "Spawn.properties";
	private final FirstSpawnRandomizerPlayerListener playerListener = new FirstSpawnRandomizerPlayerListener(this);
	private static Location mainLoc;
	public boolean RandomSpawn=false;
	private Integer delay=200;

    private Random rand = new Random(System.nanoTime());

    public void onEnable() {
    	loadProperties();
    	setMainLoc(new Location(this.getServer().getWorlds().get(0), 0, 0, 0));
    	PluginManager pm = getServer().getPluginManager();
    	pm.registerEvent(Type.PLAYER_JOIN, playerListener, Priority.Low, this);
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
        for(World world:this.getServer().getWorlds())
        {
        	getServer().getScheduler().scheduleAsyncRepeatingTask(this,new FirstSpawnRandomizerLocationRand(this,world.getSpawnLocation()),1L, delay);
        }
    }
    public void onDisable() {
        System.out.println("FirstSpawnRandomizer Disabled!");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
	{
		String commandName = command.getName().toLowerCase();
		if(commandName=="random")
		{
			if(sender instanceof Player)
			{
				this.teleport((Player) sender);
				return true;
			}else{
				for(Player p:sender.getServer().getOnlinePlayers())
				{
					this.teleport(p);
					return true;
				}
			}
		}
		return false;
	}
    
	protected void teleport(Player player) {
		Location location = getRandomLocation(getMainLoc(),40);
		player.teleportTo(location);
	}
	private void loadProperties() {
        Scanner properties;
        String next;
        
        getDataFolder().mkdir();
        try {
            properties = new Scanner(new File(getDataFolder().getPath()+"/"+filePath));
            
            while(properties.hasNext()) {
                next = properties.next();
				if(next.equals("RandomFirstSpawn:"))
                    RandomSpawn = properties.nextBoolean();
				if(next.equals("DelayBetweanSpawnChange:"))
                    delay = properties.nextInt();
            }
            
            properties.close();
        } catch (FileNotFoundException fnfe) {
            saveProperties();
            return;
        }
    }
    private void saveProperties() {
        BufferedWriter file = null;
        
        try {
            file = new BufferedWriter(new FileWriter(getDataFolder().getPath()+"/"+filePath));
            file.write("RandomFirstSpawn: "+RandomSpawn);
            file.newLine();
            file.write("DelayBetweanSpawnChange: "+delay);
            file.close();
        } catch (IOException ioe) {}//do nothing
    }
	/**
	 * old version
	 * @param world
	 * @param radius
	 * @return
	 */
	@SuppressWarnings("unused")
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
	public synchronized static void setMainLoc(Location mainLoc) {
		FirstSpawnRandomizer.mainLoc = mainLoc;
	}
	/**
	 * @return the mainLoc
	 */
	public synchronized static Location getMainLoc() {
		return mainLoc;
	}

}

