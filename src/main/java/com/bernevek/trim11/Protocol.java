package com.bernevek.trim11;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
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
    public static final String SERVER_URL = "http://localhost:8080/chat/server/";



    private ConnectionManager connectionManager;
    private javax.ws.rs.client.Client client;
    private String login;
    private String password;

    public Protocol(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.client = connectionManager.getClient();
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
        String response = client.target(SERVER_URL + "echo")
                .request(MediaType.TEXT_PLAIN_TYPE)
                .post(Entity.text(text), String.class);
        System.out.println(response);
    }

    public void ping() {
        String response = client.target(SERVER_URL + "ping")
                .request(MediaType.TEXT_PLAIN_TYPE).get(String.class);
        System.out.println(response);
    }

    public void login(String login, String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        Entity userInfoEntity = Entity.entity(user,
                MediaType.APPLICATION_JSON_TYPE);
        Response response = client.target(SERVER_URL + "user")
                .request()
                .put(userInfoEntity);
        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
            this.client.register(HttpAuthenticationFeature.basic(login, password));
            this.login = login;
            this.password = password;
        } else if (response.getStatus() == Response.Status.ACCEPTED.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
            this.client.register(HttpAuthenticationFeature.basic(login, password));
            this.login = login;
            this.password = password;
        } else if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            System.out.println("Server error");
        }
    }

    public void list() {
        Response response = client.target(SERVER_URL + "users")
                .request(MediaType.APPLICATION_JSON_TYPE).get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            WrappedList users = response.readEntity(WrappedList.class);
            users.items.forEach((user) -> System.out.println(user));
        } else if (response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            System.out.println("Server error");
        }
    }

    public void msg(String destinationUser, String text) {
        Response response =
                client.target(SERVER_URL + destinationUser + "/messages")
                        .request(MediaType.APPLICATION_JSON_TYPE)
                        .post(Entity.text(text));
        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.NOT_ACCEPTABLE.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            System.out.println("Server error");
        }
    }

    public void receiveMsg() {
        Response response = client.target(SERVER_URL + login + "/messages")
                .request(MediaType.APPLICATION_JSON_TYPE).get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            WrappedList messages = response.readEntity(WrappedList.class);
            messages.items.forEach((message) -> {
                getOne(message);
                deleteOne(message);
            });
        } else if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            System.out.println("Server error");
        }
    }

    public void getOne(String messageId) {
        Response response = client.target(SERVER_URL + login + "/messages/" + messageId)
                .request(MediaType.APPLICATION_JSON_TYPE).get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            Message message = response.readEntity(Message.class);
            System.out.println(message.getSender() + " " + message.getMessage());
        } else if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            System.out.println("Server error");
        } else if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        }
    }

    public void deleteOne(String messageId) {
        Response response = client.target(SERVER_URL + login + "/messages/" + messageId)
                .request(MediaType.APPLICATION_JSON_TYPE).delete();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
        } else if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            System.out.println("Server error");
        } else if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        }
    }

    public void file(String destinationUser, String filePath) throws IOException {
        File file = new File(filePath);
        java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
        String fileContent = encoder
                .encodeToString(Files.readAllBytes(file.toPath()));
        FileInfo fileInfo = new FileInfo();
        fileInfo.setSender(login);
        fileInfo.setFilename(file.getName());
        fileInfo.setContent(fileContent);

        Entity fileInfoEntity = Entity.entity(fileInfo,
                MediaType.APPLICATION_JSON_TYPE);
        Response response = client.target(SERVER_URL + destinationUser + "/files")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(fileInfoEntity);
        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.NOT_ACCEPTABLE.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            System.out.println("Server error");
        }
    }

    public void receiveFile() {
//
//        String encodedFileContent = ...;
//        java.util.Base64.Decoder decoder = java.util.Base64.Base64.getDecoder();
//        byte[] decodedContent = decoder.decode(encodedFileContent);
//        FileInfo fileInfo = proxy.receiveFile(sessionId);
//        System.out.println(fileInfo.getSender() + ": " + fileInfo.getFilename());

        Response response = client.target(SERVER_URL + login + "/files")
                .request(MediaType.APPLICATION_JSON_TYPE).get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            WrappedList files = response.readEntity(WrappedList.class);
            files.items.forEach((file) -> {
                getOneFile(file);
                deleteOneFile(file);
            });
        } else if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            System.out.println("Server error");
        }
    }

    public void getOneFile(String messageId) {
        Response response = client.target(SERVER_URL + login + "/files/" + messageId)
                .request(MediaType.APPLICATION_JSON_TYPE).get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            FileInfo fileInfo = response.readEntity(FileInfo.class);
            System.out.println(fileInfo.getSender() + " file " + fileInfo.getFilename());
        } else if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            System.out.println("Server error");
        } else if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        }
    }

    public void deleteOneFile(String messageId) {
        Response response = client.target(SERVER_URL + login + "/files/" + messageId)
                .request(MediaType.APPLICATION_JSON_TYPE).delete();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
        } else if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        } else if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            System.out.println("Server error");
        } else if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            System.out.println(response.readEntity(String.class));
        }
    }

    public void exit() {
        if (client != null)
            client.close();
    }

    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class WrappedList {

        public List<String> items;

        public WrappedList() {
        }

        public WrappedList(List<String> items) {
            this.items = items;
        }

        public WrappedList(String[] items) {
            this.items = new ArrayList<>(Arrays.asList(items));
        }
    }

    @XmlRootElement
    public static class Message {
        private String sender;
        private String message;

        public Message() {
        }

        public Message(String sender, String message) {
            this.sender = sender;
            this.message = message;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    @XmlRootElement
    public static class FileInfo {
        private String sender;
        private String filename;
        private String content;

        public FileInfo() {
        }

        public FileInfo(String sender, String filename, String content) {
            this.sender = sender;
            this.filename = filename;
            this.content = content;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    @XmlRootElement
    public static class User {
        private String login;
        private String password;

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
