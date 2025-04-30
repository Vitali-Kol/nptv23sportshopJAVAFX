package org.example.kolesnikovsport_shop.service;

import org.example.kolesnikovsport_shop.model.entity.ChatMessage;
import org.example.kolesnikovsport_shop.model.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    private final ChatMessageRepository repo;

    public ChatService(ChatMessageRepository repo) {
        this.repo = repo;
    }

    public ChatMessage saveMessage(String username, String message) {
        return repo.save(new ChatMessage(username, message));
    }

    public List<ChatMessage> getLastMessages() {
        return repo.findTop50ByOrderByTimestampAsc();
    }
}
