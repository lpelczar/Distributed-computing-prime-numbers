package server;

import models.Range;

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

        List<ObjectOutputStream> outputStreams = new ArrayList<>();
        List<ObjectInputStream> inputStreams = new ArrayList<>();

        for (Socket socket : clients) {
            outputStreams.add(new ObjectOutputStream(socket.getOutputStream()));
            inputStreams.add(new ObjectInputStream(socket.getInputStream()));
        }

        List<Range> ranges = range.divideRanges(2);

        for (int i = 0; i < ranges.size(); i++) {
            outputStreams.get(i).writeObject(ranges.get(i));
        }

        waitForResults(inputStreams);
    }

    private void waitForResults(List<ObjectInputStream> inputStreams) {

        boolean isCalculationFinished = false;

        while (!isCalculationFinished) {

            for (ObjectInputStream ois : inputStreams) {
                ois.readObject();
            }
        }
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
