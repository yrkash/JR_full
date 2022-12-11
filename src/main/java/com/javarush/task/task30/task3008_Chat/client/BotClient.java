package com.javarush.task.task30.task3008_Chat.client;

import com.javarush.task.task30.task3008_Chat.ConsoleHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class BotClient extends Client {
    public class BotSocketThread extends SocketThread {
        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
            if ((!message.contains(": ")) || (message == null)) return;
            String parseRequest = message.substring(message.indexOf(":") + 2);
            String userName = message.substring(0, message.indexOf(":"));
            //sendTextMessage("Информация для " + userName);

            switch (parseRequest) {
                case("дата"):
                    sendTextMessage("Информация для " + userName + ": " + new SimpleDateFormat("d.MM.YYYY").format(new GregorianCalendar().getTime()));
                    break;
                case("день"):
                    sendTextMessage("Информация для " + userName + ": " + new SimpleDateFormat("d").format(new GregorianCalendar().getTime()));
                    break;
                case("месяц"):
                    sendTextMessage("Информация для " + userName + ": " + new SimpleDateFormat("MMMM").format(new GregorianCalendar().getTime()));
                    break;
                case("год"):
                    sendTextMessage("Информация для " + userName + ": " + new SimpleDateFormat("YYYY").format(new GregorianCalendar().getTime()));
                    break;
                case("время"):
                    sendTextMessage("Информация для " + userName + ": " + new SimpleDateFormat("H:mm:ss").format(new GregorianCalendar().getTime()));
                    break;
                case("час"):
                    sendTextMessage("Информация для " + userName + ": " + new SimpleDateFormat("H").format(new GregorianCalendar().getTime()));
                    break;
                case("минуты"):
                    sendTextMessage("Информация для " + userName + ": " + new SimpleDateFormat("m").format(new GregorianCalendar().getTime()));
                    break;
                case("секунды"):
                    sendTextMessage("Информация для " + userName + ": " + new SimpleDateFormat("s").format(new GregorianCalendar().getTime()));
                    break;
            }

            //super.processIncomingMessage(message);
        }
    }

    @Override
    protected String getUserName() {
        int x = (int) (Math.random() * 100);
        return "date_bot_" + x;
    }

    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }
    public static void main(String[] args) {
        BotClient botClient = new BotClient();
        botClient.run();
    }
}
