package com.warder.jrtb.command;

import com.warder.jrtb.repository.entity.GroupSub;
import com.warder.jrtb.repository.entity.TelegramUser;
import com.warder.jrtb.repository.subs.GroupSubRepository;
import com.warder.jrtb.service.groupSub.GroupSubService;
import com.warder.jrtb.service.sendMessage.SendBotMessageService;
import com.warder.jrtb.service.user.TelegramUserService;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.NotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.warder.jrtb.command.CommandName.DELETE_GROUP_SUB;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class DeleteGroupSubCommand implements Command{

    private final GroupSubService groupSubService;
    private final TelegramUserService userService;
    private final SendBotMessageService messageService;

    public DeleteGroupSubCommand(GroupSubService groupSubService, TelegramUserService userService, SendBotMessageService messageService) {
        this.groupSubService = groupSubService;
        this.userService = userService;
        this.messageService = messageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String message = update.getMessage().getText();

        if(message.equalsIgnoreCase(DELETE_GROUP_SUB.getCommandName())) {
            sendGroupIdList(chatId);
            return;
        }

        String groupId = message.split(SPACE)[1];

        if(isNumeric(groupId)) {
            Optional<GroupSub> optionalGroupSub = groupSubService.findById(Integer.valueOf(groupId));

            if(optionalGroupSub.isPresent()) {
                    GroupSub sub = optionalGroupSub.get();
                    TelegramUser user = userService.findByChatId(chatId).orElseThrow(NotFoundException::new);
                    sub.getUsers().remove(user);
                    groupSubService.save(sub);
                    messageService.sendMessage(chatId, String.format("Удалил подписку на группу: %s\n", sub.getTitle()));
            } else {
                messageService.sendMessage(chatId, String.format("Не нашел такой группы\n"));
            }
        } else {
            messageService.sendMessage(chatId, "Неверный формат ID группы.\n" +
                    "ID должно быть целым положительным числом\n");
        }
    }

    private void sendGroupIdList(String chatId) {
        String message;
        List<GroupSub> groupSubs = userService.findByChatId(chatId)
                .orElseThrow(NotFoundException::new)
                .getSubscribes();

        if(CollectionUtils.isEmpty(groupSubs)) {
            message = "Пока нет подписок на группы. Чтобы посмотреть доступные команды наберите /help";
            messageService.sendMessage(chatId, message);
            return;
        } else {
            message = "Чтобы удалить подписку на группу - передай комадну вместе с ID группы. \n" +
                    "Например: /deleteGroupSub 16 \n\n" +
                    "я подготовил список всех групп, на которые ты подписан) \n\n" +
                    "имя группы - ID группы \n\n" +
                    "%s";
        }

        String userGroupSubData = groupSubs.stream()
                .map(sub -> String.format("%s - %s\n", sub.getTitle(), sub.getId()))
                .collect(Collectors.joining());

        messageService.sendMessage(chatId, String.format(message, userGroupSubData));
    }
}
