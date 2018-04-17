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
                if (!serverSocket.isClosed()) {
                    Socket client = serverSocket.accept(); //посмотреть почему эти две
                    es.execute(new InputService(client));//строчки не выполняются в теле иф-контроллера
                }
                //serverSocket.close();
                Socket sockToClient = new Socket("127.0.0.1", 4343);
                es.execute(new OutputService(sockToClient));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Something went wrong");
            }
        es.shutdown();
    }
}