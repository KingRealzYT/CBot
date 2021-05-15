package com.kingrealzyt.cbot.commands.commands.misc;

import com.kingrealzyt.cbot.commands.CommandContext;
import com.kingrealzyt.cbot.commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.time.Instant;
import java.util.Arrays;

public class InviteCommand implements ICommand {
    @Override
    public void handle(CommandContext event) {
        Member member = event.getMember();
        TextChannel channel = event.getChannel();
        Member selfBot = member.getGuild().getSelfMember();

        if (!member.hasPermission(Permission.MESSAGE_EMBED_LINKS)) {
            channel.sendMessage("Sorry " + member.getAsMention() + ", you don't have the perms to do that. (Missing Embed Links Permission)").queue();
            return;
        } else if (!selfBot.hasPermission(Permission.MESSAGE_EMBED_LINKS)) {
            channel.sendMessage("Sorry, I don't have the perms to do that. (I'm Missing Embed Links Permission)").queue();
            return;
        }

        EmbedBuilder eb = new EmbedBuilder();

        eb.setTimestamp(Instant.now());
        eb.setColor(0xd01212);
        eb.setFooter("Requested By: " + event.getAuthor().getName());
        eb.setThumbnail(selfBot.getUser().getEffectiveAvatarUrl());
        eb.setTitle("CBot Invite Command");
        eb.addField("Invite:", "[Invite Link](" + event.getJDA().getInviteUrl() + ")", true);
        eb.addField("Guilds Bot is in", "CBot is in " + event.getSelfUser().getJDA().getGuilds(), true);

        event.getChannel().sendMessage(eb.build()).queue();
    }

    @Override
    public String getName() {
        return "invite";
    }
}
