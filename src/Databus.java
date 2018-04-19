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

                while (!serverSocket.isClosed()) {
                    Socket getFromClient = serverSocket.accept(); //посмотреть почему эти две
                    //DataInputStream streamSwitcher = new DataInputStream(getFromClient.getInputStream());
                    //String chooser = streamSwitcher.readUTF();
                    es.execute(new InputService(getFromClient));//строчки не выполняются в теле иф-контроллера
                    Socket sendToClient = serverSocket.accept();
                    es.execute(new OutputService(sendToClient));//отсутствие гибкости программы, вследствии последовательного выполнения получения-отправки сообщения в очередь
                }
                //serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Something went wrong");
            }
        es.shutdown();
    }
}