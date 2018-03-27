import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

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
                System.out.println("Read message from Client: \n" + entry);
                Thread.sleep(3000);
                // инициализация проверки условия продолжения работы с клиентом по этому сокету по кодовому слову
                //       - quit

                if(entry.equalsIgnoreCase("quit")) {
                    System.out.println("Ending of work");
                    out.writeUTF("Server reply" + entry);
                    out.flush();
                    Thread.sleep(3000);
                    break;
                }
                //out.writeUTF(entry);
                //out.flush();
                Thread.sleep(1000);
            }
            out.close();
            in.close();
            Thread.sleep(1000);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Something went wrong");
        }
    }
}

