package me.darrionat.commandcooldown.commands.subcommands;

import me.darrionat.commandcooldown.CommandCooldownPlugin;
import me.darrionat.commandcooldown.commands.CommandCooldownCommand;
import me.darrionat.commandcooldown.interfaces.IMessageService;
import me.darrionat.pluginlib.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveCooldownsCommand extends SubCommand {
    private final CommandCooldownPlugin plugin;
    private final IMessageService messageService;

    public RemoveCooldownsCommand(CommandCooldownCommand parentCommand, CommandCooldownPlugin plugin, IMessageService messageService) {
        super(parentCommand, plugin);
        this.plugin = plugin;
        this.messageService = messageService;
    }

    @Override
    public String getSubCommand() {
        return "removecooldowns";
    }

    @Override
    public int getRequiredArgs() {
        return 2;
    }

    @Override
    public boolean onlyPlayers() {
        return false;
    }

    @Override
    // cc removecooldowns [player]
    protected void runCommand(CommandSender sender, String[] args) {
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null)
            return;
        plugin.removePlayerCooldowns(target);
        messageService.sendResetMessage(sender, target);
    }
}