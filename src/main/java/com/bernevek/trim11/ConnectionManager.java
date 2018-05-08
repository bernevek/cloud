package com.bernevek.trim11;

import lpi.server.soap.ChatServer;
import lpi.server.soap.IChatServer;

public class ConnectionManager {

    private ChatServer serverWrapper;
    private IChatServer serverProxy;


    public ConnectionManager() {
        serverWrapper = new ChatServer();
        serverProxy = serverWrapper.getChatServerProxy();
    }

    public ChatServer getServerWrapper() {
        return serverWrapper;
    }

    public IChatServer getServerProxy() {
        return serverProxy;
    }
}
