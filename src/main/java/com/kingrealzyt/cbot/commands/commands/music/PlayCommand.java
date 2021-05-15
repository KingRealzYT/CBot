package com.kingrealzyt.cbot.commands.commands.music;

import com.kingrealzyt.cbot.commands.CommandContext;
import com.kingrealzyt.cbot.commands.ICommand;
import com.kingrealzyt.cbot.database.DatabaseManager;
import com.kingrealzyt.cbot.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.net.URI;
import java.net.URISyntaxException;

public class PlayCommand implements ICommand {
    @SuppressWarnings("ConstantConditions")
    @Override
    public void handle(CommandContext event) {
        final TextChannel channel = event.getChannel();

        if (event.getArgs().isEmpty()) {
            channel.sendMessage("Correct usage is `" + DatabaseManager.INSTANCE.getPrefix(event.getGuild().getIdLong()) + "play (youtube link)`").queue();
            return;
        }

        final Member self = event.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("I need to be in a voice channel for this to work!").queue();
            return;
        }

        final Member member = event.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("You need to be in a voice channel for this command to work!").queue();
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("You need to be in the same voice channel as me for this to work!").queue();
            return;
        }

        String link = String.join(" ", event.getArgs());

        if (!isUrl(link)) {
            link = "ytsearch:" + link;
        }

        PlayerManager.getInstance()
                .loadAndPlay(channel, link);
    }

    @Override
    public String getName() {
        return "play";
    }

    private boolean isUrl(String url) {
        try {
            new URI(url);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
