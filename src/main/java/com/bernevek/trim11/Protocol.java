package com.bernevek.trim11;

import lpi.server.soap.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

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
    private IChatServer proxy;
    private String sessionId;

    public Protocol(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.proxy = connectionManager.getServerProxy();
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

    public void echo(String text) {
        String response = proxy.echo(text);
        System.out.println(response);
    }

    public void ping() {
        proxy.ping();
    }

    public void login(String login, String password) throws ArgumentFault, LoginFault, ServerFault {
        sessionId = proxy.login(login, password);
        System.out.println("login ok");
    }

    public void list() throws ArgumentFault, ServerFault {
        List<String> users = proxy.listUsers(sessionId);
        users.forEach((user) -> System.out.println(user));
    }

    public void msg(String destinationUser, String text) throws ArgumentFault, ServerFault {
        Message message = new Message();
        message.setReceiver(destinationUser);
        message.setMessage(text);
        proxy.sendMessage(sessionId, message);
        System.out.println("message sended ok");
    }

    public void file(String destinationUser, String filePath) throws ArgumentFault, ServerFault, IOException {
        File file = new File(filePath);
        FileInfo fileInfo = new FileInfo();
        fileInfo.setReceiver(destinationUser);
        fileInfo.setFilename(file.getName());
        fileInfo.setFileContent(Files.readAllBytes(file.toPath()));
        proxy.sendFile(sessionId, fileInfo);
        System.out.println("file sended ok");
    }

    public void receiveMsg() throws ArgumentFault, ServerFault {
        Message message = proxy.receiveMessage(sessionId);
        System.out.println(message.getSender() + ": " + message.getMessage());
    }

    public void receiveFile() throws ArgumentFault, ServerFault {
        FileInfo fileInfo = proxy.receiveFile(sessionId);
        System.out.println(fileInfo.getSender() + ": " + fileInfo.getFilename());
    }

    public void exit() throws ArgumentFault, ServerFault {
        proxy.exit(sessionId);
    }
}
