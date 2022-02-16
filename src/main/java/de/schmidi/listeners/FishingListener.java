package de.schmidi.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;

import de.schmidi.chatutil.ChatUtil;
import de.schmidi.config.SettingsConfig;

public class FishingListener implements Listener {

	private Map<Player, Integer> playerFishingCount;

	private final int fishingCount;
	private final String disableFishingMessage;
	private final int minFoodlevel;

	private final String byPassPermission;

	public FishingListener(SettingsConfig settingsConfig) {
		this.playerFishingCount = new HashMap<>();
		this.byPassPermission = settingsConfig.getFishingBypassPermission();
		this.minFoodlevel = settingsConfig.getMinFoodlevel();
		this.fishingCount = settingsConfig.getFishingCount();
		this.disableFishingMessage = settingsConfig.getDisableFishingMsg();
	}

	@EventHandler
	public void onPlayerFishing(PlayerFishEvent event) {
		State fishingState = event.getState();
		Player p = event.getPlayer();
		if (fishingState == State.FISHING) {
			if (p.getFoodLevel() <= this.minFoodlevel && !p.hasPermission(this.byPassPermission)) {
				event.setCancelled(true);
				ChatUtil.sendErrorMessage(p, this.disableFishingMessage);
			}
		} else if (fishingState == State.CAUGHT_ENTITY || fishingState == State.CAUGHT_FISH) {
			if (!p.hasPermission(this.byPassPermission)) {
				Integer fishedEntities = this.playerFishingCount.getOrDefault(p, 0);

				fishedEntities++;

				if (fishedEntities >= this.fishingCount) {
					p.setFoodLevel(p.getFoodLevel() - 1);
					fishedEntities = 0;
				}
				this.playerFishingCount.put(p, fishedEntities);
			}

		}
	}

}
