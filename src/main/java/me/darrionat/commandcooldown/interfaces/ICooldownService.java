package me.darrionat.commandcooldown.interfaces;

import me.darrionat.commandcooldown.cooldowns.Cooldown;
import org.bukkit.entity.Player;

public interface ICooldownService extends Service {
    /**
     * Gets a cooldown from a command message.
     *
     * @param s The string of the command that does not contain the command special character.
     * @return the cooldown if it exists; otherwise {@code null}.
     */
    Cooldown parseCooldown(String s);

    /**
     * Gives a player a defined cooldown.
     *
     * @param p        The player to give the cooldown.
     * @param cooldown The cooldown the player will receive.
     */
    void giveCooldown(Player p, Cooldown cooldown);

    /**
     * Removes all cooldowns from a specified player.
     *
     * @param p The player
     */
    void removePlayerCooldowns(Player p);

    /**
     * Gets the player's remaining cooldown for a particular command in seconds.
     *
     * @param p        the player.
     * @param cooldown the command with a cooldown.
     * @return returns a player's remaining cooldown for the command; {@code -1} if the player has no cooldown.
     */
    double getRemainingCooldown(Player p, Cooldown cooldown);

    /**
     * Determines if the player has a cooldown for a command.
     *
     * @param p        the player.
     * @param cooldown the cooldown.
     * @return {@code true} if the player has a cooldown on the command; otherwise {@code false}.
     */
    boolean playerHasCooldown(Player p, Cooldown cooldown);

    /**
     * Checks a player's permissions to see if they have a permission that affects the cooldown's duration.
     *
     * @param p        The player.
     * @param cooldown The cooldown to check for duration changes.
     * @return The cooldown with a changed duration if the player's permissions affect it; otherwise the original
     *         cooldown.
     */
    Cooldown permissionCooldownChange(Player p, Cooldown cooldown);

    /**
     * Loads all cooldowns from the saved cooldowns config and puts them into effect.
     */
    void loadAllCooldowns();

    /**
     * Saves all cooldowns to the saved cooldowns config.
     * <p>
     * All expired cooldowns will be removed.
     */
    void saveAllCooldowns();
}