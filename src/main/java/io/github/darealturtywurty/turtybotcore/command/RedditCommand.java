package io.github.darealturtywurty.turtybotcore.command;

import java.util.Set;

public interface RedditCommand extends GuildCommand {

    Set<String> getSubreddits();
}
