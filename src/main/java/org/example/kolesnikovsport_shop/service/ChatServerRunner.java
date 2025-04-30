package org.example.kolesnikovsport_shop.service;

import org.example.kolesnikovsport_shop.ChatServer;
import org.example.kolesnikovsport_shop.controller.ChatFormController;

public class ChatServerRunner implements Runnable {
    private ChatFormController chatFormController;

    // Конструктор теперь принимает ChatFormController
    public ChatServerRunner(ChatFormController chatFormController) {
        this.chatFormController = chatFormController;
    }

    @Override
    public void run() {
        ChatServer.startServer();
    }
}
