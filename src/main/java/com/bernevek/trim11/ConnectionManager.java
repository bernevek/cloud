package com.bernevek.trim11;

import lpi.server.rmi.IServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ConnectionManager {

    private Registry registry;
    private IServer proxy;
    private String serverIp;
    private Integer serverPort;

    public ConnectionManager(String serverIp, Integer serverPort) throws RemoteException, NotBoundException {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        registry = LocateRegistry.getRegistry(serverIp, serverPort);
        proxy = (IServer) registry.lookup(IServer.RMI_SERVER_NAME);
    }

    public Registry getRegistry() {
        return registry;
    }

    public IServer getProxy() {
        return proxy;
    }
}
