package org.schors.eva;

import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TempMail {
    private Map<String, MailDialog> dialogs = new ConcurrentHashMap<>();

    public TempMail() {
    }

    public SendMessage processCommand(Message message) {
        return null;

    }
}
