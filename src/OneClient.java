import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.scene.SubScene;

import javax.xml.stream.events.Characters;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;

public class OneClient {





    public static void main (String args[]) {
        //try {
            OneClient oneClient = new OneClient();
            System.out.println("Choose mode for the next work");
            System.out.println("Enter one of commands");
            System.out.println("\"-send\" or \"-get\" and your message");


         //Scanner scanner = new Scanner(System.in);
         //String switcherClientLogic = scanner.toString();
         BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
         try {
             String textFromPanel = bf.readLine();
             bf.close();
             if (textFromPanel.contains("-get")) {
                    System.out.println("The system is prepared to get message");
                    oneClient.getMessageFromQuery();
                } else if (textFromPanel.contains("-send ")) {
                    System.out.println("A message is prepared to send");
                    oneClient.sendMessageToServer(textFromPanel);
                } else {
                    System.out.println("Input one of following commands \"-get\" or \"send\" ");
                }
            } catch (IOException io) {
                io.printStackTrace();
            }
                /*
                switch (switcherClientLogic) {
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


    public void sendMessageToServer(String string) {
        try{
            Socket socketToSever = new Socket("127.0.0.1", 4242);
            //BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream outputStream = new DataOutputStream(socketToSever.getOutputStream());
            while (!socketToSever.isOutputShutdown()) {
                //if (bf.ready()) {
                    System.out.println("ClientSender starts writing in channel");
                    //String textToServer = bf.readLine();
                    outputStream.writeUTF(string);
                    outputStream.flush();
                    System.out.println("Your message " + "<<" + string + ">>" + "  has been sent");
                    System.out.println("Socket is closed");
                    socketToSever.shutdownOutput();
                //}
            }
            //socketToSever.shutdownOutput();
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

    public void getMessageFromQuery() throws FileNotFoundException, IOException {
        System.out.println("Конечный клиент подключился");
        System.out.println("Подготовка к отправке сообщения клиенту");
        File fileMsg = new File("Titles.txt");
        FileReader fileReader = new FileReader(fileMsg);

        //BufferedReader br = new BufferedReader(fileReader);
        //String line = br.readLine();





        String lastLine = readLastLine(fileMsg);
        System.out.println(lastLine);

        //на удаление последней строки
        List<String> lines = FileUtils.readLines(fileMsg);
        List<String> updatedLines = lines.stream().filter(s -> !s.contains(lastLine)).collect(Collectors.toList());
        FileUtils.writeLines(fileMsg,updatedLines);

        }

        private static String readLastLine(File file) throws FileNotFoundException, IOException {
        String result = null;
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            long startIdx = file.length();
            while (startIdx >= 0 && (result == null || result.length() == 0)) {
                raf.seek(startIdx);
                if (startIdx > 0)
                    raf.readLine();
                result = raf.readLine();
                startIdx--;
            }
        }


        return result;
    }
}