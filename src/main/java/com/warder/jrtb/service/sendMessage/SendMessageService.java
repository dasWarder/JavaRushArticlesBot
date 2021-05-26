package com.warder.jrtb.service.sendMessage;


import com.warder.jrtb.bot.JRTelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Service
public class SendMessageService implements SendBotMessageService {

    private JRTelegramBot telegramBot;

    @Autowired
    public SendMessageService(JRTelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);

        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String chatId, List<String> messages) {
        if(messages.isEmpty()) {
            return;
        }

        messages.forEach(message -> sendMessage(chatId, message));
    }
}
