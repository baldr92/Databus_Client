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

                while (!serverSocket.isClosed()) {

                    Socket client = serverSocket.accept(); //посмотреть почему эти две

                    es.execute(new InputService(client));//строчки не выполняются в теле иф-контроллера

                    es.execute(new OutputService(socketToGetter));
                    // переведем код из потока вывода
                    /*
                    try {
                            Socket sendSocket = new Socket("127.0.0.1", 4343);  //ДЛЯ ВЫВОДА В ДРУГОЙ КЛИЕНТ
                            if (!sendSocket.isOutputShutdown()) { //сделать условие на подключение нового клинта
                                System.out.println("Конечный клиент подключился");

                                File fileMsg = new File("Titles.txt");
                                FileReader fileReader = new FileReader(fileMsg);

                                BufferedReader br = new BufferedReader(fileReader);
                                String line;
                                DataOutputStream outputStream = new DataOutputStream(sendSocket.getOutputStream());
                                while ((line = br.readLine()) != null) {
                                    outputStream.writeUTF(line);
                                }

                                br.close();
                            }
                            sendSocket.close();
                        }catch (Exception ex) {
                            ex.printStackTrace();
                            System.out.println(ex.getMessage());
                        }*/

                    client.close();
                }
                serverSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Something went wrong");
            }
        es.shutdown();
    }
}