package com.kingrealzyt.cbot.commands.commands.misc;

import com.kingrealzyt.cbot.commands.CommandContext;
import com.kingrealzyt.cbot.commands.ICommand;
import com.kingrealzyt.cbot.database.DatabaseManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;


import java.time.Instant;
import java.util.List;

public class HelpCommand implements ICommand {

    @Override
    public void handle(CommandContext event) {
        final long guildId = event.getGuild().getIdLong();
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

        List<String> args = event.getArgs();
        String msg = event.getMessage().getContentRaw().toLowerCase();

        if (args.isEmpty()) {

            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Help");
            eb.addField("Fun", "Some Fun Commands", false);
            eb.addField("Misc", "Commands with no category", false);
            eb.addField("Staff", "Commands for a Servers Staff", false);
            eb.addField("All", "Shows every command for the bot", false);
            eb.setFooter("Ex: " + DatabaseManager.INSTANCE.getPrefix(guildId) + "help misc to get more info!\n" + "Requested By: " + member.getEffectiveName());
            eb.setTimestamp(Instant.now());
            eb.setColor(0xd01212);
            event.getChannel().sendMessage(eb.build()).queue();

        } else if (msg.toLowerCase().contains("fun")) {

            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Fun Commands!");
            eb.addField("Meme", "Sends a random meme!", true);
            eb.setColor(0xd01212);
            event.getChannel().sendMessage(eb.build()).queue();

        } else if (msg.toLowerCase().contains("misc")) {

            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Misc Commands!");
            eb.addField("Ping", "Shows the ping of the bot to the discord server!", true);
            eb.addField("Paste", "Lets you paste text! Needs a language given.", false);
            eb.addField("UserInfo", "Shows the info of yourself or anyone you mention. Aliases: ui", false);
            eb.addField("BotSuggestion", "Sends a link to the github where you can suggest features for the bot! Aliases: bsuggest", false);
            eb.addField("ServerStats", "Sends the stats of the server the command is on. Aliases: servstat", false);
            eb.addField("HostInfo", "Shows the info of the host.", false);
            eb.addField("Help", "Shows the first help embed!", false);
            eb.setColor(0xd01212);
            event.getChannel().sendMessage(eb.build()).queue();

        } else if (msg.toLowerCase().contains("staff")) {

            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Staff Commands!");
            eb.addField("Kick", "Kicks the member mentioned!", true);
            eb.addField("Prefix", "Changes the prefix of the bot!", false);
            eb.setColor(0xd01212);
            event.getChannel().sendMessage(eb.build()).queue();

        } else if (msg.toLowerCase().contains("all")) {

            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("All Commands!");
            eb.addField("Help", "Shows all the category's!", true);
            eb.addField("Fun", "Some Fun Commands!", true);
            eb.addField("Misc", "Commands with no category!", true);
            eb.addField("Staff", "Commands for a Servers Staff!", true);
            eb.addField("Meme", "Sends a random meme!", true);
            eb.addField("Ping", "Shows the ping of the bot to the discord server!", true);
            eb.addField("Paste", "Lets you paste text! Needs a language given.", true);
            eb.addField("Kick", "Kicks the member mentioned!", true);
            eb.addField("Prefix", "Changes the prefix of the bot!", true);
            eb.addField("UserInfo", "Shows the info of yourself or anyone you mention. Aliases: ui", true);
            eb.addField("BotSuggestion", "Sends a link to the github where you can suggest features for the bot! Aliases: bsuggest", true);
            eb.addField("ServerStats", "Sends the stats of the server the command is on. Aliases: servstat", true);
            eb.addField("HostInfo", "Shows the info of the host.", true);
            eb.addField("Help", "Shows the first help embed!", true);
            eb.setColor(0xd01212);
            event.getChannel().sendMessage(eb.build()).queue();
        }
    }

    @Override
    public String getName() {
        return "help";
    }
}
