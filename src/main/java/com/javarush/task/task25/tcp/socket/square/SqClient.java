package com.javarush.task.task25.tcp.socket.square;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SqClient {

    public static void main(String[] args) throws Exception {
        try (Socket socket = new Socket("localhost", 11112)) {

            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(10);
            outputStream.flush();

            InputStream inputStream = socket.getInputStream();
            int response = inputStream.read();

            System.out.println(response);
        }
    }
}
