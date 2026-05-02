package com.killersword;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KillerSwordCommand implements CommandExecutor {

    private final KillerSwordPlugin plugin;

    public KillerSwordCommand(KillerSwordPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("give")) {
            Player target = null;
            if (args.length >= 2) {
                target = plugin.getServer().getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(ChatColor.RED + "Player nahi mila!");
                    return true;
                }
            } else if (sender instanceof Player) {
                target = (Player) sender;
            } else {
                sender.sendMessage(ChatColor.RED + "Player ka naam do!");
                return true;
            }
            if (!sender.hasPermission("killersword.give")) {
                sender.sendMessage(ChatColor.RED + "Permission nahi hai!");
                return true;
            }
            ItemStack sword = KillerSwordItem.create();
            target.getInventory().addItem(sword);
            target.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Killer Sword mila!");
            if (sender != target) {
                sender.sendMessage(ChatColor.GREEN + "Killer Sword diya: " + target.getName());
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(ChatColor.AQUA + "=== Killer Sword ===");
            sender.sendMessage(ChatColor.YELLOW + "/killersword give" + ChatColor.GRAY + " - Sword lo");
            sender.sendMessage(ChatColor.YELLOW + "/killersword give <player>" + ChatColor.GRAY + " - Kisi ko do");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Galat command!");
        return true;
    }
}    
