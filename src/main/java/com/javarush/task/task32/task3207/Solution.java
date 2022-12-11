package com.javarush.task.task32.task3207;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/*
К серверу по RMI
*/

public class Solution {
    public static final String UNIC_BINDING_NAME = "double.string";
    public static Registry registry;

    // Pretend we're starting an RMI client as the CLIENT_THREAD thread
    public static Thread CLIENT_THREAD = new Thread(new Runnable() {
        @Override
        public void run() {
            //напишите тут ваш код
            final Registry registry;
            try {
//                registry = LocateRegistry.getRegistry(2099);
                DoubleString service = (DoubleString) Solution.registry.lookup(UNIC_BINDING_NAME);
                String result = service.doubleString("Yes");
                System.out.println(result);

            } catch (RemoteException e) {
                throw new RuntimeException(e);
            } catch (NotBoundException e) {
                throw new RuntimeException(e);
            }

        }
    });

    public static void main(String[] args) throws InterruptedException, NoSuchObjectException {
        // Pretend we're starting an RMI server as the main thread
        Remote stub = null;
        final DoubleStringImpl service = new DoubleStringImpl();
        try {
            registry = LocateRegistry.createRegistry(2099);

            stub = UnicastRemoteObject.exportObject(service, 0);
            registry.bind(UNIC_BINDING_NAME, stub);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }

        // Start the client
        CLIENT_THREAD.start();
        CLIENT_THREAD.sleep(1000);
        UnicastRemoteObject.unexportObject(service,true);
    }
}
