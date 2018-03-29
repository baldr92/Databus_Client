import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Databus {

    public static void main(String args[]) {
        ExecutorService es = Executors.newFixedThreadPool(2);
        Socket client;
        try {
            ServerSocket serverSocket = new ServerSocket(4242);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            //Socket client = serverSocket.accept();
            System.out.println("Connection was accepted");

            /*
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            DataInputStream in = new DataInputStream(client.getInputStream());
            System.out.println("DataInputStream created");
            System.out.println("DataOutputStream created");
            */
            while (!serverSocket.isClosed()) {
                if (br.ready()) {
                    String serverText = br.readLine();
                        if (serverText.equalsIgnoreCase("quit")) {
                        serverSocket.close();
                        break;
                        }
                        client = serverSocket.accept();
                        es.execute(new CreationFactory(client));
                }
            }
            es.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Something went wrong");
        }
    }
}

