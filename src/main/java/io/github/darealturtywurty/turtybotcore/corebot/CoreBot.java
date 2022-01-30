package io.github.darealturtywurty.turtybotcore.corebot;

import javax.security.auth.login.LoginException;

import io.github.darealturtywurty.turtybotcore.config.Config.ConfigBuilder;
import io.github.darealturtywurty.turtybotcore.module.BotModule;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class CoreBot implements BotModule {
    private static CoreBot instance;
    private final JDA jda;
    private final CoreConfig config;
    
    private CoreBot() throws LoginException {
        this.config = new CoreConfig(
                new ConfigBuilder("core").add("BotToken", "undefined").add("OwnerID", 309776610255437824L));
        this.jda = JDABuilder.createDefault(this.config.getBotToken()).setMemberCachePolicy(MemberCachePolicy.ALL)
                .setChunkingFilter(ChunkingFilter.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES).build();
    }

    @Override
    public JDA getBot() {
        return this.jda;
    }
    
    @Override
    public CoreConfig getConfig() {
        return this.config;
    }
    
    public static CoreBot getInstance() {
        return instance;
    }
}
