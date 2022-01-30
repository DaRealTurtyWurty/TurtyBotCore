package io.github.darealturtywurty.turtybotcore.database;

import java.util.HashMap;
import java.util.Map;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

public class GuildData {
    protected long moderatorRoleID = 0L;
    protected Map<Long, UserData> userData = new HashMap<>();
    
    public UserData getUser(long userId) {
        return this.userData.containsKey(userId) ? this.userData.get(userId)
                : this.userData.put(userId, new UserData());
    }

    public UserData getUser(Member member) {
        return getUser(member.getIdLong());
    }

    public UserData getUser(String userId) {
        return getUser(Long.parseLong(userId));
    }

    public UserData getUser(User user) {
        return getUser(user.getIdLong());
    }
    
    public long getModeratorRole() {
        return this.moderatorRoleID;
    }
}
