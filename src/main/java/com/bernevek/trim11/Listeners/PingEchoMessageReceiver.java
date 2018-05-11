package com.bernevek.trim11.Listeners;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class PingEchoMessageReceiver implements MessageListener {

    @SuppressWarnings("unchecked")
    @Override
    public void onMessage(Message message) {
        if(message instanceof TextMessage) {
            TextMessage txtMsg = (TextMessage) message;
            try {
                System.out.println(txtMsg.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        } else if(message instanceof Message) {
            System.out.println("Ok");
        }
    }
}
