import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class CreationFactory implements Runnable {
    static Socket clientDialogue;
    public CreationFactory(Socket socket) {
        CreationFactory.clientDialogue = socket;
    }

    @Override
    public void run() {
        try {
            DataOutputStream outputStream = new DataOutputStream(clientDialogue.getOutputStream());
            DataInputStream inputStream = new DataInputStream(clientDialogue.getInputStream());
            System.out.println("DataOutputStream is created");
            System.out.println("DataInputStream is created");
            while(!clientDialogue.isClosed()) {
                //reading block
                System.out.println("Start reading");
                String message = inputStream.readUTF();
                System.out.println("\n" + message);
                //writing block
                System.out.println("Start writing to channel");
                outputStream.writeUTF(message);
                System.out.println("Your message has been sent to Client");
                outputStream.flush();
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException io) {
            io.printStackTrace();
            System.out.println(io.getMessage());
        }
    }
}