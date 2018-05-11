package com.bernevek.trim11;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

public class ConnectionManager {

    private static org.apache.activemq.ActiveMQConnectionFactory connectionFactory
            = new org.apache.activemq.ActiveMQConnectionFactory("tcp://localhost:61616");;
    private javax.jms.Connection connection;
    private javax.jms.Session session;

    public ConnectionManager() throws JMSException {
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
    }

    public void close() throws JMSException {
        session.close();
        connection.close();
    }

    public ActiveMQConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public Connection getConnection() {
        return connection;
    }

    public Session getSession() {
        return session;
    }
}
