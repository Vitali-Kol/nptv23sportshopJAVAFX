package org.example.kolesnikovsport_shop;

import java.io.*;
import java.net.*;
import java.util.function.Consumer;

public class ChatClient extends Thread {
    private final String serverIp;
    private final int    serverPort;
    private final String username;
    private final Consumer<String> onMessage;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter    out;

    public ChatClient(String serverIp, int serverPort, String username, Consumer<String> onMessage) {
        this.serverIp   = serverIp;
        this.serverPort = serverPort;
        this.username   = username;
        this.onMessage  = onMessage;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(serverIp, serverPort);
            in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println(username);  // Отправляем имя пользователя

            String line;
            while ((line = in.readLine()) != null) {
                onMessage.accept(line);  // Вызываем callback для обработки сообщений
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg) {
        if (out != null) out.println(msg);
    }

    public void close() throws IOException {
        if (socket != null) socket.close();
    }
}
