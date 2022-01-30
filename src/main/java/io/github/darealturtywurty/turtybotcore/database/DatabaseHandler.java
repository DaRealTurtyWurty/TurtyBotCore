package io.github.darealturtywurty.turtybotcore.database;

import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import io.github.darealturtywurty.turtybotcore.corebot.CoreBot;
import net.dv8tion.jda.api.entities.Guild;

public final class DatabaseHandler {
    private static MongoDatabase database;

    static {
        final var client = MongoClients.create(CoreBot.getInstance().getConfig().getMongoConnection());
        DatabaseHandler.database = client.getDatabase("TurtyBot");
    }

    private DatabaseHandler() {
    }

    public static GuildData getDataForGuild(Guild guild) {
        return getDataForGuild(guild.getIdLong());
    }
    
    public static GuildData getDataForGuild(long guildId) {
        return getDataForGuild(guildId + "");
    }
    
    public static GuildData getDataForGuild(String guildId) {
        final var data = new GuildData();
        final MongoCollection<Document> collection = getOrCreateCollection(guildId);
        final FindIterable<Document> search = collection.find(Filters.exists("Data"));

        final Map<String, Object> updateData = new HashMap<>();
        final Document dataDocument = new Document("Data", 1);
        Document document = search.first();
        if (document == null) {
            document = dataDocument;
            collection.insertOne(document);
        }
        collection.updateOne(dataDocument, new Document("$set", new Document(updateData)));
        
        return data;
    }

    private static MongoCollection<Document> getOrCreateCollection(String name) {
        MongoCollection<Document> collection;
        try {
            collection = database.getCollection(name);
        } catch (final IllegalArgumentException exception) {
            database.createCollection(name);
            collection = database.getCollection(name);
        }

        return collection;
    }
}
