package io.github.darealturtywurty.turtybotcore.database;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import io.github.darealturtywurty.turtybotcore.config.Config;

public class Database {
    public static final MongoDatabase DATABASE;
    static {
        final var client = MongoClients.create(Config.get("MongoConnection"));
        Database.DATABASE = client.getDatabase("TurtyBot");
    }
}
