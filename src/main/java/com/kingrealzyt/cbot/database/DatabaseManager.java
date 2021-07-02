package com.kingrealzyt.cbot.database;

public interface DatabaseManager {

    DatabaseManager INSTANCE = new SQLiteDataSource();

    String getPrefix(long guildId);
    void setPrefix(long guildId, String newPrefix);

    String getBcId(long guildId);
    void setBcId(long guildId, String newBcId);
    boolean doesBcidExist(String guildID);

}
