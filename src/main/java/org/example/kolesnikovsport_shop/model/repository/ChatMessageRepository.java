package org.example.kolesnikovsport_shop.model.repository;

import org.example.kolesnikovsport_shop.model.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    // забираем последние 50 сообщений в порядке возрастания времени
    List<ChatMessage> findTop50ByOrderByTimestampAsc();
}
