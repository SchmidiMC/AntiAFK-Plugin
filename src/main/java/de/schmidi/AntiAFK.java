package de.schmidi;

import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.schmidi.chatutil.ChatUtil;
import de.schmidi.config.SettingsConfig;
import de.schmidi.listeners.BlockDestroyListener;
import de.schmidi.listeners.FishingListener;

public class AntiAFK extends JavaPlugin {

	private SettingsConfig settingsConfig;
	private Server server;

	public void onEnable() {
		this.server = this.getServer();
		this.settingsConfig = new SettingsConfig(this.getDataFolder());
		ChatUtil.setPrefix(this.settingsConfig.getPrefix());
		ChatUtil.setErrorPrefix(this.settingsConfig.getErrorPrefix());
		ChatUtil.setConsole(this.server.getConsoleSender());
		ChatUtil.sendConsoleMessage("loading...");

		registerListeners();

		ChatUtil.sendConsoleMessage("started successfully!");

	}

	public void onDisable() {
		ChatUtil.sendConsoleMessage("is disabled.");
	}

	private void registerListeners() {
		PluginManager pm = this.server.getPluginManager();
		pm.registerEvents(new BlockDestroyListener(this.settingsConfig), this);
		pm.registerEvents(new FishingListener(this.settingsConfig), this);
	}

	public SettingsConfig getSettingsConfig() {
		return this.settingsConfig;
	}

}
