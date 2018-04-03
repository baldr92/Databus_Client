import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Databus {

    public static void main(String args[]) {
        ExecutorService es = Executors.newFixedThreadPool(2);
        try {
            ServerSocket serverSocket = new ServerSocket(4242);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Connection was accepted");

            System.out.println("DataInputStream created");
            while (!serverSocket.isClosed()) {
                if(bufferedReader.ready()){
                    String serverText = bufferedReader.readLine();
                    if (serverText.equalsIgnoreCase("quit")){
                        System.out.println("Server is prepare to exit");
                        break;
                    }
                }
                Socket client = serverSocket.accept(); //посмотреть почему эти две
                es.execute(new CreationFactory(client));//строчки не выполняются в теле иф-контроллера
            }
            es.shutdown();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Something went wrong");
        }
    }
}