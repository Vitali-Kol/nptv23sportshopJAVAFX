package org.example.kolesnikovsport_shop.service;

import javafx.application.Platform;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;

/**
 * Клиент, который подключается к локальному серверу и
 * пересылает входящие сообщения в UI через Consumer<String>.
 */
@Service
public class ChatClientService {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    /** Подключиться и начать слушать входящие */
    public void connect(String host, int port, Consumer<String> onMessage) {
        new Thread(() -> {
            try {
                socket = new Socket(host, port);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                String line;
                while ((line = in.readLine()) != null) {
                    String msg = line;
                    Platform.runLater(() -> onMessage.accept(msg));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "chat-client-reader").start();
    }

    /** Отправить сообщение на сервер */
    public void send(String msg) {
        if (out != null) out.println(msg);
    }
}
