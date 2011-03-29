package rocode.FirstSpawnRandomizer;

import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;

/**
 * Handle events for all Player related events
 * @author rocode
 */
public class FirstSpawnRandomizerPlayerListener extends PlayerListener {
    private final FirstSpawnRandomizer plugin;
    public FirstSpawnRandomizerPlayerListener(FirstSpawnRandomizer instance) {
        plugin = instance;
    }
    
    @Override
    public void onPlayerJoin (PlayerEvent event){
		if (plugin.isFirstLogin(event.getPlayer()))
    	{
			plugin.teleport(event.getPlayer());
		}
		super.onPlayerJoin(event);
    }
    
}

