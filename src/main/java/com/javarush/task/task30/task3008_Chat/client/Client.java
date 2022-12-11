package com.javarush.task.task30.task3008_Chat.client;

import com.javarush.task.task30.task3008_Chat.Connection;
import com.javarush.task.task30.task3008_Chat.ConsoleHelper;
import com.javarush.task.task30.task3008_Chat.Message;
import com.javarush.task.task30.task3008_Chat.MessageType;

import java.io.IOException;
import java.net.Socket;

public class Client {

    protected Connection connection;
    private volatile boolean clientConnected = false;
    protected String getServerAddress() {
        ConsoleHelper.writeMessage("Введите адрес сервера");
        return ConsoleHelper.readString();
    }
    protected int getServerPort() {
        ConsoleHelper.writeMessage("Введите номер порта");
        return ConsoleHelper.readInt();
    }
    protected String getUserName() {
        ConsoleHelper.writeMessage("Введите имя пользователя");
        return ConsoleHelper.readString();
    }
    protected boolean shouldSendTextFromConsole() {
        return true;
    }
    protected SocketThread getSocketThread() {
        return new SocketThread();
    }
    protected void sendTextMessage(String text)  {
        try {
            connection.send(new Message(MessageType.TEXT,text));
        } catch (IOException e) {
            clientConnected = false;
        }
    }
    /*
     * считываться сообщения с помощью метода ConsoleHelper.readString.
     * если была введена команда "exit" - тупо break;
     * если метод shouldSentTextFromConsole в true - тупо sendTextMessage(считаннаяСтрока);
     */

    public void run() {
        SocketThread socketThread = getSocketThread();
        socketThread.setDaemon(true);
        socketThread.start();
        synchronized (this) {
            try {
                wait();
                if (clientConnected) {
                    ConsoleHelper.writeMessage("Соединение установлено. Для выхода наберите команду 'exit'.");
                    while (clientConnected) {
                        String clientMessage = ConsoleHelper.readString();
                        if (clientMessage.equals("exit")) break;
                        if (shouldSendTextFromConsole()) sendTextMessage(clientMessage);
                    }
                } else {
                    ConsoleHelper.writeMessage("Произошла ошибка во время работы клиента");

                }
            } catch (InterruptedException e) {
                ConsoleHelper.writeMessage("Возникло исключение. ВЫход из программы...");
                return;
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }
    public class SocketThread extends Thread {

        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
        }
        protected void informAboutAddingNewUser (String userName) {
            ConsoleHelper.writeMessage(String.format("Пользователь %s присоединился к чату", userName));
        }
        protected void informAboutDeletingNewUser(String userName) {
            ConsoleHelper.writeMessage(String.format("Пользователь %s покинул чат", userName));
        }
        protected void notifyConnectionStatusChanged (boolean clientConnected) {

            Client.this.clientConnected = clientConnected;
            synchronized (Client.this) {
                 Client.this.notify();
            }
        }

        protected void clientHandshake() throws IOException, ClassNotFoundException {
            while (true) {
                //connection.send(new Message(MessageType.NAME_REQUEST));

                Message message = connection.receive();
                if (message.getType() == MessageType.NAME_REQUEST) {
                    String userName = getUserName();
                    Message messageWithName = new Message(MessageType.USER_NAME, userName);
                    connection.send(messageWithName);
                } else if (message.getType() == MessageType.NAME_ACCEPTED) {
                    notifyConnectionStatusChanged(true);
                    break;
                } else {
                    throw new IOException("Unexpected MessageType");
                }
            }
        }

        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            while (true) {
                Message message = connection.receive();
                if (message.getType() == MessageType.TEXT) processIncomingMessage(message.getData());
                else if (message.getType() == MessageType.USER_ADDED) informAboutAddingNewUser(message.getData());
                else if (message.getType() == MessageType.USER_REMOVED) informAboutDeletingNewUser(message.getData());
                else {
                    throw new IOException("Unexpected MessageType");
                }
            }
        }

        @Override
        public void run() {
            String serverAddress = getServerAddress();
            int serverPort = getServerPort();
            try {
                Socket socket = new Socket(serverAddress, serverPort);
                connection = new Connection(socket);
                clientHandshake();
                clientMainLoop();
            } catch (IOException | ClassNotFoundException e) {
                notifyConnectionStatusChanged(false);
            }
        }
    }
}
