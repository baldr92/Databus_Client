import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Databus {

    public static void main(String args[]) throws InterruptedException {
        try {
            ServerSocket serverSocket = new ServerSocket(4242);
            Socket client = serverSocket.accept();
            System.out.println("Connection was accepted");
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            DataInputStream in = new DataInputStream(client.getInputStream());
            System.out.println("DataInputStream created");
            while (!client.isClosed()) {
                System.out.println("System is ready for getting data");
                String entry = in.readUTF();
                System.out.println("Read message from ClientSender: \n" + entry);

                if(entry.equalsIgnoreCase("quit")) {
                    System.out.println("Ending of work");
                    out.writeUTF("Databus reply" + entry);
                    out.flush();
                    break;
                }
                //out.writeUTF(entry);
                //out.flush();
            }
            out.close();
            in.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Something went wrong");
        }
    }
}

