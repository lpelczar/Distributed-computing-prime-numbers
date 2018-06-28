package modes;

import models.ClientData;
import models.Range;
import models.Result;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
//        List<ObjectOutputStream> outputStreams = new ArrayList<>();
//        List<ObjectInputStream> inputStreams = new ArrayList<>();
        List<ClientData> clientsData = new ArrayList<>();

        for (Socket socket : clients) {
            System.out.println("Getting streams!");
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//            outputStreams.add(oos);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
//            inputStreams.add(ois);
            ClientData clientData = new ClientData(ois, oos);
            clientsData.add(clientData);
        }

        List<Range> ranges = range.divideRanges(3);
        Map<ClientData, Range> activeTasks = new HashMap<>();
        for (int i = 0; i < ranges.size(); i++) {
            activeTasks.put(clientsData.get(i), ranges.get(i));
        }
        List<Result> results = new ArrayList<>();
        do {
            for(Entry<ClientData, Range> task : activeTasks.entrySet()){


                task.getKey().getObjectOutputStream().writeObject(task.getValue());
                results.add(waitForResults(task.getKey()));

            }
        }while(!(clientsData.size() <= results.size()));

        System.out.println(results);
        stop();

}



    private Result waitForResults(ClientData clientData) {


        try {
            Result result = (Result) clientData.getObjectInputStream().readObject();
            System.out.println("Client end work");
            return result;

        } catch (ClassNotFoundException | IOException e) {
            throw new IllegalStateException("Client has disconnected");
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
