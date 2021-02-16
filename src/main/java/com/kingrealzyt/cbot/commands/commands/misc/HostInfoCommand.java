package com.kingrealzyt.cbot.commands.commands.misc;

import com.kingrealzyt.cbot.commands.CommandContext;
import com.kingrealzyt.cbot.commands.ICommand;
import com.sun.management.OperatingSystemMXBean;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.lang.management.*;
import java.time.Instant;

public class HostInfoCommand implements ICommand {
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

        long avaProcessors = Runtime.getRuntime().availableProcessors();
        OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        java.lang.management.OperatingSystemMXBean operatingSystemMXBeans = ManagementFactory.getOperatingSystemMXBean();
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        ThreadMXBean threadMXBean =  ManagementFactory.getThreadMXBean();
        long prevUpTime = runtimeMXBean.getUptime();
        long prevProcessCpuTime = operatingSystemMXBean.getProcessCpuTime();
        double cpuUsage;
        try
        {
            Thread.sleep(500);
        }
        catch (Exception ignored) { }

        long uptime = runtimeMXBean.getUptime();
        long uptimeInSeconds = uptime / 1000;
        long numberOfHours = uptimeInSeconds / (60 * 60);
        long numberOfMinutes = (uptimeInSeconds / 60) - (numberOfHours * 60);
        long numberOfSeconds = uptimeInSeconds % 60;
        operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        long upTime = runtimeMXBean.getUptime();
        long processCpuTime = operatingSystemMXBean.getProcessCpuTime();
        long elapsedCpu = processCpuTime - prevProcessCpuTime;
        long elapsedTime = upTime - prevUpTime;
        cpuUsage = Math.min(99F, elapsedCpu / (elapsedTime * 10000F * avaProcessors));
        String jvmVendor = runtimeMXBean.getVmVendor();
        String jvmVersion = runtimeMXBean.getVmVersion();
        String jvmSpecName = runtimeMXBean.getSpecName();
        String jvmSpecVendor = runtimeMXBean.getSpecVendor();
        String jvmSpecVersion = runtimeMXBean.getSpecVersion();
        int threadCount = threadMXBean.getThreadCount();
        String os = operatingSystemMXBeans.getName();
        String osVers = operatingSystemMXBeans.getVersion();
        String osArch = operatingSystemMXBeans.getArch();



        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Host Information", "https://stackoverflow.com/questions/74674/how-do-i-check-cpu-and-memory-usage-in-java/15733233#15733233");
        eb.setColor(0xd01212);
        eb.addField("OS", os, true);
        eb.addField("OS Version", osVers, true);
        eb.addField("OS Arch", osArch, true);
        eb.addBlankField(false);
        eb.addField("Uptime", numberOfHours + " Hours, " + numberOfMinutes + " Minutes, " + numberOfSeconds + " Seconds", true);
        eb.addBlankField(false);
        eb.addField("Available Processors", avaProcessors + " Processors", true);
        eb.addField("CPU Load", cpuUsage * 100 + "%", true);
        eb.addField("Thread Count", threadCount + " Threads", true);
        eb.addBlankField(false);
        eb.addField("JVM Vendor", jvmVendor, true);
        eb.addField("JVM Version", jvmVersion, true);
        eb.addField("JVM Spec", jvmSpecName, false);
        eb.addField("JVM Spec Vendor", jvmSpecVendor, true);
        eb.addField("JVM Spec Version", jvmSpecVersion, true);
        eb.addBlankField(false);
        eb.addField("Code:", "Got my code from [xf8b](https://github.com/xf8b/xf8bot) and [StackOverflow](https://stackoverflow.com/questions/74674/how-do-i-check-cpu-and-memory-usage-in-java/15733233#15733233)", false);
        eb.setFooter("Requested By: " + member.getEffectiveName());
        eb.setTimestamp(Instant.now());

        channel.sendMessage(eb.build()).queue();

    }


    @Override
    public String getName() {
        return "hostinfo";
    }
}
