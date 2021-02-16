package com.kingrealzyt.cbot;


import com.kingrealzyt.cbot.commands.commands.staff.SetPrefixCommand;
import com.kingrealzyt.cbot.database.SQLiteDataSource;
import me.duncte123.botcommons.BotCommons;
import com.kingrealzyt.cbot.database.DatabaseManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Listener extends ListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    private final CommandManager manager = new CommandManager();

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        LOGGER.info("{} is ready", event.getJDA().getSelfUser().getAsTag());
        List<Guild> guildsList = event.getJDA().getGuilds();
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        User user = event.getAuthor();

        if (user.isBot() || event.isWebhookMessage()) {
            return;
        }

        final long guildId = event.getGuild().getIdLong();
        String prefix = PrefixStuff.PREFIXES.computeIfAbsent(guildId, DatabaseManager.INSTANCE::getPrefix);
        String raw = event.getMessage().getContentRaw();

        if (raw.equalsIgnoreCase(prefix + "shutdown")
                && user.getId().equals(Config.owner_id)) {
            event.getChannel().sendMessage("I am now shutting down.").queue();
            LOGGER.info("Shutting down");
            event.getJDA().shutdown();
            BotCommons.shutdown(event.getJDA());
            return;
        }

        if (raw.startsWith(prefix)) {
            manager.handle(event, prefix);
        }

        String msg = event.getMessage().getContentRaw().toLowerCase();

        if (msg.equals("<@!794775670383050773>")) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(0xd01212);
            eb.setTitle("CBot Info!");
            eb.setFooter("Requested by: " + event.getMessage().getAuthor().getAsTag());
            eb.setTimestamp(Instant.now());
            eb.addField("Version", "1.2A", true);
            eb.addField("License", "[GNU General Public License v3.0](https://www.gnu.org/licenses/gpl-3.0.en.html)", true);
            eb.addField("Current Prefix: ", "`" + DatabaseManager.INSTANCE.getPrefix(guildId) + "`", true);
            eb.addField("API", "[JDA](https://github.com/DV8FromTheWorld/JDA)", true);
            eb.addField("Code", "[Github](https://github.com/KingRealzYT/CBot)", true);

            event.getChannel().sendMessage(eb.build()).queue();
        }
    }
}

