import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


public class Databus {
    public static void main(String args[]) {
        Databus databus = new Databus();
            try {
                ServerSocket serverSocket = new ServerSocket(4242);
                System.out.println("Connection was accepted");

                while (!serverSocket.isClosed()) {
                    Socket getFromClient = serverSocket.accept();
                    DataInputStream getterCommand = new DataInputStream(getFromClient.getInputStream());
                    String readCommandFromServer  = getterCommand.readUTF();
                    getFromClient.close();
                    if (readCommandFromServer.contains("-send ")) {
                        System.out.println("Поток создан");

                       String path_string = "C:\\Users\\PLatchenkov\\IdeaProjects\\Databus_Client\\Titles.txt";
                        Path path = Paths.get(path_string);
                        if (!Files.exists(path)) {
                            File f = new File(path_string);
                            f.createNewFile();
                        }

                        System.out.println(readCommandFromServer);
                        File file = new File("Titles.txt");
                        FileWriter fileWriter = new FileWriter(file, true);
                        fileWriter.write(readCommandFromServer + "\n");
                        fileWriter.close();

                    } else if (readCommandFromServer.contains("-get")){
                        //send message to client and remove last read string
                        databus.sendMessageToClient();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Something went wrong");
            }
    }
    public void sendMessageToClient() throws FileNotFoundException, IOException { //должно делаться на сервере
        System.out.println("Конечный клиент подключился");
        System.out.println("Подготовка к отправке сообщения клиенту");
        File fileMsg = new File("Titles.txt");
        //FileReader fileReader = new FileReader(fileMsg);
        String lastLine = readLastLine(fileMsg);
        //Socket socket = serverSocket.accept();
        Socket newSocketToClient = new Socket("127.0.0.1", 4444);
        DataOutputStream sendMsgStream = new DataOutputStream(newSocketToClient.getOutputStream());
        sendMsgStream.writeUTF(lastLine);
        System.out.println("Your message" + "<<" + lastLine + ">>" + "has been sent");
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