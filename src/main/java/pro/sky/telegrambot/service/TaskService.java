package pro.sky.telegrambot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Task;
import pro.sky.telegrambot.repository.TaskRepository;

import javax.print.DocFlavor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        Pattern pattern = Pattern.
                compile("(\\d{2}\\.\\d{2}\\.\\d{4})(\\s)(\\d{2}:\\d{2})(\\s+)(.+)");
        Matcher matcher = pattern.matcher(userMessage);
        DateTimeFormatter DateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter TimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        if (matcher.find()) {



            String message = matcher.group(5); // Сообщение

            Task task = new Task();
            task.setText(message);
            task.setChatId(chatId);
            task.setDate(LocalDate.parse(matcher.group(1), DateFormatter));
            task.setTime(LocalTime.parse(matcher.group(3), TimeFormatter).truncatedTo(ChronoUnit.MINUTES));// Сохраняем LocalDateTime напрямую

            // Сохранение задачи в репозитории
            taskRepository.save(task);
        } else {
            System.out.println("Нет совпадений.");
        }
    }


    public List<Object> sendNotification(LocalDate date, LocalTime time){
        LocalDateTime testTime = LocalDateTime.of(2025, 1, 1, 0, 0);
        List<Task> tasks = taskRepository.findAllByDateAndTime(date, time);
        List<Object> chatIdsAndTexts = new ArrayList<>();
        for (Task task : tasks) {
            chatIdsAndTexts.add(task.getChatId());
            chatIdsAndTexts.add(task.getText());
            taskRepository.delete(task);
        }
        return chatIdsAndTexts;
    }
}



