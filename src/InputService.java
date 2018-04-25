import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InputService implements Runnable {
    static Socket clientDialogue;
    public InputService(Socket socket) {
        clientDialogue = socket;
    }

    @Override
    public void run() {
        ExecutorService esForGetting = Executors.newCachedThreadPool();
        try {
            DataInputStream inputStream = new DataInputStream(clientDialogue.getInputStream());
            System.out.println("DataInputStream is created");
            while (!clientDialogue.isClosed()) {
                //reading block
                System.out.println("Start reading");
                String message = inputStream.readUTF();

                FileReader fileForCheck = new FileReader("Titles.txt");
                BufferedReader bufferedReader = new BufferedReader(fileForCheck);
                if (!(bufferedReader.readLine() == null)) {
                        //String newMessage = message.replaceAll("[-send ]","");
                        System.out.println(message);
                        File file = new File("Titles.txt");
                        FileWriter fileWriter = new FileWriter(file, true);
                        fileWriter.write(message + "\n");
                        fileWriter.close();

                }
                inputStream.close();
                //clientDialogue.close();
            }
        } catch (IOException io) {
            io.printStackTrace();
            System.out.println(io.getMessage());
        }
    }
}