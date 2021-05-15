package com.kingrealzyt.cbot.commands.commands.staff;

import com.kingrealzyt.cbot.commands.CommandContext;
import com.kingrealzyt.cbot.commands.ICommand;
import com.kingrealzyt.cbot.database.DatabaseManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

import java.time.Instant;
import java.util.List;

public class UnbanCommand implements ICommand {
    @Override
    public void handle(CommandContext event) {
        final long guildId = event.getGuild().getIdLong();
        List<String> args = event.getArgs();
        String strmember = args.get(0);
        Member member = null;
        Member author = event.getMember();
        Member bot = event.getGuild().getSelfMember();

        if (args.isEmpty() || args.size() > 1) {
            event.getChannel().sendMessage("Missing Arguments\n Usage: " + DatabaseManager.INSTANCE.getPrefix(guildId) + "unban <user>").queue();
        } else {
            try {
                 member = event.getMessage().getMentionedMembers().get(0);
            } catch (Exception e) {

            }
            if (!author.hasPermission(Permission.BAN_MEMBERS)) {
                event.getChannel().sendMessage("Unban Failed | You are missing the permission Unban Members").queue();
                return;
            }
            if (!bot.hasPermission(Permission.BAN_MEMBERS)) {
                event.getChannel().sendMessage("Unban Failed | I am missing the permission Unban Members").queue();
                return;
            }

            try {
                if (strmember.equals("<")) {
                    event.getGuild().unban(member.getUser()).queue();
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("Successfully Unbanned: " + strmember);
                    eb.addField("Info: ", "Unbanned: " + strmember + " By: " + author.getAsMention(), true);
                    eb.setColor(0xd01212);
                    eb.setFooter("Unbanned User");
                    eb.setTimestamp(Instant.now());
                    event.getChannel().sendMessage(eb.build()).queue();
                } else {
                    event.getGuild().unban(strmember).queue();
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("Successfully Unbanned: " + strmember);
                    eb.addField("Info: ", "Unbanned: " + strmember + " By: " + author.getAsMention(), true);
                    eb.setColor(0xd01212);
                    eb.setFooter("Unbanned User");
                    eb.setTimestamp(Instant.now());
                    event.getChannel().sendMessage(eb.build()).queue();
                }
            } catch (Exception e) {
                event.getChannel().sendMessage("Could not successfully unban " + strmember).queue();
            }
        }
    }

    @Override
    public String getName() {
        return "unban";
    }
}
