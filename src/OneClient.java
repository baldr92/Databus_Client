import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;

public class OneClient {
    public static void main (String args[]) {
        //try {

            OneClient oneClient = new OneClient();
            System.out.println("Choose mode for the next work");
            System.out.println("Enter one of commands");
            System.out.println("\"-send\" or \"-get\" and your message");

         BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
         try {
             Socket socket = new Socket("127.0.0.1", 4242);
             String textFromPanel = bf.readLine();
             bf.close();
             if (textFromPanel.contains("-get")) {
                 ServerSocket serverSocket = new ServerSocket(4444);
                 Socket getFromDataBus = serverSocket.accept();
                 DataInputStream getFromMQ = new DataInputStream(getFromDataBus.getInputStream());
                 String stringMQ = getFromMQ.readUTF();
                 System.out.println(stringMQ);
                 /*
                    System.out.println("The system is prepared to get message");
                    //oneClient.getMessageFromQuery();
                    oneClient.sendMessageToServer(textFromPanel, socket);
                    System.out.println("1");
                    DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                    inputStream.readUTF();
                    */
                } else if (textFromPanel.contains("-send ")) {
                    System.out.println("A message is prepared to send");
                    oneClient.sendMessageToServer(textFromPanel, socket);
                } else {
                    System.out.println("Input one of following commands \"-get\" or \"send\" ");
                }
            } catch (IOException io) {
                io.printStackTrace();
            }
    }

    //сделать этот метод общим, а внутри сделать ветвление на отправку/прием
    public void sendMessageToServer(String string, Socket socketToSever) {
        try{
            DataOutputStream outputStream = new DataOutputStream(socketToSever.getOutputStream());
            while (!socketToSever.isOutputShutdown()) {
                    System.out.println("ClientSender starts writing in channel");
                    outputStream.writeUTF(string);
                    outputStream.flush();
                    System.out.println("Your message " + "<<" + string + ">>" + "  has been sent");
                    System.out.println("Socket is closed");
                    socketToSever.shutdownOutput();
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

    public void getMessageFromQuery() throws FileNotFoundException, IOException { //должно делаться на сервере
        System.out.println("Конечный клиент подключился");
        System.out.println("Подготовка к отправке сообщения клиенту");
        File fileMsg = new File("Titles.txt");
        FileReader fileReader = new FileReader(fileMsg);
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