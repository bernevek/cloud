package com.bernevek.trim11;

import java.io.IOException;

public class MessageListner extends Thread {

    private ConnectionManager connectionManager;
    private Boolean stop = false;
    private Protocol protocol;

    public MessageListner(ConnectionManager connectionManager, Protocol protocol) {
        this.connectionManager = connectionManager;
        this.protocol = protocol;
    }

    public void close() {
        stop = true;
    }

    @Override
    public void run() {
        byte[] inputArray;
        while (!stop) {
            try {
                inputArray = connectionManager.receive();
                if (inputArray.length == 1) {
                    protocol.parseComand(inputArray);
                } else {
                    protocol.parseAndShowMessage(inputArray);
                }
            } catch (ClassNotFoundException e) {
                System.out.println("Failed to receive messages. Reason: " + e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                close();
            }
        }
    }

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public Boolean getStop() {
        return stop;
    }

    public void setStop(Boolean stop) {
        this.stop = stop;
    }
}
