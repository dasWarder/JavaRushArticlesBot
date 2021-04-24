package com.warder.jrtb.command;

import com.warder.jrtb.javarushclient.JavaRushGroupClient;
import com.warder.jrtb.repository.entity.GroupSub;
import com.warder.jrtb.repository.entity.TelegramUser;
import com.warder.jrtb.service.groupSub.GroupSubService;
import com.warder.jrtb.service.sendMessage.SendBotMessageService;
import com.warder.jrtb.service.user.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;


public class ListGroupSubCommand implements Command{

    private final SendBotMessageService messageService;
    private final TelegramUserService userService;

    public ListGroupSubCommand(SendBotMessageService messageService, TelegramUserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        TelegramUser user = userService.findByChatId(chatId)
                .orElseThrow(NotFoundException::new);

            List<GroupSub> subscribes = user.getSubscribes();

            String subs = subscribes.stream()
                    .map(sub -> String.format("%s - %s\n", sub.getTitle(), sub.getId()))
                    .collect(Collectors.joining());

            if(subs.isEmpty()) {
                messageService.sendMessage(chatId, "Простите, я не нашел подписок\n");
                return;
            }

            messageService.sendMessage(chatId, subs);
        }

}
