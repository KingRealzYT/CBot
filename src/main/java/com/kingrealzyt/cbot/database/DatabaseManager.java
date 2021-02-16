package com.kingrealzyt.cbot.database;

public interface DatabaseManager {

    DatabaseManager INSTANCE = new SQLiteDataSource();

    String getPrefix(long guildId);
    void setPrefix(long guildId, String newPrefix);
}
