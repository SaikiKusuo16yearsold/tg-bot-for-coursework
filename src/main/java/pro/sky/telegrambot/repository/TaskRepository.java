package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.Task;

import java.time.LocalDateTime;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findByData(LocalDateTime currentDateTime);
}
