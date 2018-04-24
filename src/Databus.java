import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Databus {
    public static void main(String args[]) {

        ExecutorService es = Executors.newFixedThreadPool(6);
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
                    String readCommandFromServer;
                    DataInputStream getterCommand = new DataInputStream(getFromClient.getInputStream());
                    readCommandFromServer = getterCommand.readUTF();
                    if (readCommandFromServer.contains("-send ")) {

                        System.out.println("Поток создан");
                        //String parserText = inputForParsing.readUTF();
                        //inputForParsing.close();
                        // if (parserText.contains("-send")){
                        //   Socket socketForSending = serverSocket.accept();
                        es.execute(new InputService(getFromClient));
                    } else if (readCommandFromServer.contains("-get ")){
                        System.out.println("Поток на отправке создан");
                        //es.execute(new OutputService());

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