package tech.sodiium.pamatic.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tech.sodiium.pamatic.SchematicManager;

import java.io.File;
import java.io.IOException;

public class PasteSchematic implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) return false;

        if (commandSender instanceof Player) {
            try {
                if (args.length >= 4) {
                    World world;
                    if (args.length == 5) {
                        world = Bukkit.getWorld(args[4]);
                        if (world == null) {
                            commandSender.sendMessage(ChatColor.RED + "[Pamatic] The given world does not exist!");
                            return true;
                        }
                    } else {
                        world = ((Player) commandSender).getWorld();
                    }
                    SchematicManager.pasteSchematic(new File(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), world);
                    commandSender.sendMessage(ChatColor.GREEN + "[Pamatic] Schematic pasted successfully!");
                } else {
                    SchematicManager.pasteSchematic(new File(args[0]), ((Player) commandSender).getLocation());
                    commandSender.sendMessage(ChatColor.GREEN + "[Pamatic] Schematic pasted successfully!");
                }
            } catch (NumberFormatException ex) {
                commandSender.sendMessage(ChatColor.RED + "[Pamatic] The set coordinates do not exist!");
            } catch (IOException e) {
                commandSender.sendMessage(ChatColor.RED + "[Pamatic] The set schematic does not exist/isn't a valid schematic!");
            }
        } else {
            if (args.length == 5) {
                World world = Bukkit.getWorld(args[4]);
                if (world == null) {
                    commandSender.sendMessage(ChatColor.RED + "[Pamatic] The given world does not exist!");
                    return true;
                }
                try {
                    SchematicManager.pasteSchematic(new File(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), world);
                    commandSender.sendMessage(ChatColor.GREEN + "[Pamatic] Schematic pasted successfully!");
                } catch (IOException e) {
                    commandSender.sendMessage(ChatColor.RED + "[Pamatic] The set schematic does not exist/isn't a valid schematic!");
                }
            }
            else {
                commandSender.sendMessage(ChatColor.RED + "[Pamatic] You arent a player! You need to specify coordinates and world after the schematic!");
                return false;
            }
        }

        return true;
    }
}
