package de.schmidi.config;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.md_5.bungee.api.ChatColor;

public class SettingsConfig {

	private final File cfgPath;
	private FileConfiguration config;
	private final String defaultPath = "antiafk.";

	private final String amountOfBlocksPath = "block-amount";
	private final String minFoodPath = "min-foodlevel";
	private final String disableFarmMessagePath = "disable-farming-message";

	private final String amountOfCaughtEntitiesPath = "caught-fishes";
	private final String disableFishingMessagePath = "disable-fishing-message";

	private final String miningPermissionPath = "mining-permission";
	private final String fishingPermissionPath = "fishing-permission";

	private final String prefixPath = "prefix";
	private final String errorPrefixPath = "errorPrefix";

	public SettingsConfig(File dataFolder) {
		this.cfgPath = new File(dataFolder, "settings.yml");
		new YamlConfiguration();
		this.config = YamlConfiguration.loadConfiguration(cfgPath);

		this.setDefaultCfg().saveConfig();
	}

	private SettingsConfig setDefaultCfg() {
		config.options().header("**********************************************************************************\n"
				+ "										GENERAL										\n"
				+ "**********************************************************************************\n\n"

				+ "		You can edit the minimum amount of the foodlevel which a player needs\n"
				+ "			to farm (1/20) at the '" + this.minFoodPath + "' section.\n"
				+ "		You can edit the permission to bypass the afk mechanic for mining in\n" + "			the '"
				+ this.miningPermissionPath + "' section.\n"
				+ "		You can edit the permission to bypass the afk mechanic for fishing in\n" + "			the '"
				+ this.fishingPermissionPath + "' section.\n" + "You can edit the '" + this.prefixPath
				+ "' to change the prefix of the plugin.\n" + "You can edit the '" + this.errorPrefixPath
				+ "' section.\n\n"

				+ "**********************************************************************************\n"
				+ "										Mining										\n"
				+ "**********************************************************************************\n\n"

				+ "		You can set the amount of blocks that have to be destroyed for \n"
				+ "			every foodlevel you loose in the '" + this.amountOfBlocksPath + "' section.\n" + "\n"
				+ "		You can edit the error message when your foodlevel is too low (for mining)\n"
				+ "			in the '" + this.disableFarmMessagePath + "' section.\n\n"

				+ "**********************************************************************************\n"
				+ "										Fishing										\n"
				+ "**********************************************************************************\n\n"
				+ "		You can set the amount of entities that have to be fished for \n"
				+ "			every foodlevel you loose in the '" + this.amountOfCaughtEntitiesPath + "' section.\n"
				+ "\n" + "		You can edit the error message when your foodlevel is too low (for fishing)\n"
				+ "			in the '" + this.disableFishingMessagePath + "' section.\n\n"
				+ "**********************************************************************************\n\n");

		config.addDefault(defaultPath + minFoodPath, 6);
		config.addDefault(defaultPath + miningPermissionPath, "antiafk.mining.bypass");
		config.addDefault(defaultPath + fishingPermissionPath, "antiafk.fishing.bypass");
		config.addDefault(defaultPath + prefixPath, "§l§2AntiAFK §a»");
		config.addDefault(defaultPath + errorPrefixPath, "§l§4AntiAFK §c»");

		config.addDefault(defaultPath + amountOfBlocksPath, 25);
		config.addDefault(defaultPath + disableFarmMessagePath,
				ChatColor.RED + "You need to eat something before you are able to destroy more blocks.");

		config.addDefault(defaultPath + amountOfCaughtEntitiesPath, 5);
		config.addDefault(defaultPath + disableFishingMessagePath,
				ChatColor.RED + "You need to eat something before you are able to continue fishing.");

		config.options().copyDefaults(true);
		return this;
	}

	private void saveConfig() {
		try {
			this.config.save(cfgPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getBlockCount() {
		return config.getInt(defaultPath + amountOfBlocksPath);
	}

	public int getMinFoodlevel() {
		return config.getInt(defaultPath + minFoodPath);
	}

	public String getDisableDestroyingBlockMsg() {
		String msg = config.getString(defaultPath + disableFarmMessagePath);
		String parsedMsg = msg.replaceAll("&", "§");
		return parsedMsg;

	}

	public int getFishingCount() {
		return config.getInt(defaultPath + amountOfCaughtEntitiesPath);
	}

	public String getDisableFishingMsg() {
		return config.getString(defaultPath + disableFishingMessagePath);
	}

	public String getMiningBypassPermission() {
		return config.getString(defaultPath + miningPermissionPath);
	}

	public String getFishingBypassPermission() {
		return config.getString(defaultPath + fishingPermissionPath);
	}

	public String getPrefix() {
		return config.getString(defaultPath + prefixPath).replaceAll("&", "§");
	}

	public String getErrorPrefix() {
		return config.getString(defaultPath + errorPrefixPath).replaceAll("&", "§");
	}

}
