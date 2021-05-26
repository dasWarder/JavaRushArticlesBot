package com.warder.jrtb.service.sendMessage;

import java.util.List;

public interface SendBotMessageService {

    void sendMessage(String chatId, String message);

    void sendMessage(String chatId, List<String> messages);
}
