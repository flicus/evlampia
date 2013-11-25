package org.schors.evlampia.commands;

import org.jivesoftware.smackx.muc.MultiUserChat;
import org.joda.time.Instant;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.schors.evlampia.ConfigurationManager;
import org.schors.evlampia.core.Command;
import org.schors.evlampia.core.CommandContext;
import org.schors.evlampia.core.Jbot;

import java.lang.management.ManagementFactory;

/**
 * Copyright (c) 2013 Amdocs jNetX.
 * http://www.amdocs.com
 * All rights reserved.
 * <p/>
 * This software is the confidential and proprietary information of
 * Amdocs jNetX. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license
 * agreement you entered into with Amdocs jNetX.
 * <p/>
 * User: Sergey Skoptsov (sskoptsov@amdocs.com)
 * Date: 25.11.13
 * Time: 18:37
 * <p/>
 * $Id:
 */

public class BotInfoCmd extends Command {

    private static final PeriodFormatter pf = new PeriodFormatterBuilder().printZeroRarelyLast().appendYears().appendSuffix(" year", " years").appendSeparator(", ")//.printZeroRarelyLast()
            .appendMonths().appendSuffix(" month", " months").appendSeparator(", ")
            .appendDays().appendSuffix(" day", " days").appendSeparator(", ")
            .appendHours().appendSuffix(" hour", " hours").appendSeparator(", ")
            .appendMinutes().appendSuffix(" minute", " minutes").appendSeparator(", ")
            .appendSecondsWithOptionalMillis().appendSuffix(" second", " seconds").toFormatter();

    @Override
    public void execute(CommandContext context) throws Exception {
        MultiUserChat muc = (MultiUserChat) context.getFacilities().get(Jbot.F_MUC);

        StringBuilder info = new StringBuilder();
        info.append(Jbot.newline).append("OS: ").append(ManagementFactory.getOperatingSystemMXBean().getName())
                .append(" ").append(ManagementFactory.getOperatingSystemMXBean().getVersion()).append(" ")
                .append(ManagementFactory.getOperatingSystemMXBean().getArch()).append(Jbot.newline)
                .append("Memory: ").append(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed())
                .append("/").append(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax()).append(Jbot.newline)
                .append("CPU: ").append(ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage()).append(Jbot.newline)
                .append("Uptime: ").append(new Period(ConfigurationManager.startTime, new Instant()).toString(pf));

        muc.sendMessage(info.toString());
    }
}
