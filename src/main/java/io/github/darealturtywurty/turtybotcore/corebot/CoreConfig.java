package io.github.darealturtywurty.turtybotcore.corebot;

import io.github.darealturtywurty.turtybotcore.config.Config;
import io.github.darealturtywurty.turtybotcore.module.TokenableConfig;

public class CoreConfig extends Config implements TokenableConfig {
    public CoreConfig(ConfigBuilder config) {
        super(config);
    }

    @Override
    public String getBotToken() {
        return getJsonObject().get("BotToken").getAsString();
    }

    public String getMongoConnection() {
        return getJsonObject().get("MongoConnection").getAsString();
    }

    public long getOwnerID() {
        return getJsonObject().get("OwnerID").getAsLong();
    }
}
