package lpi.server.rmi;

import java.io.Closeable;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ComputeEngine implements Runnable, Closeable, Compute {

    private int port = 4321;

    private Compute proxy;
    private Registry registry;

    public ComputeEngine(String[] args) {
        if (args.length > 0) {
            try {
                this.port = Integer.parseInt(args[0]);
            } catch (Exception ex) {
            }
        }
    }

    @Override
    public void close() throws IOException {

        if (this.registry != null) {
            try {
                this.registry.unbind(RMI_SERVER_NAME);
            } catch (NotBoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            this.registry = null;
        }

        if (this.proxy != null) {
            UnicastRemoteObject.unexportObject(this, true);
            this.proxy = null;
        }
    }

    @Override
    public void run() {
//		if (System.getSecurityManager() == null) {
//			System.setSecurityManager(new SecurityManager());
//		}
        try {
            this.proxy = (Compute) UnicastRemoteObject.exportObject(this, this.port);
            this.registry = LocateRegistry.createRegistry(this.port);
            this.registry.bind(RMI_SERVER_NAME, this.proxy);
            System.out.printf("The RMI server was started successfully on the port %s%n", this.port);

        } catch (AlreadyBoundException | RemoteException e) {
            throw new RuntimeException("Failed to start server", e);
        }
    }

    @Override
    public String ping() {
        return "Pong";
    }

    @Override
    public String echo(String text) {
        return String.format("ECHO: %s", text);
    }

    @Override
    public <T> T executeTask(Task<T> task) throws ArgumentException, ServerException, RemoteException {
        try {
            if (task == null)
                throw new ArgumentException("msg", "The message has to be specified");
            return task.execute();
        } catch (RemoteException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServerException("Server failed to process your command", ex);
        }
    }
}
