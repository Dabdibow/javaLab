package ru.itis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class EchoServerSocket {

    private ArrayList<MessageHandler> clients;

    public void start(int port) {
        ServerSocket server;
        Socket client;
        clients = new ArrayList<>();
        try {
            server = new ServerSocket(port);

            while (true) {
                client = server.accept();
                MessageHandler newUser = new MessageHandler(client, this);
                clients.add(newUser);
            }

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void messageForChat(String message) {
        for (MessageHandler client: clients) {
            client.sendMessage(message);
        }
    }


}
