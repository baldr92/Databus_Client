import java.io.*;
import java.net.Socket;

public class InputService implements Runnable {
    static Socket clientDialogue;
    public InputService(Socket socket) {
        clientDialogue = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream inputStream = new DataInputStream(clientDialogue.getInputStream());
            System.out.println("DataOutputStream is created");
            System.out.println("DataInputStream is created");
            if (!clientDialogue.isClosed()) {
                //reading block
                System.out.println("Start reading");
                String message = inputStream.readUTF();
                System.out.println("\n" + message);

                FileWriter fileWriter = new FileWriter("Titles.txt");
                fileWriter.write(message);
                fileWriter.close();

                clientDialogue.close();
                inputStream.close();

            }



        } catch (IOException io) {
            io.printStackTrace();
            System.out.println(io.getMessage());
        }
    }
}