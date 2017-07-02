package main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {

    public Server() {}
    int port = 1099;
        private void runServer() {
        try {
            //Basically, don't touch this unless you know what you're doing.
            InterfaceImpl impl = new InterfaceImpl();
            Interface serverSkeleton = (Interface) UnicastRemoteObject.exportObject(impl, port);
            System.out.println("Calculator skeleton created");
            Registry registry = LocateRegistry.createRegistry(port);
            System.out.println("RMI Registry starter ...");
            registry.rebind("main.Interface", serverSkeleton);
            System.out.println("main.Interface skeleton bound ...");
            System.out.println("main.Server running ...");
        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e);
        }
    }
    public static void main(String[] args) {
        new Server().runServer();
    }
}