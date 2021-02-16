package com.kingrealzyt.cbot.commands.commands.staff;

import com.kingrealzyt.cbot.Config;
import com.kingrealzyt.cbot.PrefixStuff;
import com.kingrealzyt.cbot.commands.CommandContext;
import com.kingrealzyt.cbot.commands.ICommand;
import com.kingrealzyt.cbot.database.DatabaseManager;
import net.dv8tion.jda.api.entities.User;

import java.util.Arrays;
import java.util.List;

public class BroadcastCommand implements ICommand {
    @Override
    public void handle(CommandContext event) {
        final long guildId = event.getGuild().getIdLong();
        String prefix = PrefixStuff.PREFIXES.computeIfAbsent(guildId, DatabaseManager.INSTANCE::getPrefix);
        String raw = event.getMessage().getContentRaw();
        User user = event.getAuthor();
        List<String> args = event.getArgs();
        StringBuilder sb1 = new StringBuilder("This is a message sent from Realz (Owner of the Bot):\n ");

        if (user.getId().equals(Config.owner_id)) {
            sb1.append(Arrays.toString(event.getMessage().getContentRaw().split(".bc")));
            event.getGuild().getSystemChannel().sendMessage(sb1.toString()).queue();
            event.getChannel().sendMessage("Successfully Sent Broadcast!").queue();
        } else if (!user.getId().equals(Config.owner_id)) {
            event.getChannel().sendMessage("Your not the owner of this bot, you cannot do that!").queue();
        }
    }

    @Override
    public String getName() {
        return "broadcast";
    }

    @Override
    public List<String> getAliases() {
        return List.of("bc");
    }
}
