package com.bernevek.trim11;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.awt.*;
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

    private static final String PING_QUEUE = "chat.diag.ping";
    private static final String ECHO_QUEUE = "chat.diag.echo";
    private static final String LOGIN_QUEUE = "chat.login";
    private static final String LIST_USERS_QUEUE = "chat.listUsers";
    private static final String SEND_MESSAGE_QUEUE = "chat.sendMessage";
    private static final String MESSAGES_QUEUE = "chat.messages";
    private static final String SEND_FILE_QUEUE = "chat.sendFile";
    private static final String FILES_QUEUE = "chat.files";
    private static final String EXIT_QUEUE = "chat.exit";

    private javax.jms.Destination pingQueue;
    private javax.jms.Destination echoQueue;
    private javax.jms.Destination loginQueue;
    private javax.jms.Destination listUsersQueue;
    private javax.jms.Destination sendMessageQueue;
    private javax.jms.Destination messagesQueue;
    private javax.jms.Destination sendFileQueue;
    private javax.jms.Destination filesQueue;
    private javax.jms.Destination exitQueue;

    private javax.jms.MessageProducer pingProducer;
    private javax.jms.MessageProducer echoProducer;
    private javax.jms.MessageProducer loginProducer;
    private javax.jms.MessageProducer listUsersProducer;
    private javax.jms.MessageProducer sendMessageProducer;
    private javax.jms.MessageProducer sendFileProducer;
    private javax.jms.MessageProducer exitProducer;

    private javax.jms.MessageConsumer messagesConsumer;
    private javax.jms.MessageConsumer filesConsumer;



    private ConnectionManager connectionManager;
    private Session session;
    private String login;
    private String password;

    public Protocol(ConnectionManager connectionManager) throws JMSException {
        this.connectionManager = connectionManager;
        this.session = connectionManager.getSession();
        createQueues(session);
        creareProducersAndConsumers(session);
    }

    public void createQueues(Session session) throws JMSException {
        pingQueue = session.createQueue(PING_QUEUE);
        echoQueue = session.createQueue(ECHO_QUEUE);
        loginQueue = session.createQueue(LOGIN_QUEUE);
        listUsersQueue = session.createQueue(LIST_USERS_QUEUE);
        sendMessageQueue = session.createQueue(SEND_MESSAGE_QUEUE);
        messagesQueue = session.createQueue(MESSAGES_QUEUE);
        sendFileQueue = session.createQueue(SEND_FILE_QUEUE);
        filesQueue = session.createQueue(FILES_QUEUE);
        exitQueue = session.createQueue(EXIT_QUEUE);
    }

    public void creareProducersAndConsumers(Session session) throws JMSException {

        pingProducer = session.createProducer(pingQueue);
        echoProducer = session.createProducer(echoQueue);
        loginProducer = session.createProducer(loginQueue);
        listUsersProducer = session.createProducer(listUsersQueue);
        sendMessageProducer = session.createProducer(sendMessageQueue);
        sendFileProducer = session.createProducer(sendFileQueue);
        exitProducer = session.createProducer(exitQueue);


        messagesConsumer = session.createConsumer(messagesQueue);
        filesConsumer = session.createConsumer(filesQueue);
        messagesConsumer.setMessageListener(new MessageReceiver());

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

    public void echo(String text) throws JMSException {
        TextMessage textMessage = session.createTextMessage(text);
        textMessage.setJMSReplyTo(messagesQueue);
        echoProducer.send(textMessage);
    }

    public void ping() throws JMSException {
        Message message = session.createMessage();
        message.setJMSReplyTo(messagesQueue);
        pingProducer.send(message);
    }

    public void login(String login, String password) {
//        User user = new User();
//        user.setLogin(login);
//        user.setPassword(password);
//        Entity userInfoEntity = Entity.entity(user,
//                MediaType.APPLICATION_JSON_TYPE);
//        Response response = client.target(SERVER_URL + "user")
//                .request()
//                .put(userInfoEntity);
//        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//            this.client.register(HttpAuthenticationFeature.basic(login, password));
//            this.login = login;
//            this.password = password;
//        } else if (response.getStatus() == Response.Status.ACCEPTED.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//            this.client.register(HttpAuthenticationFeature.basic(login, password));
//            this.login = login;
//            this.password = password;
//        } else if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
//            System.out.println("Server error");
//        }
    }

    public void list() {
//        Response response = client.target(SERVER_URL + "users")
//                .request(MediaType.APPLICATION_JSON_TYPE).get();
//        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
//            WrappedList users = response.readEntity(WrappedList.class);
//            users.items.forEach((user) -> System.out.println(user));
//        } else if (response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
//            System.out.println("Server error");
//        }
    }

    public void msg(String destinationUser, String text) {
//        Response response =
//                client.target(SERVER_URL + destinationUser + "/messages")
//                        .request(MediaType.APPLICATION_JSON_TYPE)
//                        .post(Entity.text(text));
//        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.NOT_ACCEPTABLE.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
//            System.out.println("Server error");
//        }
    }

    public void receiveMsg() {
//        Response response = client.target(SERVER_URL + login + "/messages")
//                .request(MediaType.APPLICATION_JSON_TYPE).get();
//        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
//            WrappedList messages = response.readEntity(WrappedList.class);
//            messages.items.forEach((message) -> {
//                getOne(message);
//                deleteOne(message);
//            });
//        } else if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
//            System.out.println("Server error");
//        }
    }

    public void getOne(String messageId) {
//        Response response = client.target(SERVER_URL + login + "/messages/" + messageId)
//                .request(MediaType.APPLICATION_JSON_TYPE).get();
//        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
//            Message message = response.readEntity(Message.class);
//            System.out.println(message.getSender() + " " + message.getMessage());
//        } else if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
//            System.out.println("Server error");
//        } else if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        }
    }

    public void deleteOne(String messageId) {
//        Response response = client.target(SERVER_URL + login + "/messages/" + messageId)
//                .request(MediaType.APPLICATION_JSON_TYPE).delete();
//        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
//        } else if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
//            System.out.println("Server error");
//        } else if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        }
    }

    public void file(String destinationUser, String filePath) throws IOException {
//        File file = new File(filePath);
//        java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
//        String fileContent = encoder
//                .encodeToString(Files.readAllBytes(file.toPath()));
//        FileInfo fileInfo = new FileInfo();
//        fileInfo.setSender(login);
//        fileInfo.setFilename(file.getName());
//        fileInfo.setContent(fileContent);
//
//        Entity fileInfoEntity = Entity.entity(fileInfo,
//                MediaType.APPLICATION_JSON_TYPE);
//        Response response = client.target(SERVER_URL + destinationUser + "/files")
//                .request(MediaType.APPLICATION_JSON_TYPE)
//                .post(fileInfoEntity);
//        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.NOT_ACCEPTABLE.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
//            System.out.println("Server error");
//        }
    }

    public void receiveFile() {
//
//        String encodedFileContent = ...;
//        java.util.Base64.Decoder decoder = java.util.Base64.Base64.getDecoder();
//        byte[] decodedContent = decoder.decode(encodedFileContent);
//        FileInfo fileInfo = proxy.receiveFile(sessionId);
//        System.out.println(fileInfo.getSender() + ": " + fileInfo.getFilename());

//        Response response = client.target(SERVER_URL + login + "/files")
//                .request(MediaType.APPLICATION_JSON_TYPE).get();
//        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
//            WrappedList files = response.readEntity(WrappedList.class);
//            files.items.forEach((file) -> {
//                getOneFile(file);
//                deleteOneFile(file);
//            });
//        } else if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
//            System.out.println("Server error");
//        }
    }

    public void getOneFile(String messageId) {
//        Response response = client.target(SERVER_URL + login + "/files/" + messageId)
//                .request(MediaType.APPLICATION_JSON_TYPE).get();
//        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
//            FileInfo fileInfo = response.readEntity(FileInfo.class);
//            System.out.println(fileInfo.getSender() + " file " + fileInfo.getFilename());
//        } else if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
//            System.out.println("Server error");
//        } else if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        }
    }

    public void deleteOneFile(String messageId) {
//        Response response = client.target(SERVER_URL + login + "/files/" + messageId)
//                .request(MediaType.APPLICATION_JSON_TYPE).delete();
//        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
//        } else if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        } else if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
//            System.out.println("Server error");
//        } else if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
//            System.out.println(response.readEntity(String.class));
//        }
    }

    public void exit() throws JMSException {
        if (connectionManager != null)
            connectionManager.close();
    }
}
