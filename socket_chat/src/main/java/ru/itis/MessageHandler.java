package ru.itis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageHandler {

    Socket client;
    EchoServerSocket server;
    BufferedReader fromClient;
    PrintWriter toClient;

    public MessageHandler(Socket client, EchoServerSocket server) {
        this.client = client;
        this.server = server;
        try {
            this.fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.toClient = new PrintWriter(client.getOutputStream(), true);
            new Thread(messageHandler).start();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private Runnable messageHandler = () -> {
        try {
            String name = fromClient.readLine();
            System.out.println(name + " entered the chat");
            toClient.println("welcome, " + name);
            String message = fromClient.readLine();
            while (true) {
                server.messageForChat(name + ": " + message);
                System.out.println(name + ": " + message);
                message = fromClient.readLine();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    };

    public void sendMessage(String message) {
        toClient.println(message);
    }
}
