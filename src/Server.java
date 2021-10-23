//start rmiregistry -J-Djava.rmi.server.codebase=file:///c:/Users/zusma/IdeaProjects/Lab3/out/production/lab3/

import gorodagame.Gamer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Server {
    public Server() {
    }

    public static void main(String[] argv) throws Exception {

        System.out.println("Initializing BillingService...");
        AdditionImplementation impl = new AdditionImplementation();
        AdditionInterface stub = (AdditionInterface) UnicastRemoteObject.exportObject(impl, 0);
        Registry registry = LocateRegistry.getRegistry();
        registry.bind("Addition", stub);
        System.err.println("Server ready");

    }

}