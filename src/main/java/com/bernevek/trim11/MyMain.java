package com.bernevek.trim11;

import java.io.IOException;
import java.rmi.NotBoundException;

/**
 * Hello world!
 *
 * @author ivan
 */
public class MyMain {
    public static void main(String[] args) throws IOException, NotBoundException {
        ConnectionManager connectionManager = new ConnectionManager();
        Protocol protocol = new Protocol(connectionManager);
        ConsoleParser consoleParser = new ConsoleParser(protocol);
        consoleParser.start();
    }
}
