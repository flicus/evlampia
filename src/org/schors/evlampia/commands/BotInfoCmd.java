/*
 * The MIT License
 *
 * Copyright (c) 2014.  schors (https://github.com/flicus)
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
