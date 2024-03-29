package com.warder.jrtb.command;

import com.warder.jrtb.bot.JRTelegramBot;
import com.warder.jrtb.service.sendMessage.SendBotMessageService;
import com.warder.jrtb.service.sendMessage.SendMessageService;
import com.warder.jrtb.service.user.TelegramUserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public abstract class AbstractCommandTest {

    protected TelegramUserService userService = Mockito.mock(TelegramUserService.class);
    protected JRTelegramBot bot = Mockito.mock(JRTelegramBot.class);
    protected SendBotMessageService messageService = new SendMessageService(bot);

    abstract String getCommandName();

    abstract String getCommandMessage();

    abstract Command getCommand();

    @Test
    public void correctCommandExecution() throws TelegramApiException {

        Long chatId = 1234567824356L;

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn(getCommandName());
        update.setMessage(message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(getCommandMessage());
        sendMessage.enableHtml(true);

        getCommand().execute(update);

        Mockito.verify(bot).execute(sendMessage);
    }

    public static Update prepareUpdate(Long chatId, String commandName) {
        Update update = new Update();

        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn(commandName);

        update.setMessage(message);

        return update;
    }
}
