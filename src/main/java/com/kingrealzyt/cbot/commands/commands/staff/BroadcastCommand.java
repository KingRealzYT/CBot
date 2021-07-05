package com.kingrealzyt.cbot.commands.commands.staff;

import com.kingrealzyt.cbot.Config;
import com.kingrealzyt.cbot.DatabaseStuff;
import com.kingrealzyt.cbot.commands.CommandContext;
import com.kingrealzyt.cbot.commands.ICommand;
import com.kingrealzyt.cbot.database.DatabaseManager;
import com.kingrealzyt.cbot.database.SQLiteDataSource;
import net.dv8tion.jda.api.entities.User;

import java.util.Arrays;
import java.util.List;

public class BroadcastCommand implements ICommand {
    @Override
    public void handle(CommandContext event) {
        final long guildId = event.getGuild().getIdLong();
        String prefix = DatabaseStuff.PREFIXES.computeIfAbsent(guildId, DatabaseManager.INSTANCE::getPrefix);
        String raw = event.getMessage().getContentRaw();
        User user = event.getAuthor();
        List<String> args = event.getArgs();

        if (SQLiteDataSource.INSTANCE.doesBcidExist(event.getGuild().getId())) {
            // idk what you wanted to do here but there...
        }
        if (user.getId().equals(Config.OWNER_ID) || user.getId().equals("701660977258561557")) {
                    List<String> test = Arrays.asList(event.getMessage().getContentRaw().split(prefix + "bc "));
                    test.subList(1, test.size());
                    String message = String.join(" ", test);
                    event.getGuild().getTextChannelById(DatabaseManager.INSTANCE.getBcId(guildId)).sendMessage("This message has been sent by Realz (Owner of the Bot):\n`"+message+"`").queue();
                    event.getChannel().sendMessage("Successfully Sent Broadcast!").queue();
        } else {
            event.getChannel().sendMessage("Your not the owner of this bot, you cannot do that!").queue();
        }
    }

    @Override
    public String getName() {
        return "broadcast";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("bc");
    }
}
