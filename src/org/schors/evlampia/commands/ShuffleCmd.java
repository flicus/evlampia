/*
 * The MIT License (MIT)
 *
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

package org.schors.evlampia.commands;

import org.jivesoftware.smackx.muc.MultiUserChat;
import org.schors.evlampia.core.Command;
import org.schors.evlampia.core.CommandContext;
import org.schors.evlampia.core.Jbot;

import java.util.Random;

public class ShuffleCmd extends Command {

    private Random random;

    @Override
    public void execute(CommandContext context) throws Exception {
        random = (Random) context.getFacilities().get(Jbot.F_RANDOM);
        MultiUserChat muc = (MultiUserChat) context.getFacilities().get(Jbot.F_MUC);

        random.setSeed(System.currentTimeMillis());
        String rest = getWithoutPrefix(context.getBody());
        if (rest != null) {
            String[] words = rest.trim().split(" ");
            StringBuilder sb = new StringBuilder();
            for (String item : words) {
                if (item.length() < 4) {
                    sb.append(item).append(" ");
                    continue;
                }
                sb.append(shuffle(item)).append(" ");
            }
            muc.sendMessage(sb.toString());
        }
    }

    private String shuffle(String substring) {
        char[] item = substring.toCharArray();
        if (item.length == 4) {
            char tmp = item[1];
            item[1] = item[2];
            item[2] = tmp;
        } else {
            for (int i = 1; i < substring.length() - 1; i++) {
                int moveTo = random.nextInt(substring.length() - 2) + 1;
                char tmp = item[moveTo];
                item[moveTo] = item[i];
                item[i] = tmp;
            }
        }
        return String.valueOf(item);
    }
}
