package io.github.darealturtywurty.turtybotcore.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class CoreCommandContext implements CommandContext {
    private final SlashCommandInteractionEvent event;
    
    public CoreCommandContext(final SlashCommandInteractionEvent event) {
        this.event = event;
    }
    
    @Override
    public SlashCommandInteractionEvent getEvent() {
        return this.event;
    }
}
