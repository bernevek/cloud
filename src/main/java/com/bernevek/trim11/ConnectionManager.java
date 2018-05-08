package com.bernevek.trim11;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectionManager {

    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private String serverIp;
    private Integer serverPort;

    public ConnectionManager(String serverIp, Integer serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    public void connect() throws IOException {
        socket = new Socket(serverIp, serverPort);
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    public void send(byte[] requestBody) throws IOException {
        dataOutputStream.writeInt(requestBody.length);
        dataOutputStream.write(requestBody);
        dataOutputStream.flush();
    }

    public byte[] receive() throws IOException {
        int responseLength = dataInputStream.readInt();
        byte[] responseBody = new byte[responseLength];
        dataInputStream.readFully(responseBody);
        return responseBody;
    }

    public void close() {
        try {
            dataInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
