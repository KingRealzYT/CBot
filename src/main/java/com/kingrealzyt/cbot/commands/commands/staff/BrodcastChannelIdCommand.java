package com.kingrealzyt.cbot.commands.commands.staff;

import com.kingrealzyt.cbot.DatabaseStuff;
import com.kingrealzyt.cbot.commands.CommandContext;
import com.kingrealzyt.cbot.commands.ICommand;
import com.kingrealzyt.cbot.database.DatabaseManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BrodcastChannelIdCommand implements ICommand {
    /* Big thank for V Play Games for helping me with this!*/
    @Override
    public void handle(CommandContext event) {
        Member author = event.getMember();
        Pattern channelPattern = Pattern.compile("<?#?(\\d+)>?");
        String arg = event.getArgs().get(0);
        Matcher m = channelPattern.matcher(arg);

         if (!author.hasPermission(Permission.MANAGE_SERVER)) {
            event.getChannel().sendMessage("Sorry, You don't have the right permissions to do that! (Needs Manage Server)").queue();
        } else {
             if (!m.find()) {
                 event.getChannel().sendMessage("Error, Could not find channel").queue();
             }
             String channelId = m.group(1);
             if (event.getJDA().getTextChannelCache().stream().noneMatch(t -> channelId.equals(t.getId()))) {
                 event.getChannel().sendMessage("Error, Could not find channel").queue();
             }
             final String newBcid = channelId;
             updateBcid(event.getGuild().getIdLong(), newBcid);
             event.getChannel().sendMessageFormat("New broadcast channel has been set to `%s`", newBcid).queue();
         }


    }

    @Override
    public String getName() {
        return "broadcastid";
    }

    private void updateBcid(long guildId, String newBcid) {
        DatabaseStuff.BCID.put(guildId, newBcid);
        DatabaseManager.INSTANCE.setBcId(guildId, newBcid);
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("bcid");
    }
}
