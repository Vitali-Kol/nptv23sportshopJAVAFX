package org.example.kolesnikovsport_shop.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.kolesnikovsport_shop.service.ChatService;
import org.example.kolesnikovsport_shop.service.CustomerService;
import org.example.kolesnikovsport_shop.service.FormService;
import org.springframework.stereotype.Component;

@Component
public class ChatFormController {

    private final CustomerService customerService;
    private final ChatService     chatService;
    private final FormService     formService;
    private static boolean        serverStarted = false;

    @FXML private Label    lblNickname;
    @FXML private TextArea taChat;
    @FXML private TextField tfMessage;
    @FXML private Button   btnSend;
    @FXML private Button   btnClose;

    private String nickname;

    public ChatFormController(CustomerService customerService,
                              ChatService chatService,
                              FormService formService) {
        this.customerService = customerService;
        this.chatService     = chatService;
        this.formService     = formService;
    }

    @FXML
    public void initialize() {
        // раз и навсегда запускаем встроенный сервер
        if (!serverStarted) {
            new Thread(() -> org.example.kolesnikovsport_shop.ChatServer.startServer()).start();
            serverStarted = true;
        }

        nickname = customerService.getCurrentCustomer().getUsername();
        lblNickname.setText("Вы в чате как: " + nickname);

        // загрузим историю
        chatService.getLastMessages()
                .forEach(m -> taChat.appendText(
                        "[" + m.getTimestamp().toLocalTime() + "] "
                                + m.getUsername() + ": " + m.getMessage() + "\n"
                ));

        btnSend.setOnAction(e -> {
            var text = tfMessage.getText().trim();
            if (text.isEmpty()) return;
            // сохраняем в БД
            chatService.saveMessage(nickname, text);
            // рассылаем по всем клиентам
            org.example.kolesnikovsport_shop.ChatServer.broadcast(
                    nickname + ": " + text, null
            );
            taChat.appendText(nickname + ": " + text + "\n");
            tfMessage.clear();
        });
    }

    /** onAction="#closeChat" */
    @FXML
    private void closeChat() {
        ((Stage) btnClose.getScene().getWindow()).close();
    }

    /**
     * Вызывается из ClientHandler при получении чужого сообщения
     */
    public void addMessageToChat(String msg) {
        // сохранить и вывести
        chatService.saveMessage("remote", msg);
        Platform.runLater(() -> taChat.appendText(msg + "\n"));
    }
}
