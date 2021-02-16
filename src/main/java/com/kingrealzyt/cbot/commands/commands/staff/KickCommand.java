package com.kingrealzyt.cbot.commands.commands.staff;

import com.kingrealzyt.cbot.commands.CommandContext;
import com.kingrealzyt.cbot.commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class KickCommand implements ICommand {
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

        final Message message = event.getMessage();
        final List<String> args = event.getArgs();

        if (args.size() < 2 || message.getMentionedMembers().isEmpty()) {
            channel.sendMessage("Missing arguments").queue();
            return;
        }

        final Member target = message.getMentionedMembers().get(0);

        if (!member.canInteract(target) || !member.hasPermission(Permission.KICK_MEMBERS)) {
            channel.sendMessage("You are missing permission to kick this member " + event.getAuthor().getAsMention()).queue();
            return;
        }

        final Member selfMember = event.getSelfMember();

        if (!selfMember.canInteract(target) || !selfMember.hasPermission(Permission.KICK_MEMBERS)) {
            channel.sendMessage("I am missing permissions to kick that member").queue();
            return;
        }

        final String reason = String.join(" ", args.subList(1, args.size()));

        event.getGuild()
                .kick(target, reason)
                .reason(reason)
                .queue(
                        (error) -> channel.sendMessageFormat("Could not kick %s").queue()
                );

        EmbedBuilder eb = new EmbedBuilder();
        eb.addField("Target: ", target.getEffectiveName(), true);
        eb.addField("Reason: ", reason, false);
        eb.addField("By: ", event.getAuthor().getAsMention(), false);
        eb.setColor(0xd01212);
        event.getChannel().sendMessage(eb.build()).queue();


    }

    @Override
    public String getName() {
        return "kick";
    }
}
