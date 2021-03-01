package com.warder.jrtb.service;

import com.warder.jrtb.bot.JRTelegramBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


class SendMessageServiceTest {
    private JRTelegramBot bot;
    private SendBotMessageService messageService;


    @BeforeEach
    public void init() {
        bot = Mockito.mock(JRTelegramBot.class);
        messageService = new SendMessageService(bot);
    }

    @Test
    public void correctSendMessage() throws TelegramApiException {

        String chatId = "test_chat_id";
        String message = "test_message";

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);

        messageService.sendMessage(chatId, message);

        Mockito.verify(bot).execute(sendMessage);
    }
}