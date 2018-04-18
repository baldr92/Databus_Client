import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.net.Socket;

public class OutputService implements Runnable {
    static Socket sendSocket;
    public OutputService(Socket socket){
        sendSocket = socket;
    }
    @Override
    public void run() {
        try {
            //sendSocket = new Socket("127.0.0.1", 4343);  //ДЛЯ ВЫВОДА В ДРУГОЙ КЛИЕНТ
            if (!sendSocket.isOutputShutdown()) { //сделать условие на подключение нового клиента
                System.out.println("Конечный клиент подключился");
                System.out.println("Подготовка к отправке сообщения клиенту");
                File fileMsg = new File("Titles.txt");
                FileReader fileReader = new FileReader(fileMsg);

                BufferedReader br = new BufferedReader(fileReader);
                String line;
                DataOutputStream outputStream = new DataOutputStream(sendSocket.getOutputStream());
                while ((line = br.readLine()) != null) {
                    outputStream.writeUTF(line);
                    System.out.println("Ваше сообщение " + line + " отправлено");
                }
                br.close();
            }
            //sendSocket.close();
        }catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }
}
