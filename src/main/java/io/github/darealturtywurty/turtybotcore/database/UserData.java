package io.github.darealturtywurty.turtybotcore.database;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class UserData {
    protected Map<String, Long> commandCooldown = new HashMap<>();
    
    /**
     * Decrements the cooldown for the given command
     *
     * @param command - the command name to decrement the cooldown for
     * @param amount  - the amount of time to decrement
     * @return the new cooldown time (or 0 if there is no cooldown)
     */
    public long decrementCooldown(String command, long amount) {
        final var retCooldown = new AtomicLong(0L);
        this.commandCooldown.computeIfPresent(command, (key, value) -> {
            retCooldown.set(value - amount);
            if (retCooldown.get() <= 0L)
                return null;
            return retCooldown.get();
        });
        return retCooldown.get();
    }
    
    /**
     * Gets the map of command name to cooldown time
     *
     * @return the map
     */
    public Map<String, Long> getCommandCooldown() {
        return this.commandCooldown;
    }
    
    /**
     * Gets the cooldown for the given command
     *
     * @param command - the name of the command
     * @return the cooldown for that command
     */
    public long getCooldown(String command) {
        return this.commandCooldown.containsKey(command) ? this.commandCooldown.get(command) : 0L;
    }
    
    public long putCooldown(String command, long time) {
        if (time <= 0L && !this.commandCooldown.containsKey(command))
            return 0L;
        
        if (time <= 0L && this.commandCooldown.containsKey(command)) {
            removeCooldown(command);
            return 0L;
        }

        if (this.commandCooldown.containsKey(command) && time > 0L) {
            this.commandCooldown.put(command, this.commandCooldown.get(command) + time);
        }

        return this.commandCooldown.computeIfAbsent(command, key -> time);
    }
    
    /**
     * Removes a cooldown for the given command
     *
     * @param command - the command name to remove the cooldown for
     * @return the cooldown before it was removed
     */
    public long removeCooldown(String command) {
        return this.commandCooldown.remove(command);
    }
}
