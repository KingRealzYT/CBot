package com.kingrealzyt.cbot.commands.commands.misc;

import com.kingrealzyt.cbot.commands.CommandContext;
import com.kingrealzyt.cbot.commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class UserInfoCommand implements ICommand {
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

        List<String> args = event.getArgs();
        Member selfTarget = event.getMember();

        if (args.size() == 0) {
            User selfUsernameT = event.getAuthor();
            String selfUsername = event.getAuthor().getAsMention();
            String selfAvatar = event.getAuthor().getEffectiveAvatarUrl();

            @Nonnull
            List<Activity> selfActivities =  selfTarget.getActivities();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(selfUsernameT.getName() + "'s Info");
            eb.setColor(0xd01212);
            eb.setThumbnail(selfAvatar);
            eb.addField("Name", selfUsername, true);
            eb.setTimestamp(Instant.now());
            eb.setFooter("Requested By: " + selfUsernameT.getAsTag());
            if (selfActivities.isEmpty()) {
                eb.addField("Game:", "None", true);
            } else {
                eb.addField("Game: ", selfActivities.get(0).getName(), true);
            }
            if (selfTarget.getOnlineStatus() == OnlineStatus.ONLINE) {
                eb.addField("Status: ", "Online", true);
            } else if (selfTarget.getOnlineStatus() == OnlineStatus.DO_NOT_DISTURB) {
                eb.addField("Status: ", "Do Not Disturb", true);
            } else if (selfTarget.getOnlineStatus() == OnlineStatus.IDLE) {
                eb.addField("Status: ", "Idle", true);
            } else if (selfTarget.getOnlineStatus() == OnlineStatus.INVISIBLE) {
                eb.addField("Status: ", "Invisible", true);
            } else if (selfTarget.getOnlineStatus() == OnlineStatus.OFFLINE) {
                eb.addField("Status: ", "Offline", true);
            } else if (selfTarget.getOnlineStatus() == OnlineStatus.UNKNOWN) {
                eb.addField("Status: ", "Unknown Online Status", true);
            }
            if (selfTarget.getActiveClients().contains(ClientType.DESKTOP)) {
                eb.addField("Client: ", "Desktop", true);
            } else if (selfTarget.getActiveClients().contains(ClientType.MOBILE)) {
                eb.addField("Client: ", "Mobile", true);
            } else if (selfTarget.getActiveClients().contains(ClientType.WEB)) {
                eb.addField("Client: ", "Web", true);
            } else if (selfTarget.getActiveClients().contains(ClientType.UNKNOWN)) {
                eb.addField("Client: ", "Unknown", true);
            }else {
                eb.addField("Client: ", "None/Offline", true);
            }
            eb.addField("Server Join Time", selfTarget.getTimeJoined().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")), true);
            eb.addField("Account Creation Time", selfTarget.getTimeCreated().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")), true);
            event.getChannel().sendMessage(eb.build()).queue();

        } else if (event.getMessage().getMentionedUsers().size() == 1) {

            User userNameT = event.getMessage().getMentionedUsers().get(0);
            String userName = event.getMessage().getMentionedUsers().get(0).getAsMention();
            String usernameAvatar = userNameT.getEffectiveAvatarUrl();
            String selfUsernameT = event.getAuthor().getAsTag();
            Member target = event.getMessage().getMentionedMembers().get(0);

            @Nonnull
            List<Activity> activities =  target.getActivities();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(target.getUser().getName() + "'s Info");
            eb.setColor(0xd01212);
            eb.setThumbnail(usernameAvatar);
            eb.addField("Name", userName, true);
            eb.setTimestamp(Instant.now());
            eb.setFooter("Requested By: " + selfUsernameT);
            if (activities.isEmpty()) {
                eb.addField("Game:", "None", true);
            } else {
                eb.addField("Game: ", activities.get(0).getName(), true);
            }
            if (target.getOnlineStatus() == OnlineStatus.ONLINE) {
                eb.addField("Status: ", "Online", true);
            } else if (target.getOnlineStatus() == OnlineStatus.DO_NOT_DISTURB) {
                eb.addField("Status: ", "Do Not Disturb", true);
            } else if (target.getOnlineStatus() == OnlineStatus.IDLE) {
                eb.addField("Status: ", "Idle", true);
            } else if (target.getOnlineStatus() == OnlineStatus.INVISIBLE) {
                eb.addField("Status: ", "Invisible", true);
            } else if (target.getOnlineStatus() == OnlineStatus.OFFLINE) {
                eb.addField("Status: ", "Offline", true);
            } else if (target.getOnlineStatus() == OnlineStatus.UNKNOWN) {
                eb.addField("Status: ", "Unknown Status", true);
            }
            if (target.getActiveClients().contains(ClientType.DESKTOP)) {
                eb.addField("Client: ", "Desktop", true);
            } else if (target.getActiveClients().contains(ClientType.MOBILE)) {
                eb.addField("Client: ", "Mobile", true);
            } else if (target.getActiveClients().contains(ClientType.WEB)) {
                eb.addField("Client: ", "Web", true);
            } else if (target.getActiveClients().contains(ClientType.UNKNOWN)) {
                eb.addField("Client: ", "Unknown", true);
            } else {
                eb.addField("Client: ", "None/Offline", true);
            }
            eb.addField("Server Join Time", target.getTimeJoined().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")), true);
            eb.addField("Account Creation Time", target.getTimeCreated().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")), true);
            event.getChannel().sendMessage(eb.build()).queue();
        } else {
            event.getChannel().sendMessage("You must mention a user!").queue();
        }
    }

    @Override
    public String getName() {
        return "userinfo";
    }

    @Override
    public List<String> getAliases() {
        return List.of("ui");
    }
}
