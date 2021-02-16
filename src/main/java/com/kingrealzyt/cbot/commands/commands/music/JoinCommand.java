package com.kingrealzyt.cbot.commands.commands.music;

import com.kingrealzyt.cbot.commands.CommandContext;
import com.kingrealzyt.cbot.commands.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class JoinCommand implements ICommand {

    @Override
    public void handle(CommandContext event) {
        final TextChannel channel = event.getChannel();

        final Member self = event.getSelfMember();

        final GuildVoiceState selfvoiceState = self.getVoiceState();

        if (selfvoiceState.inVoiceChannel()) {
            channel.sendMessage("I am already in a voice channel!").queue();
            return;
        }

        final Member member = event.getMember();

        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("You need to be in a voice channel to use /join!").queue();
            return;
        }

        if (!self.hasPermission(Permission.VOICE_CONNECT)) {
            channel.sendMessage("I don't have permission to join voice channels!").queue();
        }

        final AudioManager audioManager = event.getGuild().getAudioManager();
        final VoiceChannel memberChannel = memberVoiceState.getChannel();

        audioManager.openAudioConnection(memberChannel);
        channel.sendMessageFormat("Connecting to `\uD83D\uDD0A %s`", memberChannel.getName()).queue();
    }

    @Override
    public String getName() {
        return "join";
    }
}
