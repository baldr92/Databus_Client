import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Databus {
    public static void main(String args[]) {

        ExecutorService es = Executors.newFixedThreadPool(6);
            try {
                ServerSocket serverSocket = new ServerSocket(4242);
                System.out.println("Connection was accepted");

                // Create new socket
                // command = read from socket
                // if command == 'send' {
                //      create new socket
                //      input = read from socket
                // } else if

                while (!serverSocket.isClosed()) {

                    Socket getFromClient = serverSocket.accept(); //посмотреть почему эти две
                    String s; // проверка и отсечение
                    DataInputStream inputForParsing = new DataInputStream(getFromClient.getInputStream());
                    String parserText = inputForParsing.readUTF();
                    inputForParsing.close();
                    if
                    es.execute(new InputService(getFromClient));//строчки не выполняются в теле иф-контроллера
                    //Socket sendToClient = serverSocket.accept();
                    es.execute(new OutputService(sendToClient));//отсутствие гибкости программы, вследствии последовательного выполнения получения-отправки сообщения в очередь
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Something went wrong");
            }
        es.shutdown();
    }
}