package com.bernevek.trim11;

import com.bernevek.trim11.Listeners.*;
import lpi.server.mq.FileInfo;

import javax.jms.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Base64.Encoder;


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

    MessageListener pingEchoMessageReceiver;
    MessageListener messagesReceiver;
    MessageListener filesReceiver;
    MessageListener loginReceiver;
    MessageListener listReceiver;
    MessageListener sendMessage;
    MessageListener sendFile;
    private Destination messagesQueue;
    private Destination inputTemporaryQueue;
    private Destination filesQueue;
    private javax.jms.MessageProducer producer;
    private MessageConsumer messagesConsumer;
    private MessageConsumer filesConsumer;
    private MessageConsumer inputConsumer;


    private ConnectionManager connectionManager;
    private Session session;
    private String login;
    private String password;

    public Protocol(ConnectionManager connectionManager) throws JMSException {
        this.connectionManager = connectionManager;
        this.session = connectionManager.getSession();
        createQueuesAndListeners(session);
        creareProducersAndConsumers(session);
    }

    public void createQueuesAndListeners(Session session) throws JMSException {
        inputTemporaryQueue = session.createTemporaryQueue();

        pingEchoMessageReceiver = new PingEchoMessageReceiver();
        messagesReceiver = new MessagesReceiver();
        filesReceiver = new FilesReceiver();
        loginReceiver = new LoginReceiver();
        listReceiver = new ListReceiver();
        sendMessage = new SendMessage();
        sendFile = new SendFile();
    }

    public void creareProducersAndConsumers(Session session) throws JMSException {

        messagesConsumer = session.createConsumer(session.createQueue(MESSAGES_QUEUE));
        filesConsumer = session.createConsumer(session.createQueue(FILES_QUEUE));
        inputConsumer = session.createConsumer(inputTemporaryQueue);
        messagesConsumer.setMessageListener(messagesReceiver);
        filesConsumer.setMessageListener(filesReceiver);

    }

    public void showAll() {
        System.out.println(Protocol.ECHO);
        System.out.println(Protocol.EXIT);
        System.out.println(Protocol.FILE);
        System.out.println(Protocol.LIST);
        System.out.println(Protocol.LOGIN);
        System.out.println(Protocol.MSG);
        System.out.println(Protocol.PING);
    }

    public void echo(String text) throws JMSException {
        producer = session.createProducer(session.createQueue(ECHO_QUEUE));
        inputConsumer.setMessageListener(pingEchoMessageReceiver);
        TextMessage textMessage = session.createTextMessage(text);
        textMessage.setJMSReplyTo(inputTemporaryQueue);
        producer.send(textMessage);
        producer.close();
    }

    public void ping() throws JMSException {
        producer = session.createProducer(session.createQueue(PING_QUEUE));
        inputConsumer.setMessageListener(pingEchoMessageReceiver);
        Message message = session.createMessage();
        message.setJMSReplyTo(inputTemporaryQueue);
        producer.send(message);
        producer.close();
    }

    public void login(String login, String password) throws JMSException {
        producer = session.createProducer(session.createQueue(LOGIN_QUEUE));
        inputConsumer.setMessageListener(loginReceiver);
        MapMessage request = session.createMapMessage();
        request.setString("login", login);
        request.setString("password", password);
        request.setJMSReplyTo(inputTemporaryQueue);
        producer.send(request);
        producer.close();
    }

    public void list() throws JMSException {
        producer = session.createProducer(session.createQueue(LIST_USERS_QUEUE));
        inputConsumer.setMessageListener(listReceiver);
        Message message = session.createMessage();
        message.setJMSReplyTo(inputTemporaryQueue);
        producer.send(message);
        producer.close();
    }

    public void msg(String destinationUser, String text) throws JMSException {
        producer = session.createProducer(session.createQueue(SEND_MESSAGE_QUEUE));
        inputConsumer.setMessageListener(sendMessage);
        MapMessage message = session.createMapMessage();
        message.setString("receiver", destinationUser);
        message.setString("message", text);
        message.setJMSReplyTo(inputTemporaryQueue);
        producer.send(message);
        producer.close();
    }

    public void file(String destinationUser, String filePath) throws JMSException, IOException {
        producer = session.createProducer(session.createQueue(SEND_FILE_QUEUE));
        inputConsumer.setMessageListener(sendFile);
        File file = new File(filePath);
        Encoder encoder = Base64.getEncoder();
        byte[] fileContent = encoder
                .encode(Files.readAllBytes(file.toPath()));
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileContent(fileContent);
        fileInfo.setSender(login);
        fileInfo.setReceiver(destinationUser);
        fileInfo.setFilename(file.getName());
        ObjectMessage message = session.createObjectMessage(fileInfo);
        message.setJMSReplyTo(inputTemporaryQueue);
        producer.send(message);
        producer.close();
    }



    public void exit() throws JMSException {
        producer = session.createProducer(session.createQueue(EXIT_QUEUE));
        inputConsumer.setMessageListener(pingEchoMessageReceiver);
        Message message = session.createMessage();
        message.setJMSReplyTo(inputTemporaryQueue);
        producer.send(message);
        producer.close();
        if (connectionManager != null)
            connectionManager.close();
    }
}
