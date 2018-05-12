package com.bernevek.trim11;

import lpi.server.rmi.Compute;
import lpi.server.rmi.ShakerSort;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * Created by ivan on 24.02.18.
 */
public class Protocol {

    public static final String SHOW_ALL = "showAll";
    public static final String ECHO = "echo";
    public static final String PING = "ping";
    public static final String PROCESS = "process";
    public static final String EXIT = "exit";


    private ConnectionManager connectionManager;
    private Compute proxy;

    public Protocol(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.proxy = connectionManager.getProxy();
    }

    public void showAll() {
        System.out.println(Protocol.PING);
        System.out.println(Protocol.ECHO);
        System.out.println(Protocol.PROCESS);
        System.out.println(Protocol.EXIT);
    }

    public void echo(String text) throws RemoteException {
        String response = proxy.echo(text);
        System.out.println(response);
    }

    public void ping() throws IOException {
        String response = proxy.ping();
        System.out.println(response);
    }

    public void process(String inputFilePath, String outputFilePath) throws IOException {
        ShakerSort task = new ShakerSort();
        proxy.executeTask(task);
        System.out.println("process ok");
    }
}
