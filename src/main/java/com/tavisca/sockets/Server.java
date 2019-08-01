package com.tavisca.sockets;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(80);

//      System.out.println("IS any one listening");
        while (true) {
            Socket socket = server.accept();

            Thread t;
            t = new ClientHandler(socket);
            t.start();
        }
    }
}