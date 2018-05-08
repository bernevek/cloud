package com.bernevek.trim11;

public class InputMessageParser {
    private static final byte CMD_PING_RESPONSE = 2;
    private static final byte CMD_LOGIN_OK_NEW = 6;
    private static final byte CMD_LOGIN_OK = 7;
    private static final byte CMD_MSG_SENT = 16;
    private static final byte CMD_FILE_SENT = 21;
    private static final byte CMD_RECEIVE_MSG_EMPTY = 26;
    private static final byte CMD_RECEIVE_FILE_EMPTY = 31;

    // Errors
    private static final byte SERVER_ERROR = 100;
    private static final byte INCORRECT_CONTENT_SIZE = 101;
    private static final byte SERIALIZATION_ERROR = 102;
    private static final byte INCORRECT_COMMAND = 103;
    private static final byte WRONG_PARAMS = 104;

    private static final byte LOGIN_WRONG_PASSWORD = 110;
    private static final byte LOGIN_FIRST = 112;
    private static final byte FAILED_SENDING = 113;

    public void parseComand(byte[] receivedMessage) {
        if (receivedMessage.length == 1) {
            switch (receivedMessage[0]) {
                case CMD_PING_RESPONSE: {
                    System.out.println("connection ok");
                    break;
                }
                case CMD_LOGIN_OK: {
                    System.out.println("login ok");
                    break;
                }
                case CMD_LOGIN_OK_NEW: {
                    System.out.println("login new ok");
                    break;
                }
                case CMD_MSG_SENT: {
                    System.out.println("message sended");
                    break;
                }
                case CMD_FILE_SENT: {
                    System.out.println("file sended");
                    break;
                }
                case CMD_RECEIVE_MSG_EMPTY: {
                    System.out.println("no message");
                    break;
                }
                case CMD_RECEIVE_FILE_EMPTY: {
                    System.out.println("no file");
                    break;
                }
                case SERVER_ERROR: {
                    System.out.println("server error");
                    break;
                }
                case INCORRECT_CONTENT_SIZE: {
                    System.out.println("incorrect size");
                    break;
                }
                case SERIALIZATION_ERROR: {
                    System.out.println("serialisation");
                    break;
                }
                case INCORRECT_COMMAND: {
                    System.out.println("incorrect command");
                    break;
                }
                case WRONG_PARAMS: {
                    System.out.println("wrong params");
                    break;
                }
                case LOGIN_WRONG_PASSWORD: {
                    System.out.println("wrong password");
                    break;
                }
                case LOGIN_FIRST: {
                    System.out.println("login first");
                    break;
                }
                case FAILED_SENDING: {
                    System.out.println("failed sending");
                    break;
                }
            }
        }
    }
}
