package com.bernevek.trim11;

import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
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


    private static final byte CMD_PING = 1;
    private static final byte CMD_ECHO = 3;
    private static final byte CMD_LOGIN = 5;
    private static final byte CMD_LIST = 10;
    private static final byte CMD_MSG = 15;
    private static final byte CMD_FILE = 20;
    private static final byte CMD_RECEIVE_MSG = 25;
    private static final byte CMD_RECEIVE_FILE = 30;

    private ConnectionManager connectionManager;
    private InputMessageParser inputMessageParser;
    private byte executedComand;

    public Protocol(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.inputMessageParser = new InputMessageParser();
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

    public void echo(String text) throws IOException {
        executedComand = CMD_ECHO;
        connectionManager.send(ArrayUtils.addAll(new byte[]{CMD_ECHO}, text.getBytes()));
    }

    public void ping() throws IOException {
        connectionManager.send(new byte[]{CMD_PING});
    }

    public void login(String login, String password) throws IOException {
        String[] loginData = {login, password};
        connectionManager.send(ArrayUtils.addAll(new byte[]{CMD_LOGIN}, serialize(loginData)));
    }

    public void list() throws IOException {
        executedComand = CMD_LIST;
        connectionManager.send(new byte[]{CMD_LIST});
    }

    public void msg(String destinationUser, String message) throws IOException {
        String[] messageData = {destinationUser, message};
        connectionManager.send(ArrayUtils.addAll(new byte[]{CMD_MSG}, serialize(messageData)));
    }

    public void file(String destinationUser, String filePath) throws IOException {
        byte[] file;
        try (FileInputStream stream = new FileInputStream(filePath)) {
            file = new byte[stream.available()];
            stream.read(file);
        } catch (FileNotFoundException e) {
            throw new IOException("File Not Found");
        }
        String[] pathArray = filePath.split("/");
        Object[] fileData = {destinationUser, pathArray[pathArray.length - 1], file};
        connectionManager.send(ArrayUtils.addAll(new byte[]{CMD_FILE}, serialize(fileData)));
    }

    public void receiveMsg() throws IOException {
        executedComand = CMD_RECEIVE_MSG;
        connectionManager.send(new byte[]{CMD_RECEIVE_MSG});
    }

    public void receiveFile() throws IOException {
        executedComand = CMD_RECEIVE_FILE;
        connectionManager.send(new byte[]{CMD_RECEIVE_FILE});
    }

    public void parseComand(byte[] receivedMessage) {
        inputMessageParser.parseComand(receivedMessage);
    }

    private byte[] serialize(Object object) throws IOException {
        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
             ObjectOutputStream objectStream = new ObjectOutputStream(byteStream)) {
            objectStream.writeObject(object);
            return byteStream.toByteArray();
        }
    }

    private <T> T deserialize(byte[] data, int offset, Class<T> clazz) throws ClassNotFoundException, IOException {
        try (ByteArrayInputStream stream = new ByteArrayInputStream(data, offset, data.length - offset);
             ObjectInputStream objectStream = new ObjectInputStream(stream)) {
            return (T) objectStream.readObject();
        }
    }

    public void parseAndShowMessage(byte[] inputArray) throws ClassNotFoundException {
        try {
            switch (executedComand) {
                case Protocol.CMD_ECHO: {
                    System.out.println(new String(inputArray));
                    break;
                }
                case Protocol.CMD_LIST: {
                    System.out.println("List");
                    System.out.println(Arrays.toString(deserialize(inputArray, 0, String[].class)));
                    break;
                }
                case Protocol.CMD_RECEIVE_MSG: {
                    System.out.println("Message");
                    System.out.println(Arrays.toString(deserialize(inputArray, 0, String[].class)));
                    break;
                }
                case Protocol.CMD_RECEIVE_FILE: {
                    System.out.println("File");
                    System.out.println(Arrays.toString(deserialize(inputArray, 0, String[].class)));
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setExecutedComand(byte executedComand) {
        this.executedComand = executedComand;
    }
}
