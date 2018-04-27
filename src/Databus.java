import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


public class Databus {
    public static void main(String args[]) {

        ExecutorService es = Executors.newFixedThreadPool(6);
        Databus databus = new Databus();
            try {
                ServerSocket serverSocket = new ServerSocket(4242);
                System.out.println("Connection was accepted");
                // Create new socket
                // command = read from socket
                // if command == 'send' {
                //      create new socket
                //      input = read from socket
                // } else if

                while (!serverSocket.isClosed()) {

                    Socket getFromClient = serverSocket.accept();

                    DataInputStream getterCommand = new DataInputStream(getFromClient.getInputStream());
                    String readCommandFromServer  = getterCommand.readUTF();
                    //System.out.println(readCommandFromServer);
                    //DataOutputStream outToService = new DataOutputStream(getFromClient.getOutputStream());
                    //outToService.writeUTF(readCommandFromServer);
                    getFromClient.close();
                    if (readCommandFromServer.contains("-send ")) {
                        System.out.println("Поток создан");
                        FileReader fileForCheck = new FileReader("Titles.txt");
                        BufferedReader bufferedReader = new BufferedReader(fileForCheck);
                        if (!(bufferedReader.readLine() == null)) {
                            //String newMessage = message.replaceAll("[-send ]","");
                            System.out.println(readCommandFromServer);
                            File file = new File("Titles.txt");
                            FileWriter fileWriter = new FileWriter(file, true);
                            fileWriter.write(readCommandFromServer + "\n");
                            fileWriter.close();

                        }
                        //Socket determineSocket = serverSocket.accept();
                        //String parserText = inputForParsing.readUTF();
                        //inputForParsing.close();
                        // if (parserText.contains("-send")){
                        //   Socket socketForSending = serverSocket.accept();

                        //es.execute(new InputService(getFromClient));
                    } else if (readCommandFromServer.contains("-get")){
                        //send message to client and remove last readed string
                        databus.sendMessageToClient();


                    }


                    // } else if(parserText.contains("-get")){
                    //Socket sendToClient = serverSocket.accept();
                    //es.execute(new OutputService(sendToClient));//отсутствие гибкости программы, вследствии последовательного выполнения получения-отправки сообщения в очередь
                }

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Something went wrong");
            }
        //es.shutdown();
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








/* try { //утащить в другой класс
                                    DataInputStream inputStream = new DataInputStream(socketForSending.getInputStream());
                                    System.out.println("DataInputStream is created");
                                    while (!socketForSending.isClosed()) {
                                        //reading block
                                        System.out.println("Start reading");
                                        String[] message = inputStream.readUTF().split("-send ");
                                        FileReader fileForCheck = new FileReader("Titles.txt");
                                        BufferedReader bufferedReader = new BufferedReader(fileForCheck);
                                        if (!(bufferedReader.readLine() == null)) {
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
                                } */