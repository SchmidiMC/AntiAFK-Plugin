package de.schmidi.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import de.schmidi.chatutil.ChatUtil;
import de.schmidi.config.SettingsConfig;

public class BlockDestroyListener implements Listener {

	public Map<Player, Integer> playerBlockCounter;

	private final int blockCount;
	private final int minFoodlevel;
	private final String disableFarmMessage;

	private final String byPassPermission;

	public BlockDestroyListener(SettingsConfig settingsConfig) {
		this.playerBlockCounter = new HashMap<>();
		this.byPassPermission = settingsConfig.getMiningBypassPermission();
		this.blockCount = settingsConfig.getBlockCount();
		this.minFoodlevel = settingsConfig.getMinFoodlevel();
		this.disableFarmMessage = settingsConfig.getDisableDestroyingBlockMsg();

	}

	@EventHandler
	public void onBlockBreaking(BlockBreakEvent event) {

		Player p = event.getPlayer();

		if (!p.hasPermission(this.byPassPermission)) {
			if (p.getFoodLevel() <= this.minFoodlevel) {
				event.setCancelled(true);
				ChatUtil.sendErrorMessage(p, this.disableFarmMessage);
				return;
			}

			Integer blocks = this.playerBlockCounter.getOrDefault(p, 0);

			blocks++;

			if (blocks >= this.blockCount) {
				p.setFoodLevel(p.getFoodLevel() - 1);
				blocks = 0;
			}
			this.playerBlockCounter.put(p, blocks);
		}

	}

}
