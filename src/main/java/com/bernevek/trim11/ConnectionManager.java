package com.bernevek.trim11;


import javax.ws.rs.client.Client;

public class ConnectionManager {

    private javax.ws.rs.client.Client client;


    public ConnectionManager() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
    }

    public Client getClient() {
        return client;
    }
}
