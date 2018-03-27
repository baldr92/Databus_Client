import java.io.*;
import java.net.Socket;


public class Client {
    public static void main(String args[]) throws InterruptedException {
        try {
            Socket socket = new Socket("127.0.0.1", 4242);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            while (!socket.isOutputShutdown()) {

                if (bufferedReader.ready()) {
                    System.out.println("Client starts to writing in channel");
                    Thread.sleep(1000);
                    String text = bufferedReader.readLine();

                    outputStream.writeUTF(text);
                    outputStream.flush();
                    System.out.println("Your message " + text +"  has been sent");
                    Thread.sleep(1000);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Something went wrong");
        }
    }
}
