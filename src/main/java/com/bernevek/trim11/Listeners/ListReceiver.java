package com.bernevek.trim11.Listeners;

import javax.jms.*;
import java.io.Serializable;

public class ListReceiver implements MessageListener {

    @SuppressWarnings("unchecked")
    @Override
    public void onMessage(Message message) {
        if (message instanceof ObjectMessage) {
            try {
                Serializable obj = ((ObjectMessage) message).getObject();
                if (obj != null && obj instanceof String[]) {
                    String[] users = (String[]) obj;
                    for (String user : users) {
                        System.out.println(user);
                    }
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        } else if (message instanceof MapMessage) {
            System.out.println("On processing errors");
        } else if (message instanceof TextMessage) {
            TextMessage txtMsg = (TextMessage) message;
            try {
                System.out.println("On unexpected input message");
                System.out.println(txtMsg.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
