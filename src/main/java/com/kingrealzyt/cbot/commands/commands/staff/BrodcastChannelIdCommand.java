package com.kingrealzyt.cbot.commands.commands.staff;

import com.kingrealzyt.cbot.commands.CommandContext;
import com.kingrealzyt.cbot.commands.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

import java.util.Arrays;
import java.util.List;

public class BrodcastChannelIdCommand implements ICommand {
    @Override
    public void handle(CommandContext event) {
        String broadcastChannelID = "";
        Member author = event.getMember();

        StringBuilder sb = new StringBuilder();

        if (!author.hasPermission(Permission.MANAGE_SERVER)) {
            event.getChannel().sendMessage("Sorry, You don't have the right permissions to do that! (Needs Manage Server)").queue();
        }
    }

    @Override
    public String getName() {
        return "broadcastid";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("bcid");
    }
}
