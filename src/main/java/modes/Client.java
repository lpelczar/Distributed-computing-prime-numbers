package modes;

import models.Range;
import models.Result;
import solver.PrimeCalculator;

import java.io.IOException;
import java.io.ObjectInputStream;
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
                throw new RuntimeException("Error connecting to server", e);
            }

            try {
                waitForTask(socket);
            } catch (Exception e) {
                System.out.println("Error!");
            }
        }
        System.exit(0);
    }

    private void waitForTask(Socket socket) throws IOException {

        while (!isStopped()) {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            try {
                Range range = (Range) ois.readObject();

                PrimeCalculator primeCalculator = new PrimeCalculator(range);
                Result result = primeCalculator.calculate();
                System.out.println("I have finished!");
                System.out.println("I have found these prime numbers: " + result);
                oos.writeObject(result);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    private synchronized void stop(){
        this.isStopped = true;
    }
}
