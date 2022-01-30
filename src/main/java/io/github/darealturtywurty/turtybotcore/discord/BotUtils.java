package io.github.darealturtywurty.turtybotcore.discord;

import java.util.concurrent.atomic.AtomicBoolean;

import io.github.darealturtywurty.turtybotcore.corebot.CoreBot;
import io.github.darealturtywurty.turtybotcore.database.DatabaseHandler;
import io.github.darealturtywurty.turtybotcore.database.GuildData;
import io.github.darealturtywurty.turtybotcore.utils.Constants;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

public final class BotUtils {
    private BotUtils() {
    }
    
    public static boolean isBotOwner(User user) {
        return user.getIdLong() == CoreBot.getInstance().getConfig().getOwnerID();
    }

    public static boolean isModerator(Member member) {
        if (member == null)
            return false;
        final GuildData data = DatabaseHandler.getDataForGuild(member.getGuild());
        if (data.getModeratorRole() == 0L)
            return false;

        final var found = new AtomicBoolean(false);
        member.getGuild().findMembersWithRoles(member.getGuild().getRoleById(data.getModeratorRole()))
                .onSuccess(members -> found.set(members.contains(member)));
        return found.get();
    }

    public static boolean notTestServer(Guild guild) {
        return guild.getIdLong() != Constants.TEST_SERVER_ID;
    }
}
