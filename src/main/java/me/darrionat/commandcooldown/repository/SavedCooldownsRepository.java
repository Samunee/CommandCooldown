package me.darrionat.commandcooldown.repository;

import me.darrionat.commandcooldown.CommandCooldownPlugin;
import me.darrionat.commandcooldown.cooldowns.Cooldown;
import me.darrionat.commandcooldown.cooldowns.PlayerCooldown;
import me.darrionat.commandcooldown.cooldowns.SavedCommand;
import me.darrionat.commandcooldown.interfaces.ISavedCooldownsRepository;
import me.darrionat.pluginlib.files.Config;
import me.darrionat.pluginlib.files.ConfigBuilder;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class SavedCooldownsRepository implements ISavedCooldownsRepository {
    private static final String LIST_NAME = "savedCooldowns";
    private final CommandCooldownPlugin plugin;
    private final Config config;
    private FileConfiguration file;

    public SavedCooldownsRepository(CommandCooldownPlugin plugin) {
        this.plugin = plugin;
        ConfigBuilder builder = new ConfigBuilder(plugin, SAVED_COOLDOWNS);
        config = builder.build();
        init();
    }

    public void init() {
        file = config.getFileConfiguration();
    }

    @Override
    public void savePlayerCooldown(PlayerCooldown cooldown) {
        Collection<PlayerCooldown> cooldownList = loadAllCooldowns();
        cooldownList.add(cooldown);
        List<String> toSave = new ArrayList<>();
        for (PlayerCooldown cd : cooldownList)
            toSave.add(cd.toString());
        file.set(LIST_NAME, toSave);
        config.save(file);
    }

    @Override
    public void savePlayerCooldowns(Collection<PlayerCooldown> cooldowns) {
        Collection<PlayerCooldown> cooldownList = loadAllCooldowns();
        cooldownList.addAll(cooldowns);

        Set<String> set = new HashSet<>();
        for (PlayerCooldown cd : cooldownList)
            if (!cd.expired())
                set.add(cd.toString());

        List<String> toSave = Arrays.asList(set.toArray(new String[0]));
        file.set(LIST_NAME, toSave);
        config.save(file);
    }

    @Override
    public Collection<PlayerCooldown> loadAllCooldowns() {
        // pc.toString() = uuid/warp/home 2/3456789
        HashSet<PlayerCooldown> toReturn = new HashSet<>();
        for (String pc : file.getStringList(LIST_NAME)) {
            String[] arr = pc.split(PlayerCooldown.SEP);
            if (arr.length != 5) {
                plugin.getErrorHandler().loadingSavedCooldownsError();
                continue;
            }
            UUID uuid = UUID.fromString(arr[0]);
            String label = arr[1];
            String argsStr = arr[2];
            double duration = Double.parseDouble(arr[3]);
            long end = Long.parseLong(arr[4]);

            SavedCommand command = new SavedCommand(label);
            Cooldown cooldown = new Cooldown(command, argsStr, duration);
            PlayerCooldown playerCd = new PlayerCooldown(uuid, cooldown, end);
            if (!playerCd.expired())
                toReturn.add(playerCd);
        }
        return toReturn;
    }
}