package modes;

import models.Range;
import models.Result;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {

    private int serverPort;
    private ServerSocket serverSocket;
    private boolean isStopped;
    private Range range;

    private List<Socket> clients = new ArrayList<>();
    private long startTime;

    public Server(int port, Range range) {
        this.serverPort = port;
        this.range = range;
    }

    @Override
    public void run() {
        openServerSocket();

        while (!isStopped()) {

            while (clients.size() < 3) {

                Socket clientSocket;
                try {
                    clientSocket = this.serverSocket.accept();
                    clients.add(clientSocket);
                } catch (IOException e) {
                    if (isStopped()) {
                        System.out.println("Server Stopped.");
                        return;
                    }
                    throw new RuntimeException("Error accepting client connection", e);
                }

            }
            try {
                start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        System.out.println("Server Stopped.");

    }

    private void start() throws IOException {
        startTime = System.nanoTime();
        List<ObjectOutputStream> outputStreams = new ArrayList<>();
        List<ObjectInputStream> inputStreams = new ArrayList<>();

        for (Socket socket : clients) {
            System.out.println("Getting streams!");
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            outputStreams.add(oos);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            inputStreams.add(ois);
        }

        List<Range> ranges = range.divideRanges(3);

        for (int i = 0; i < ranges.size(); i++) {
            outputStreams.get(i).writeObject(ranges.get(i));
        }

        waitForResults(inputStreams);

    }

    private void waitForResults(List<ObjectInputStream> inputStreams) {

        List<Result> results = new ArrayList<>();

       do{

            for (ObjectInputStream ois : inputStreams) {

                try {
                    Result result = (Result) ois.readObject();
                    System.out.println("Client end work");
                    results.add(result);

                } catch (ClassNotFoundException | IOException e) {
                    System.out.println("ERROR");
                    e.printStackTrace();
                }

            }
        } while (!(inputStreams.size() <= results.size()));
        System.out.println("END CALCULATING");
        System.out.println(results);
        long endTime = System.nanoTime() - startTime;
        double seconds = (double) endTime / 1000000000.0;
        System.out.println(seconds);
        stop();
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + serverPort, e);
        }
    }
}
