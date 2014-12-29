/*
 * The MIT License (MIT)
 * Copyright (c) 2014 schors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.schors.eva.command;

import org.apache.log4j.Logger;
import org.schors.eva.dialog.GroupDialog;
import org.schors.eva.facility.EvaExecutors;

import java.util.List;

@Command(
        dependsOn = {"executors"},
        group = "General",
        longDescription = "",
        name = "Alarm",
        prefixes = {},
        shortDescription = ""
)
public class AlarmCmd {

    private static final Logger log = Logger.getLogger(AlarmCmd.class);
    private static volatile boolean alarmGap = false;

    @CommandExecute
    public void execute(CommandContext ctx) throws Exception {
        if (!(ctx.getDialog() instanceof GroupDialog)) {
            ctx.getDialog().sendMessage("Только групповой чат");
        }

        String result = null;
        if (alarmGap) result = "Иди колядуй";
        else {
            List<String> participants = ((GroupDialog) ctx.getDialog()).getParticipants();
            if (participants != null && participants.size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (String item : participants) {
                    sb.append(item).append(" ");
                }
                result = sb.toString();
            } else {
                result = "Никого нет дома";
            }
            ctx.getFacility(EvaExecutors.class).getExecutor().execute(new GapCleaner());
        }
        ctx.getDialog().sendMessage(result);
    }


    private class GapCleaner implements Runnable {

        @Override
        public void run() {
            alarmGap = true;
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                log.warn(e, e);
            }
            alarmGap = false;
        }
    }
}
