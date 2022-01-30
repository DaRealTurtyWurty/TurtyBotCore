package io.github.darealturtywurty.turtybotcore.module;

import io.github.darealturtywurty.turtybotcore.config.Config;
import net.dv8tion.jda.api.JDA;

public interface BotModule {

    JDA getBot();

    Config getConfig();
}
