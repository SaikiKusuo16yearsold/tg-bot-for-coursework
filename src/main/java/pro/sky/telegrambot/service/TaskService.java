package pro.sky.telegrambot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Task;
import pro.sky.telegrambot.repository.TaskRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TaskService {


    @Autowired
    TaskRepository taskRepository;

    public void saveTaskToDb(String userMessage, Long chatId) {
        String pattern = "(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(userMessage);
        if (matcher.find()) {
            LocalDateTime dateTime = LocalDateTime.parse(matcher.group(1), formatter); // Дата и время
            String message = matcher.group(3);   // Сообщение
            Task task = new Task();
            task.setText(message);
            task.setChatId(chatId);
            task.setData(dateTime);
            taskRepository.save(task);
        } else {
            System.out.println("Нет совпадений.");
        }
    }

    public List<Object> sendNotification() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:00");
        String formattedTime = currentTime.format(formatter);
        Task task = taskRepository.findByData(LocalDateTime.parse(formattedTime, formatter));
        System.out.println(formattedTime);
        String textMessage = task.getText();
        return new ArrayList(List.of(task.getChatId(), textMessage));
    }


}
