package com.bernevek.trim11;

import java.io.IOException;

/**
 * Hello world!
 *
 * @author ivan
 */
public class MyMain {
    public static void main(String[] args) throws IOException {
        ConnectionManager connectionManager = new ConnectionManager("localhost", 4321);
        connectionManager.connect();
        Protocol protocol = new Protocol(connectionManager);
        MessageListner messageListner = new MessageListner(connectionManager, protocol);
        ConsoleParser consoleParser = new ConsoleParser(protocol, messageListner, connectionManager);
        consoleParser.start();
        messageListner.start();

    }
}
