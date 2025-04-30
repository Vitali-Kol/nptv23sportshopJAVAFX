package org.example.kolesnikovsport_shop.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String message;
    private LocalDateTime timestamp;

    // JPA требует конструктор без аргументов
    public ChatMessage() {}

    public ChatMessage(String username, String message) {
        this.username  = username;
        this.message   = message;
        this.timestamp = LocalDateTime.now();
    }

    // геттеры/сеттеры
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public void setUsername(String u) { this.username = u; }
    public String getMessage() { return message; }
    public void setMessage(String m) { this.message = m; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime t) { this.timestamp = t; }
}
