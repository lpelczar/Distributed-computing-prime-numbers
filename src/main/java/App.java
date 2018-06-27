
import models.Range;
import modes.Client;
import modes.Server;

import java.math.BigInteger;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        if(args[0].equals("CLIENT") || args[0].equals("SERVER")) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter min value of range:");
            String min = sc.nextLine();
            System.out.println("Enter max value of range:");
            String max = sc.nextLine();
            Range range = new Range(new BigInteger(min), new BigInteger(max));
            if (args.length == 2) {
                int portNumber = Integer.valueOf(args[1]);
                Server server = new Server(portNumber, range);
                new Thread(server).start();
            } else if (args.length == 3) {
                int portNumber = Integer.valueOf(args[2]);
                String hostName = args[1];
                Client client = new Client(hostName, portNumber );
               new Thread(client).start();

            }else{
                System.out.println("Command signature should look in a following way: java App mode [address] port");
                System.out.println("Mode are: CLIENT or SERVER");
                System.out.println("If mode is CLIENT enter address of SERVER");
            }

        }

    }
}