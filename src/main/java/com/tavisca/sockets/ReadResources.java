package com.tavisca.sockets;

import java.io.*;
import java.net.Socket;
import java.util.Date;

class ReadResources {
    private static String filename;
    private static Socket socket;
    ReadResources(String string,Socket sc){
        filename = string;
        socket=sc;
    }
    public static String readResourcesFunction(){

        StringBuilder result = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String contents;
            while ((contents = bufferedReader.readLine()) != null) {
                result.append(contents);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
    public static void serveImage(){
        try {
            BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
            BufferedInputStream reader = new BufferedInputStream(new FileInputStream(filename));


            Date d = new Date();
            String httpResponse = "HTTP/1.1 200 OK\r\n";
            httpResponse+= "Server is Java HTTP Server : 1.0\r\n";
            httpResponse+="Date: "+d.toString() + "\r\n";
            httpResponse+="Content-Type: image/jpg\r\n\r\n";
            out.write(httpResponse.getBytes("UTF-8"));
            out.flush();


            byte[] buffer = new byte[ reader.available()];
            int bytesRead;
            while ( (bytesRead = reader.read(buffer)) != -1 ) {
                out.write( buffer, 0, bytesRead );
            }

            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
