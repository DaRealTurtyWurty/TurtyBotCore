package io.github.darealturtywurty.turtybotcore.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.apache.commons.lang3.NotImplementedException;
import org.bson.Document;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mongodb.client.MongoCollection;

import io.github.darealturtywurty.turtybotcore.utils.Constants;
import io.github.darealturtywurty.turtybotcore.utils.json.JsonUtils;
import net.dv8tion.jda.annotations.Incubating;
import net.dv8tion.jda.api.entities.Guild;

public final class DataAccess {
    private static final Path GUILDS_PATH = Path.of("/guilds/%s.json");

    private DataAccess() {
        throw new IllegalAccessError("Unable to construct utility class: '" + this.getClass().getName() + "'!");
    }
    
    public static GuildData getGuildData(Guild guild) {
        final JsonObject processed = getProcessedData(guild);
        final var data = new GuildData();
        data.moderatorRoleID = getModeratorRole(processed);
        return data;
    }

    public static JsonObject getProcessedData(Guild guild) {
        final var fileData = getRawFileData(guild);
        try {
            return Constants.GSON.fromJson(Files.readString(fileData), JsonObject.class);
        } catch (JsonSyntaxException | IOException exception) {
            throw new IllegalStateException("There was an issue attempting to access or read file: '" + fileData + "'",
                    exception);
        }
    }

    public static Path getRawFileData(Guild guild) {
        final Path guildFile = Path.of(GUILDS_PATH.toString().formatted(guild.getId()));
        try {
            if (Files.notExists(guildFile, LinkOption.NOFOLLOW_LINKS)) {
                guildFile.toFile().mkdirs();
                Files.writeString(guildFile, Constants.GSON.toJson(new JsonObject()), StandardOpenOption.WRITE);
            }
            
            return guildFile;
        } catch (final SecurityException | IOException exception) {
            throw new IllegalStateException("Unable to get or create file at: '" + guildFile + "'", exception);
        }
    }

    @Incubating
    public static MongoCollection<Document> getRawMongoData(Guild guild) throws NotImplementedException {
        throw new NotImplementedException("This has not yet been implemented!");
    }

    private static long getModeratorRole(JsonObject json) {
        return JsonUtils.getLongFromJson(json, "ModeratorRole");
    }
}
