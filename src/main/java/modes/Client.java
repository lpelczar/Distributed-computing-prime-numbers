package modes;

import models.Range;
import models.Result;
import solver.PrimeCalculator;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client implements Runnable {

    private int serverPort;
    private String serverHost;
    private boolean isStopped;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

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
            } catch (EOFException e) {
                System.out.println("SERVER DISCONNECTED");
                stop();
            } catch (IOException e) {
                System.out.println("ERROR");
                e.printStackTrace();
            }
        }
        System.exit(0);
    }

    private void waitForTask(Socket socket) throws IOException {
        ois = new ObjectInputStream(socket.getInputStream());
        oos = new ObjectOutputStream(socket.getOutputStream());
        while (!isStopped()) {

            try {
                Range range = (Range) ois.readObject();

                PrimeCalculator primeCalculator = new PrimeCalculator(range);
                Result result = primeCalculator.calculate();
                oos.writeObject(result);
                System.out.println("I have found these prime numbers: " + result);

            } catch (ClassNotFoundException e) {
                System.out.println("Something wrong!");
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
