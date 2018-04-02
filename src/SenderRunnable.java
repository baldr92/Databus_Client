import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SenderRunnable implements Runnable{
    static Socket sender_socket;
    public SenderRunnable(Socket socket) {
        sender_socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream inputStream = new DataInputStream(sender_socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(sender_socket.getOutputStream());
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
