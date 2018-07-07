package modes;

import models.ClientData;
import models.Range;
import models.Result;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
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
            System.out.println("Waiting for clients!");
            int counter = 1;
            while (clients.size() < 3) {

                Socket clientSocket;
                try {
                    clientSocket = this.serverSocket.accept();
                    clients.add(clientSocket);
                    System.out.println("Client " + counter + " has connected!");
                    counter++;
                } catch (IOException e) {
                    if (isStopped()) {
                        System.out.println("Server Stopped.");
                        return;
                    }
                    throw new RuntimeException("Error accepting client connection", e);
                }

            }
            try {
                System.out.println("Calculating...");
                start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        System.out.println("DURATION TIME: " + (System.nanoTime() - startTime) / 1000000000.0);
        System.out.println("Server Stopped.");

    }

    private void start() throws IOException {
        startTime = System.nanoTime();
        List<ClientData> clientsData = getClientsData();

        List<Range> ranges = range.divideRanges(clientsData.size());

        Map<ClientData, Range> activeTasks = new HashMap<>();
        for (int i = 0; i < ranges.size(); i++) {
            activeTasks.put(clientsData.get(i), ranges.get(i));
        }
        List<Result> results = getResults(activeTasks);
        System.out.println(results);
        stop();

}

    private List<Result> getResults(Map<ClientData, Range> activeTasks) throws IOException{
        List<ClientData> ready = new ArrayList<>();
        List<Result> results = new ArrayList<>();
        do {
            for(Entry<ClientData, Range> task : activeTasks.entrySet()){
                try {
                    task.getKey().getObjectOutputStream().writeObject(task.getValue());
                    results.add(waitForResults(task.getKey()));
                    ready.add(task.getKey());
                    task.getKey().getObjectOutputStream().reset();

                }catch(IllegalStateException | SocketException e){

                    if(ready.size() != 0){
                        ready.get(0).getObjectOutputStream().writeObject(task.getValue());
                        results.add(waitForResults(ready.get(0)));
                    }
                }

            }
        }while(!(activeTasks.size() <= results.size()));

        return results;
    }

    private List<ClientData> getClientsData() throws IOException{
        List<ClientData> clientDataList = new ArrayList<>();
        int counter = 1;
        for (Socket socket : clients) {
            System.out.println("Getting client " + counter + " stream!");
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ClientData clientData = new ClientData(ois, oos);
            clientDataList.add(clientData);
            counter++;
        }
        return clientDataList;
    }


    private Result waitForResults(ClientData clientData) {


        try {
            Result result = (Result) clientData.getObjectInputStream().readObject();
            System.out.println("Client has finished his work");
            return result;

        } catch (ClassNotFoundException | IOException e) {
            System.out.println(clientData.getObjectOutputStream());
            throw new IllegalStateException("Client has disconnected");
        }
    }




    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    private synchronized void stop(){
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
