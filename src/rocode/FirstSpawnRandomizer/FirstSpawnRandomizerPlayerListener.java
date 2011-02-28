package rocode.FirstSpawnRandomizer;

import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;

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
	public void onPlayerLogin(PlayerLoginEvent event) {
		if (plugin.isFirstLogin(event.getPlayer())) {
			plugin.teleport(event.getPlayer());
		}
		
		super.onPlayerLogin(event);
	}
    
}

