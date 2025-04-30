package org.example.kolesnikovsport_shop;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final Socket socket;
    private BufferedReader in;
    private PrintWriter  out;
    private String       name;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            this.name = in.readLine();
            ChatServer.broadcast(">>> " + name + " вошел в чат", this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getClientName() {
        return name;
    }

    public void sendMessage(String msg) {
        out.println(msg);
    }

    @Override
    public void run() {
        try {
            String line;
            while ((line = in.readLine()) != null) {
                if ("/quit".equalsIgnoreCase(line)) break;
                ChatServer.broadcast(name + ": " + line, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ChatServer.broadcast("<<< " + name + " покинул чат", this);
            ChatServer.removeClient(this);
        }
    }
}
