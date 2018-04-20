import javafx.scene.SubScene;

import javax.xml.stream.events.Characters;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class OneClient {
    public static void main (String args[]) {
        //try {
            OneClient oneClient = new OneClient();
            System.out.println("Choose mode for the next work");
            System.out.println("Enter one of commands");
            System.out.println("\"-send\" or \"-get\" and your message");
            Scanner scanner = new Scanner(System.in);
            String switcherClientLogic = scanner.next();

            if (switcherClientLogic.contains("-get")) {
                System.out.println("Your last message is:");
                oneClient.getMessageFromServer();
            } else if (switcherClientLogic.contains("-send")) {
                System.out.println("A message is prepared to send");
                oneClient.sendMessageToServer();
            } else {
                System.out.println("Input one of following commands \"-get\" or \"send\" ");
            }


                /*switch (switcherClientLogic) {
                    case switcherClientLogic.contains("-send"):
                        System.out.println("Input your message");
                        oneClient.sendMessageToServer();
                        break;
                    case "get":
                        oneClient.getMessageFromServer();
                        break;
                    default:
                        System.out.println("Choose option you would like to use");
                        break;
                }  */
    }


    public void sendMessageToServer() {
        try{
            Socket socketToSever = new Socket("127.0.0.1", 4242);
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream outputStream = new DataOutputStream(socketToSever.getOutputStream());
            while (!socketToSever.isOutputShutdown()) {
                if (bf.ready()) {
                    System.out.println("ClientSender starts writing in channel");
                    String textToServer = bf.readLine();
                    outputStream.writeUTF(textToServer);
                    outputStream.flush();
                    System.out.println("Your message " + "<<" + textToServer + ">>" + "  has been sent");
                    System.out.println("Socket is closed");
                }
            }
            socketToSever.shutdownOutput();
            outputStream.close();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public void getMessageFromServer() {
        try {
            Socket socketFromServer = new Socket("127.0.0.1", 4242);
            DataInputStream inputStream = new DataInputStream(socketFromServer.getInputStream());
            if (!socketFromServer.isOutputShutdown()) {
                System.out.println("System is ready to get data from Server");
                String textFromServer = inputStream.readUTF();
                System.out.println("Your message from server is:"+ "\n" + textFromServer);
                socketFromServer.close();
            }
        } catch (IOException io){
            io.printStackTrace();
        }
    }
}
