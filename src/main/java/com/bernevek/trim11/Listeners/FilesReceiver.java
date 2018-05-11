package com.bernevek.trim11.Listeners;

import lpi.server.mq.FileInfo;

import javax.jms.*;
import java.io.Serializable;

public class FilesReceiver implements MessageListener {

    @SuppressWarnings("unchecked")
    @Override
    public void onMessage(Message message) {
        if (message instanceof ObjectMessage) {
            try {
                Serializable obj = ((ObjectMessage) message).getObject();
                if (obj != null && obj instanceof FileInfo) {
                    FileInfo fileInfo = (FileInfo) obj;
                    System.out.println("New file from " + fileInfo.getSender() + " file name " + fileInfo.getFilename());
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
