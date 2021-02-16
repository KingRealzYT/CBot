package com.kingrealzyt.cbot.commands.commands.staff;

import com.kingrealzyt.cbot.PrefixStuff;
import com.kingrealzyt.cbot.commands.CommandContext;
import com.kingrealzyt.cbot.commands.ICommand;
import com.kingrealzyt.cbot.database.DatabaseManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class BanCommand implements ICommand {
    @Override
    public void handle(CommandContext event) {
        final long guildId = event.getGuild().getIdLong();
        String prefix = PrefixStuff.PREFIXES.computeIfAbsent(guildId, DatabaseManager.INSTANCE::getPrefix);
        List<String> args = event.getArgs();
        Member member = event.getMember();
        TextChannel channel = event.getChannel();
        Member selfBot = member.getGuild().getSelfMember();
        long targetId = Long.parseLong(args.get(0).replaceAll("<@", "").replaceAll(">", ""));
        Member target = event.getGuild().getMemberById(targetId);


        if (!member.hasPermission(Permission.MESSAGE_EMBED_LINKS)) {
            channel.sendMessage("Sorry " + member.getAsMention() + ", you don't have the perms to do that. (Missing Embed Links Permission)").queue();
            return;
        } else if (!selfBot.hasPermission(Permission.MESSAGE_EMBED_LINKS)) {
            channel.sendMessage("Sorry, I don't have the perms to do that. (I'm Missing Embed Links Permission)").queue();
            return;
        } else if (!member.hasPermission(Permission.BAN_MEMBERS)) {
            channel.sendMessage("Sorry, " + member.getAsMention() + " You don't have the perms to do that. (Missing Ban Members").queue();
            return;
        } else if (!selfBot.hasPermission(Permission.BAN_MEMBERS)) {
            channel.sendMessage("Sorry, I don't have the perms to do that. (Missing Ban Members").queue();
            return;
        } else if(target.hasPermission(Permission.BAN_MEMBERS) || !selfBot.canInteract(Objects.requireNonNull(target))) {
            channel.sendMessage("Sorry, " + member + " I can't ban the user: " + target).queue();
            return;
        } else if (args.size() < 2) {
            channel.sendMessage("Wrong command usage! Usage\n " + prefix + "ban <member> <reason>").queue();
        }
        int days = 0;
        String reason = "";
        try {
            days = Integer.parseInt(args.get(1));
        } catch(Exception e) {

        } try {
            if (days == 0) {
                reason = String.join(" ", args.subList(1, args.size()));
            } else {
                reason = String.join(" ", args.subList(2, args.size()));
            }
        } catch (Exception e) {

        }
        target.ban(days == 0 ? days : 0, reason == "" ? null : reason);
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Banned user");
        eb.addField("User Banned: ", target.getEffectiveName(), true);
        eb.addField("Reason: ", reason, true);
        eb.addField("Banned By: ", event.getMessage().getAuthor().getName(), true);
        eb.setTimestamp(Instant.now());

        channel.sendMessage(eb.build()).queue();
    }

    @Override
    public String getName() {
        return "ban";
    }
}
