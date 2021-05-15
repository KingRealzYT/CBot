package com.kingrealzyt.cbot.commands.commands.staff;

import com.kingrealzyt.cbot.Config;
import com.kingrealzyt.cbot.PrefixStuff;
import com.kingrealzyt.cbot.commands.CommandContext;
import com.kingrealzyt.cbot.commands.ICommand;
import com.kingrealzyt.cbot.database.DatabaseManager;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.internal.requests.Route;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class BroadcastCommand implements ICommand {
    @Override
    public void handle(CommandContext event) {
        final long guildId = event.getGuild().getIdLong();
        String prefix = PrefixStuff.PREFIXES.computeIfAbsent(guildId, DatabaseManager.INSTANCE::getPrefix);
        String raw = event.getMessage().getContentRaw();
        User user = event.getAuthor();
        List<String> args = event.getArgs();

        if (user.getId().equals(Config.owner_id) || user.getId().equals("701660977258561557")) {
                    List<String> test = Arrays.asList(event.getMessage().getContentRaw().split(prefix + "bc "));
                    test.subList(1, test.size());
                    String message = String.join(" ", test);
                    event.getGuild().getSystemChannel().sendMessage("This message has been sent by Realz (Owner of the Bot):\n`"+message+"`").queue();
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
