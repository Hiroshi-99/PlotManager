package org.champa.plotManager.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.champa.plotManager.PlotManager;
import org.champa.plotManager.util.ColorUtil;

import java.util.ArrayList;
import java.util.List;

public class PlotCommand implements CommandExecutor, TabCompleter {
    private final PlotManager plugin;

    public PlotCommand(PlotManager plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ColorUtil.color("&cOnly players can use this command!"));
            return true;
        }

        if (!player.hasPermission("plotmanager.use")) {
            player.sendMessage(ColorUtil.color("&cYou don't have permission to use this command!"));
            return true;
        }

        if (args.length == 0) {
            plugin.getMenuManager().openMainMenu(player);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "plots" -> plugin.getMenuManager().openPlotsMenu(player);
            case "biome" -> plugin.getMenuManager().openBiomeMenu(player);
            case "settings" -> plugin.getMenuManager().openSettingsMenu(player);
            case "help" -> sendHelp(player);
            case "reload" -> {
                if (sender.hasPermission("plotmanager.admin")) {
                    plugin.reloadPluginConfig();
                    sender.sendMessage(ColorUtil.color("&aConfiguration reloaded!"));
                } else {
                    sender.sendMessage(ColorUtil.color("&cYou don't have permission to do that!"));
                }
            }
            default -> player.sendMessage(ColorUtil.color("&cUnknown subcommand. Use /plot help for help."));
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("plots");
            completions.add("biome");
            completions.add("settings");
            completions.add("help");
        }

        return completions;
    }

    private void sendHelp(Player player) {
        player.sendMessage(ColorUtil.color("&6=== PlotManager Help ==="));
        player.sendMessage(ColorUtil.color("&e/plot &7- Open main menu"));
        player.sendMessage(ColorUtil.color("&e/plot plots &7- View your plots"));
        player.sendMessage(ColorUtil.color("&e/plot biome &7- Change plot biome"));
        player.sendMessage(ColorUtil.color("&e/plot settings &7- Manage plot settings"));
    }

}