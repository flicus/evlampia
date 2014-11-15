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

import org.apache.log4j.Logger;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.schors.evlampia.core.Command;
import org.schors.evlampia.core.CommandContext;
import org.schors.evlampia.core.Jbot;
import org.schors.evlampia.dao.vbotDAOInterface;
import org.schors.evlampia.search.SearchManager;

import java.io.IOException;

public class UpdateIndexCmd extends Command {
    private final static Logger log = Logger.getLogger(UpdateIndexCmd.class);

    @Override
    public void execute(final CommandContext context) throws Exception {
        final vbotDAOInterface dao = (vbotDAOInterface) context.getFacilities().get(Jbot.F_DAO);
        final MultiUserChat muc = (MultiUserChat) context.getFacilities().get(Jbot.F_MUC);

        synchronized (UpdateIndexCmd.class) {
            dao.flush();
            boolean done = false;
            try {
                SearchManager.getInstanse().updateIndex(true);
                done = true;
            } catch (IOException e) {
                log.error(e, e);
            }
            if (!context.getFacilities().containsKey("silentIndex")) {
                try {
                    if (done)
                        muc.sendMessage("Индекс обновлен");
                    else
                        muc.sendMessage("Ошибка обновления индекса");
                } catch (Exception e) {
                    log.error(e, e);
                }
            }
        }
    }
}
