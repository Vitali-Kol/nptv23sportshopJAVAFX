package org.example.kolesnikovsport_shop;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {
    private static final int PORT = 12345;
    private static volatile boolean started = false;
    private static final Set<ClientHandler> clients =
            Collections.synchronizedSet(new HashSet<>());

    public static synchronized void startServer() {
        if (started) return;
        started = true;
        try (ServerSocket ss = new ServerSocket(PORT)) {
            System.out.println("Chat server started on port " + PORT);
            while (true) {
                Socket sock = ss.accept();
                ClientHandler h = new ClientHandler(sock);
                clients.add(h);
                h.start();
            }
        } catch (BindException be) {
            System.out.println("ChatServer: порт " + PORT + " уже занят, пропускаем.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcast(String msg, ClientHandler sender) {
        synchronized (clients) {
            for (ClientHandler c : clients) {
                if (c != sender) c.sendMessage(msg);
            }
        }
    }

    public static void addClient(ClientHandler h) {
        clients.add(h);
    }

    public static void removeClient(ClientHandler h) {
        clients.remove(h);
        System.out.println("Клиент отключен: " + h.getClientName());
    }
}
