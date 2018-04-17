import java.io.IOException;
import java.net.Socket;

public class OneClient {
    public static void main (String args[]) {
        OneClient oneClient = new OneClient();
    }


    public void sendMessageToServer() {
        try{
            Socket socketToSever = new Socket("127.0.0.1", 4242);

        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
