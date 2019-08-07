package com.tavisca.sockets;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread{
    private Socket socket;
    private InputStreamReader inputStreamReader=null;
    private BufferedReader bufferedReader=null;

    ClientHandler(Socket sc) {
        socket=sc;
    }

    public void run(){
        try{
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);

            String input = bufferedReader.readLine();
            //System.out.println(input);

            String[] check = input.split(" ");
            String filename = check[1].substring(1);

            CheckResources checkResources = new CheckResources(filename);
            int isPresent = CheckResources.CheckFile();
            System.out.println(isPresent);

            if (isPresent == 0) {
                String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + "<html> <head> </head> <body> <p> <font size=\"6\">Not Found!</font></p> </body> </html>";
                socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
            } else {
                ReadResources readResources = new ReadResources(filename,socket);
                if(filename.contains(".jpg")||filename.contains(".ico")){
                    readResources.serveImage();
                }
                else{
                String result = ReadResources.readResourcesFunction();
                System.out.println(filename);
                String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + result;
                socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            inputStreamReader.close();
            bufferedReader.close();
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}