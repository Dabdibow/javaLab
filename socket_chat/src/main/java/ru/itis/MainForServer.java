package ru.itis;

public class MainForServer {
    public static void main(String[] args) {
        EchoServerSocket echoServerSocket = new EchoServerSocket();
        echoServerSocket.start(7777) ;
        for (int i = 1015; i < 8975; ++i) {

        }
    }
}
