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
            Socket client = serverSocket.accept();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Connection was accepted");
            DataOutputStream outputStream = new DataOutputStream(client.getOutputStream());
            DataInputStream inputStream = new DataInputStream(client.getInputStream());
            System.out.println("DataInputStream created");
            while (!client.isClosed()) {
                if(bufferedReader.ready()){
                    String serverText = bufferedReader.readLine();
                    if (serverText.equalsIgnoreCase("quit")){
                        System.out.println("Server is prepare to exit");
                        break;
                    }
                    es.execute(new CreationFactory(client));
                }

            }
            outputStream.close();
            inputStream.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Something went wrong");
        }
    }
}