package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.service.TaskService;

import javax.annotation.PostConstruct;
import java.util.List;


@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final TaskService taskService;

    public TelegramBotUpdatesListener(TaskService taskService) {
        this.taskService = taskService;
    }

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }


    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            String textMessage = update.message().text();
            Long chatId = update.message().chat().id();
            if (textMessage.equals("/start")) {
                sendMessage(chatId);
            } else {
                taskService.saveTaskToDb(textMessage, chatId);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


    public void sendMessage(Long chatId) {
        String welcomeMessage = "Привет, как дела?";
        telegramBot.execute(new SendMessage(chatId, welcomeMessage));
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void sendNotification() {
        List<Object> task = taskService.sendNotification();
        telegramBot.execute(new SendMessage(task.get(0), task.get(1).toString()));
    }

}
