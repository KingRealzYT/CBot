package com.kingrealzyt.cbot.commands.commands.music;

import com.kingrealzyt.cbot.commands.CommandContext;
import com.kingrealzyt.cbot.commands.ICommand;
import com.kingrealzyt.cbot.lavaplayer.GuildMusicManager;
import com.kingrealzyt.cbot.lavaplayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

public class SkipCommand implements ICommand {
    @Override
    public void handle(CommandContext event) {
    final TextChannel channel = event.getChannel();
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

    final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
    final AudioPlayer audioPlayer = musicManager.audioPlayer;

    if (audioPlayer.getPlayingTrack() == null) {
        channel.sendMessage("No track playing currently!").queue();
        return;
    }

    musicManager.scheduler.nextTrack();
    channel.sendMessage("Skipped the current track!").queue();
}

    @Override
    public String getName() {
        return "skip";
    }
}
