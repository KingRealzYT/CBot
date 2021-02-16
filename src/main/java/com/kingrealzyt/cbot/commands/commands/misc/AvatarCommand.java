package com.kingrealzyt.cbot.commands.commands.misc;

import com.kingrealzyt.cbot.commands.CommandContext;
import com.kingrealzyt.cbot.commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class AvatarCommand implements ICommand {
    @Override
    public void handle(CommandContext event) {
        EmbedBuilder eb = new EmbedBuilder();
        User member =  event.getAuthor();
        TextChannel channel = event.getChannel();
        List<String> args = event.getArgs();
        User avatar = event.getMessage().getMentionedUsers().get(0);
        String targetAvatar = avatar.getEffectiveAvatarUrl();
        Member target = event.getMessage().getMentionedMembers().get(0);
        String selfAvatar = event.getAuthor().getEffectiveAvatarUrl();

        if (args.size() == 0) {
            eb.setTitle(event.getMessage().getAuthor().getAsTag()+ "'s Avatar");
            eb.setImage(selfAvatar);
            eb.setColor(0xd01212);
            channel.sendMessage(eb.build()).queue();
        } else if (event.getMessage().getMentionedUsers().size() == 1) {
            eb.setTitle(target.getEffectiveName() + "'s Avatar", targetAvatar);
            eb.setImage(targetAvatar);
            eb.setColor(0xd01212);
            channel.sendMessage(eb.build()).queue();
        }
    }

    @Override
    public String getName() {
        return "avatar";
    }
}
