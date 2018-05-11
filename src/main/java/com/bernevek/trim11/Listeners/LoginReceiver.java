package com.bernevek.trim11.Listeners;

import javax.jms.*;

public class LoginReceiver implements MessageListener {

    @SuppressWarnings("unchecked")
    @Override
    public void onMessage(Message message) {
        if (message instanceof MapMessage) {
            MapMessage mapMsg = (MapMessage) message;
            try {
                if (mapMsg.getBoolean("success")) {
                    try {
                        System.out.println(mapMsg.getString("message"));
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                } else if (message instanceof Message) {
                    try {
                        System.out.println(mapMsg.getString("message"));
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        } else if (message instanceof TextMessage) {
            TextMessage txtMsg = (TextMessage) message;
            try {
                System.out.println(txtMsg.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}