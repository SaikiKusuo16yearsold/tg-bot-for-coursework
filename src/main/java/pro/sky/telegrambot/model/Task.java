package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification_task") // Убедитесь, что имя таблицы соответствует вашей базе данных
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // или GenerationType.AUTO
    private Long id;
    @Column(name = "chat_id")
    private long chatId; // Изменено на Integer
    private String text;
    private LocalDateTime data; // Или используйте LocalDateTime

    // Конструкторы, геттеры и сеттеры

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getChatId() { // Изменено на Integer
        return chatId;
    }

    public void setChatId(long chatId) { // Изменено на Integer
        this.chatId = chatId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

}
