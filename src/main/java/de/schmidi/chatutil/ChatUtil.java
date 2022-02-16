package de.schmidi.chatutil;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;


public class ChatUtil {

	private static ConsoleCommandSender console = null;

	private static String prefix;
	private static String errorPrefix;
	
	private static ChatColor defaultMsgColor = ChatColor.GREEN;
	private static ChatColor defaultErrorColor = ChatColor.RED;

	private static String warningPrefix = ChatColor.YELLOW + "WARNING: " + ChatColor.WHITE;

	public static void setConsole(ConsoleCommandSender console) {
		ChatUtil.console = console;
	}

	public static void sendConsoleMessage(String msg) {
		sendConsoleMessage(msg, defaultMsgColor, prefix);
	}

	public static void sendConsoleErrorMessage(String msg) {
		sendConsoleMessage(msg, defaultErrorColor, errorPrefix);
	}

	public static void sendMessage(CommandSender sender, String msg) {
		sendServerMessage(sender, msg, defaultMsgColor, prefix);
	}

	public static void sendErrorMessage(CommandSender sender, String msg) {
		sendServerMessage(sender, msg, defaultErrorColor, errorPrefix);

	}

	private static void sendServerMessage(CommandSender sender, String msg, ChatColor defaultColor, String msgPrefix) {
		if (!prefix.isEmpty()) {
			if (sender != null) {
				sender.sendMessage(msgPrefix + defaultColor + msg);
			}
		} else {
			System.out.println(warningPrefix + "Der Plugin Prefix wurde nicht gesetzt.");
		}
	}

	private static void sendConsoleMessage(String msg, ChatColor defaultColor, String msgPrefix) {
		if (ChatUtil.console != null) {
			console.sendMessage(msgPrefix + defaultColor + msg);
		} else {
			System.out.println(warningPrefix + "Die Konsole wurde dem ChatUtil nicht übergeben.");
		}
	}
	
	public static void setPrefix(String prefix) {
		ChatUtil.prefix = prefix+" ";
	}

	public static void setErrorPrefix(String errorPrefix) {
		ChatUtil.errorPrefix = errorPrefix+" ";
	}
}
