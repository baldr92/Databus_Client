import java.io.*;
import java.net.Socket;

public class ClientGetter {
    public static void main(String args[]) {
        ClientGetter clientGetter = new ClientGetter();
        clientGetter.getMessageFromServer();
    }

    public void getMessageFromServer(){
        try {
            Socket socket = new Socket("127.0.0.1", 4242);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            while(!socket.isInputShutdown()){
                if(bufferedReader.ready()) {
                    System.out.println("System is ready to get data");
                    String text1 = inputStream.readUTF();
                    System.out.println(text1);
                }
            }
        } catch (IOException io) {
            io.printStackTrace();
            System.out.println(io.getMessage());
        }
    }
    public void createFile(){

    }
    public void writeToFile(){

    }
}
