package com.kingrealzyt.cbot.commands.commands.staff;

import com.kingrealzyt.cbot.commands.CommandContext;
import com.kingrealzyt.cbot.commands.ICommand;
import com.kingrealzyt.cbot.database.DatabaseManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

import java.time.Instant;
import java.util.List;

public class BanCommand implements ICommand {

    @Override
    public void handle(CommandContext event) {
        final long guildId = event.getGuild().getIdLong();
        List<String> args = event.getArgs();
        long targetID = Long.parseLong(args.get(0).replaceAll("<@", "").replaceAll(">", "").replaceAll("!", ""));
        Member target = event.getGuild().getMemberById(targetID);
        Member author = event.getMember();
        Member bot = event.getGuild().getSelfMember();
        int days = 0;
        String reason = "";


        if (args.isEmpty()) {
            event.getChannel().sendMessage("Missing Arguments\n Usage: " + DatabaseManager.INSTANCE.getPrefix(guildId) + "ban <user> <time> <reason>").queue();
        } else if (args.size() < 2) {
            event.getChannel().sendMessage("Missing Arguments\n Usage: ``" + DatabaseManager.INSTANCE.getPrefix(guildId) + "ban <user> <time> <reason>``").queue();
        } else if (args.size() > 2) {
            if (!author.hasPermission(Permission.BAN_MEMBERS)) {
                event.getChannel().sendMessage("Ban Failed | You are missing the permission Ban Members").queue();
                return;
            }
            if (!bot.hasPermission(Permission.BAN_MEMBERS)) {
                event.getChannel().sendMessage("Ban Failed | I am missing the permission Ban Members").queue();
                return;
            }
            if (target.hasPermission(Permission.BAN_MEMBERS) || !bot.canInteract(target)) {
                event.getChannel().sendMessage("Ban Failed | Target (" + target.getEffectiveName() + ") Has the Permission **BAN_MEMBERS** or has a higher role then me.").queue();
            }
            try {
                days = Integer.parseInt(args.get(1));
            } catch (Exception e) {

            }
            try {
                if (days == 0) {
                    reason = String.join(" ", args.subList(1, args.size()));
                } else {
                    reason = String.join(" ", args.subList(2, args.size()));
                }
            } catch (Exception e) {

            }
            target.ban(0, reason.equals("") ? null : reason).queue();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Successfully Banned: " + target.getEffectiveName());
            eb.addField("Info: ", "Banned: " + target.getAsMention() + " By: " + author.getAsMention() + "\n Reason: " + reason, true);
            eb.setColor(0xd01212);
            eb.setFooter("Banned User");
            eb.setTimestamp(Instant.now());
            event.getChannel().sendMessage(eb.build()).queue();
        }
    }

    @Override
    public String getName() {
        return "ban";
    }
}
