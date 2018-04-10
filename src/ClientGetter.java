import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientGetter {
    public static void main(String args[]) {
        ClientGetter clientGetter = new ClientGetter();
        clientGetter.getMessageFromServer();
    }

    public void getMessageFromServer(){
        try {
            ServerSocket getterSocket = new ServerSocket(4343);
            Socket client = getterSocket.accept();
            //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            DataInputStream inputStream = new DataInputStream(client.getInputStream());
            while(!client.isClosed()){
                    System.out.println("System is ready to get data");
                    String text1 = inputStream.readUTF();
                    System.out.println(text1);
            }
        } catch (IOException io) {
            io.printStackTrace();
            System.out.println(io.getMessage());
        }
    }
}
