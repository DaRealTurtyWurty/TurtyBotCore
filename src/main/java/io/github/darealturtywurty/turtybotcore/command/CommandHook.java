package io.github.darealturtywurty.turtybotcore.command;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

import io.github.darealturtywurty.turtybotcore.discord.BotUtils;
import io.github.darealturtywurty.turtybotcore.utils.Constants;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

public class CommandHook extends ListenerAdapter {
    
    protected final CommandManager manager = new CommandManager();
    private final Timer readTimer = new Timer();
    
    @Override
    public void onGuildReady(final GuildReadyEvent event) {
        final Guild guild = event.getGuild();
        this.readTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                guild.loadMembers().onSuccess(members -> Constants.LOGGER.log(Level.FINE,
                        String.format("%s Members loaded for guild: %s", members.size(), guild.getName())));
            }
        }, 5000);
        
        final CommandListUpdateAction updates = guild.updateCommands();
        updates.addCommands(this.manager.commands.stream()
                .filter(cmd -> cmd.productionReady() || !BotUtils.notTestServer(guild)).map(cmd -> {
                    final var data = Commands.slash(cmd.getName(), cmd.getDescription());
                    if (!cmd.getSubcommandGroupData().isEmpty()) {
                        data.addSubcommandGroups(cmd.getSubcommandGroupData());
                    } else if (!cmd.getSubcommandData().isEmpty()) {
                        data.addSubcommands(cmd.getSubcommandData());
                    } else if (!cmd.getOptions().isEmpty()) {
                        data.addOptions(cmd.getOptions());
                    }
                    
                    return data;
                }).toList());
        updates.queue();
    }
    
    @Override
    public void onSlashCommandInteraction(final SlashCommandInteractionEvent event) {
        if (event.getUser().isBot() || event.getUser().isSystem())
            return;
        
        this.manager.handle(event);
    }
}
