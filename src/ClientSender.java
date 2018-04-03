import java.io.*;
import java.net.Socket;


public class ClientSender {
    public static void main(String args[]) throws InterruptedException {
        try {
            Socket socket = new Socket("127.0.0.1", 4242);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            //DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            while (!socket.isOutputShutdown()) {

        //впилил получение инфы 02.04.2018
                if (bufferedReader.ready()) {
                    System.out.println("ClientSender starts writing in channel");
                    String text = bufferedReader.readLine();
                    outputStream.writeUTF(text);
                    outputStream.flush();
                    System.out.println("Your message " + "<<" + text + ">>" + "  has been sent");
                    //System.out.println("Гет реди фо геттин сообщение с сервера");
                    //String getTextFromServer = inputStream.readUTF();
                    //System.out.println(getTextFromServer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Something went wrong");
        }
    }
}