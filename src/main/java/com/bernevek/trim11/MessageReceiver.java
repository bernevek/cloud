package com.bernevek.trim11;

import javax.jms.*;

class MessageReceiver implements MessageListener {
    @SuppressWarnings("unchecked")
    @Override
    public void onMessage(Message message) {
        System.out.println("00000000000000000000000000000000");
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
