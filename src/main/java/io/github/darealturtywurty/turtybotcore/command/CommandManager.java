package io.github.darealturtywurty.turtybotcore.command;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import io.github.darealturtywurty.turtybotcore.corebot.CoreBot;
import io.github.darealturtywurty.turtybotcore.database.DataAccess;
import io.github.darealturtywurty.turtybotcore.database.GuildData;
import io.github.darealturtywurty.turtybotcore.database.UserData;
import io.github.darealturtywurty.turtybotcore.discord.BotUtils;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class CommandManager {

    public final Set<GuildCommand> commands = new HashSet<>();

    public CommandManager() {
        registerCommands();
    }

    public void addCommand(final GuildCommand cmd) {
        this.commands.add(cmd);
    }

    public void addCommands(final GuildCommand... cmds) {
        Collections.addAll(this.commands, cmds);
    }

    public GuildCommand getCommand(final String name) {
        final Optional<GuildCommand> optional = this.commands.stream()
                .filter(cmd -> cmd.getName().equalsIgnoreCase(name)).findFirst();
        return optional.isPresent() ? optional.get() : null;
    }

    public void registerCommands() {
        final var reflections = new Reflections(
                new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage(CoreBot.class.getPackageName()))
                        .setScanners(Scanners.SubTypes, Scanners.TypesAnnotated)
                        .filterInputsBy(new FilterBuilder().includePackage(CoreBot.class.getPackageName())));
        reflections.getTypesAnnotatedWith(RegisterBotCmd.class).forEach(command -> {
            try {
                if (command.getAnnotation(RegisterBotCmd.class).needsManager()) {
                    addCommand((GuildCommand) command.getDeclaredConstructor(this.getClass()).newInstance(this));

                } else {
                    addCommand((GuildCommand) command.getDeclaredConstructor().newInstance());
                }
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
        });
    }

    protected void handle(final SlashCommandInteractionEvent event) {
        final GuildData guildData = DataAccess.getGuildData(event.getGuild());
        final UserData userData = guildData.getUser(event.getUser().getIdLong());
        final GuildCommand command = getCommand(event.getName());
        if (command == null)
            return;

        var allowed = true;
        if (command.blacklistChannels().contains(event.getChannel().getName().toLowerCase().trim())) {
            allowed = false;
        }

        if (command.whitelistChannels().contains(event.getChannel().getName().toLowerCase().trim())) {
            allowed = true;
        }

        if ((!command.isNSFW() || !((TextChannel) event.getChannel()).isNSFW()) && command.isNSFW()) {
            allowed = false;
        }

        if (!allowed) {
            event.getHook().deleteOriginal().queue();
        }

        if ((!command.isBotOwnerOnly() || !BotUtils.isBotOwner(event.getUser())) && command.isBotOwnerOnly()) {
            event.deferReply().setEphemeral(true).setContent("You must be the bot owner to use this command!").queue();
            return;
        }

        if ((!command.isModeratorOnly() || !BotUtils.isModerator(event.getMember())) && command.isModeratorOnly()) {
            event.deferReply().setEphemeral(true).setContent("You must be a moderator to use this command!").queue();
            return;
        }

        if (command.isBoosterOnly()
                && (!BotUtils.isBotOwner(event.getUser()) || event.getMember().getTimeBoosted() == null)) {
            event.deferReply().setEphemeral(true).setContent("You must be a server booster to use this command!")
                    .queue(hook -> {
                        hook.deleteOriginal().queueAfter(15, TimeUnit.SECONDS);
                        event.getHook().deleteOriginal().queueAfter(15, TimeUnit.SECONDS);
                    });
            return;
        }

        if (command.getCooldownMillis() > 0L) {
            final long cooldown = userData.getCooldown(command.getName());
            if (cooldown > 0L) {
                event.deferReply(true).setContent("You are currently on cooldown! You can run this command again in "
                        + TimeUnit.MILLISECONDS.toSeconds(cooldown) + " seconds!").queue();
                return;
            }
            userData.putCooldown(command.getName(), command.getCooldownMillis());
        }

        command.handle(new CoreCommandContext(event));
    }
}
