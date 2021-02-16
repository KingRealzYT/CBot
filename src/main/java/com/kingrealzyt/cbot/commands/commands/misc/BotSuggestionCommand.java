package com.kingrealzyt.cbot.commands.commands.misc;

import com.kingrealzyt.cbot.commands.CommandContext;
import com.kingrealzyt.cbot.commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class BotSuggestionCommand implements ICommand {

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
        eb.setColor(0xd01212);
        eb.setTitle("Make Suggestions for the bot!");
        eb.addField("Link to github: ", "https://github.com/KingRealzYT/CBot/issues", true);
        eb.addField("How to suggest?", "On the issues tab press new issue, in the title explain what it is and then write the suggestion with the name and what it does", false);
        eb.setFooter("Bot by KingRealzYT" + " Requested by: " + event.getAuthor().getAsTag());
        event.getChannel().sendMessage(eb.build()).queue();
    }

    @Override
    public String getName() {
        return "botsuggestion";
    }

    @Override
    public List<String> getAliases() {
        return List.of("bsuggest");
    }
}
