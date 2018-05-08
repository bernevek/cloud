package com.bernevek.trim11;

import lpi.server.rmi.IServer;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Arrays;

/**
 * Created by ivan on 24.02.18.
 */
public class Protocol {

    public static final String SHOW_ALL = "showAll";
    public static final String ECHO = "echo";
    public static final String PING = "ping";
    public static final String LOGIN = "login";
    public static final String LIST = "list";
    public static final String MSG = "msg";
    public static final String FILE = "file";
    public static final String EXIT = "exit";
    public static final String RECEIVE = "receive";
    public static final String RECEIVE_MSG = "receive msg";
    public static final String RECEIVE_FILE = "receive file";



    private ConnectionManager connectionManager;
    private IServer proxy;
    private String sessionId;

    public Protocol(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.proxy = connectionManager.getProxy();
    }

    public void showAll() {
        System.out.println(Protocol.ECHO);
        System.out.println(Protocol.EXIT);
        System.out.println(Protocol.FILE);
        System.out.println(Protocol.LIST);
        System.out.println(Protocol.LOGIN);
        System.out.println(Protocol.MSG);
        System.out.println(Protocol.PING);
        System.out.println(Protocol.RECEIVE_MSG);
        System.out.println(Protocol.RECEIVE_FILE);
    }

    public void echo(String text) throws RemoteException {
        String response = proxy.echo(text);
        System.out.println(response);
    }

    public void ping() throws IOException {
        proxy.ping();
    }

    public void login(String login, String password) throws IOException {
        sessionId = proxy.login(login, password);
        System.out.println("login ok");
    }

    public void list() throws IOException {
        String[] users = proxy.listUsers(sessionId);
        System.out.println(Arrays.toString(users));
    }

    public void msg(String destinationUser, String message) throws IOException {
        proxy.sendMessage(sessionId, new IServer.Message(destinationUser, message));
        System.out.println("message sended ok");
    }

    public void file(String destinationUser, String filePath) throws IOException {
        File file = new File(filePath);
        proxy.sendFile(sessionId, new IServer.FileInfo(destinationUser, file));
        System.out.println("file sended ok");
    }

    public void receiveMsg() throws IOException {
        IServer.Message message = proxy.receiveMessage(sessionId);
        System.out.println(message.getSender() + ": " + message.getMessage());
    }

    public void receiveFile() throws IOException {
        IServer.FileInfo fileInfo = proxy.receiveFile(sessionId);
        System.out.println(fileInfo.getSender() + ": " + fileInfo.getFilename());
    }

    public void exit() throws IOException {
        proxy.exit(sessionId);
    }
}
