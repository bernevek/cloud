package com.bernevek.trim11;

import lpi.server.soap.ArgumentFault;
import lpi.server.soap.LoginFault;
import lpi.server.soap.ServerFault;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleParser extends Thread {
    private Boolean stop = false;
    private BufferedReader bufferedReader;
    private String[] command = null;
    private Protocol protocol;

    public ConsoleParser(Protocol protocol) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        this.protocol = protocol;
    }

    @Override
    public void run() {
        while (!stop) {
            System.out.println("Enter comand");
            System.out.println("To show all comands enter \"showAll\"");
            try {
                command = bufferedReader
                        .readLine()
                        .trim()
                        .replaceAll("  ", " ")
                        .split(" ");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                switch (command[0]) {
                    case Protocol.ECHO: {
                        if (command.length == 2) {
                            protocol.echo(command[1]);
                        } else {
                            System.out.println("command is incorrect");
                        }
                        break;
                    }
                    case Protocol.EXIT: {
                        stop = true;
                        protocol.exit();
                        break;
                    }
                    case Protocol.LOGIN: {
                        if (command.length == 3) {
                            protocol.login(command[1], command[2]);
                        } else {
                            System.out.println("command is incorrect");
                        }
                        break;
                    }
                    case Protocol.LIST: {
                        protocol.list();
                        break;
                    }
                    case Protocol.RECEIVE: {
                        if (command[1].equals(Protocol.RECEIVE_MSG.split(" ")[1])) {
                            protocol.receiveMsg();
                        } else {
                            protocol.receiveFile();
                        }
                        break;
                    }
                    case Protocol.SHOW_ALL: {
                        protocol.showAll();
                        break;
                    }
                    case Protocol.PING: {
                        protocol.ping();
                        break;
                    }
                    case Protocol.MSG: {
                        if (command.length == 3) {
                            protocol.msg(command[1], command[2]);
                        } else {
                            System.out.println("command is incorrect");
                        }
                        break;
                    }
                    case Protocol.FILE: {
                        if (command.length == 3) {
                            protocol.file(command[1], command[2]);
                        } else {
                            System.out.println("command is incorrect");
                        }
                        break;
                    }
                    default:
                        System.out.println("command not found");
                }
            } catch (ArgumentFault e) {
                e.getFaultInfo();
            } catch (ServerFault e) {
                e.getFaultInfo();
            } catch (LoginFault e) {
                e.getFaultInfo();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
