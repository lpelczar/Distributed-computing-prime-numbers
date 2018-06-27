package modes;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client implements Runnable {

    private int serverPort;
    private String serverHost;
    private boolean isStopped;

    public Client(String host, int port) {
        this.serverHost = host;
        this.serverPort = port;
    }

    @Override
    public void run() {

        while (!isStopped()) {

            Socket socket;
            try {
                socket = new Socket(serverHost, serverPort);
            } catch (IOException e) {
                if (isStopped()) {
                    return;
                }
                throw new RuntimeException("* Error connecting to server", e);
            }

            try {
                waitForTask(socket);
            } catch (Exception e) {
                System.out.println("* Error sending message!");
            }
        }
        System.exit(0);
    }

    private void waitForTask(Socket socket) throws IOException {

        while (!isStopped()) {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        }
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    private synchronized void stop(){
        this.isStopped = true;
    }
}
