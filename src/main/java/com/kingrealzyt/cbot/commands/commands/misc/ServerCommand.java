package com.kingrealzyt.cbot.commands.commands.misc;

import com.kingrealzyt.cbot.commands.CommandContext;
import com.kingrealzyt.cbot.commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.lang.reflect.Array;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static net.dv8tion.jda.api.OnlineStatus.ONLINE;

public class ServerCommand implements ICommand {
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

        if (!(event.getMessage().getAuthor().getIdLong() == event.getGuild().getOwner().getIdLong())) {

            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(0xd01212);
            eb.setTitle(event.getGuild().getName());
            eb.setFooter("Requested by: " + event.getMessage().getAuthor().getAsTag());
            eb.setThumbnail(event.getGuild().getIconUrl());
            eb.setTimestamp(Instant.now());
            eb.addField("Server Owner:", event.getGuild().getOwner().getAsMention(), true);
            eb.addField("Member's", event.getGuild().getMembers().size() + " members in total!\n " + event.getGuild().getBoostCount() + " Boosts!", false);

            event.getChannel().sendMessage(eb.build()).queue();
        }

        if (event.getMessage().getAuthor().getIdLong() == event.getGuild().getOwnerIdLong()) {
            event.getChannel().sendMessage("So your " + event.getMessage().getAuthor().getAsMention() + "? Why do you need the info if your the owner? Eh might as well give it to you").queue();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(0xd01212);
            eb.setTitle(event.getGuild().getName());
            eb.setFooter("Requested by: " + event.getMessage().getAuthor().getAsTag());
            eb.setThumbnail(event.getGuild().getIconUrl());
            eb.setTimestamp(Instant.now());
            eb.addField("Server Owner:", event.getGuild().getOwner().getAsMention(), true);
            if (event.getGuild().getBoostCount() <= 10) {
                eb.addField("Member's", event.getGuild().getMembers().size() + " in total\n " + "**" + event.getGuild().getBoostCount() + "**, Wow you must be a bad owner with " + event.getGuild().getBoostCount() + " boosts", false);
            } else if (event.getGuild().getBoostCount() >= 11 && event.getGuild().getBoostCount() <= 20) {
                eb.addField("Member's", event.getGuild().getMembers().size() + " in total\n " + "**" + event.getGuild().getBoostCount() + "**, Only " + event.getGuild().getBoostCount() + " boosts? Still trash!", false);
            }

            event.getChannel().sendMessage(eb.build()).queue();
        }
    }

    @Override
    public String getName() {
        return "server";
    }
}
