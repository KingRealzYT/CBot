package com.kingrealzyt.cbot;

import com.kingrealzyt.cbot.commands.CommandContext;
import com.kingrealzyt.cbot.commands.ICommand;
import com.kingrealzyt.cbot.commands.commands.fun.MemeCommand;
import com.kingrealzyt.cbot.commands.commands.misc.*;
import com.kingrealzyt.cbot.commands.commands.staff.*;
import com.kingrealzyt.cbot.commands.commands.music.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CommandManager {

    private final List<ICommand> commands = new ArrayList<>();

    public CommandManager() {
        addCommand(new PingCommand());
        addCommand(new HelpCommand());
        addCommand(new PasteCommand());
        addCommand(new KickCommand());
        addCommand(new MemeCommand());
        addCommand(new SetPrefixCommand());
        addCommand(new JoinCommand());
        addCommand(new PlayCommand());
        addCommand(new StopCommand());
        addCommand(new SkipCommand());
        addCommand(new NowPlayingCommand());
        addCommand(new QueueCommand());
        addCommand(new LoopCommand());
        addCommand(new LeaveCommand());
        addCommand(new UserInfoCommand());
        addCommand(new BotSuggestionCommand());
        addCommand(new ServerCommand());
        addCommand(new HostInfoCommand());
        addCommand(new AvatarCommand());
        addCommand(new BanCommand());
        addCommand(new BroadcastCommand());
        addCommand(new InviteCommand());
        addCommand(new UnbanCommand());
        addCommand(new BrodcastChannelIdCommand());
    }

        private void addCommand(ICommand cmd) {
        boolean nameFound = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(cmd.getName()));

        if (nameFound) {
            throw new IllegalArgumentException("A command with this name is already present");
        }

        commands.add(cmd);
    }

    public List<ICommand> getCommands() {
        return commands;
    }

    @Nullable
    public ICommand getCommand(String search) {
        String searchLower = search.toLowerCase();

        for (ICommand cmd : this.commands) {
            if (cmd.getName().equals(searchLower) || cmd.getAliases().contains(searchLower)) {
                return cmd;
            }
        }

        return null;
    }

    void handle(GuildMessageReceivedEvent event, String prefix) {
        String[] split = event.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote(prefix), "")
                .split("\\s+");

        String invoke = split[0].toLowerCase();
        ICommand cmd = this.getCommand(invoke);

        if (cmd != null) {
            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1, split.length);

            CommandContext ctx = new CommandContext(event, args);

            cmd.handle(ctx);
        }
    }

}
