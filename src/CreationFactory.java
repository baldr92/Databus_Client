import java.io.*;
import java.net.Socket;

public class CreationFactory implements Runnable {
    Socket clientDialogue;
    public CreationFactory(Socket socket) {
        clientDialogue = socket;
    }

    @Override
    public void run() {
        try {
            //DataOutputStream outputStream = new DataOutputStream(clientDialogue.getOutputStream());
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
                //clientDialogue.close();

                /**/

                //writing block
                //System.out.println("Start writing to channel");
                //outputStream.writeUTF(message);
                //System.out.println("Your message has been sent to Client");
                //outputStream.flush();
            }
            //outputStream.close();
            inputStream.close();
            clientDialogue.close();
        } catch (IOException io) {
            io.printStackTrace();
            System.out.println(io.getMessage());
        }
    }
}