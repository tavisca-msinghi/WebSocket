package com.tavisca.sockets;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(80);
        System.out.println("IS any one listening");

        Socket socket = server.accept();

        InputStreamReader isr = new InputStreamReader(socket.getInputStream());
        BufferedReader reader = new BufferedReader(isr);

        String input = reader.readLine();
        System.out.println(input);

        String[] check = input.split(" ");
        String filename = check[1].substring(1);
        int isAvailable = checkresources(filename);
        System.out.println(isAvailable);

        if (isAvailable == 0) {
            String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + "<html> <head> </head> <body> Not Found </body> </html>";
            socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
        } else {
            String result = readResources(filename);
            System.out.println(result);
            String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + result;
            socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
        }

    }

    private static String readResources(String filename) {
        String result = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String contents;
            while ((contents = bufferedReader.readLine()) != null) {
                result += contents;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static int checkresources(String filename) {
        try (FileInputStream fileInputStream = new FileInputStream(filename)) {
        } catch (FileNotFoundException e) {
            return 0;
        } catch (IOException e) {
            return 0;
        }
        return 1;
    }
}
