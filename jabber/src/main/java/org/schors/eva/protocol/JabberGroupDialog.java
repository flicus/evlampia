/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 schors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
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

package org.schors.eva.protocol;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.disco.ServiceDiscoveryManager;
import org.jivesoftware.smackx.disco.packet.DiscoverItems;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.schors.eva.dialog.DialogException;
import org.schors.eva.dialog.GroupDialog;

import java.util.ArrayList;
import java.util.List;

public class JabberGroupDialog extends GroupDialog {

    private MultiUserChat muc;
    private XMPPConnection connection;

    public JabberGroupDialog(XMPPConnection connection, MultiUserChat muc) {
        super(muc.getRoom());
        this.muc = muc;
        this.connection = connection;
    }

    @Override
    public void sendPrivateMessage(String endpoint, String message) throws DialogException {
        Chat chat = muc.createPrivateChat(endpoint, new MessageListener() {
            @Override
            public void processMessage(Chat chat, Message message) {

            }
        });
        try {
            chat.sendMessage(message);
        } catch (Exception e) {
            throw new DialogException(e.getMessage(), e);
        } finally {
            chat.close();
        }
    }

    @Override
    public List<String> getParticipants() throws DialogException {
        List<String> list = new ArrayList<>();
        try {
            ServiceDiscoveryManager discoManager = ServiceDiscoveryManager.getInstanceFor(connection);
            DiscoverItems items = discoManager.discoverItems(muc.getRoom());
            for (DiscoverItems.Item item : items.getItems()) {
                String occupant = StringUtils.parseResource(item.getEntityID());
                list.add(occupant);
            }
        } catch (Exception e) {
            throw new DialogException(e.getMessage(), e);
        }
        return list;
    }

    @Override
    public void changeNick(String nick) throws DialogException {
        try {
            muc.changeNickname(nick);
        } catch (Exception e) {
            throw new DialogException(e.getMessage(), e);
        }
    }

    @Override
    public void sendMessage(String message) throws DialogException {
        try {
            muc.sendMessage(message);
        } catch (Exception e) {
            throw new DialogException(e.getMessage(), e);
        }
    }

}
