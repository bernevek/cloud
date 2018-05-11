package com.bernevek.trim11.Listeners;

import javax.jms.*;

public class MessagesReceiver implements MessageListener {

    @SuppressWarnings("unchecked")
    @Override
    public void onMessage(Message message) {
        if (message instanceof MapMessage) {
            try {
                MapMessage mapMsg = (MapMessage) message;
                String sender = mapMsg.getString("sender");
                String inMessage = mapMsg.getString("message");
                System.out.println("New message from " + sender + " - " + inMessage);
            } catch (JMSException e) {
                e.printStackTrace();
            }
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