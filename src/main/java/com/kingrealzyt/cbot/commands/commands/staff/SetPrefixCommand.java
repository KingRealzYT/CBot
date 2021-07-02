package com.kingrealzyt.cbot.commands.commands.staff;

import com.kingrealzyt.cbot.commands.CommandContext;
import com.kingrealzyt.cbot.commands.ICommand;
import com.kingrealzyt.cbot.DatabaseStuff;
import com.kingrealzyt.cbot.database.DatabaseManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class SetPrefixCommand implements ICommand {

    @Override
    public void handle(CommandContext event) {
        final TextChannel channel = event.getChannel();
        final List<String> args = event.getArgs();
        final Member member = event.getMember();
        String prefix = DatabaseStuff.PREFIXES.get(event.getGuild().getIdLong());


        if (!member.hasPermission(Permission.MANAGE_SERVER)) {
            channel.sendMessage("You don't have the right permissions (Needs Manage Server)").queue();
            return;
        }

        if (args.isEmpty()) {
            channel.sendMessage("Missing arguments").queue();
            return;
        }

         final String newPrefix = String.join("", args);
        updatePrefix(event.getGuild().getIdLong(), newPrefix);

        channel.sendMessageFormat("New prefix has been set to `%s`", newPrefix).queue();
    }

    @Override
    public String getName() {
        return "prefix";
    }

    private void updatePrefix(long guildId, String newPrefix) {
        DatabaseStuff.PREFIXES.put(guildId, newPrefix);
        DatabaseManager.INSTANCE.setPrefix(guildId, newPrefix);
    }
}
