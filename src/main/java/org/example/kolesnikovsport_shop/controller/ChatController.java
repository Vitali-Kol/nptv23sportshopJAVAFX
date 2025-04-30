package org.example.kolesnikovsport_shop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.kolesnikovsport_shop.service.ChatClientService;
import org.springframework.stereotype.Component;

@Component
public class ChatController {

    private final ChatClientService clientService;

    @FXML private TextArea taChat;
    @FXML private TextField tfMessage;

    public ChatController(ChatClientService clientService) {
        this.clientService = clientService;
    }

    @FXML
    public void initialize() {
        // При инициализации сразу подключаемся к серверу на localhost:12345
        clientService.connect("localhost", 12345, this::appendMessage);
    }

    private void appendMessage(String msg) {
        taChat.appendText(msg + "\n");
    }

    @FXML
    private void onSend() {
        String text = tfMessage.getText().trim();
        if (!text.isEmpty()) {
            clientService.send(text);
            tfMessage.clear();
        }
    }
}
